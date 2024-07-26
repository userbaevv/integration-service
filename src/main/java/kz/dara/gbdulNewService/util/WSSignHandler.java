package kz.dara.gbdulNewService.util;
import kz.gov.pki.kalkan.jce.provider.KalkanProvider;
import kz.gov.pki.kalkan.xmldsig.KncaXS;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.SecurityTokenReference;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//import org.slf4j.Logger;

//import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author: anonymous <none@gmail.com>
 * 9/30/17.
 */

public class WSSignHandler implements SOAPHandler<SOAPMessageContext> {

    Logger logger = LoggerFactory.getLogger(WSSignHandler.class);
    private static final String ALGORITHM_DIGEST = Constants.MoreAlgorithmsSpecNS + "gost34311";
    private static final String ALGORITHM_SIGNATURE = Constants.MoreAlgorithmsSpecNS + "gost34310-gost34311";

    private static final String WSS_NAMESPACE ="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";


    static {
        Provider provider = new KalkanProvider();
        Security.removeProvider(KalkanProvider.PROVIDER_NAME);
        Security.addProvider(provider);
        KncaXS.loadXMLSecurity();

    }

    private String keyPath;
    private String keyPass;

    public WSSignHandler(String keyPath, String keyPass){
        this.keyPath = keyPath;
        this.keyPass = keyPass;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            // ---

            // ---
            Boolean beforeRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            if (beforeRequest) {
                SOAPEnvelope env = context.getMessage().getSOAPPart().getEnvelope();
                String bodyId = "id-" + UUID.randomUUID();

//                logger.info("________");
//                context.getMessage().writeTo(System.out);
//                logger.info("________");
                sign(env, bodyId);
                context.getMessage().saveChanges();
                OutputStream outStream = new ByteArrayOutputStream();

                context.getMessage().writeTo(outStream);
                logger.info(" ___request_body: " + outStream);
            } else {
//                logger.info("________response");
                OutputStream outStream = new ByteArrayOutputStream();
                context.getMessage().writeTo(outStream);
                logger.info(" ___response_body: " + outStream);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
//            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) { }

    @Override
    public Set<QName> getHeaders() {
        QName securityHeader = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                "Security");
        HashSet<QName> headers = new HashSet<QName>();
        headers.add(securityHeader);
        return headers;
    }

    public String sign(SOAPEnvelope env, String bodyId) throws SOAPException, XMLSecurityException, IOException, PrivilegedActionException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, ClassNotFoundException, NoSuchProviderException, KeyStoreException, WSSecurityException, TransformerException {

        Class.forName("kz.gov.pki.kalkan.jce.X509Principal");
        SOAPBody body = env.getBody();
        Document doc = body.getOwnerDocument();

        SOAPHeader header = env.getHeader();
        if (header == null) {
            header = env.addHeader();
        }

        // Создаем новый заголовок безопасности
        SOAPElement securityElement = header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        securityElement.setPrefix("wsse");

        String prefix = "wsu";
        for (int i = 0; i < header.getAttributes().getLength(); i++) {
            Attr attr = (Attr) header.getAttributes().item(i);
            if (WSS_NAMESPACE.equals(attr.getNamespaceURI())) {
                prefix = attr.getName();
            }
        }

        // Добавляем пространство имен к заголовку безопасности
        securityElement.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:" + prefix, WSS_NAMESPACE);

        body.addAttribute(new QName(WSS_NAMESPACE, "Id", prefix), bodyId);
        body.setIdAttributeNS(WSS_NAMESPACE, "Id", true);

        KeyStore store = KeyStore.getInstance("PKCS12", KalkanProvider.PROVIDER_NAME);
        InputStream inputStream = WSSignHandler.class.getResourceAsStream(keyPath);
        store.load(inputStream, keyPass.toCharArray());
        Enumeration<String> als = store.aliases();
        String alias = null;
        while (als.hasMoreElements()) {
            alias = als.nextElement();
        }
        final PrivateKey privateKey = (PrivateKey) store.getKey(alias, keyPass.toCharArray());
        final X509Certificate x509Certificate = (X509Certificate) store.getCertificate(alias);

        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);

        XMLSignature sig = new XMLSignature(doc, "", ALGORITHM_SIGNATURE, Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        sig.addDocument("#" + bodyId, transforms, ALGORITHM_DIGEST);
        sig.getSignedInfo().getSignatureMethodElement().setNodeValue(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);
        securityElement.appendChild(sig.getElement());

        SecurityTokenReference reference = new SecurityTokenReference(doc);
        try {
            reference.setKeyIdentifier(x509Certificate);
        } catch (WSSecurityException e) {
            e.printStackTrace();
        }
        sig.getKeyInfo().addUnknownElement(reference.getElement());
        sig.sign(privateKey);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }


}
