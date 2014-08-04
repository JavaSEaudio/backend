package Entity;


import BusinessLogic.UserLogic;
import DAO.RestoreDAO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "restore")
public class RestoreEntity {
    private int id;
    private int userid;
    private String uniq;

    public RestoreEntity(){}

    public RestoreEntity(int userid){
        this.userid = userid;
        this.uniq = UserLogic.uid(20);
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

    @Column(name = "userid", unique = true)
    public int getUserid() {
        return userid;
    }


    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Column(name = "uniq", unique  = true)
    public String getUniq() {
        return uniq;
    }

    public void setUniq(String uniq) {
        this.uniq = uniq;
    }
}
