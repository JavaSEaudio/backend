package rest.service;

import BusinessLogic.UserLogic;
import DAO.SessionDAO;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.UserEntity;
import org.apache.log4j.Logger;
import util.EmailSender;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/pwd")
public class RestorePassword {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @GET
    @Consumes("text/html")
    @Produces("text/html")
    public Response resetPassword(@CookieParam(value = "name") String uid,
                                  @FormParam("email") String email) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid != -1) {
            log.info("Restore Pass: is not restored: user is logged" );
            return Response.status(400).entity("is logged").build();
        }


        UserDAO uDAO = Factory.getInstance().getUserDAO();
        UserEntity user = uDAO.getByEmail(email);
        if(user == null) {
            user = uDAO.getByLogin(email);
            if(user == null) {
                log.info("Restore Pass: email or login incorrect");
                return Response.status(401).entity("login not correct").build();
            }
            email = user.getEmail();
        }
        if(Factory.getInstance().getBannedDAO().isUserId(user.getId())) {
            log.info("Restore Pass: banned user: " + user.getLogin() + " trie to restore");
            return Response.status(402).entity("BANED!!!").build();
        }
        String newPassword = UserLogic.uid(12);
        try {
            user.setPassword(newPassword);
            uDAO.change(user);
        } catch (Exception e) {
            log.info("Restore Pass: pass is not restored");
            return Response.status(203).build();
        }
        EmailSender.sendPassword(email, newPassword);
        log.info("Restore Pass: paas is restored user: " + user.getLogin());
        return Response.status(200).entity(email).build();

    }


}