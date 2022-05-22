package service;

import domain.*;
import repository.RepositoryException;
import repository.bookRepository.BookRepository;
import repository.borrowingRepository.BorrowingRepository;
import repository.subcriberRepository.SubscriberRepository;
import validator.SubscriberValidator;
import validator.ValidatorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

public class Service extends Observable {
    private BookRepository bookRepository;
    private SubscriberRepository subscriberRepository;
    private SubscriberValidator subscriberValidator;
    private BorrowingRepository borrowingRepository;
    private static Service instance;

    public void setRepo(BookRepository bookDbRepository, SubscriberRepository subscriberRepository, BorrowingRepository borrowingRepository){
        this.bookRepository = bookDbRepository;
        this.subscriberRepository = subscriberRepository;
        this.borrowingRepository = borrowingRepository;
        this.subscriberValidator = new SubscriberValidator();
    }

    public static Service getInstance() {
        if(instance == null) {
            instance = new Service();
        }

        return instance;
    }

    private Service(){};

    public void addSubscriber(String username, String firstName, String lastName, String Cnp, String phoneNo, String password) throws RepositoryException, ValidatorException {
        Subscriber subscriber = new Subscriber(username, firstName, lastName, Cnp, phoneNo, password);
        subscriberValidator.validate(subscriber);
        subscriberRepository.add(subscriber);
    }

    public Subscriber findSubscriberByUsernameAndPassword(String username, String password){
        Subscriber subscriber = subscriberRepository.findByUsernameAndPassword(username, password);
        return subscriber;
    }

    public Iterable<Book> findAllBooks(){
        Iterable<Book> books = bookRepository.findAll();
        return books;
    }

    public void addBorrowing(int subscriber, Collection<Integer> books, LocalDateTime data, ReturnStatus status) throws RepositoryException {
        Borrowing borrowing = new Borrowing(subscriber, books, data, status);
        borrowingRepository.add(borrowing);
        for(Integer bookId : books)
            bookRepository.changeBookStatus(BookStatus.borrowed, bookId);
        setChanged();
        notifyObservers();
    }

    public List<Book>findBooksByBorrowing(int id){
        Iterable<Integer> booksId = borrowingRepository.getBooksByBorrowing(id);
        System.out.println(booksId);
        List<Book> books = new ArrayList<>();
        for(Integer bookId : booksId)
            books.add(bookRepository.findById(bookId));
        return books;
    }

    public Iterable<Borrowing> findAllBorrowingsForASubscriber(int id){
        Iterable<Borrowing> borrowings = borrowingRepository.findAllBorrowingsForASubscriber(id);
        return borrowings;
    }

    public void returnBooks(int id){
        borrowingRepository.changeStatus(ReturnStatus.pending, id);
        setChanged();
        notifyObservers();
    }

    public List<Borrowing> findAllBorrowingsPendingStatus(){
        List<Borrowing> borrowings = new ArrayList<>();
        for(Borrowing borrowing : borrowingRepository.findAll())
            if(borrowing.getStatus() == ReturnStatus.pending)
                borrowings.add(borrowing);
        return borrowings;
    }

    public Subscriber findSubscriberById(int id){
        Subscriber subscriber = subscriberRepository.findById(id);
        return subscriber;
    }

    public void acceptReturn(int id){
        borrowingRepository.changeStatus(ReturnStatus.yes, id);
        Borrowing borrowing = borrowingRepository.findById(id);
        for(Integer book : borrowing.getBooks())
            bookRepository.changeBookStatus(BookStatus.available, book);
        setChanged();
        notifyObservers();
    }

    public void rejectReturn(int id){
        borrowingRepository.changeStatus(ReturnStatus.no, id);
        setChanged();
        notifyObservers();
    }
}
