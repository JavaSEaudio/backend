package rest.file;

import java.io.*;
import java.io.InputStream;
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
import util.FileWrite;
import util.ProjectPath;
import util.StringUtil;


@Path("/file")
public class FileService {
    private final static Logger log = Logger.getLogger("com.audiostorage.report");

    @Path("/uploadAudio")
    @POST
    @Consumes("multipart/form-data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@CookieParam(value = "name") String uid,
                               @QueryParam(value = "nameAudio") String nameA,
                               @QueryParam(value = "artist") String artist,
                               @QueryParam(value = "album") String album,
                               @FormDataParam("audioFile") InputStream uploadAudioStream
    ) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if (userid == -1) {
            log.info("Upload File: file can not write: sign in");
            return Response.status(202).entity("File not uploaded! Please sign in!!!").build();
        }
        AudioEntity audioEntity = new AudioEntity(nameA, artist, album);
        Factory.getInstance().getAudioDAO().add(audioEntity);
        String uploadedFileLocation = "C://upload//audio//" + audioEntity.getId() + ".mp3";
        try {
            FileWrite.writeToFile(uploadAudioStream, uploadedFileLocation);
        } catch (Exception e) {
            log.info("Upload File: file can not write");
            Factory.getInstance().getAudioDAO().delete(audioEntity);
            return Response.ok("can not").status(201).build();
        }
        try {
            saveFile(uploadedFileLocation, nameA, album, artist, audioEntity, userid, "/rest/get/image?id=0");
        } catch (Exception e) {
            File file = new File(uploadedFileLocation);
            file.delete();
            Factory.getInstance().getAudioDAO().delete(audioEntity);
            return Response.ok("chren").status(203).build();
        }
        return Response.status(200).build();
    }

    @Path("/uploadImage")
    @POST
    @Consumes("multipart/form-data")
    public Response uploadFile(@CookieParam(value = "name") String uid,
                               @QueryParam(value = "idA") Integer idA,
                               @FormDataParam("image") InputStream uploadImageStream) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if (userid == -1) {
            log.info("DUpload Image: not logged in");
            return Response.status(201).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioEntity audioEntity = Factory.getInstance().getAudioDAO().getById(idA);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {
                log.info("Delete File: not access");
                return Response.status(200).entity("You can't edit this file!!").build();
            }
        }
        String uploadImageLocation = ProjectPath.getPath() + "image//" + idA + ".jpg";
        try {
            FileWrite.writeToFile(uploadImageStream, uploadImageLocation);
        } catch (Exception e) {
            log.info("Upload Image: file can not write");
        }
        audioEntity.setLinkImage("/rest/get/image?id=" + idA);
        Factory.getInstance().getAudioDAO().change(audioEntity);
        log.info("Upload Image: success");
        return Response.status(200).build();
    }


    @POST
    @Path("/delete")
    public Response deleteFile(@CookieParam(value = "name") String uid,
                               @QueryParam("id") int idFile
    ) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if (userid == -1) {
            log.info("Delete File: not logged in");
            return Response.status(200).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(idFile);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {
                log.info("Delete File: not access");
                return Response.status(200).entity("You can't edit this file!!").build();
            }
        }
        File file = new File(ProjectPath.getAudioPath(audioEntity));
        if (file.delete()) {
            audioDAO.delete(audioEntity);
            log.info("Delete File: " + file.getName() + " deleted");
            return Response.status(200).build();
        }
        log.info("Delete File: " + file.getName() + " not removed");
        return Response.status(200).entity("not removed").build();

    }

    @POST
    @Path("/edit")
    public Response editFile(@CookieParam(value = "name") String uid,
                             @QueryParam("idfile") int id,
                             @FormParam("title") String name,
                             @FormParam("album") String album,
                             @FormParam("artist") String artist,
                             @FormParam("comment") String comment,
                             @FormParam("genre") String genre,
                             @FormParam("year") int year,
                             @FormParam("price") double price,
                             @FormParam("access") String access
    ) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if (userid == -1) {
            log.info("Edit File: not logged in");
            return Response.status(200).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(id);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {
                log.info("Edit File: not access");
                return Response.status(200).entity("You can't edit this file!!").build();
            }
        }

        FileOperation fileEdit = new FileOperation(ProjectPath.getAudioPath(audioEntity));
        if ((name != null) || (!name.equals(""))) {
            try {
                fileEdit.setName(name);

            } catch (Exception e) {}
            audioEntity.setName(name);
        }
        if ((album != null) || (!album.equals(""))) {
            try {
                fileEdit.setAlbum(album);

            } catch (Exception e) {}
            audioEntity.setAlbum(album);
        }
        if ((artist != null) || (!artist.equals(""))) {
            try {
                fileEdit.setArtist(artist);

            } catch (Exception e) {}
            audioEntity.setArtist(artist);
        }
        if ((comment != null) || (!comment.equals(""))) {
            try {
                fileEdit.setComments(comment);

            } catch (Exception e) {}
            audioEntity.setComment(comment);
        }

        if ((genre != null) || (!genre.equals(""))) {
            try {
                fileEdit.setGenre(genre);

            } catch (Exception e) {}
            audioEntity.setGenre(genre);
        }
        if ((year >= 0) || year < 2015) {
            try {
                fileEdit.setYear(year);

            } catch (Exception e) {}
            audioEntity.setYear(year);
        }
        if (price >= 0) {
            audioEntity.setPrice(price);
        } else {
            log.info("Edit File: price wrong");
            return Response.status(203).entity("price wrong").build();
        }
        Factory.getInstance().getAudioDAO().change(audioEntity);
        log.info("Edit File: success");
        return Response.status(200).entity("suc").build();
    }


    private void saveFile(String audioLocation, String name, String album, String artist,
                          AudioEntity audioEntity, int userid, String imgLocation) {
        try {
            FileOperation fileOperation = new FileOperation(audioLocation);
            if (name == null || name.equals("") || name.equals(" ")) {
                name = fileOperation.getName();
                if(name == null || name.equals("") || name.equals(" ")) {
                    name = "Unknown";
                }
                audioEntity.setName(name);
            } else {
                try { fileOperation.setName(name); } catch (Exception e){}
            }
            if (artist == null || artist.equals("") || artist.equals(" ")) {
                artist = fileOperation.getArtist();
                if(artist == null || artist.equals("") || artist.equals(" ")) {
                    artist = "Unknown";
                }
                audioEntity.setArtist(artist);
            } else {
                try { fileOperation.setArtist(artist); } catch (Exception e){}
            }
            if (album == null || album.equals("") || album.equals(" ")) {
                album = fileOperation.getAlbum();
                if(album == null || album.equals("") || album.equals(" ")) {
                    album = "Unknown";
                }
                audioEntity.setAlbum(album);
            } else {
                try { fileOperation.setAlbum(album); } catch (Exception e){}
            }
            if(fileOperation.getImage(audioEntity.getId())){
                audioEntity.setLinkImage("/rest/get/image?id="+audioEntity.getId());
            } else {
                audioEntity.setLinkImage(imgLocation);
            }
            audioEntity.setType(".mp3");
            audioEntity.setAccess(0);
            audioEntity.setUserid(userid);
            audioEntity.setLinkFile("/rest/get/audio?id="+audioEntity.getId());

            try { audioEntity.setYear(fileOperation.getYear()); } catch (Exception e){}
            try { audioEntity.setComment(fileOperation.getComments()); } catch (Exception e){}
            try { audioEntity.setGenre(fileOperation.getGenre()); } catch (Exception e){}
            try { audioEntity.setLength(fileOperation.getLength()); } catch (Exception e){}
            try { audioEntity.setSize(fileOperation.getSize()); } catch (Exception e){}


            Factory.getInstance().getAudioDAO().change(audioEntity);
            log.info("Upload File: audio save in the DB success");
        } catch (Exception e) {
            log.info("Upload File: audio is not store in the DB ");
            throw new RuntimeException();
        }
    }
}