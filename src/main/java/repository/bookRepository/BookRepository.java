package repository.bookRepository;

import domain.Book;
import domain.BookCategory;
import domain.BookStatus;
import repository.JdbcUtils;
import repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BookRepository implements IBookRepository {
    private JdbcUtils jdbcUtils;

    public BookRepository(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Book elem) throws RepositoryException {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Book elem) {

    }

    @Override
    public Book findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Book> findAll() {
        Connection con = jdbcUtils.getConnection();
        List<Book> books = new ArrayList<>();
        try(PreparedStatement preStm = con.prepareStatement("select * from Books")){
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String code = result.getString("code");
                    String name = result.getString("name");
                    String author = result.getString("author");
                    BookCategory category = BookCategory.valueOf(result.getString("category"));
                    BookStatus status = BookStatus.valueOf(result.getString("status"));
                    Book book = new Book(code, name, author, category, status);
                    book.setId(id);
                    books.add(book);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return books;
    }

    @Override
    public int size() {
        return 0;
    }
}
