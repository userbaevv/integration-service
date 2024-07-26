
package kz.dara.gbdulNewService.sync.v10.types.response;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the kz.bee.bip.syncchannel.v10.types.response package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: kz.bee.bip.syncchannel.v10.types.response
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseData }
     * 
     */
    public ResponseData createResponseData() {
        return new ResponseData();
    }

    /**
     * Create an instance of {@link SyncSendMessageResponse }
     * 
     */
    public SyncSendMessageResponse createSyncSendMessageResponse() {
        return new SyncSendMessageResponse();
    }

}
