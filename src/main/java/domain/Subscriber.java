package domain;

public class Subscriber extends Entity {
    private String username;
    private String firstName;
    private String lastName;
    private String Cnp;
    private String phoneNo;
    private String password;

    public Subscriber(String username, String firstName, String lastName, String cnp, String phoneNo, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        Cnp = cnp;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return Cnp;
    }

    public void setCnp(String cnp) {
        Cnp = cnp;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
