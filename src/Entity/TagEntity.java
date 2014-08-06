package Entity;

import DAO.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class TagEntity {

    private int id;
    private String name;
    private String audioIds = "";
    private int counts;

    public  TagEntity(){}

    public TagEntity(String name) {
        audioIds = "";
        this.name = name;
        counts = 0;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", unique = true)
    public int getId() {
        return id;
    }


    public void setAudioIds(String audioIds) {
        this.audioIds = audioIds;
    }

    @Basic
    @Column(name = "audioIds")
    public String getAudioIds() {
        return audioIds;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setCounts(int counts) {
        this.counts = counts;
    }
    @Basic
    @Column(name = "count")
    public int getCounts() {
        return counts;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Integer> addAudioIdsArray(){
        List<Integer> res = new ArrayList<>();
        int[] result = new int[0];
        if(audioIds == null || audioIds.equals("") || audioIds.equals(" ")) return res;
        String [] temp;
        try {
            System.out.println("audioIds: " + audioIds);
            temp = audioIds.split(" ");
            result = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                res.add(Integer.parseInt(temp[i]));
            }
        } catch (Exception e){  }
        return res;
    }

    public void addAudioIds(int audioId){
        this.audioIds += audioId + " ";
        counts ++;
    }

    public int audioCount() {
        return counts;
    }



}