package rest.file;

import java.io.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import BusinessLogic.FileOperation;
import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.TagDAO;
import Entity.AudioEntity;
import Entity.PrivateEntity;
import Entity.TagEntity;
import Entity.UserEntity;
import com.sun.jersey.multipart.FormDataParam;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.*;


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
                               @QueryParam(value = "access") String access,
                               @FormDataParam("audioFile") InputStream uploadAudioStream
    ) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("Upload File: file can not write: sign in");
            return Response.status(402).entity("File not uploaded! Please sign in!!!").build();
        }


        if(!access.equals("true")){
            return savePublic(nameA, artist, album, uploadAudioStream, userid);
        } else {
            return savePrivate(nameA, artist, album, uploadAudioStream, userid);
        }
    }

    @Path("/uploadImage")
    @POST
    @Consumes("multipart/form-data")
    public Response uploadFile(@CookieParam(value = "name") String uid,
                               @QueryParam(value = "idA") Integer idA,
                               @FormDataParam("image") InputStream uploadImageStream) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("DUpload Image: not logged in");
            return Response.status(401).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioEntity audioEntity = Factory.getInstance().getAudioDAO().getById(idA);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {
                log.info("Delete File: not access");
                return Response.status(402).entity("You can't edit this file!!").build();
            }
        }
        String uploadImageLocation = "C://upload//image//" + idA + ".jpg";
        try {
            FileWrite.writeToFile(uploadImageStream, uploadImageLocation);
        } catch (Exception e) {
            log.info("Upload Image: file can not write");
        }
        Factory.getInstance().getAudioDAO().change(audioEntity);
        log.info("Upload Image: success");
        return Response.status(200).build();
    }


    @GET
     @Path("/delete")
     public Response deleteFile(@CookieParam(value = "name") String uid,
                                @QueryParam("id") int idFile
    ) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("Delete File: not logged in");
            return Response.status(400).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(idFile);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {
                log.info("Delete File: not access");
                return Response.status(400).entity("You can't edit this file!!").build();
            }
        }

        new File("C://upload//audio//"+audioEntity.getId()+".mp3").delete();
        new File("C://upload//audio//"+audioEntity.getId()+".ogg").delete();
        new File("C://upload//audio//"+audioEntity.getId()+".wav").delete();
        new File("C://upload//audio//"+audioEntity.getId()+".tmp").delete();
        new File("C://upload//image//"+audioEntity.getId()+".jpg").delete();
