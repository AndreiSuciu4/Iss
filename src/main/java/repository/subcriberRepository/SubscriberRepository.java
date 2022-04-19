package repository.subcriberRepository;

import domain.Subscriber;
import repository.JdbcUtils;
import repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SubscriberRepository implements ISubscriberRepository {
    private JdbcUtils jdbcUtils;

    public SubscriberRepository(Properties props){
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Subscriber elem) throws RepositoryException {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preSmt = connection.prepareStatement("insert into Subscribers(username, firstName, lastName, Cnp, phoneNo, password) values(?, ?, ?, ?, ?, ?)")){
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
            preSmt.setString(1, elem.getUsername());
            preSmt.setString(2, elem.getFirstName());
            preSmt.setString(3, elem.getLastName());
            preSmt.setString(4, elem.getCnp());
            preSmt.setString(5, elem.getPhoneNo());
            preSmt.setString(6, elem.getPassword());
            preSmt.executeUpdate();
        } catch (SQLException throwables) {
            throw new RepositoryException("invalid username");
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Subscriber elem) {

    }

    @Override
    public Subscriber findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Subscriber> findAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Subscriber findByUsernameAndPassword(String username, String password) {
        Connection con = jdbcUtils.getConnection();
        Subscriber subscriber = null;
        try(PreparedStatement preStm = con.prepareStatement("select * from Subscribers where username = ? and password = ?")){
            preStm.setString(1, username);
            preStm.setString(2, password);
            try(ResultSet result = preStm.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    String cnp = result.getString("Cnp");
                    String phoneNo = result.getString("phoneNo");
                    subscriber = new Subscriber(username, firstName, lastName, cnp, phoneNo, password);
                    subscriber.setId(id);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return subscriber;
    }
}
