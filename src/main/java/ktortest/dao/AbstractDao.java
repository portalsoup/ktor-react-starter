package ktortest.dao;

import ktortest.util.Generics;
import ktortest.util.HibernateUtil;
import ktortest.util.TriFunction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbstractDao<E> {

    private final Class<E> entityClass;

    public AbstractDao() {
        this.entityClass = (Class<E>) Generics.getTypeParameter(getClass());
    }

    protected <T> T withTransaction(Function<Session, T> supplier) {
        T returnValue;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            returnValue = supplier.apply(session);
            transaction.commit();
        } catch (Throwable e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RollbackException();
        }
        return returnValue;
    }

    protected void withTransaction(Consumer<Session> supplier) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            supplier.accept(session);
            transaction.commit();
        } catch (Throwable e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RollbackException(e);
        }
    }

    protected <T> T withSession(Function<Session, T> supplier) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return supplier.apply(session);
        }
    }

    public <T> List<E> asCriteria(TriFunction<CriteriaBuilder, CriteriaQuery<E>, Root<E>, T> function) {
        return withSession(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
            Root<E> root = query.from(entityClass);

            query.select(root);

            // Do the work
            function.apply(criteriaBuilder, query, root);

            Query<E> q = session.createQuery(query);
            return q.getResultList();
        });
    }
}
