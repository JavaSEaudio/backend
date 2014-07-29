package rest.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import BusinessLogic.FileOperation;
import DAO.AudioDAO;
import DAO.SessionDAO;
import Entity.AudioEntity;
import com.sun.jersey.multipart.FormDataParam;
import DAO.util.Factory;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class FileService {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@CookieParam(value = "name") String uid,
                               @FormDataParam("audioFile") InputStream uploadAudioStream
//                               @FormDataParam("image") InputStream uploadImageStream,
//                               @FormDataParam("nameA") final String nameA
//                               @FormDataParam("artist") String artist
//                               @FormDataParam("album") String album

    ) {

        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            return Response.status(200).entity("File not uploaded! Please sign in!!!").build();
        }

        String nameA = "";
        String album = "";
        String artist = "";

        AudioEntity audioEntity = new AudioEntity(nameA, artist, album);
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        audioDAO.add(audioEntity);
        int path = audioEntity.getId();
        String uploadImageLocation = "C:/web//image/" + path + ".jpg";     // String uploadImageLocation = System.getProperty("user.dir")+"C://web//image//" + path + ".jpg";
        String uploadAudioLocation = "C:/web//audio/" + path + ".mp3";
        writeToFile(uploadAudioStream, uploadAudioLocation);

       // writeToFile(uploadImageStream, uploadImageLocation);
        String output = "File uploaded to : " + uploadAudioLocation;
        try {
            saveFile(uploadAudioLocation, nameA, album, artist, audioEntity, userid, uploadImageLocation);
        } catch (Exception e) {
            return Response.status(200).entity("Failed upload file :(").build();
        }
        return Response.status(200).entity(output).build();
    }

//    @POST
//    @Path("delete")
//    public Response deleteFile(@CookieParam(value = "name") String uid
//                               @QueryParam("id") int idFile
//
//
//
//    ) {
//
//    }


    private void saveFile(String location, String name, String album, String artist,
                          AudioEntity audioEntity, int userid, String imgLocation) {
        try{
            FileOperation fileOperation = new FileOperation(location);
            if(name == null || name.equals("") || name.equals(" ")) {
                audioEntity.setName( fileOperation.getName() );
            } else {
                fileOperation.setName(name);
            }
            if(artist == null || artist.equals("") || artist.equals(" ") ) {
                audioEntity.setArtist(fileOperation.getArtist());
            } else {
                fileOperation.setArtist(artist);
            }
            if(album == null || album.equals("") || album.equals(" ")) {
                audioEntity.setAlbum(fileOperation.getAlbum());
            } else {
                fileOperation.setAlbum(album);
            }
            audioEntity.setYear(fileOperation.getYear());
            audioEntity.setComment(fileOperation.getComments());
            audioEntity.setAccess(0);
            audioEntity.setGenre(fileOperation.getGenre());
            audioEntity.setLength(fileOperation.getLength());
            audioEntity.setLinkFile(location);
            audioEntity.setSize(fileOperation.getSize());
            audioEntity.setType(".mp3");
            audioEntity.setUserid(userid);
            audioEntity.setLinkImage(imgLocation);
            AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
            audioDAO.change(audioEntity);
            System.out.println("Save file in DB success");
        } catch (Exception e) {
            System.out.println("Problem with save file in DB");
        }
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/edit")
    public Response editFile(  @CookieParam(value = "name") String uid,
                               @QueryParam("idfile") int id,
                               @FormParam("title")  String name,
                               @FormParam("album") String album,
                               @FormParam("artist") String artist,
                               @FormParam("comment") String comment,
                               @FormParam("genre") String genre

    ) {
          System.out.println("llaa");
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            return Response.status(200).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(id);
        if(userid != audioEntity.getUserid()){
            return Response.status(200).entity("You can't edit this file!!").build();
        }
       FileOperation fileEdit = new FileOperation(audioEntity.getLinkFile());
       if((name != "") && (name != null) && name.equals(fileEdit.getName())!= true){
           fileEdit.setName(name);
           audioEntity.setName(name);
       }
        if((album != "") && (album != null) && album.equals(fileEdit.getAlbum())!= true){
            fileEdit.setAlbum(album);
            audioEntity.setAlbum(album);
        }
        if((artist != "") && (artist != null) && artist.equals(fileEdit.getArtist())!= true){
            fileEdit.setArtist(artist);
            audioEntity.setArtist(artist);
        }
        if((comment != "") && (comment != null) && comment.equals(fileEdit.getComments())!= true){
            fileEdit.setComments(comment);
            audioEntity.setComment(comment);
        }

        if((genre != "") && (genre != null) && genre.equals(fileEdit.getGenre())!= true){
            fileEdit.setGenre(genre);
            audioEntity.setGenre(genre);
        }

        return Response.status(200).build();
    }


}