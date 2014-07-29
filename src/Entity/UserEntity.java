package Entity;

import DTO.UserDTO;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "user")
public class UserEntity {

    private int id;
    private String login;
    private String password;
    private String email;
    private String information;
    private String name;
    private double money;
    private int access;
    private String buylist;


    public UserEntity() {}

    public UserEntity(String login, String password, String email, String name, String info) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.money = 1000;
        this.access = 0;
        this.buylist = "";
        this.information = "";
    }

    public void setDRO(UserDTO userDRO) {
        this.id = userDRO.getId();
        this.login = userDRO.getLogin();
        this.email = userDRO.getEmail();
        this.information = userDRO.getInformation();
        this.name = userDRO.getName();
        this.money = userDRO.getMoney();
        this.access = userDRO.getAccess();
        this.buylist = userDRO.getBuylist();
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "money")
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
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
}
