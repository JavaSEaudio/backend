package rest.Email;

import BusinessLogic.Sessions;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.RestoreEntity;
import Entity.UserEntity;
import org.apache.log4j.Logger;
import util.Crypto;
import util.EmailSender;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/password")
public class RestorePass {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");

    @GET
    @Path("/restore")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@CookieParam(value = "name") String uid,
                             @QueryParam("uniq") String uniq) {
        int userid = Sessions.uid(uid);
        if (userid != -1) {
            log.info("Pass Restore: logged in");
            return Response.status(404).entity("u are logged in").build();
        }

        RestoreEntity restore = Factory.getInstance().getRestoreDAO().getByUniq(uniq);
        if(restore == null){
            log.info("Pass Restore: uniq wrong");
            return Response.status(404).entity("uniq wrong").build();
        }
        log.info("Pass Restore: uniq cool");
        return Response.status(200).entity("uniq cool").build();
    }

    @POST
    @Path("/change")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(@CookieParam(value = "name") String uid,
                             @QueryParam("uniq") String uniq,
                             @FormParam("passOne") String passOne,
                             @FormParam("passTwo") String passTwo) {

        int userid = Sessions.uid(uid);
        if (userid != -1) {
            log.info("Pass Change: logged in");
            return Response.status(404).entity("u are logged in").build();
        }

        RestoreEntity restore = Factory.getInstance().getRestoreDAO().getByUniq(uniq);
        if(restore == null){
            log.info("Pass Change: uniq wrong");
            return Response.status(404).entity("uniq wrong").build();
        }
        UserEntity user = Factory.getInstance().getUserDAO().getById(restore.getUserid());
        if(user == null){
            log.info("Pass Change: uniq user wrong");
            return Response.status(404).entity("user wrong").build();
        }
        if(!passOne.equals(passTwo)){
            log.info("Pass Change: pass wrong");
            return Response.status(404).entity("pass wrong").build();
        }
        passOne = Crypto.MD5(passOne);
        user.setPassword(passOne);
        Factory.getInstance().getUserDAO().change(user);
        Factory.getInstance().getRestoreDAO().delete(restore);
        log.info("Pass Change: Reset pass success");
        return Response.status(200).entity("uniq cool").build();
    }

        @GET
        @Path("/forgot")
        @Produces(MediaType.APPLICATION_JSON)
        public Response resetPassword(@CookieParam(value = "name") String uid,
                                      @FormParam("email") String email) {

            int userid = Sessions.uid(uid);
            if (userid != -1) {
                log.info("Pass Forgot: is not restored: user is logged");
                return Response.status(400).entity("is logged").build();
            }


            UserDAO uDAO = Factory.getInstance().getUserDAO();
            UserEntity user = uDAO.getByEmail(email);
            if (user == null) {
                user = uDAO.getByLogin(email);
                if (user == null) {
                    log.info("Pass Forgot: email or login incorrect");
                    return Response.status(401).entity("login not correct").build();
                }
                email = user.getEmail();
            }

            if (Factory.getInstance().getBannedDAO().isUserId(user.getId())) {
                log.info("Pass Forgot: banned user: " + user.getLogin() + " trie to restore");
                return Response.status(402).entity("BANED!!!").build();
            }
            RestoreEntity restoreEntity = new RestoreEntity(user.getId());
            try {
                Factory.getInstance().getRestoreDAO().add(restoreEntity);
            } catch (Exception e){
                Factory.getInstance().getRestoreDAO().delete(Factory.getInstance().getRestoreDAO().getByUserID(user.getId()));
                Factory.getInstance().getRestoreDAO().add(restoreEntity);
            }
            String massege = "Hello, " + user.getLogin() +
                    "If you want to recover your password, go to http://localhost:8080/#/profile/pwd/"+restoreEntity.getUniq();
            try {
                EmailSender.generateAndSendEmail(email, massege);
            } catch (MessagingException e) {}
            log.info("Pass Forgot: pass is restored user: " + user.getLogin());
            return Response.status(200).entity(email).build();
        }
}
