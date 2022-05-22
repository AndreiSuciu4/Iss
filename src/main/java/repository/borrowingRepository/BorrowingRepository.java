package repository.borrowingRepository;

import domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.RepositoryException;

import java.util.Collection;

public class BorrowingRepository implements IBorrowingRepository{
    public BorrowingRepository(){
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
    public void add(Borrowing borrowing) throws RepositoryException {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try{
                tr = session.beginTransaction();
                session.save(borrowing);
                tr.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tr != null)
                    tr.rollback();
            }
        }
    }

    @Override
    public Iterable<Integer> getBooksByBorrowing(int id) {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        Collection<Integer> books = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try{
                tr = session.beginTransaction();
                books = session.createQuery("from Borrowing where id = ?", Borrowing.class)
                        .setParameter(0, id)
                        .setMaxResults(1)
                        .uniqueResult().getBooks();
                tr.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tr != null)
                    tr.rollback();
            }
        }
        return books;
    }

    @Override
    public Iterable<Borrowing> findAllBorrowingsForASubscriber(int id) {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        Iterable<Borrowing> borrowings = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try {
                tr = session.beginTransaction();
                borrowings = session.createQuery("from Borrowing where subscriber = ? ", Borrowing.class)
                        .setParameter(0, id)
                        .list().stream().filter(x -> x.getStatus() == ReturnStatus.no).toList();
                tr.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tr != null)
                    tr.rollback();
            }
        }
        return borrowings;
    }

    @Override
    public void changeStatus(ReturnStatus status, int id) {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try {
                tr = session.beginTransaction();
                String hqlUpdate = "update Borrowing set status = ? where id = ?";
                session.createQuery( hqlUpdate )
                        .setParameter(0, status)
                        .setParameter(1, id)
                        .executeUpdate();
                tr.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tr != null)
                    tr.rollback();
            }
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Borrowing elem) {

    }

    @Override
    public Borrowing findById(Integer id) {
        Borrowing borrowing = null;
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                borrowing = session.createQuery("from Borrowing  where id = ?", Borrowing.class)
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
        return borrowing;
    }

    @Override
    public Iterable<Borrowing> findAll() {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        Iterable<Borrowing> borrowings = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try {
                tr = session.beginTransaction();
                borrowings = session.createQuery("from Borrowing", Borrowing.class)
                        .list();
                tr.commit();
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(tr != null)
                    tr.rollback();
            }
        }
        return borrowings;
    }

    @Override
    public int size() {
        return 0;
    }
}
