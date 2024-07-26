package kz.dara.gbdulNewService.util;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

public class JaxBSerializer {

    private JaxBSerializer() {
        throw new UnsupportedOperationException();
    }

    public static String serialize(Object obj) {
        StringWriter result = new StringWriter();

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(obj.getClass());
            Marshaller m = context.createMarshaller();
            JAXBElement<Object> e = new JAXBElement(new QName(""), obj.getClass(), obj);
            m.marshal(e, result);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String serializeForSignature(Object obj) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);

            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return "";
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String str, Class<T> requiredClass) {
        try {
            JAXBContext payloadContext = JAXBContext.newInstance(requiredClass);
            if (requiredClass.getClass().getAnnotation(XmlRootElement.class) != null) {
                return (T) payloadContext.createUnmarshaller().unmarshal(new StringReader(str));
            } else {
                Source source = new StreamSource(new StringReader(str));
                Unmarshaller u = payloadContext.createUnmarshaller();
                return u.unmarshal(source, requiredClass).getValue();
            }
        } catch (JAXBException e) {
            throw new IllegalArgumentException(String.format("Could not parse %s from string\n%s\n%s", requiredClass.getName(), str, e.getMessage()), e);
        }
    }
}
