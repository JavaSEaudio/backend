package rest.user;

import BusinessLogic.UserLogic;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.UserEntity;
import org.apache.log4j.Logger;
import util.EmailSender;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class Admin {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @GET
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserAdmin(@QueryParam("userID") int userID,
                                  @FormParam("pass") String pass,
                                  @FormParam("email") String email,
                                  @FormParam("information") String information,
                                  @FormParam("name") String name,
                                  @CookieParam("name") String uid) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        UserEntity admin = userDAO.getById(Factory.getInstance().getSessionDAO().haveKey(uid));
        if(admin.getAccess() == 2) {
            try {
                UserEntity user = userDAO.getById(userID);
                if (user == null) {
                    log.info("ADMIN: "+admin.getLogin()+" user not found... \'in edit\'");
                    return Response.ok().status(400).build();
                }
                user.setPassword(pass);
                user.setEmail(email);
                user.setInformation(information);
                user.setName(name);
                userDAO.change(user);
                log.info("ADMIN: "+admin.getLogin()+" success edit user: "+user.getLogin() );
                return Response.ok().status(200).build();
            } catch (Exception e) {
                log.info("ADMIN: "+admin.getLogin()+" have some trouble with edit user");
                return Response.ok().status(203).build();
            }
        } else {
            log.info("ADMIN: was not admin... \'in edit\'");
            return Response.ok().status(204).build();
        }
    }

    @GET
    @Path("/ban")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ban(@CookieParam("name") String uid,
                        @QueryParam("userID") int userID) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        UserEntity admin = userDAO.getById(Factory.getInstance().getSessionDAO().haveKey(uid));
        if(admin.getAccess() == 2) {
            try {
                UserEntity user = userDAO.getById(userID);
                if (user == null) {
                    log.info("ADMIN: " + admin.getLogin() + " user not found... \'in ban\'");
                    return Response.ok().status(400).build();
                }
                user.setPassword(UserLogic.uid(40));
                String email = user.getEmail();
                userDAO.change(user);
                EmailSender.generateAndSendEmail(email, "You are banned, motherfucker!");
                Factory.getInstance().getSessionDAO().delete(userID);
                log.info("ADMIN: " + admin.getLogin() + " success banned user: " + user.getLogin());
                return Response.ok().status(200).build();
            } catch (Exception e) {
                log.info("ADMIN: " + admin.getLogin() + " have some trouble with banned user");
                return Response.ok().status(203).build();
            }
        } else {
            log.info("ADMIN: was not admin... \'in ban\'");
            return Response.ok().status(204).build();
        }
    }

    @GET
    @Path("/unban")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unban(@CookieParam("name") String uid,
                          @QueryParam("userID") int userID
    ) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        UserEntity admin = userDAO.getById(Factory.getInstance().getSessionDAO().haveKey(uid));
        if (admin.getAccess() == 2) {
            try {
                UserEntity user = userDAO.getById(userID);
                if (user == null) {
                    log.info("ADMIN: " + admin.getLogin() + " user not found... \'in unban\'");
                    return Response.ok().status(400).build();
                }
                String email = user.getEmail();
                String pass = user.getPassword();
                String message = "Congratz, you are unbanned ! Here is your new password: " + pass;
                EmailSender.generateAndSendEmail(email, message);
                log.info("ADMIN: " + admin.getLogin() + " success unbanned user: " + user.getLogin());
                return Response.ok().status(200).build();
            } catch (Exception e) {
                log.info("ADMIN: " + admin.getLogin() + " have some trouble with banned user");
                return Response.ok().status(400).build();
            }
        } else {
            log.info("ADMIN: was not admin... \'in unban\'");
            return Response.ok().status(204).build();
        }
    }
}
