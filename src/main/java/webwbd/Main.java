package webwbd;

import webwbd.middleware.LoggerServlet;
import webwbd.service.Service;
import webwbd.util.HibernateUtil;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        Endpoint endpoint  = Endpoint.create(new Service());
        List<Handler> handlerChain = endpoint.getBinding().getHandlerChain();
        handlerChain.add(new LoggerServlet());
        endpoint.getBinding().setHandlerChain(handlerChain);

        endpoint.publish("http://localhost:50435/api/Service");
    }
}
