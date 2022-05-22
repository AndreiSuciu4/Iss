package repository.subcriberRepository;

import domain.Subscriber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.RepositoryException;

public class SubscriberRepository implements ISubscriberRepository {

    public SubscriberRepository(){
        try {
            initialize();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
        }finally {
            close();
        }
    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    @Override
    public void add(Subscriber subscriber) throws RepositoryException {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(subscriber);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("add error " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Subscriber findByUsernameAndPassword(String username, String password) {
        Subscriber subscriber = null;
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                subscriber = session.createQuery("from Subscriber  where username = ? and password = ?", Subscriber.class)
                        .setParameter(0, username)
                        .setParameter(1, password)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tx != null)
                    tx.rollback();
            }
        }
        return subscriber;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Subscriber elem) {

    }

    @Override
    public Subscriber findById(Integer id) {
        Subscriber subscriber = null;
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                subscriber = session.createQuery("from Subscriber  where id = ?", Subscriber.class)
                        .setParameter(0, id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tx != null)
                    tx.rollback();
            }
        }
        return subscriber;
    }

    @Override
    public Iterable<Subscriber> findAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
