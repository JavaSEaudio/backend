package rest.authoriz;

import BusinessLogic.*;
import DAO.SessionDAO;
import Entity.SessionEntity;
import Entity.UserEntity;
import org.apache.log4j.Logger;
import util.Factory;
import util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/Login")
public class Login {
    private final static Logger logger =  Logger.getLogger("com.vaannila.report");
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {

        if (!StringUtil.minMaxLength(login , 2 , 225)  ||  !StringUtil.minMaxLength(password , 2 , 225))
        {
            System.out.println("not valid length or type - login or password");
            logger.info("not valid length or type - login or password " + login);
            return Response.ok("false").build();
        }
        UserEntity user = UserLogic.authorization(login, password);
        if(user != null ) {
            String uid = UserLogic.uid();
            try {
                SessionEntity sess = new SessionEntity(user.getId(), uid);
                SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
                sessionDAO.add(sess);
                NewCookie cookie = new NewCookie("name", uid);
                System.out.println("Logged success");
                logger.info("Logged sucsess " + login);
                return Response.ok("true").cookie(cookie).header("Access-Control-Allow-Origin", "*").build();
            } catch (Exception e) {
                logger.info(" You logged before " + login);
                System.out.println("You logged before");
                return Response.ok("false1").build();
            }
        } else {
            logger.info(" Not logged in, cant( " + login);
            System.out.println("Not logged in");
            return Response.ok("false").build();
        }
    }
}