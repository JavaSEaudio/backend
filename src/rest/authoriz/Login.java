package rest.authoriz;

import BusinessLogic.*;
import DAO.SessionDAO;
import Entity.SessionEntity;
import Entity.UserEntity;
import util.Factory;
import util.StringUtil;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/Login")
public class Login {
    @POST
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {
        if (!StringUtil.minMaxLength(login , 2 , 225)  ||  !StringUtil.minMaxLength(password , 2 , 225))
        {
            System.out.println("not valid length or type - login or password");
            return Response.status(400).build();
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
                return Response.ok().cookie(cookie).header("Access-Control-Allow-Origin", "*").build();
            } catch (Exception e) {
                System.out.println("You logged before");
                return Response.status(400).build();
            }
        } else {
            System.out.println("Not logged in");
            return Response.status(400).build();
        }
    }
}