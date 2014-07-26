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

@Path("/file")
public class FileService {

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@CookieParam(value = "name") String uid,
                               @FormDataParam("audioFile") InputStream uploadedInputStream
    ) {

        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            return Response.status(200).entity("File not uploaded! Please sign in!!!").build();
        }
        String name = "";
        String album = "";
        String artist = "";
        AudioEntity audioEntity = new AudioEntity(name, artist, album);
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        audioDAO.add(audioEntity);
        int path = audioEntity.getId();
        String uploadedFileLocation = "c://upload//audio//" + path + ".mp3";
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded to : " + uploadedFileLocation;
        try {
            saveFile(uploadedFileLocation, name, album, artist, audioEntity, userid);
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
                          AudioEntity audioEntity, int userid) {
        try{
            FileOperation fileOperation = new FileOperation(location);
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
            audioEntity.setLinkFile(location);
            audioEntity.setSize(fileOperation.getSize());
            audioEntity.setType(".mp3");
            audioEntity.setUserid(userid);
            audioEntity.setBitrate(0);
            audioEntity.setLinkImage("netuuuuu");
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
}