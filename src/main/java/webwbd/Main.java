package webwbd;

import webwbd.service.Service;
import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        try {
            // TODO: change this Endpoint publishing
            Endpoint.publish("http://localhost:50345/ws/testing", new Service());
            System.out.println("Server started");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
