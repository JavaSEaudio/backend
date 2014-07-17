package rest.login;

/**
 * Created by Lysyi on 15.07.2014.
 */

import businessLogic.UserLogic;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by oper4 on 17.07.2014.
 */
@Path("/")
public class Authorization {
    @POST
    @Path("/Login")
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {

        System.out.println(login + " " + password);

        UserLogic ul = new UserLogic();
        if(ul.login(login, password) != null ) {
            System.out.println("ok");
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        }
        System.out.println("noooooooooooooooo");
        return Response.status(400).build();
    }


}