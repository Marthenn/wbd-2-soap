package webwbd.service;

import io.github.cdimascio.dotenv.Dotenv;
import webwbd.model.AccountWrapper;
import webwbd.model.Request;
import webwbd.model.RequestWrapper;
import webwbd.repository.AccountRepository;
import webwbd.repository.RequestRepository;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class Service {
    private static final RequestRepository RequestRepository = new RequestRepository();
    private static final AccountRepository AccountRepository = new AccountRepository();

    @WebMethod(operationName = "CreateRequest")
    public String createRequest(@WebParam(name = "username") String username,
                                @WebParam(name = "email") String email,
                                @WebParam(name = "proof_directory") String proofDirectory,
                                @WebParam(name = "api_key") String apiKey) {
        if (!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return "Not authorized";
        }
        return RequestRepository.createRequest(username, email, proofDirectory);
    }

    @WebMethod(operationName = "ApproveRequest")
    public String approveRequest(@WebParam(name = "id") int id,
                                 @WebParam(name = "api_key") String apiKey) {
        if (!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return "Not authorized";
        }
        return RequestRepository.approveRequest(id);
    }

    @WebMethod(operationName = "GetRequest")
    public Request getRequest(@WebParam(name = "username") String username,
                              @WebParam(name = "api_key") String apiKey) {
        if (!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return null;
        }
        return RequestRepository.getRequest(username);
    }

    @WebMethod(operationName = "GetRequestPage")
    public RequestWrapper getRequestPage(@WebParam(name = "page") int page,
                                         @WebParam(name = "api_key") String apiKey) {
        if (!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return null;
        }
        return RequestRepository.getRequestPage(page);
    }

    @WebMethod(operationName = "SynchronizeAccounts")
    public String synchronizeAccounts(@WebParam(name = "accounts") AccountWrapper accounts,
                                      @WebParam(name = "api_key") String apiKey) {
        if (!apiKey.equals(Dotenv.load().get("SOAP_KEY"))) {
            return "Not authorized";
        }
        return AccountRepository.synchronizeAccounts(accounts);
    }
}
