package rest.service;

import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.util.Factory;
import Entity.AudioEntity;
import Entity.UserEntity;
import util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class Test {
    @GET
    @Path("/check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(@CookieParam(value = "name") String uid) {
        int userid = Sessions.uid(uid);
        if(userid != -1) {
            int access = Factory.getInstance().getUserDAO().getById(userid).getAccess();
            if(access == 0) return Response.ok("true").build();
            if(access == 1) return Response.ok("moderator").build();
            if(access == 2) return Response.ok("admin").build();
        }
        return Response.ok("false").build();
    }
    @GET
    @Path("/checkAudio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(@CookieParam(value = "name") String uid,
                          @QueryParam(value = "id") int idfile){
        int userid = Sessions.uid(uid);
        if (userid == -1) {

            return Response.ok("false").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        AudioEntity audioEntity = audioDAO.getById(idfile);
        if (userid != audioEntity.getUserid()) {
            UserEntity user = Factory.getInstance().getUserDAO().getById(userid);
            if (user.getAccess() < 1) {

                return Response.ok("false").build();
            }
        }
        return Response.ok("true").build();
    }

    @GET
    @Path("/checkUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user(@FormParam(value = "login") String login) {
        if(login == null)
            return Response.status(401).entity("false").build();
        if(login.length() < 3)
            return Response.status(402).entity("false").build();
        if(!StringUtil.valid(login))
            return Response.status(403).entity("false").build();
        if(!StringUtil.validLogin(login))
            return Response.status(405).entity("false").build();
        return Response.status(200).entity("true").build();
    }

    @GET
    @Path("/checkMail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mail(@FormParam(value = "mail") String login) {
        if(login == null)
            return Response.status(401).entity("false").build();
        if(login.length() < 3)
            return Response.status(402).entity("false").build();
        if(!StringUtil.valid(login))
            return Response.status(403).entity("false").build();
        return Response.status(200).entity("true").build();
    }

    @GET
    @Path("/checkPass")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pass(@FormParam(value = "passOne") String passOne,
                         @FormParam(value = "passTwo") String passTwo) {
        if(passOne == null)
            return Response.status(401).entity("false").build();
        if(passOne.length() < 3)
            return Response.status(402).entity("false").build();
        if(!passOne.equals(passTwo))
            return Response.status(403).entity("false").build();

        return Response.status(200).entity("true").build();
    }


}