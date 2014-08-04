package rest.file;

import java.io.*;
import java.io.InputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import BusinessLogic.FileOperation;
import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.SessionDAO;
import Entity.AudioEntity;
import Entity.PrivateEntity;
import Entity.UserEntity;
import com.sun.jersey.multipart.FormDataParam;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.CopyFiles;
import util.FileWrite;
import util.ProjectPath;


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
        System.out.println();
        int acs= -1;
        if(access.equals("true")) acs = 1;
        String uploadedFileLocation;
        AudioEntity audioEntity = null;
        PrivateEntity privateEntity = null;
        if(acs != 1) {
            audioEntity = new AudioEntity(nameA, artist, album);
            Factory.getInstance().getAudioDAO().add(audioEntity);
            uploadedFileLocation = "C://upload//audio//" + audioEntity.getId() + ".mp3";
        } else {
            privateEntity = new PrivateEntity(nameA, artist, album);
            Factory.getInstance().getPrivateDAO().add(privateEntity);
            uploadedFileLocation = "C://upload//private//" + privateEntity.getId() + ".mp3";
        }

        try {
            FileWrite.writeToFile(uploadAudioStream, uploadedFileLocation);
        } catch (Exception e) {
            log.info("Upload File: file can not write");
            new File(uploadedFileLocation).delete();
            if(acs != 1) {
                Factory.getInstance().getAudioDAO().delete(audioEntity);
            } else {
                Factory.getInstance().getPrivateDAO().delete(privateEntity);
            }

            return Response.ok("can not").status(401).build();
        }
        try {
                FileOperation fileOperation = new FileOperation(uploadedFileLocation);
                if (nameA == null || nameA.equals("") || nameA.equals(" ")) {
                    nameA = fileOperation.getName();
                    if(nameA == null || nameA.equals("") || nameA.equals(" ")) {
                        nameA = "Unknown";
                    }
                    if(acs != 1) {
                        audioEntity.setName(nameA);
                    } else {
                        privateEntity.setName(nameA);
                    }
                } else {
                    try { fileOperation.setName(nameA); } catch (Exception e){}
                }
                if (artist == null || artist.equals("") || artist.equals(" ")) {
                    artist = fileOperation.getArtist();
                    if(artist == null || artist.equals("") || artist.equals(" ")) {
                        artist = "Unknown";
                    }
                    if(acs != 1) {
                        audioEntity.setArtist(artist);
                    } else {
                        privateEntity.setArtist(artist);
                    }
                } else {
                    try { fileOperation.setArtist(artist); } catch (Exception e){}
                }
                if (album == null || album.equals("") || album.equals(" ")) {
                    album = fileOperation.getAlbum();
                    if(album == null || album.equals("") || album.equals(" ")) {
                        album = "Unknown";
                    }
                    if(acs != 1) {
                        audioEntity.setAlbum(album);
                    } else {
                        privateEntity.setAlbum(album);
                    }
                } else {
                    try { fileOperation.setAlbum(album); } catch (Exception e){}
                }

                if(acs != 1) {
                    fileOperation.getImage("image//"+audioEntity.getId());
                    audioEntity.setType(".mp3");
                    audioEntity.setUserid(userid);
                    try { audioEntity.setYear(fileOperation.getYear()); } catch (Exception e){}
                    try { audioEntity.setComment(fileOperation.getComments()); } catch (Exception e){}
                    try { audioEntity.setGenre(fileOperation.getGenre()); } catch (Exception e){}
                    try { audioEntity.setLength(fileOperation.getLength()); } catch (Exception e){}
                    try { audioEntity.setSize(fileOperation.getSize()); } catch (Exception e){}
                } else {
                    fileOperation.getImage("privateImage//"+privateEntity.getId());
                    privateEntity.setUserid(userid);
                }
        if(acs != 1) {
            Factory.getInstance().getAudioDAO().change(audioEntity);
        } else {
            Factory.getInstance().getPrivateDAO().change(privateEntity);
        }

                log.info("Upload File: audio save in the DB success");

        } catch (Exception e) {
            if(new File(uploadedFileLocation).delete());
            if(acs != 1) {
                Factory.getInstance().getAudioDAO().delete(audioEntity);
            } else {
                Factory.getInstance().getPrivateDAO().delete(privateEntity);
            }
            return Response.ok("chren").status(403).build();
        }
        return Response.status(200).build();
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

        File file = new File("C://upload//audio//"+audioEntity.getId()+".mp3");
        file.delete();
        file = new File("C://upload//image//"+audioEntity.getId()+".jpg");
        file.delete();
        try {
            Factory.getInstance().getLikeDAO().delete(Factory.getInstance().getLikeDAO().getByAudio(audioEntity.getId()));
        } catch (Exception e){}
        audioDAO.delete(audioEntity);
        log.info("Delete File: " + file.getName() + " deleted");
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
                             @FormParam("access") String access
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

        FileOperation fileEdit = new FileOperation("C://upload//private//"+audioEntity.getId()+".mp3");
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
        Factory.getInstance().getAudioDAO().change(audioEntity);
        int acs = -1;
        if(access == null);
        else if(access.equals("private")) acs = 1;

        if(acs != 1) {
            log.info("Edit File: success");
            return Response.status(200).entity("success").build();
        } else {
            PrivateEntity privateEntity = new PrivateEntity(audioEntity);
            Factory.getInstance().getLikeDAO().delete(Factory.getInstance().getLikeDAO().getByAudio(audioEntity.getId()));
            Factory.getInstance().getPrivateDAO().add(privateEntity);
            File source = new File("C://upload//audio//"+audioEntity.getId()+".mp3");
            File destination = new File("C://upload//private//"+privateEntity.getId()+".mp3");
            try {
                CopyFiles.copyFileUsingStream(source, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}