package webwbd.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import webwbd.model.Account;
import webwbd.model.AccountWrapper;
import webwbd.util.HibernateUtil;

import java.util.List;

public class AccountRepository {
    public String synchronizeAccounts(AccountWrapper accounts) {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            List<Account> accountsList = accounts.getAccounts();

            /*
             * 1. Delete all accounts
             * 2. Insert all accounts
             * 3. Commit
             * */
            session.beginTransaction();
            session.createQuery("delete from Account").executeUpdate();
            for (Account account : accountsList) {
                session.save(account);
            }
            session.getTransaction().commit();

            return "Successfully synchronize accounts";
        } catch (Exception e) {
//            return e.getMessage();
            return "Fail to synchronize accounts";
        }
    }
}
