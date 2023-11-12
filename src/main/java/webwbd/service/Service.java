package webwbd.service;

import io.github.cdimascio.dotenv.Dotenv;
import webwbd.repository.RequestRepository;

import javax.jws.*;

@WebService
public class Service {
    @WebMethod(operationName = "CreateRequest")
    public String createRequest(@WebParam(name = "username") String username,
                                 @WebParam(name = "email") String email,
                                 @WebParam(name = "proof_directory") String proofDirectory,
                                 @WebParam(name = "api_key") String apiKey) {
        if(!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return "Not authorized";
        }
        return RequestRepository.createRequest(username, email, proofDirectory);
    }
}
