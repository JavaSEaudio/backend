package rest.goodies;

import BusinessLogic.Sessions;
import DAO.util.Factory;
import DTO.CommentsDTO;
import DTO.GetListDTO;
import Entity.AudioEntity;
import Entity.CommentsEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/comment")
public class CommentSystem {

    @POST
    @Path("/put")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putLike(@CookieParam("name") String uid,
                            @FormParam(value = "comment") String comment,
                            @FormParam(value = "audio") int id){
        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(400).entity("logged in pls").build();
        }
        AudioEntity audioEntity = Factory.getInstance().getAudioDAO().getById(id);
        if(audioEntity == null){
            return Response.status(401).entity("audio is not defined").build();
        }
        Factory.getInstance().getCommentsDAO().add(new CommentsEntity(id, userid, comment));
        return Response.status(200).entity("success").build();
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLike(@QueryParam(value = "audio") int id){

        AudioEntity audioEntity = Factory.getInstance().getAudioDAO().getById(id);
        if(audioEntity == null){
            return Response.status(401).entity("audio is not defined").build();
        }
        ArrayList<CommentsDTO> arrayList = (
                ArrayList<CommentsDTO>)
                GetListDTO.getListCommentsDTO(
                        Factory.getInstance().getCommentsDAO().getByAudio(id)
                );
        return Response.ok(new GenericEntity<ArrayList<CommentsDTO>>((ArrayList<CommentsDTO>) arrayList) {
        }).build();
    }


}
