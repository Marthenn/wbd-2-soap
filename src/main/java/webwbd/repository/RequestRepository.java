package webwbd.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import webwbd.model.Request;
import webwbd.model.Account;
import webwbd.util.EmailUtil;
import webwbd.util.HibernateUtil;

import java.util.Date;

public class RequestRepository {
    private final static int PAGE_SIZE = 5;
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

            // check if username and email exists which means there's already a pending request
            session.beginTransaction();
            Request existingRequest = session.createQuery("from Request where username = :username or email = :email and status = :status", Request.class)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .setParameter("status", "Pending")
                    .uniqueResult();
            if (existingRequest != null) {
                return "A pending request already exists";
            }

            // check account
            Account account = session.createQuery("from Account where username = :username or email = :email", Account.class)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .uniqueResult();
            // if admin
            if (account != null && account.isAdmin()) {
                return "Admin cannot create request";
            }
            // if exist an account but with different email
            if (account != null && !account.getEmail().equals(email)) {
                return "Account with the specified username already exists with different email";
            }
            // if exist an account but with different username
            if (account != null && !account.getUsername().equals(username)) {
                return "Account with the specified email already exists with different username";
            }

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

    public static Request getRequest(int id) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Request request = session.createQuery("from Request where id = :id", Request.class)
                    .setParameter("id", id)
                    .uniqueResult();
            session.getTransaction().commit();

            return request;
        } catch (Exception e) {
            return null;
        }
    }

    public static Request[] getRequestPage(int page) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Request[] requests = session.createQuery("from Request where status = 'Pending'", Request.class)
                    .setFirstResult((page - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE)
                    .list()
                    .toArray(new Request[0]);
            session.getTransaction().commit();

            return requests;
        } catch (Exception e) {
            return null;
        }
    }
}
