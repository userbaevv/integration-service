package kz.dara.gbdulNewService.sync.v10.types;



import kz.dara.gbdulNewService.sync.v10.types.response.SyncSendMessageResponse;

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
 *         &lt;element name="response" type="{http://bip.bee.kz/SyncChannel/v10/Types/Response}SyncSendMessageResponse"/>
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
    "response"
})
@XmlRootElement(name = "SendMessageResponse")
public class SendMessageResponse {

    @XmlElement(required = true, nillable = true)
    protected SyncSendMessageResponse response;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link SyncSendMessageResponse }
     *     
     */
    public SyncSendMessageResponse getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link SyncSendMessageResponse }
     *     
     */
    public void setResponse(SyncSendMessageResponse value) {
        this.response = value;
    }

}
