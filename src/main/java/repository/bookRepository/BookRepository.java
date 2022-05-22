package repository.bookRepository;

import domain.Book;
import domain.BookStatus;
import domain.Subscriber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.RepositoryException;


public class BookRepository implements IBookRepository {

    public BookRepository(){
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
    public void add(Book book) throws RepositoryException {
        if (sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(book);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("add error " + ex);
                if (tx != null)
                    tx.rollback();

            }
        }
    }

    @Override
    public void delete(java.lang.Integer integer) {

    }

    @Override
    public void update(java.lang.Integer integer, Book elem) {

    }

    @Override
    public Book findById(java.lang.Integer id) {
        Book book = null;
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                book = session.createQuery("from Book  where id = ?", Book.class)
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
        return book;
    }

    @Override
    public Iterable<Book> findAll() {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        Iterable<Book> books = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try {
                tr = session.beginTransaction();
                books = session.createQuery("from Book", Book.class)
                        .list().stream().filter(x -> x.getStatus() == BookStatus.available).toList();
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
    public int size() {
        return 0;
    }

    @Override
    public void changeBookStatus(BookStatus status, Integer id) {
        if(sessionFactory == null || sessionFactory.isClosed())
            initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tr = null;
            try {
                tr = session.beginTransaction();
                String hqlUpdate = "update Book set status = ? where id = ?";
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
}
