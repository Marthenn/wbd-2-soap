package webwbd.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import webwbd.model.Request;
import webwbd.util.HibernateUtil;

import java.util.Date;

public class RequestRepository {
    public Boolean createRequest(String username, String email, String proofDirectory) {
        try {
            Request request = new Request();
            request.setUsername(username);
            request.setEmail(email);
            request.setProofDirectory(proofDirectory);
            request.setDate(new Date());
            request.setStatus("Pending");
            request.setDescription("");

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(request);
            session.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean approveRequest(int id) {
        try {
            // TODO: implement approveRequest
            /*
            * 1. Request id sudah ada (dari parameter)
            * 2. Set status request menjadi Approved
            * 3. Set description jadi "Request approved by admin at {date}"
            * */
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean declineRequest(int id) {
        try {
            // TODO: implement declineRequest
            /*
             * 1. Request id sudah ada (dari parameter)
             * 2. Set status request menjadi Declined
             * 3. Set description jadi "Request declined by admin at {date}. Cause: {cause}"
             * */
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
