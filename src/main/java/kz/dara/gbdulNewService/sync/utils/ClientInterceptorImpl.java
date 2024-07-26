package kz.dara.gbdulNewService.sync.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Log4j2
public class ClientInterceptorImpl implements ClientInterceptor {
    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        System.out.println("### SOAP RESPONSE ###");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            log.info(payload);
        } catch (IOException e) {
            throw new WebServiceClientException("Can not write the SOAP response into the out stream", e) {
                private static final long serialVersionUID = -7118480620416458069L;
            };
        }
        return true;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {

        System.out.println("### SOAP REQUEST ###");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getRequest().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            log.info(payload);
        } catch (IOException e) {
            throw new WebServiceClientException("Can not write the SOAP request into the out stream", e) {
                private static final long serialVersionUID = -7118480620416458069L;
            };
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        System.out.println("### SOAP FAULT ###");
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            messageContext.getResponse().writeTo(buffer);
            String payload = buffer.toString(java.nio.charset.StandardCharsets.UTF_8.name());
            log.error(payload);
        } catch (IOException e) {
            throw new WebServiceClientException("Can not write the SOAP fault into the out stream", e) {
                private static final long serialVersionUID = 3538336091916808141L;
            };
        }
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }
}
