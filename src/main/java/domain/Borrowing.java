package domain;

import java.util.*;
import java.time.LocalDateTime;

public class Borrowing extends EntityObj {
    private int subscriber;
    private Collection<Integer> books = new ArrayList<>();
    private LocalDateTime data;
    private ReturnStatus status;

    public Borrowing(){}

    public Borrowing(int subscriber, Collection<Integer> books, LocalDateTime data, ReturnStatus status) {
        this.subscriber = subscriber;
        this.books = books;
        this.data = data;
        this.status = status;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }


    public Collection<Integer> getBooks() {
        return books;
    }

    public void setBooks(Collection<Integer> books) {
        this.books = books;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public ReturnStatus getStatus() {
        return status;
    }

    public void setStatus(ReturnStatus status) {
        this.status = status;
    }

    public void addBook(Integer book){
        this.books.add(book);
    }

    public void deleteBook(Integer book){
        this.books.remove(book);
    }
}
