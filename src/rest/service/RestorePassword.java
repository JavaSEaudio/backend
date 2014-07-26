package rest.service;

import BusinessLogic.UserLogic;
import DAO.UserDAO;
import DAO.util.Factory;
import Entity.UserEntity;
import util.EmailSender;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/pwd")
public class RestorePassword {

    @GET
    @Consumes("text/html")
    @Produces("text/html")
    public Response resetPassword(@FormParam("email") String email) {
        String newPassword = UserLogic.uid();

        UserDAO uDAO = Factory.getInstance().getUserDAO();
        UserEntity user = uDAO.getByEmail(email);
        if(user == null) {
            user = uDAO.getByLogin(email);
            if(user == null) return Response.ok("email or login not correct").build();
            email = user.getEmail();
        }
        user.setPassword(newPassword);
        uDAO.change(user);

        EmailSender.sendPassword(email, newPassword);
        return Response.status(200).entity(email).build();

    }


}