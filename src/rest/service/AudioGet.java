package rest.service;


import BusinessLogic.Sessions;
import DAO.util.Factory;
import DTO.UserDTO;
import util.Convert;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.SocketException;

@Path("get")
public class AudioGet {
    @GET
    @Path("/audio")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAudio(@CookieParam("name") String uid,
                             @QueryParam(value = "id") int id,
                             @QueryParam(value = "ext") String extension
    )throws IOException, WebApplicationException, SocketException {
        String path = "C://upload//audio//"+id+"."+extension;
        int userid = Sessions.uid(uid);
        if(userid == -1) {
            Response.ok().build();
        }
        System.out.println(extension);
        boolean bool = false;
        for(int i : new UserDTO(Factory.getInstance().getUserDAO().getById(userid)).getBuyListArray()){
            if(i == id) {
                bool = true;
                break;
            }
        }
        if(!bool){
            Response.ok().build();
        }
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            Convert.convertFile(new File("C://upload//audio//"+id+".mp3"), "C://upload//audio//"+id, extension);
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
                               @CookieParam("name") String uid,
                               @QueryParam(value = "ext") String extension) throws IOException, WebApplicationException, SocketException {
        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(400).entity("login pls").build();
        }
        if(
            Factory.getInstance().getPrivateDAO().getById(id).getUserid() != userid
                ) {
            return Response.status(400).entity("not access").build();
        }


        String path = "C://upload//private//"+id+"."+extension;
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
        int userid = Sessions.uid(uid);
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
