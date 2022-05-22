package repository.borrowingRepository;

import domain.Borrowing;
import domain.ReturnStatus;
import repository.Repository;

import java.util.Collection;

public interface IBorrowingRepository extends Repository<Integer, Borrowing> {
    Iterable<Integer> getBooksByBorrowing(int id);
    Iterable<Borrowing> findAllBorrowingsForASubscriber(int id);
    void changeStatus(ReturnStatus status, int id);
}
