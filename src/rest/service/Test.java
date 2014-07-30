package rest.service;

import DAO.util.Factory;
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



}