package DTO;

import DAO.util.Factory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArtistDTO {
    private String artist;
    private int tracks;
    private int album;

    public ArtistDTO() {}

    public ArtistDTO(String artist) {
        this.artist = artist;
        this.tracks = Factory.getInstance().getAudioDAO().getbyArtistTracks(artist, 0, Integer.MAX_VALUE).size();
        this.album = Factory.getInstance().getAudioDAO().getbyArtistAlbum(artist, 0, Integer.MAX_VALUE).size();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }
}
