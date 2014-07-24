package BusinessLogic;

import DAO.AudioDAO;
import Entity.AudioEntity;
import com.beaglebuddy.mp3.MP3;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Администратор on 22.07.2014.
 */
public class FileOperation {

    private MP3 mp3;

    FileOperation(String path){
        try {
            mp3 = new MP3(path);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    // Не Чіпати інша реалізація метода що нижще
 //  public static void addAudioFile(File file, String comment,String tags,int userID){
//       try {
//           InputStream input = new FileInputStream(file);
//           ContentHandler handler = new DefaultHandler();
//           Metadata metadata = new Metadata();
//           Parser parser = new Mp3Parser();
//           ParseContext prsCntxt = new ParseContext();
//           parser.parse(input, handler, metadata, prsCntxt);
//           input.close();
//           String nameOfFile = metadata.get("title");
//           String nameOfArtist = metadata.get("xmpDM:artist");
//           String nameOfAlbum = metadata.get("xmpDM:album");
//           String nameOfGenre = metadata.get("xmpDM:genre");
//           AudioEntity temp = new AudioEntity(nameOfFile,nameOfArtist,nameOfAlbum,nameOfGenre);
//           temp.setComment(comment);
//           temp.setTags(tags);
//           int yearOfRelease = Integer.parseInt(metadata.get("xmpDM:releaseDate"));
//           temp.setYear(yearOfRelease);
//
//          String lengthOfAudio = metadata.get("xmpDM:duration"); //длительность в милисикундах
//           int length = (int) (new Double(lengthOfAudio).doubleValue());
//           System.out.println(length);
//           temp.setLength(length);
//           temp.setSize((int) file.length()); // размер
//           temp.setSize(userID);
//           temp.setAccess(0);
//           AudioDAO operation = new AudioDAO();
//           operation.add(temp);
//       } catch(IOException e){
//           e.printStackTrace();
//       }catch(SAXException e){
//           e.printStackTrace();
//       }catch (TikaException e){
//           e.printStackTrace();
//       }
//   }

public static boolean addAudioFile(File file, String tags, int userID){

    AudioEntity temp = new AudioEntity();
    try {
        MP3 mp3File = new MP3(file);

        temp.setName(mp3File.getTitle());
        temp.setArtist(mp3File.getBand());
        temp.setAlbum(mp3File.getAlbum());
        temp.setGenre(mp3File.getMusicType());
        temp.setComment(mp3File.getComments());
        //temp.setTags(tags);
        temp.setYear(mp3File.getYear());
        temp.setType(".mp3");
        temp.setLength(mp3File.getAudioDuration()); // в милисикундах
        temp.setSize(mp3File.getAudioSize());
        temp.setUserid(userID);
        temp.setUpload_date(new Date(System.currentTimeMillis()));  // SQL дата

        new AudioDAO().add(temp);
        return true;
    }catch (Exception e ){
       e.printStackTrace();
        return false;
    }


}


    public void setName(String name){
        mp3.setTitle(name);
        try {
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setArtist(String artist){
        mp3.setBand(artist);
        try {
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setAlbum(String album){
        mp3.setAlbum(album);
        try {
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setGenre(String genre){
        mp3.setMusicType(genre);
        try {
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setComment(String comment){
        mp3.setComments(comment);
        try {
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setYearOfRelease(int year){
        mp3.setYear(year);
        try {
            mp3.save();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    }
