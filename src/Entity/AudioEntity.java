package Entity;

import BusinessLogic.FileOperation;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@Table(name = "audiofile")
public class AudioEntity {
    private int id;
    private String name;
    private String artist;
    private String album;
    private String genre;
    private String comment;
    private int year;
    private double price;
    private String type;
    private int length; // seconds?
    private int size; // byte?
    private int userid;
    @Temporal(TemporalType.DATE)
    private Date upload_date;

    public AudioEntity(String name, String artist, String album, String genre){
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
        this.comment = "comment";
        this.type = "mp3";
        this.year = 2000;
        this.price = 2;
        this.length = 100; //in second
        this.size = 1000;
        this.userid = 1;
    }

    public AudioEntity() {
    }

    public AudioEntity(PrivateEntity privat) {
        FileOperation fileOperation = new FileOperation("C://upload//private//"+privat.getId()+".mp3");
        this.name = privat.getName();
        this.album = privat.getAlbum();
        this.artist = privat.getArtist();

        this.type = ".mp3";
        try{this.year = fileOperation.getYear();}catch (Exception e){}
        this.price = 0;
        try{this.length = fileOperation.getLength();}catch (Exception e){}
        try{this.size = fileOperation.getSize();}catch (Exception e){}
        try{this.userid = privat.getUserid();}catch (Exception e){}
    }

    public AudioEntity(String name, String artist, String album) {
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.genre = "";
        this.comment = "";
        this.type = "";
        this.year = 0;
        this.price = 0;
        this.length = 0;
        this.size = 0;
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

    @Column(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

}
