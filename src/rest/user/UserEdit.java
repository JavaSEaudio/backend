package rest.user;

import DAO.SessionDAO;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.UserEntity;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/useredit")
public class UserEdit {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @POST
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@CookieParam(value = "name") String uid,
                             @FormParam("pass") String pass,
                             @FormParam("email") String email,
                             @FormParam("information") String information,
                             @FormParam("name") String name) {

        UserDAO userDAO = Factory.getInstance().getUserDAO();
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        try {
            UserEntity user = userDAO.getById(sessionDAO.haveKey(uid));
            if(user == null) {
                log.info("UserEdit: not logged in");
                return Response.ok().status(400).build();
            }
            System.out.println(user.getLogin());
            user.setPassword(pass);
            try {
                user.setEmail(email);
            } catch (Exception e) {
                log.info("UserEdit: the email is occupied");
                Response.ok().status(203).build();
            }
            user.setInformation(information);
            user.setName(name);
            userDAO.change(user);
            log.info("UserEdit: logged in success");
            return Response.ok().status(204).build();
        } catch (Exception e) {
            log.info("UserEdit: some problem");
            return Response.ok().status(202).build();
        }
    }
}
