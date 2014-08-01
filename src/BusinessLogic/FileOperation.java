package BusinessLogic;

import com.beaglebuddy.id3.enums.PictureType;
import com.beaglebuddy.id3.pojo.AttachedPicture;
import com.beaglebuddy.mp3.MP3;
import util.ProjectPath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation {

    private MP3 mp3;

   public FileOperation(String path){
        try {
            mp3 = new MP3(path);
        }catch (IOException e){}
    }
    public String getName() {
        return mp3.getTitle();
    }
    public String getAlbum() {
        return mp3.getAlbum();
    }
    public String getArtist() {
        String artist = new String();
        try {
            artist = mp3.getLeadPerformer();
        } catch (Exception e){}
        if(artist == null || artist.equals("") || artist.equals(" ")){
            artist = mp3.getBand();
        }
        return artist;
    }
    public String getComments() {
        return mp3.getComments();
    }
    public String getGenre() {
        return mp3.getMusicType();
    }
    public int getLength() {
        return mp3.getAudioDuration();
    }
    public int getSize() {
        return mp3.getAudioSize();
    }
    public int getYear() {
        return mp3.getYear();
    }
    public boolean getImage(int id) {
        AttachedPicture attachedPicture = mp3.getPicture(PictureType.FRONT_COVER);
        File file = new File(ProjectPath.getPath() + "web//file//image//"+id+".jpg");
        try{
            FileOutputStream out = new FileOutputStream(file);
            out.write(attachedPicture.getImage());
            return true;
        } catch (Exception e) {}
        file.delete();
        return false;
    }

    public void setImage(int id) {
        try {
            File image = new File(ProjectPath.getPath() + "web//file//image//"+id+".jpg");
            mp3.setPicture(PictureType.FRONT_COVER, image);
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setName(String name) {
        try {
            mp3.setTitle(name);
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setArtist(String artist) {
        try {
            mp3.setBand(artist);
            mp3.setLeadPerformer(artist);
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setAlbum(String album) {
        try {
            mp3.setAlbum(album);
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setGenre(String genre) {
        try {
            mp3.setMusicType(genre);
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setYear(int year) {
        try {
            mp3.setYear(year);
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setComments(String comments) {
        try {
            mp3.setComments(comments);
            mp3.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}