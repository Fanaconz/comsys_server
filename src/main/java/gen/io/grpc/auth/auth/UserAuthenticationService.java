package gen.io.grpc.auth.auth;

import gen.io.grpc.auth.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserAuthenticationService {

    private final SessionFactory sessionFactory;

    public UserAuthenticationService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean verifyUserExistence(String login, String password) {
        try (Session session = sessionFactory.openSession()) {
            return verifyUserExistenceHibernate(session, login, password);
        }
    }

    private static boolean verifyUserExistenceHibernate(Session session, String login, String password) {
        Transaction transaction = session.beginTransaction();

        //  Получаем доступ к пользователю на основе логина и пароля (есть другой метод, возможно, более правильный, но не такой интуитивный)
        User user = (User) session.createQuery("FROM  User WHERE login = :login AND password = :password")
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();

        transaction.commit();

        return user != null; // возвращаем true, если такой пользователь был найден в бд
    }
}