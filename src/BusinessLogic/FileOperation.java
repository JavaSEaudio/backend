package BusinessLogic;

import com.beaglebuddy.mp3.MP3;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(artist.equals("") || artist.equals(" ") || artist == null){
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

    public static String getExpansion (String fileName){
      String expansion;
      //  String regexpAudio = "/\.(?:mp3|wav|og(?:g|a)|flac|midi?|rm|aac|wma|mka|ape)$/";
        String regexpAudio1 = "([^\\s]+(\\.(?i)(mp3|wav|og(?:g|a)|flac|midi?|rm|aac|wma|mka|ape))$)";
        Pattern pattern = Pattern.compile(regexpAudio1);
        Matcher matcher = pattern.matcher(fileName);

        //Проверка является ли файл аудиофайлом
        if(matcher.matches()){
            String [] temp =  fileName.split("\\.");
            //Записиваем расшерение
            expansion = temp[temp.length-1];
        }else {
          expansion = null;
        }
        return expansion;
    }

}



























//// Не Чіпати інша реалізація метода що нижще
////  public static void addAudioFile(File file, String comment,String tags,int userID){
////       try {
////           InputStream input = new FileInputStream(file);
////           ContentHandler handler = new DefaultHandler();
////           Metadata metadata = new Metadata();
////           Parser parser = new Mp3Parser();
////           ParseContext prsCntxt = new ParseContext();
////           parser.parse(input, handler, metadata, prsCntxt);
////           input.close();
////           String nameOfFile = metadata.get("title");
////           String nameOfArtist = metadata.get("xmpDM:artist");
////           String nameOfAlbum = metadata.get("xmpDM:album");
////           String nameOfGenre = metadata.get("xmpDM:genre");
////           AudioEntity temp = new AudioEntity(nameOfFile,nameOfArtist,nameOfAlbum,nameOfGenre);
////           temp.setComment(comment);
////           temp.setTags(tags);
////           int yearOfRelease = Integer.parseInt(metadata.get("xmpDM:releaseDate"));
////           temp.setYear(yearOfRelease);
////
////          String lengthOfAudio = metadata.get("xmpDM:duration"); //длительность в милисикундах
////           int length = (int) (new Double(lengthOfAudio).doubleValue());
////           System.out.println(length);
////           temp.setLength(length);
////           temp.setSize((int) file.length()); // размер
////           temp.setSize(userID);
////           temp.setAccess(0);
////           AudioDAO operation = new AudioDAO();
////           operation.add(temp);
////       } catch(IOException e){
////           e.printStackTrace();
////       }catch(SAXException e){
////           e.printStackTrace();
////       }catch (TikaException e){
////           e.printStackTrace();
////       }
////   }
//public static boolean addAudioFile(File file, String tags, int userID){
//
//    AudioEntity temp = new AudioEntity();
//    try {
//        MP3 mp3File = new MP3(file);
//
////        mp3File.
////        temp.setName(mp3File.getTitle());
////        temp.setArtist(mp3File.getBand());
////        temp.setAlbum(mp3File.getAlbum());
////        temp.setGenre(mp3File.getMusicType());
////        temp.setComment(mp3File.getComments());
////        temp.setYear(mp3File.getYear());
////        temp.setType(".mp3");
////        temp.setLength(mp3File.getAudioDuration()); // в милисикундах
////        temp.setSize(mp3File.getAudioSize());
////        temp.setUserid(userID);
////        temp.setUpload_date(new Date(System.currentTimeMillis()));  // SQL дата
//
////        new AudioDAO().add(temp);
//        return true;
//    }catch (Exception e ){
//       e.printStackTrace();
//        return false;
//    }
//
//
//}
