package rest.goodies;

import BusinessLogic.Sessions;
import DAO.util.Factory;
import DTO.CommentsDTO;
import DTO.GetListDTO;
import Entity.AudioEntity;
import Entity.CommentsEntity;
import util.StringUtil;

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
    public Response putComment(@CookieParam("name") String uid,
                               @FormParam(value = "comment") String comment,
                               @FormParam(value = "audio") int id){
        comment = StringUtil.parse(comment);
        if(comment == null || comment.equals("") || comment.equals(" ")){
            return Response.status(403).entity("not valid").build();
        }
        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(400).entity("logged in pls").build();
        }
        AudioEntity audioEntity = Factory.getInstance().getAudioDAO().getById(id);
        if(audioEntity == null){
            return Response.status(401).entity("audio is not defined").build();
        }
        CommentsEntity commentsEntity = new CommentsEntity(id, userid, comment);
        try {
            Factory.getInstance().getCommentsDAO().add(commentsEntity);
        } catch (Exception e) {
            return Response.status(401).entity("length not supported").build();
        }
        CommentsDTO commentsDTO = new CommentsDTO(commentsEntity);
        return Response.status(200).entity(commentsDTO).build();
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComment(@QueryParam("count") Integer count,
                               @QueryParam("page") Integer page,
                               @QueryParam(value = "audio") int id){

        if(count == null) count = 10;
        if(page == null) page = 1;
        if(count > 100) count = 100;


        AudioEntity audioEntity = Factory.getInstance().getAudioDAO().getById(id);
        if(audioEntity == null){
            return Response.status(401).entity("audio is not defined").build();
        }
        ArrayList<CommentsDTO> commentsDTO = (
                ArrayList<CommentsDTO>)
                GetListDTO.getListCommentsDTO(
                        Factory.getInstance().getCommentsDAO().getByAudio(id, (count * (page - 1)), count)
                );
        return Response.ok(new GenericEntity<ArrayList<CommentsDTO>>((ArrayList<CommentsDTO>) commentsDTO) {
        }).build();
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteComment(@CookieParam("name") String uid,
                                  @QueryParam(value = "comment") int id){

        int userid = Sessions.uid(uid);
        if(userid == -1){
            return Response.status(401).entity("logged in pls").build();
        }
        if(Factory.getInstance().getUserDAO().getById(userid).getAccess() != 2){
            return Response.status(402).entity("u are not admin").build();
        }
        try {
            Factory.getInstance().getCommentsDAO().delete(id);
        } catch (Exception e){
            return Response.status(400).entity("neok").build();
        }
        return Response.status(200).entity("ok").build();
    }
}