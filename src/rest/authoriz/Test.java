package rest.authoriz;

import DAO.SessionDAO;
import util.Factory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/test")
public class Test {
    @GET
    @Path("/check")
    public Response check(@CookieParam(value = "name") String uid) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        if(sessionDAO.haveKey(uid) != -1)
            System.out.println("cookies detected");
        else
            System.out.println("where is cookies?");
        return Response.ok().status(400).build();
    }

}