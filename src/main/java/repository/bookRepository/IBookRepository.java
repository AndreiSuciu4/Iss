package repository.bookRepository;

import domain.Book;
import domain.BookStatus;
import repository.Repository;

public interface IBookRepository extends Repository<java.lang.Integer, Book> {
    void changeBookStatus(BookStatus status, Integer id);
}
