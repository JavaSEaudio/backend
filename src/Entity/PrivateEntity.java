package Entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "privatefile")
public class PrivateEntity {
    private int id;
    private String name;
    private String artist;
    private String album;
    private int userid;
    @Temporal(TemporalType.DATE)
    private Date upload_date;

    public PrivateEntity(String name, String artist, String album, String genre){
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.userid = 1;
    }

    public PrivateEntity() {
    }

    public PrivateEntity(String name, String artist, String album) {
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.userid = 0;
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


    @Column(name = "upload_date")
    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "artist")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Column(name = "album")
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}

