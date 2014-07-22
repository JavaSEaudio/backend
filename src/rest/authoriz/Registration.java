package rest.authoriz;

import util.StringUtil;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by oper4 on 22.07.2014.
 */
@Path("/Registration")
public class Registration {
    @POST
    //@Path("")
    public Response registration (@FormParam("login") String login,
                          @FormParam("passwordOne") String passwordOne,
                          @FormParam("passwordTwo") String passwordTwo,
                          @FormParam("email") String email) {
        if(!StringUtil.validRegistration(login, passwordOne, passwordTwo, email)){
            System.out.println("ERROR");
            return Response.status(400).build();
        } else
        System.out.println("login= | "+login+"  passOne= "+passwordOne+"  passTwo="+passwordTwo+"email= "+email);
        // ДОБАВИТЬ ПОЛЬЗОВАТЕЛЯ в БД
        return Response.ok().header("Registration-success", "*").build();
    }

}
