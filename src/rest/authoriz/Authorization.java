package rest.authoriz;

/**
 * Created by Lysyi on 15.07.2014.
 */

import BusinessLogic.*;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/")
public class Authorization {
    @POST
    @Path("/Login")
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {

        System.out.println(login + " " + password);

       // UserLogic ul = new UserLogic();
        if(UserLogic.authorization(login, password) != null ) {
            System.out.println("yeeeeeeeeeeeeeeeeeeeessssssssss");
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        }else
        System.out.println("noooooooooooooooo");
        return Response.status(400).build();
    }


}