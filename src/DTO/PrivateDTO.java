package DTO;

import Entity.PrivateEntity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class PrivateDTO {

    private int id;
    private String name;
    private String artist;
    private String album;
    private int userid;
    private Date upload_date;
    private String linkFile;
    private String linkImage;

    public PrivateDTO(){}

    public PrivateDTO(PrivateEntity privat){
        this.id = privat.getId();
        this.name = privat.getName();
        this.artist = privat.getArtist();
        this.album = privat.getAlbum();
        this.userid = privat.getUserid();
        this.upload_date = privat.getUpload_date();
        this.linkFile = "/rest/private/listen?id="+privat.getId();
        this.linkImage = "/rest/get/privateImage?id="+privat.getId();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
