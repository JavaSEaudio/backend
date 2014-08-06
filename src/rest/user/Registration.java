package rest.user;

import BusinessLogic.Sessions;
import BusinessLogic.UserLogic;
import Entity.TmpUserEntity;
import DAO.util.Factory;
import util.Crypto;
import util.EmailSender;
import util.StringUtil;

import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Registration")
public class Registration {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration (@CookieParam("name") String uid,
                                  @FormParam("login") String login,
                                  @FormParam("passwordOne") String passwordOne,
                                  @FormParam("passwordTwo") String passwordTwo,
                                  @FormParam("email") String email) {
        int userid = Sessions.uid(uid);
        if(userid != -1) {
            return Response.status(400).entity("u are logged in").build();
        }
        if(!StringUtil.validRegistration(login, passwordOne, passwordTwo, email)){
            System.out.println("Bad information");
            return Response.status(400).entity("bad info").build();
        }
        if(!StringUtil.valid(login))
            return Response.status(400).entity("incorrect login").build();
        if(!StringUtil.valid(email))
            return Response.status(400).entity("incorrect email").build();
        String uniq = UserLogic.uid(20);
        try {
            String password = Crypto.MD5(passwordOne);
            TmpUserEntity user = new TmpUserEntity(login, email, password, uniq);
            Factory.getInstance().getTmpUserDAO().add(user);

        } catch(Exception e) {
            System.out.println("User do not created");
            return Response.status(400).build();
        }
        String massege = "Hello, " + login +
                " you are trying to register on THE BEST STORAGE EVER, go to \nhttp://localhost:8080/rest/validation?uniq="+uniq +"   to complete the operation ";
        try {
            EmailSender.generateAndSendEmail(email, massege);
        } catch (MessagingException e) {}
        System.out.println("User created");
        return Response.ok().header("Registration-success", "*").build();
    }
}