package rest.Email;

import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.AudioEntity;
import Entity.UserEntity;
import util.EmailSender;
import javax.mail.MessagingException;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/mail")
public class Mailings {
    @GET
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(@CookieParam("name") String uid) throws MessagingException {
        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(400).entity("u are not logged in").build();
        }
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        if(userDAO.getById(userid).getAccess() != 2) {
            return Response.status(400).entity("u are not admin").build();
        }
        AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
        try {
            List<UserEntity> users = userDAO.getAll();
            for (UserEntity user : users) {
                String message = "";
                List<AudioEntity> list = (List)audioDAO.getSomeAudios(0, 10);
                for (int i = 0; i < 10; i ++) {
                    message += list.get(i).getArtist() + " - " + list.get(i).getName();
                    message += "\n";
                }
                System.out.println(message);
                EmailSender.generateAndSendEmail(user.getEmail(), "Hi, " + user.getName() + "!\n" +
                        "Here are current newest 10 audios:\n" +message +
                        "join our site and listen them up! http://localhost:8080/");
                System.out.println("Hi, " + user.getName() + "!\n" +
                        "Here are current newest 10 audios:\n" +message +
                        "join our site and listen them up! http://localhost:8080/");
            }
            System.out.println("Success in sending email");
            return Response.ok().status(200).build();
        } catch (Exception e) {
            System.out.println("Problem in sending email");
            return Response.ok().status(400).build();
        }
    }
}
