package webwbd.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webwbd.model.Logging;
import webwbd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPBody;
import java.util.Set;

public class LoggerServlet implements SOAPHandler<SOAPMessageContext> {
    private void logToDatabase(SOAPMessageContext messageContext) throws SOAPException {
        boolean isResponse = (Boolean) messageContext.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        HttpExchange httpExchange = (HttpExchange) messageContext.get(JAXWSProperties.HTTP_EXCHANGE);

        if(!isResponse) {
            SOAPPart soapPart = messageContext.getMessage().getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();

            Node operation = soapBody.getChildNodes().item(1);
            String content = operation.getLocalName();

            NodeList parameters = operation.getChildNodes();
            for (int i = 1 ; i < parameters.getLength(); i += 2){
                content = String.format("%s %s(%s)", content, parameters.item(i).getLocalName(), parameters.item(i).getTextContent());
            }

            Logging logging = new Logging();
            logging.setDescription(content);
            logging.setIpAddress(httpExchange.getRemoteAddress().getHostString());
            logging.setEndpoint(httpExchange.getRequestURI().getPath());
            logging.setRequestTime(new java.util.Date());

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(logging);
            session.getTransaction().commit();
        }
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext messageContext) {
        try {
            logToDatabase(messageContext);
            return true;
        } catch (SOAPException e) {
            return false;
        }
    }

    public boolean handleFault(SOAPMessageContext context) {
        try {
            logToDatabase(context);
            return true;
        } catch (SOAPException e) {
            return false;
        }
    }

    public void close (MessageContext messageContext) {}
}
