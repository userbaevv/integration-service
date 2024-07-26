package kz.dara.gbdulNewService.controller;


import kz.dara.gbdulNewService.generated.ResponseDataType;
import kz.dara.gbdulNewService.service.ULService;
import kz.dara.gbdulNewService.sync.v10.types.response.SyncSendMessageResponse;
import kz.dara.gbdulNewService.util.JaxBSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shep/gbdul")
public class ULController {

    private final ULService ulService;

    public ULController(ULService ulService) {
        this.ulService = ulService;
    }

    @GetMapping("/{bin}")
    public ResponseDataType createAccess(@PathVariable String bin) throws Exception {
        SyncSendMessageResponse response = (SyncSendMessageResponse) ulService.getLaborRequest(bin);
        String responseStr = JaxBSerializer.serialize(response.getResponseData().getData());
        String cleanedRequest = responseStr.replace("<>", "").replace("</>", "");
        String serializedRequest = cleanedRequest.replace("&lt;", "<").replace("&gt;", ">");
        String serializedRequest1 = serializedRequest.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
        System.out.println("serializedRequest = " + serializedRequest1);
        return JaxBSerializer.deserialize(serializedRequest1, ResponseDataType.class);
    }
}
