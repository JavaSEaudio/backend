package rest.service;


import DAO.SessionDAO;
import DAO.util.Factory;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.net.SocketException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

@Path("get")
public class AudioGet {
    @GET
    @Path("/audio")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAudio(@QueryParam(value = "id") int id)throws IOException, WebApplicationException, SocketException {
        String path = "C://upload//audio//"+id+".mp3";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("");
            Response.ok().build();
        }
        if (!file.exists() || file.isDirectory()) {
            Response.ok().build();
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();
    }


    @GET
    @Path("/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImage(@QueryParam(value = "id") int id) {
        String path = "C://upload//image//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//image//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//image//0.jpg");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();
    }
    @GET
    @Path("/avatar")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAvatar(@QueryParam(value = "id") int id)throws IOException, WebApplicationException, SocketException {
        String path = "C://upload//user//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//user//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//user//0.jpg");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName()+ "\"" ) //optional
                .build();
    }

    @GET
    @Path("/private")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPrivate(@QueryParam(value = "id") int id,
                               @CookieParam("name") String uid) throws IOException, WebApplicationException, SocketException {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1){
            return Response.status(400).entity("login pls").build();
        }

        String path = "C://upload//private//"+id+".mp3";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName()+ "\"" ) //optional
                .build();
    }
    @GET
    @Path("/privateImage")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPrivateImage(@QueryParam(value = "id") int id,
                                    @CookieParam("name") String uid) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1){
            return Response.status(400).entity("login pls").build();
        }
        String path = "C://upload//privateImage//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//image//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//image//0.jpg");
        }
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
                .build();
    }
}
