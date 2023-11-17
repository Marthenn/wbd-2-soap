package webwbd.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import webwbd.model.Account;
import webwbd.model.Request;
import webwbd.model.RequestWrapper;
import webwbd.util.EmailUtil;
import webwbd.util.HibernateUtil;
import webwbd.util.PasswordUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RequestRepository {
    private final static int PAGE_SIZE = 25;

    public String createRequest(String username, String email, String proofDirectory) {
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
                session.getTransaction().commit();
                return "A pending request already exists";
            }

            // check account
            Account account = session.createQuery("from Account where username = :username or email = :email", Account.class)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .uniqueResult();
            // if admin
            if (account != null && account.isAdmin()) {
                session.getTransaction().commit();
                return "Admin cannot create request";
            }
            // if exist an account but with different email
            if (account != null && !account.getEmail().equals(email)) {
                session.getTransaction().commit();
                return "Account with the specified username already exists with different email";
            }
            // if exist an account but with different username
            if (account != null && !account.getUsername().equals(username)) {
                session.getTransaction().commit();
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

    public String approveRequest(String username) {
        try {
            /*
             * 1. Request id sudah ada (dari parameter)
             * 2. Set status request menjadi Approved
             * 3. Set description jadi "Request approved by admin at {date}"
             * */
            Date date = new Date();

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.createQuery("update Request set status = 'Approved', description = 'Request approved by admin at " + date + ".' where username = :username")
                    .setParameter("username", username)
                    .executeUpdate();
            session.getTransaction().commit();

            String password = PasswordUtil.generateRandomPassword(20);
            EmailUtil.sendMail(getRequest(username).getEmail(), "Request Approved", "Your request has been approved successfully at " + date + ". Your password is " + password + ". Please change the password ASAP.");

            return "Request approved successfully. The password is " + password + ".";
        } catch (Exception e) {
            return "Failed to approve request";
        }
    }

    public String declineRequest(String username) {
        try {
            /*
             * 1. Request id sudah ada (dari parameter)
             * 2. Set status request menjadi Declined
             * 3. Set description jadi "Request declined by admin at {date}. Cause: {cause}"
             * */
            Date date = new Date();

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.createQuery("update Request set status = 'Declined', description = 'Request declined by admin at " + date + ".' where username = :username")
                    .setParameter("username", username)
                    .executeUpdate();
            session.getTransaction().commit();
            EmailUtil.sendMail(getRequest(username).getEmail(), "Request Denied", "Sorry but we need to inform you that your request is declined at " + date + ". You are still legible for a new request.");
            return "Request declined successfully";
        } catch (Exception e) {
            return "Failed to decline request";
        }
    }

    public Request getRequest(String username) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            Request request = session.createQuery("from Request where username = :username order by date desc", Request.class)
                    .setParameter("username", username)
                    .getSingleResult();
            session.getTransaction().commit();

            return request;
        } catch (Exception e) {
            return null;
        }
    }

    public RequestWrapper getRequestPage(int page) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            List<Request> requests = Arrays.asList(session.createQuery("from Request where status = 'Pending'", Request.class)
                    .setFirstResult((page - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE)
                    .list()
                    .toArray(new Request[0]));
            session.getTransaction().commit();

            return new RequestWrapper(requests);
        } catch (Exception e) {
            return null;
        }
    }
}
