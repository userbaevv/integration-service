package kz.dara.gbdulNewService.util;

import kz.dara.gbdulNewService.generated.Request;
import kz.gov.pki.kalkan.asn1.pkcs.PKCSObjectIdentifiers;
import kz.gov.pki.kalkan.jce.provider.KalkanProvider;
import kz.gov.pki.kalkan.xmldsig.KncaXS;
import kz.gov.pki.kalkan.xmldsig.SignatureGostR3410_2015;
import lombok.RequiredArgsConstructor;
import org.apache.xml.security.encryption.XMLCipherParameters;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.transforms.Transforms;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

@Service
@RequiredArgsConstructor
public class SoapMessageFactory {


    public static String signXML(final String signFilePath, String password, JAXBElement<Request> requestDataType, String tagToSign) {
        String result = null;
        try {
            // Инициализация и регистрация алгоритмов
            RegisterCustomAlgorithms.register();

            String strXML = SoapMessageFactory.requestToXML(requestDataType); // Преобразование Request в XML
            Provider provider = new KalkanProvider();
            Security.addProvider(provider);
            KncaXS.loadXMLSecurity();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            final Document doc = documentBuilder.parse(new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8)));
            Document docToSign = documentBuilder.newDocument();
            Node nodeToImport = docToSign.importNode(doc.getElementsByTagName(tagToSign).item(0), true);
            docToSign.appendChild(nodeToImport);

            final String signMethod;
            final String digestMethod;
            KeyStore store = KeyStore.getInstance("PKCS12", provider.getName());
            System.out.println("SIGNXML = " + SoapMessageFactory.class.getResource(signFilePath).toURI());
            InputStream is = SoapMessageFactory.class.getResourceAsStream(signFilePath);
            if (is == null) {
                throw new IOException("Не удалось найти файл ключа по пути: " + signFilePath);
            }
            store.load(is, password.toCharArray());

            Enumeration<String> als = store.aliases();
            if (!als.hasMoreElements()) {
                throw new KeyStoreException("Не удалось найти alias в хранилище ключей.");
            }

            String alias = als.nextElement();
            final PrivateKey privateKey = (PrivateKey) store.getKey(alias, password.toCharArray());
            final X509Certificate x509Certificate = (X509Certificate) store.getCertificate(alias);

            if (privateKey == null || x509Certificate == null) {
                throw new KeyStoreException("Приватный ключ или сертификат не найдены в хранилище ключей.");
            }

            System.out.println("Private Key Algorithm: " + privateKey.getAlgorithm());
            System.out.println("Certificate: " + x509Certificate);

            String sigAlgOid = x509Certificate.getSigAlgOID();
            if (sigAlgOid.equals(PKCSObjectIdentifiers.sha1WithRSAEncryption.getId())) {
                signMethod = Constants.MoreAlgorithmsSpecNS + "rsa-sha1";
                digestMethod = Constants.MoreAlgorithmsSpecNS + "sha1";
            } else if (sigAlgOid.equals(PKCSObjectIdentifiers.sha256WithRSAEncryption.getId())) {
                signMethod = Constants.MoreAlgorithmsSpecNS + "rsa-sha256";
                digestMethod = XMLCipherParameters.SHA256;
            } else {
                signMethod = SignatureGostR3410_2015.GostR34102015GostR34112015_512._URI;
                digestMethod = "urn:ietf:params:xml:ns:pkigovkz:xmlsec:algorithms:gostr34112015-512";
            }

            XMLSignature sig = new XMLSignature(docToSign, "", signMethod);

            if (docToSign.getFirstChild() != null) {
                docToSign.getFirstChild().appendChild(sig.getElement());

                Transforms transforms = new Transforms(docToSign);
                transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
                transforms.addTransform(XMLCipherParameters.N14C_XML_CMMNTS);
                sig.addDocument("", transforms, digestMethod);
                sig.addKeyInfo(x509Certificate);

                // Логирование перед вызовом sig.sign()
                System.out.println("Signing XML document...");
                try {
                    sig.sign(privateKey);
                } catch (XMLSignatureException e) {
                    System.err.println("Ошибка при подписании: " + e.getMessage());
                    e.printStackTrace();
                    throw e; // Повторно выбрасываем исключение после логирования
                }

                nodeToImport = doc.importNode(docToSign.getFirstChild(), true);
                Node oldChild = doc.getElementsByTagName(tagToSign).item(0);
                Node parent = oldChild.getParentNode();
                parent.removeChild(oldChild);
                parent.appendChild(nodeToImport);

                StringWriter os = new StringWriter();
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer trans = tf.newTransformer();
                trans.transform(new DOMSource(doc), new StreamResult(os));
                os.close();
                result = os.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ошибка при подписании XML: " + e.getMessage());
        }
        return result;
    }






    public static String requestToXML(JAXBElement<Request> requestDataType) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Request.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(requestDataType, writer);
        return writer.toString();
    }
}
