package webwbd.service;

import io.github.cdimascio.dotenv.Dotenv;
import webwbd.model.AccountWrapper;
import webwbd.model.Request;
import webwbd.repository.AccountRepository;
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

    @WebMethod(operationName = "GetRequest")
    public Request getRequest(@WebParam(name = "id") int id,
                              @WebParam(name = "api_key") String apiKey) {
        if(!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return null;
        }
        return RequestRepository.getRequest(id);
    }

    @WebMethod(operationName = "GetRequestPage")
    public Request[] getRequestPage(@WebParam(name = "page") int page,
                                    @WebParam(name = "api_key") String apiKey) {
        if(!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return null;
        }
        return RequestRepository.getRequestPage(page);
    }

    @WebMethod(operationName = "SynchronizeAccounts")
    public String synchronizeAccounts(@WebParam(name = "accounts") AccountWrapper accounts,
                                      @WebParam(name = "api_key") String apiKey) {
        if(!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return "Not authorized";
        }
        return AccountRepository.synchronizeAccounts(accounts);
    }
}
