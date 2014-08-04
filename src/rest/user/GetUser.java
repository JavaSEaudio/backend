package rest.user;

import DAO.SessionDAO;
import DAO.UserDAO;
import DTO.GetListDTO;
import DTO.UserDTO;
import DAO.util.Factory;
import Entity.UserEntity;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/user")
public class GetUser {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(
            @QueryParam("login") String login) {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        try {
            UserDTO user = new UserDTO(userDAO.getByLogin(login));
            log.info("GetUser Login: success");

            return Response.ok().entity(user).build();
        } catch (Exception e) {
            log.info("GetUser Login: exception");
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
            log.info("GetUser ID: success");
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            log.info("GetUser ID: exception");
            return Response.ok().status(400).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        UserDAO userDAO = Factory.getInstance().getUserDAO();
        try {
            ArrayList<UserDTO> users = (ArrayList<UserDTO>) GetListDTO.getListUserDTO(userDAO.getAll());
            log.info("GetUser ALL: success");
            return Response.ok(new GenericEntity<ArrayList<UserDTO>>(users){}).build();
        } catch (Exception e) {
            log.info("GetUser ALL: exception");
            return null;
        }
    }

     @GET
     @Path("/mylogin")
     @Produces(MediaType.APPLICATION_JSON)
     public Response editUser(@CookieParam(value = "name") String uid
                              ) {

         UserDAO userDAO = Factory.getInstance().getUserDAO();
         SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();

             UserEntity user = userDAO.getById(sessionDAO.haveKey(uid));
             UserDTO userDTO = new UserDTO(user);
             if(user == null) {

                 return Response.ok().status(400).build();
             }
             System.out.println(user.getLogin());

             return Response.ok().entity(userDTO).build();
     }
}
