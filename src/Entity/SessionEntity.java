package Entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "session")
public class SessionEntity {

    private int id;
    private int userId;
    private String skey;

    public  SessionEntity(){}

    public SessionEntity(int userId, String key) {
        this.userId = userId;
        this.skey = key;
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

    @Basic
    @Column(name = "userid", unique = true)
    public int getUserId() {
        return userId;
    }

    @Basic
    @Column(name = "skey", unique = true)
    public String getSkey() {
        return skey;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSkey(String key) {
        this.skey = key;
    }

}
