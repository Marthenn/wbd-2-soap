package webwbd;

import io.github.cdimascio.dotenv.Dotenv;
import webwbd.middleware.LoggerServlet;
import webwbd.service.Service;
import webwbd.util.HibernateUtil;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        Endpoint endpoint = Endpoint.create(new Service());
        List<Handler> handlerChain = endpoint.getBinding().getHandlerChain();
        handlerChain.add(new LoggerServlet());
        endpoint.getBinding().setHandlerChain(handlerChain);

        String port = Dotenv.load().get("PORT", "50435");
        String url = String.format("http://localhost:%s/api/Service", port);
        endpoint.publish(url);
    }
}
