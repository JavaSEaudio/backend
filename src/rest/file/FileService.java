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
import Entity.UserEntity;
import com.sun.jersey.multipart.FormDataParam;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.ProjectPath;
import util.StringUtil;

@Path("/file")
public class FileService {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@CookieParam(value = "name") String uid,
                               @FormDataParam("audioFile") InputStream uploadAudioStream,
                               @FormDataParam("image") InputStream uploadImageStream,
                               @FormParam("name") String name,
                               @FormParam("artist") String artist,
                               @FormParam("album") String album
    ) {

        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            return Response.status(200).entity("File not uploaded! Please sign in!!!").build();
        }

        AudioEntity audioEntity = new AudioEntity(name, artist, album);
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        audioDAO.add(audioEntity);
        int path = audioEntity.getId();
        String uploadImageLocation = System.getProperty("user.dir")+"//web//image//" + path + ".jpg";
        String uploadAudioLocation = System.getProperty("user.dir")+"//web//audio//" + path + ".mp3";
        writeToFile(uploadAudioStream, uploadAudioLocation);
        try {
            if (uploadImageStream.available() <= 0) {
                uploadImageLocation = System.getProperty("user.dir")+"//web//image//test.jpg";
            }
        } catch (IOException e) {}
        writeToFile(uploadImageStream, uploadImageLocation);
        String output = "File uploaded to : " + uploadAudioLocation;
        try {
            saveFile(uploadAudioLocation, name, album, artist, audioEntity, userid, uploadImageLocation);
        } catch (Exception e) {
            return Response.status(200).entity("Failed upload file :(").build();
        }
        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/delete")
    public Response deleteFile(@CookieParam(value = "name") String uid,
                               @QueryParam("id") int idFile
    ) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            log.info("Delete File: not logged in" );
            return Response.status(200).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(idFile);
        if(userid != audioEntity.getUserid()){
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if(user.getAccess() < 1) {
                log.info("Delete File: not access");
                return Response.status(200).entity("You can't edit this file!!").build();
            }
        }
        File file = new File(ProjectPath.getAudioPath(audioEntity));
        if(file.delete()){
            audioDAO.delete(audioEntity);
            log.info("Delete File: " + file.getName() + " deleted");
            return Response.status(200).build();
        }
        log.info("Delete File: " + file.getName() + " not removed");
        return Response.status(200).entity("not removed").build();

    }

    @POST
    @Path("/edit")
    public Response editFile(  @CookieParam(value = "name") String uid,
                               @QueryParam("idfile") int id,
                               @FormParam("title")  String name,
                               @FormParam("album") String album,
                               @FormParam("artist") String artist,
                               @FormParam("comment") String comment,
                               @FormParam("genre") String genre,
                               @FormParam("year") int year,
                               @FormParam("price") double price,
                               @QueryParam("access") String access
    ) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            log.info("Edit File: not logged in");
            return Response.status(200).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(id);
        if(userid != audioEntity.getUserid()){
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if(user.getAccess() < 1) {
                log.info("Edit File: not access");
                return Response.status(200).entity("You can't edit this file!!").build();
            }
        }
        name = StringUtil.parse(name);
        album = StringUtil.parse(album);
        artist = StringUtil.parse(artist);
        comment = StringUtil.parse(comment);
        genre = StringUtil.parse(genre);

        FileOperation fileEdit = new FileOperation(ProjectPath.getAudioPath(audioEntity));
        if((name == null) || (name.equals("")) || name.equals(fileEdit.getName())== true){
            fileEdit.setName(name);
            audioEntity.setName(name);
        }
        if((album == null) || (album.equals("")) || album.equals(fileEdit.getAlbum())== true){
            fileEdit.setAlbum(album);
            audioEntity.setAlbum(album);
        }
        if((artist == null) || (artist.equals("")) || artist.equals(fileEdit.getArtist()) == true){
            fileEdit.setArtist(artist);
            audioEntity.setArtist(artist);
        }
        if((comment == null) || (comment.equals("")) || comment.equals(fileEdit.getComments())== true){
            fileEdit.setComments(comment);
            audioEntity.setComment(comment);
        }

        if((genre == null) || (genre.equals("")) || genre.equals(fileEdit.getGenre())!= true){
            fileEdit.setGenre(genre);
            audioEntity.setGenre(genre);
        }
        if((year >= 0) || year < 2015) {
            fileEdit.setYear(year);
            audioEntity.setYear(year);
        }
        if(price >= 0) {
            audioEntity.setPrice(price);
        } else {
            log.info("Edit File: price wrong");
            return Response.status(203).entity("price wrong").build();
        }
        log.info("Edit File: success");
        return Response.status(200).build();
    }


    private void saveFile(String audioLocation, String name, String album, String artist,
                          AudioEntity audioEntity, int userid, String imgLocation) {
        try{
            FileOperation fileOperation = new FileOperation(audioLocation);
            if(name.equals("") || name.equals(" ") || name == null) {
                audioEntity.setName( fileOperation.getName() );
            } else {
                fileOperation.setName(name);
            }
            if(artist.equals("") || artist.equals(" ") || artist == null) {
                audioEntity.setArtist(fileOperation.getArtist());
            } else {
                fileOperation.setArtist(artist);
            }
            if(album.equals("") || album.equals(" ") || album == null) {
                audioEntity.setAlbum(fileOperation.getAlbum());
            } else {
                fileOperation.setAlbum(album);
            }
            audioEntity.setYear(fileOperation.getYear());
            audioEntity.setComment(fileOperation.getComments());
            audioEntity.setAccess(0);
            audioEntity.setGenre(fileOperation.getGenre());
            audioEntity.setLength(fileOperation.getLength());
            audioEntity.setLinkFile(audioLocation);
            audioEntity.setSize(fileOperation.getSize());
            audioEntity.setType(".mp3");
            audioEntity.setUserid(userid);
            audioEntity.setLinkImage(imgLocation);
            AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
            audioDAO.change(audioEntity);
            log.info("Upload File: audio save in the DB success");
        } catch (Exception e) {
            log.info("Upload File: audio is not store in the DB ");
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
            log.info("Write File: exception");
            e.printStackTrace();
        }
    }


}