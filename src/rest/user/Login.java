package rest.user;

import BusinessLogic.*;
import Entity.SessionEntity;
import Entity.UserEntity;
import DAO.util.Factory;
import util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/Login")
public class Login {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {

        if (!StringUtil.minMaxLength(login , 2 , 30)  ||
            !StringUtil.minMaxLength(password , 2 , 225)) {

            System.out.println("not valid length or type - login or password");
            return Response.ok("false").build();
        }
        UserEntity user = UserLogic.authorization(login, password);
        if(user != null ) {
            String uid = UserLogic.uid();
            try {
                SessionEntity sess = new SessionEntity(user.getId(), uid);
                Factory.getInstance().getSessionDAO().add(sess);
                NewCookie cookie = new NewCookie("name", uid);
                System.out.println("Logged success");
                return Response.ok("true").cookie(cookie).header("Access-Control-Allow-Origin", "*").build();
            } catch (Exception e) {
                System.out.println("You logged before");
                return Response.ok("false").build();
            }
        } else {
            System.out.println("Not logged in");
            return Response.ok("false").build();
        }
    }
}