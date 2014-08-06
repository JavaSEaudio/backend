package DTO;

import DAO.util.Factory;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlbumsDTO {
    private String album;
    private int tracks;

    public AlbumsDTO(){}

    public AlbumsDTO(String album) {
        this.album = album;
        this.tracks = Factory.getInstance().getAudioDAO().getbyAlbumTracks(album, 0, Integer.MAX_VALUE).size();
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
}
