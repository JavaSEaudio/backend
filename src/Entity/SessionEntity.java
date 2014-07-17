package Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "session")
public class SessionEntity {

    private int id;
    private int userId;
    private String key;

    public  SessionEntity(){}

    public SessionEntity(int userId, String key) {
        this.userId = userId;
        this.key = key;
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
    public String getKey() {
        return key;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionEntity that = (SessionEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (!key.equals(that.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + key.hashCode();
        return result;
    }
}
