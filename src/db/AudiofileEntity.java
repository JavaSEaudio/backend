package db;

import javax.persistence.*;

/**
 * Created by Степанов on 15.07.2014.
 */
@Entity
@Table(name = "audiofile", schema = "", catalog = "project_0.1")
public class AudiofileEntity {
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
    private int hashcode;
    private int length;
    private String size;
    private int userid;
    private int access;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "artist")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Basic
    @Column(name = "album")
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Basic
    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "bitrate")
    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    @Basic
    @Column(name = "hashcode")
    public int getHashcode() {
        return hashcode;
    }

    public void setHashcode(int hashcode) {
        this.hashcode = hashcode;
    }

    @Basic
    @Column(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Basic
    @Column(name = "size")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "access")
    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudiofileEntity that = (AudiofileEntity) o;

        if (access != that.access) return false;
        if (bitrate != that.bitrate) return false;
        if (hashcode != that.hashcode) return false;
        if (id != that.id) return false;
        if (length != that.length) return false;
        if (userid != that.userid) return false;
        if (year != that.year) return false;
        if (album != null ? !album.equals(that.album) : that.album != null) return false;
        if (artist != null ? !artist.equals(that.artist) : that.artist != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + bitrate;
        result = 31 * result + hashcode;
        result = 31 * result + length;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + userid;
        result = 31 * result + access;
        return result;
    }
}
