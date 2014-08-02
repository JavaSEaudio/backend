package Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class LikeEntity {
    private int id;
    private int audioId;
    private int counts;
    private String userIds;

    public LikeEntity(){}

    public LikeEntity(int audioId) {
        this.audioId = audioId;
        this.counts = 0;
        this.userIds = "";
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

    @Column(name = "audioid", unique = true)
    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    @Column(name = "counts")
    public int getCounts() {
        return counts;
    }

    public void setCounts(int count) {
        this.counts = count;
    }

    @Column(name = "userids", length = 1000)
    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return "L: {" +
                "id=" + id +
                ", counts=" + counts +
                '}';
    }

    public int[] addUserIdsArray(){
        int[] result = new int[0];
        if(userIds == null || userIds.equals("") || userIds.equals(" ")) return result;
        String [] temp;
        try {
            temp = userIds.split(" ");
            result = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                result[i] = Integer.parseInt(temp[i]);
            }
        } catch (Exception e){  }
        return result;
    }

    public void addUserIds(int userid){
        this.userIds += userid+" ";
    }
}