//        file = new File("C://upload//audio//cut"+audioEntity.getId()+".mp3");
//        file.delete();
        try {
            Factory.getInstance().getLikeDAO().delete(Factory.getInstance().getLikeDAO().getByAudio(audioEntity.getId()));
        } catch (Exception e){}
        audioDAO.delete(audioEntity);
        log.info("Delete File:  deleted");
        return Response.status(200).build();



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
                             @FormParam("access") String access,
                             @FormParam("youTube") String linkTube
    ) {
        int userid = Sessions.uid(uid);
        if (userid == -1) {
            log.info("Edit File: not logged in");
            return Response.status(404).entity("You can't edit file! Please sign in!!!").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(id);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {
                log.info("Edit File: not access");
                return Response.status(400).entity("You can't edit this file!!").build();
            }
        }

        FileOperation fileEdit = new FileOperation("C://upload//audio//"+audioEntity.getId()+".mp3");
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
                TagDAO tDAO = Factory.getInstance().getTagDAO();
                List<String> s = new ArrayList<String>();
                String result = "";
                s.addAll(StringUtil.tagParse(comment));
                System.out.println(s);
                fileEdit.setComments(comment);
                for (int i = 0; i < s.size(); i ++) {
                    if (i == s.size() - 1) {
                        result += s.get(i);
                        break;
                    }
                    result += s.get(i) + " ";
                }
                if (!result.equals("")) {
                    for (int i = 0; i < s.size(); i ++) {
                        String temp = s.get(i);
                        if (s.get(i).charAt(s.get(i).length() - 1) == '\n') {
                            temp = s.get(i).substring(0,(s.get(i).length() - 1));
                        }
                        if (tDAO.getByName(temp) == null) {
                            TagEntity tag = new TagEntity(temp);
                            tag.addAudioIds(id);
                            tDAO.add(tag);
                        }
                        else {
                            TagEntity tag = tDAO.getByName(temp);
                            tag.addAudioIds(id);
                            tDAO.change(tag);
                        }
                    }
                }

            } catch (Exception e) {}
            audioEntity.setComment(comment);
        }

        if ((genre != null) || (!genre.equals(""))) {
            try {
                TagDAO tDAO = Factory.getInstance().getTagDAO();
                String result = "";
                String[] s = genre.split(" ");
                for (int i = 0; i < s.length; i ++) {

                    if (tDAO.getByName(s[i]) == null) {
                        TagEntity tag = new TagEntity(s[i]);
                        tag.addAudioIds(id);
                        tDAO.add(tag);
                    }
                    else {
                        TagEntity tag = tDAO.getByName(s[i]);
                        tag.addAudioIds(id);
                        tDAO.change(tag);
                    }
                }
                fileEdit.setGenre(genre);

            } catch (Exception e) {}
            audioEntity.setGenre(genre);
        }
        if ((year >= 0) && year < 2015) {
            try {
                fileEdit.setYear(year);

            } catch (Exception e) {}
            audioEntity.setYear(year);
        }
        if (price >= 0) {
            audioEntity.setPrice(price);
        } else {
            log.info("Edit File: price wrong");
            return Response.status(403).entity("price wrong").build();
        }
        if(linkTube != null && !linkTube.equals("") && !linkTube.equals(" ")){
            audioEntity.setLinkTube(linkTube);
        }
        Factory.getInstance().getAudioDAO().change(audioEntity);



        int acs = -1;
        if(access == null);
        else if(access.equals("private")) acs = 1;

        if(acs != 1) {
            log.info("Edit File: success");
            return Response.status(200).entity("success").build();
        } else {
            PrivateEntity privateEntity = new PrivateEntity(audioEntity);
            try {
                Factory.getInstance().getLikeDAO().delete(Factory.getInstance().getLikeDAO().getByAudio(audioEntity.getId()));
            } catch (Exception e) {}
            Factory.getInstance().getPrivateDAO().add(privateEntity);
            File source = new File("C://upload//audio//"+audioEntity.getId()+".mp3");
            File destination = new File("C://upload//private//"+privateEntity.getId()+".mp3");
            try {
                CopyFiles.copyFileUsingStream(source, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            source = new File("C://upload//audio//"+audioEntity.getId()+".wav");
            destination = new File("C://upload//private//"+privateEntity.getId()+".mp3");
            try {
                CopyFiles.copyFileUsingStream(source, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            source = new File("C://upload//audio//"+audioEntity.getId()+".ogg");
            destination = new File("C://upload//private//"+privateEntity.getId()+".mp3");
            try {
                CopyFiles.copyFileUsingStream(source, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
//          new File("C://upload//audio//cut"+audioEntity.getId()+".mp3").delete();
            source.delete();
            source = new File("C://upload//image//"+audioEntity.getId()+".jpg");
            destination = new File("C://upload//privateImage//"+privateEntity.getId()+".jpg");
            try {
                CopyFiles.copyFileUsingStream(source, destination);
            } catch (Exception e) {
                e.printStackTrace();
            }
            source.delete();
            audioDAO.delete(audioEntity);

            log.info("Edit File: private success");
            return Response.status(200).entity("private success").build();
        }
    }

    private Response savePublic(String nameA,
                                String artist,
                                String album,
                                InputStream uploadAudioStream,
                                int userid
    ){
        AudioEntity audioEntity = new AudioEntity(nameA, artist, "luaged", userid);
        Factory.getInstance().getAudioDAO().add(audioEntity);
        String uploadedFileLocation = "C://upload//audio//" + audioEntity.getId() + ".tmp";
        File file = new File(uploadedFileLocation);


        try {
            FileWrite.writeToFile(uploadAudioStream, uploadedFileLocation);
        } catch (Exception e) {
            log.info("Upload File: Public file can not write");
            new File(uploadedFileLocation).delete();
            Factory.getInstance().getAudioDAO().delete(audioEntity);
            return Response.ok("can not").status(401).build();
        }
//      Cut.file(uploadedFileLocation);
        try {
            FileOperation fileOperation = new FileOperation(uploadedFileLocation);
            if (nameA == null || nameA.equals("") || nameA.equals(" ")) {
                nameA = fileOperation.getName();
                if (nameA == null || nameA.equals("") || nameA.equals(" ")) {
                    nameA = "Unknown";
                }
                audioEntity.setName(nameA);
            } else {
                try {fileOperation.setName(nameA);} catch (Exception e) {}
            }
            if (artist == null || artist.equals("") || artist.equals(" ")) {
                artist = fileOperation.getArtist();
                if (artist == null || artist.equals("") || artist.equals(" ")) {
                    artist = "Unknown";
                }
                audioEntity.setArtist(artist);
            } else {
                try {fileOperation.setArtist(artist);} catch (Exception e) {}
            }
            if (album == null || album.equals("") || album.equals(" ")) {
                album = fileOperation.getAlbum();
                if (album == null || album.equals("") || album.equals(" ")) {
                    album = "Unknown";
                }
                audioEntity.setAlbum(album);
            } else {
                try {fileOperation.setAlbum(album);} catch (Exception e) {}
            }
            fileOperation.getImage("image//" + audioEntity.getId());
            audioEntity.setType(".mp3");
            audioEntity.setUserid(userid);
            try { audioEntity.setYear(fileOperation.getYear());       } catch (Exception e) {}
            try { audioEntity.setComment(fileOperation.getComments());} catch (Exception e) {}
            try { audioEntity.setGenre(fileOperation.getGenre());     } catch (Exception e) {}
            try { audioEntity.setLength(fileOperation.getLength());   } catch (Exception e) {}
            try { audioEntity.setSize(fileOperation.getSize());       } catch (Exception e) {}
            Factory.getInstance().getAudioDAO().change(audioEntity);
        } catch (Exception e) {
            new File(uploadedFileLocation).delete();
            new File("C://upload//audio//"+audioEntity.getId()+".mp3").delete();
            new File("C://upload//audio//"+audioEntity.getId()+".wav").delete();
            new File("C://upload//audio//"+audioEntity.getId()+".ogg").delete();
            Factory.getInstance().getAudioDAO().delete(audioEntity);
            return Response.status(406).build();
        }
        new File(uploadedFileLocation).delete();
        new ThreadFuck2(file, audioEntity.getId()).start();

        return Response.status(200).build();
    }

    private Response savePrivate(String nameA,
                                 String artist,
                                 String album,
                                 InputStream uploadAudioStream,
                                 int userid
    ){
        PrivateEntity privateEntity = new PrivateEntity(nameA, artist, "luaged");
        Factory.getInstance().getPrivateDAO().add(privateEntity);
        String uploadedFileLocation = "C://upload//private//" + privateEntity.getId() + ".tmp";
        File file = new File(uploadedFileLocation);

        try {
            FileWrite.writeToFile(uploadAudioStream, uploadedFileLocation);
        } catch (Exception e) {
            log.info("Upload File: Public file can not write");
            new File(uploadedFileLocation).delete();
            Factory.getInstance().getPrivateDAO().delete(privateEntity);
            return Response.ok("can not").status(401).build();
        }
        try {
            FileOperation fileOperation = new FileOperation(uploadedFileLocation);
            if (nameA == null || nameA.equals("") || nameA.equals(" ")) {
                nameA = fileOperation.getName();
                if (nameA == null || nameA.equals("") || nameA.equals(" ")) {
                    nameA = "Unknown";
                }
                privateEntity.setName(nameA);
            } else {
                try {fileOperation.setName(nameA);} catch (Exception e) {}
            }
            if (artist == null || artist.equals("") || artist.equals(" ")) {
                artist = fileOperation.getArtist();
                if (artist == null || artist.equals("") || artist.equals(" ")) {
                    artist = "Unknown";
                }
                privateEntity.setArtist(artist);
            } else {
                try {fileOperation.setArtist(artist);} catch (Exception e) {}
            }
            if (album == null || album.equals("") || album.equals(" ")) {
                album = fileOperation.getAlbum();
                if (album == null || album.equals("") || album.equals(" ")) {
                    album = "Unknown";
                }
                privateEntity.setAlbum(album);
            } else {
                try {fileOperation.setAlbum(album);} catch (Exception e) {}
            }
            fileOperation.getImage("privateImage//"+privateEntity.getId());
            privateEntity.setUserid(userid);
            Factory.getInstance().getPrivateDAO().change(privateEntity);
        } catch (Exception e) {
            new File(uploadedFileLocation).delete();
            new File("C://upload//private//"+privateEntity.getId()+".mp3").delete();
            new File("C://upload//private//"+privateEntity.getId()+".wav").delete();
            new File("C://upload//private//"+privateEntity.getId()+".ogg").delete();
            Factory.getInstance().getPrivateDAO().delete(privateEntity);
            return Response.status(400).build();
        }
        new File(uploadedFileLocation).delete();
        new ThreadFuck(file, userid).start();

        return Response.status(200).build();

    }
}