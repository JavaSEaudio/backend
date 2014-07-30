package rest.user;

import DAO.UserDAO;
import DTO.UserDTO;
import DTO.UserListDTO;
import DAO.util.Factory;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/user")
public class GetUser {

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(
            @QueryParam("login") String login) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        try {
            UserDTO user = new UserDTO(userDAO.getByLogin(login));
            System.out.println("Success in @getUserByLogin "+user.toString());
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            System.out.println("Problem in @getUserByLogin");
            return Response.ok().status(400).build();
        }
    }

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(
            @QueryParam("id") int id) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        try {
            UserDTO user = new UserDTO(userDAO.getById(id));
            System.out.println("Success in @getUserById "+user.toString());
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            System.out.println("Problem in @getUserById");
            return Response.ok().status(400).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        try {
            ArrayList<UserDTO> users = (ArrayList<UserDTO>) UserListDTO.getListUserDTO(userDAO.getAll());
            System.out.println("Success in @getAllUsers");
            return Response.ok(new GenericEntity<ArrayList<UserDTO>>(users){}).build();
        } catch (Exception e) {
            System.out.println("Problem in @getAllUsers");
            return null;
        }
    }
}
