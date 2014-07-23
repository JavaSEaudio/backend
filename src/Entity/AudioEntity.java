package Entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "audiofile")
public class AudioEntity {
    private int id;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String comment;
    private String tags;
    private int year;
    private String type;
    private int bitrate;
    private int length; // seconds
    private int size; //byte
    private int userid;
    private int access;
    @Temporal(TemporalType.DATE)
    private Date upload_date;

    public AudioEntity(String name, String artist, String album, String genre){
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
        this.comment = "";
        this.tags = "";
        this.type = "";
        this.year = 2000;
        this.bitrate = 254;
        this.length = 100; //in second
        this.size = 1000;
        this.userid = 999; //id users
        this.access = 0;
    }

    public AudioEntity() {
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

    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "bitrate")
    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    @Column(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Column(name = "size")
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Column(name = "access")
    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
