package kz.dara.gbdulNewService.sync.v10.types;



import kz.dara.gbdulNewService.sync.v10.types.request.SyncSendMessageRequest;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://bip.bee.kz/SyncChannel/v10/Types/Request}SyncSendMessageRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "request"
})
@XmlRootElement(name = "SendMessage")
public class SendMessage {

    @XmlElement(required = true, nillable = true)
    protected SyncSendMessageRequest request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link SyncSendMessageRequest }
     *     
     */
    public SyncSendMessageRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link SyncSendMessageRequest }
     *     
     */
    public void setRequest(SyncSendMessageRequest value) {
        this.request = value;
    }

}
