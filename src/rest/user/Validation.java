package rest.user;


import BusinessLogic.Sessions;
import DAO.util.Factory;
import Entity.TmpUserEntity;
import Entity.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/validation")
public class Validation {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response validation (@CookieParam("name") String uid,
                                @QueryParam("uniq") String uniq) {
        if(Sessions.uid(uid) != -1){
            System.out.println("THE VERRRRRY BIG PROBLEM!!!");
            return Response.status(400).entity("u are logged in").build();
        }
        TmpUserEntity tmpUserEntity = Factory.getInstance().getTmpUserDAO().geBytUniq(uniq);
        UserEntity userEntity = new UserEntity(tmpUserEntity);
        Factory.getInstance().getUserDAO().add(userEntity);
        Factory.getInstance().getTmpUserDAO().delete(tmpUserEntity);
        System.out.println("THE BIG PROBLEM!!!");
        return Response.status(200).entity("success login").build();
    }
}
