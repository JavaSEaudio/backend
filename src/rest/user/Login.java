package rest.user;

import BusinessLogic.*;
import Entity.SessionEntity;
import Entity.UserEntity;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.Crypto;
import util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/Login")
public class Login {

    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@CookieParam("name") String uid,
                          @FormParam("login") String login,
                          @FormParam("password") String password) {
        if(Sessions.uid(uid) != -1){
            return Response.status(400).entity("u are logged in").build();
        }

        if (!StringUtil.minMaxLength(login , 2 , 30)  ||
                !StringUtil.minMaxLength(password , 2 , 225) /*|| !StringUtil.validLogin(login)*/) {
            log.info("not valid length");
            System.out.println("not valid length or type - login or password");
            return Response.ok("false").build();
        }
        password = Crypto.MD5(password);
        UserEntity user = UserLogic.authorization(login, password);
        if(user != null ) {
            try {
                if(Factory.getInstance().getBannedDAO().isUserId(user.getId())){
                    log.info("Login: banned user tried to logged in");
                    return Response.status(400).entity("BANNNED!!!").build();
                }
            } catch (Exception e){
                log.info("Login: Exception in banned block");
                return Response.status(400).entity("OOPS...").build();
            }
            try {
                String usid = UserLogic.uid(64);
                SessionEntity sess = new SessionEntity(user.getId(), usid);
                Factory.getInstance().getSessionDAO().add(sess);
                NewCookie cookie = new NewCookie("name", usid);
                System.out.println("Logged success");
                log.info("Logged success");
                return Response.ok("true").cookie(cookie).header("Access-Control-Allow-Origin", "*").build();
            } catch (Exception e) {
                log.info("You logged before");
                System.out.println("You logged before");
                return Response.ok("false").build();
            }
        } else {
            log.info("Not logged in");
            System.out.println("Not logged in");
            return Response.ok("false").build();
        }
    }
}