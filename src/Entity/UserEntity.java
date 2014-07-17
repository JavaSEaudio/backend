package Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {

    private int id;
    private String login;
    private String password;
    private String email;
    private int money;
    private int access;
    private String buylist;
    private String information;

    public UserEntity() {}

    public UserEntity(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.money = 1000;
        this.access = 0;
        this.buylist = "";
        this.information = "";
    }
    public UserEntity(UserEntity oldUser, int id) {
        this.id = id;
        this.login = oldUser.login;
        this.password = oldUser.password;
        this.email = oldUser.email;
        this.money = 1000;
        this.access = 0;
        this.buylist = "";
        this.information = "";
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    @Column(name = "login", unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "money")
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Column(name = "access")
    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    @Column(name = "buylist")
    public String getBuylist() {
        return buylist;
    }

    public void setBuylist(String buylist) {
        this.buylist = buylist;
    }

    @Column(name = "information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (access != that.access) return false;
        if (id != that.id) return false;
        if (money != that.money) return false;
        if (buylist != null ? !buylist.equals(that.buylist) : that.buylist != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (information != null ? !information.equals(that.information) : that.information != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + money;
        result = 31 * result + access;
        result = 31 * result + (buylist != null ? buylist.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
