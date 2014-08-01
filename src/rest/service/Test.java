package rest.service;

import DAO.AudioDAO;
import DAO.util.Factory;
import Entity.AudioEntity;
import Entity.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
public class Test {
    @GET
    @Path("/check")
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(@CookieParam(value = "name") String uid) {
        int id = Factory.getInstance().getSessionDAO().haveKey(uid);
        if(id != -1) {
            int access = Factory.getInstance().getUserDAO().getById(id).getAccess();
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
        int userid = Factory.getInstance().getSessionDAO().haveKey(uid);
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


}