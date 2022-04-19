package repository;

public interface Repository<ID, T> {
    void add(T elem) throws RepositoryException;

    void delete(ID id);

    void update(ID id, T elem);

    T findById(ID id);

    Iterable<T> findAll();

    int size();
}
