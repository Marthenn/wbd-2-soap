package webwbd.service;

import javax.jws.*;

@WebService
public class Service {
    @WebMethod
    public String hello(String name) {
        return "Hello " + name;
    }

    @WebMethod
    public String hello(){
        return "Hello World";
    }
}
