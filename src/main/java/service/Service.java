package service;

import domain.Book;
import domain.Subscriber;
import repository.RepositoryException;
import repository.bookRepository.BookRepository;
import repository.subcriberRepository.SubscriberRepository;
import validator.SubscriberValidator;
import validator.ValidatorException;

public class Service {
    private BookRepository bookRepository;
    private SubscriberRepository subscriberRepository;
    private SubscriberValidator subscriberValidator;

    public Service(BookRepository bookDbRepository, SubscriberRepository subscriberDbRepository){
        this.bookRepository = bookDbRepository;
        this.subscriberRepository = subscriberDbRepository;
        this.subscriberValidator = new SubscriberValidator();
    }

    public void AddSubscriber(String username, String firstName, String lastName, String Cnp, String phoneNo, String password) throws RepositoryException, ValidatorException {
        Subscriber subscriber = new Subscriber(username, firstName, lastName, Cnp, phoneNo, password);
        subscriberValidator.Validate(subscriber);
        subscriberRepository.add(subscriber);
    }

    public Subscriber findByUsernameAndPassword(String username, String password){
        return subscriberRepository.findByUsernameAndPassword(username, password);
    }

    public Iterable<Book> findAllBooks(){
        return bookRepository.findAll();
    }
}
