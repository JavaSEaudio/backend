package rest.authoriz;
/**
 * Created by Lysyi on 15.07.2014.
 */

import BusinessLogic.*;
import util.StringUtil;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;



@Path("/")
public class Login {
    @POST
    @Path("Login")
   // @Consumes("text/html")
    public Response login(@FormParam("login") String login,
                          @FormParam("password") String password) {

        System.out.println(login + " " + password);
        if (!StringUtil.minMaxLength(login , 2 , 17)  ||  !StringUtil.minMaxLength(password , 3 , 257))
        {
            System.out.println("not valid length or type   - login $$ password");
            return Response.status(400).build();
        }   else

        if(UserLogic.authorization(login, password) != null ) {                     // UserLogic ul = new UserLogic();
            System.out.println("YYYEEEEESSSSSSSSSSS");
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        }   else
        System.out.println("NOOOOOOOOOOOOOO");
        return Response.status(400).build();
    }
//    @Path("/")
//    @Transactional
//    public AuthenticatedUserToken createUser(CreateUserRequest request, Role role) {
//        validate(request);
//        User searchedForUser = userRepository.findByEmailAddress(request.getUser().getEmailAddress());
//        if (searchedForUser != null) {
//            throw new DuplicateUserException();
//        }
//
//        User newUser = createNewUser(request, role);
//        AuthenticatedUserToken token = new AuthenticatedUserToken(newUser.getUuid().toString(), createAuthorizationToken(newUser).getToken());
//        userRepository.save(newUser);
//        return token;
//    }
        @POST
        @Path("/add")
        public Response addUser(
                @FormParam("name") String name,
                @FormParam("age") int age) {

            return Response.status(200)
                    .entity("addUser is called, name : " + name + ", age : " + age)
                    .build();

        }

}