package rest.service;

import DAO.SessionDAO;
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
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        if(sessionDAO.haveKey(uid) != -1)
            return Response.ok("true").build();
        else
            System.out.println("where is cookies?");
        return Response.ok("false").build();
    }



}