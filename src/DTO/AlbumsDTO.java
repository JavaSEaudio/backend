package DTO;

import DAO.util.Factory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlbumsDTO {
    private String album;
    private int tracks;
    private String artist;

    public AlbumsDTO(){}

    public AlbumsDTO(String album, String artist) {
        this.album = album;
        this.artist = artist;
        this.tracks = Factory.getInstance().getAudioDAO().getbyAlbumTracks(album, artist, 0, Integer.MAX_VALUE).size();
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
