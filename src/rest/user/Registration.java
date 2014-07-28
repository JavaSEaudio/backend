package rest.user;

import DAO.UserDAO;
import Entity.UserEntity;
import DAO.util.Factory;
import util.StringUtil;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/Registration")
public class Registration {
    @POST
    public Response registration (@FormParam("login") String login,
                                  @FormParam("passwordOne") String passwordOne,
                                  @FormParam("passwordTwo") String passwordTwo,
                                  @FormParam("email") String email) {
        if(!StringUtil.validRegistration(login, passwordOne, passwordTwo, email)){
            System.out.println("Bad information");
            return Response.status(400).build();
        }
        try {
            UserDAO uDao = Factory.getInstance().getUserDAO();
            UserEntity user = new UserEntity(login, passwordOne, email, "", "");
            uDao.add(user);
        } catch(Exception e) {
            System.out.println("User do not created");
            return Response.status(400).build();
        }
        System.out.println("User created");
        return Response.ok().header("Registration-success", "*").build();
    }
}
