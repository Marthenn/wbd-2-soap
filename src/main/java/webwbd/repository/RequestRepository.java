package webwbd.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import webwbd.model.Request;
import webwbd.util.HibernateUtil;

import java.util.Date;

public class RequestRepository {
    public Boolean createRequest(int id, String username, String email, String proofDirectory) {
        try {
            Request request = new Request();
            request.setUsername(username);
            request.setEmail(email);
            request.setProofDirectory(proofDirectory);
            request.setDate(new Date());

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
}
