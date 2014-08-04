package rest.user;

import BusinessLogic.Sessions;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.BannedEntity;
import Entity.UserEntity;
import org.apache.log4j.Logger;
import util.EmailSender;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class Admin {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @POST
    @Path("/edit")//Какого хуя тут гет и формпарам?согласен, счас
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserAdmin(@QueryParam("userID") int userID,
                                  @FormParam("access") String access,
                                  @FormParam("information") String information,
                                  @FormParam("name") String name,
                                  @CookieParam("name") String uid) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        UserEntity admin = userDAO.getById(Factory.getInstance().getSessionDAO().haveKey(uid));
        int acs = -1;
        if(access.equals("user")) acs = 0;
        if(access.equals("moderator"))  acs = 1;
        if(access.equals("admin")) acs = 2;

        if(admin.getAccess() == 2) {
            try {
                UserEntity user = userDAO.getById(userID);
                if (user == null) {
                    log.info("ADMIN: "+admin.getLogin()+" user not found... \'in edit\'");
                    return Response.ok().status(400).build();
                }
                user.setInformation(information);
                if(acs != -1) user.setAccess(acs);
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
        UserEntity admin = userDAO.getById(Sessions.uid(uid));
        if(admin.getAccess() == 2) {
            try {
                UserEntity user = userDAO.getById(userID);
                if (user == null) {
                    log.info("ADMIN: " + admin.getLogin() + " user not found... \'in ban\'");
                    return Response.ok().status(400).build();
                }
                if (user.getId() == admin.getId()) {
                    log.info("ADMIN: " + admin.getLogin() + " you cant banned yourself");
                    return Response.ok().status(403).build();
                }
                if (user.getAccess() > 0) {
                    log.info("ADMIN: " + admin.getLogin() + " you cant banned moderator or admin");
                    return Response.ok().status(404).build();
                }
                Factory.getInstance().getBannedDAO().add(new BannedEntity(user.getId()));
                String email = user.getEmail();
                EmailSender.generateAndSendEmail(email, "You are banned, motherfucker!");
                Factory.getInstance().getSessionDAO().delete(userID);
                log.info("ADMIN: " + admin.getLogin() + " success banned user: " + user.getLogin());
                return Response.ok().status(200).build();
            } catch (Exception e) {
                log.info("ADMIN: " + admin.getLogin() + " have some trouble with banned user");
                return Response.ok().status(401).build();
            }
        } else {
            log.info("ADMIN: was not admin... \'in ban\'");
            return Response.ok().status(402).build();
        }
    }

    @GET
    @Path("/unban")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unban(@CookieParam("name") String uid,
                          @QueryParam("userID") int userID
    ) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        UserEntity admin = userDAO.getById(Sessions.uid(uid));
        if(admin == null)
            return Response.status(400).entity("logged in pls").build();
        if (admin.getAccess() == 2) {
            try {
                UserEntity user = userDAO.getById(userID);
                if (user == null) {
                    log.info("ADMIN: " + admin.getLogin() + " user not found... \'in unban\'");
                    return Response.ok().status(400).entity("user not found").build();
                }
                if(Factory.getInstance().getBannedDAO().isUserId(userID)) {
                    Factory.getInstance().getBannedDAO().delete(user);
                    String email = user.getEmail();
                    String message = "Congratz, you are unbanne!";
                    try {
                        EmailSender.generateAndSendEmail(email, message);
                    } catch (MessagingException exception){}

                    log.info("ADMIN: " + admin.getLogin() + " success unbanned user: " + user.getLogin());
                    return Response.ok().status(200).build();
                } else {
                    log.info("ADMIN: " + admin.getLogin() + " user: " + user.getLogin()+" not in banlist");
                    return Response.ok().status(400).entity("user not banned").build();
                }
            } catch (Exception e) {
                log.info("ADMIN: " + admin.getLogin() + " have some trouble with banned user");
                return Response.ok().status(400).entity("some problem").build();
            }
        } else {
            log.info("ADMIN: was not admin... \'in unban\'");
            return Response.ok().status(404).entity("was not admin").build();
        }
    }
}
