package kz.dara.gbdulNewService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import kz.dara.gbdulNewService.generated.Request;
import kz.dara.gbdulNewService.generated.ResponseDataType;
import kz.dara.gbdulNewService.sync.types.SenderInfo;
import kz.dara.gbdulNewService.sync.v10.interfaces.ISyncChannel;
import kz.dara.gbdulNewService.sync.v10.interfaces.ISyncChannelHttpService;
import kz.dara.gbdulNewService.sync.v10.types.ObjectFactory;
import kz.dara.gbdulNewService.sync.v10.types.SyncMessageInfo;
import kz.dara.gbdulNewService.sync.v10.types.request.RequestData;
import kz.dara.gbdulNewService.sync.v10.types.request.SyncSendMessageRequest;
import kz.dara.gbdulNewService.sync.v10.types.response.SyncSendMessageResponse;
import kz.dara.gbdulNewService.util.Common;
import kz.dara.gbdulNewService.util.DateUtils;
import kz.dara.gbdulNewService.util.JaxBSerializer;
import kz.dara.gbdulNewService.util.WSSignHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static kz.dara.gbdulNewService.util.SoapMessageFactory.signXML;


@Service
@Slf4j
@RequiredArgsConstructor
public class ULService {

    @Value("${kz.shep.url}")
    private String shepUrl;

    @Value("${kz.shep.username}")
    private String username;

    @Value("${kz.shep.password}")
    private String password;

    @Value("${kz.shep.certPath}")
    private String signFilePath;

    @Value("${kz.shep.certPassword}")
    private String signPassword;

    @Value("${kz.shep.bin}")
    private String requestBIN;

    private final String SERVICE_ID = "GbdulInfoByBin_v2";

    public Object getLaborRequest(String bin) throws Exception {

        Request requestDataType = new kz.dara.gbdulNewService.generated.ObjectFactory().createRequest();

        requestDataType.setBIN(bin);
        requestDataType.setRequestorBIN(requestBIN);

        JAXBElement<Request> requestJAXBElement = new kz.dara.gbdulNewService.generated.ObjectFactory().createRequest(requestDataType);

        String signature = signXML(signFilePath, signPassword, requestJAXBElement, "ns3:Request");

        RequestData requestData = new RequestData();
        requestData.setData(signature);

        SyncSendMessageRequest syncSendMessageRequest = createShepRequest();
        syncSendMessageRequest.getRequestInfo().setServiceId(SERVICE_ID);
        syncSendMessageRequest.setRequestData(requestData);
        return sendSyncShepRequest(syncSendMessageRequest);

    }
    public SyncSendMessageRequest createShepRequest() throws DatatypeConfigurationException {
        SyncSendMessageRequest request = new SyncSendMessageRequest();

        request.setRequestData(new RequestData());
        request.setRequestInfo(new SyncMessageInfo());

        request.getRequestInfo().setMessageId(UUID.randomUUID().toString());
        request.getRequestInfo().setMessageDate(DateUtils.toXMLGregorianCalendar(new Date()));
        request.getRequestInfo().setSender(new SenderInfo());
        request.getRequestInfo().getSender().setSenderId(username);
        request.getRequestInfo().getSender().setPassword(password);
        request.getRequestInfo().setSessionId(UUID.randomUUID().toString());

        return request;
    }

    public SyncSendMessageResponse sendSyncShepRequest(SyncSendMessageRequest syncSendMessageRequest) throws Exception {
        WSSignHandler wsSignHandler = new WSSignHandler(signFilePath, signPassword);
        URL url = new URL(shepUrl);

        QName qname = new QName("http://bip.bee.kz/SyncChannel/v10/Interfaces", "ISyncChannelHttpService");
        ISyncChannelHttpService service = new ISyncChannelHttpService(url, qname);
        service.setHandlerResolver(new HandlerResolver() {
            @Override
            public List<Handler> getHandlerChain(PortInfo portInfo) {
                List<Handler> handlerChain = new ArrayList<>();
                handlerChain.add(wsSignHandler);
                return handlerChain;
            }
        });

        ISyncChannel channel = service.getSyncChannelHttpPort();
        BindingProvider bindingProvider = (BindingProvider) channel;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, shepUrl);

//        log.info(" -- shep url = {}", shepUrl);

        return channel.sendMessage(syncSendMessageRequest);
    }


}
