package webwbd.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import webwbd.model.Request;
import webwbd.model.Account;
import webwbd.util.EmailUtil;
import webwbd.util.HibernateUtil;

import java.util.Date;

public class RequestRepository {
    public static String createRequest(String username, String email, String proofDirectory) {
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

            // check if username and email exists which means there's already a request
            session.beginTransaction();
            Request existingRequest = session.createQuery("from Request where username = :username or email = :email", Request.class)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .uniqueResult();
            if (existingRequest != null) {
                return "Request already exists";
            }

            // check if username and email exists in account table and if it's admin
            Account account = session.createQuery("from Account where username = :username or email = :email and isAdmin = True", Account.class)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .uniqueResult();
            if (account != null) {
                return "An admin account with the same username or email already exists";
            }

            // TODO: add account exist here

            session.save(request);
            session.getTransaction().commit();

            EmailUtil.sendMail(email, "Request created successfully", "Your request has been created successfully. Please wait for the admin to approve your request.");

            return "Request created successfully";
        } catch (Exception e) {
            return "Failed to create request";
        }
    }

    public static String approveRequest(int id) {
        try {
            // TODO: implement approveRequest
            /*
            * 1. Request id sudah ada (dari parameter)
            * 2. Set status request menjadi Approved
            * 3. Set description jadi "Request approved by admin at {date}"
            * */
            return "Request approved successfully";
        } catch (Exception e) {
            return "Failed to approve request";
        }
    }

    public static String declineRequest(int id) {
        try {
            // TODO: implement declineRequest
            /*
             * 1. Request id sudah ada (dari parameter)
             * 2. Set status request menjadi Declined
             * 3. Set description jadi "Request declined by admin at {date}. Cause: {cause}"
             * */
            return "Request declined successfully";
        } catch (Exception e) {
            return "Failed to decline request";
        }
    }
}
