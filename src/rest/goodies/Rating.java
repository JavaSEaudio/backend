package rest.goodies;

import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.LikeDAO;
import DAO.SessionDAO;
import DAO.util.Factory;
import DTO.AudioDTO;
import Entity.LikeEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/rate")
public class Rating {
    @GET
    @Path("/like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudios(@QueryParam("count") Integer count,
                              @QueryParam("page") Integer page,
                              @CookieParam("name") String uid
    ) {
        if(count == null) count = 10;
        if(page == null) page = 1;
        int userid = Sessions.uid(uid);
        if(count > 100) count = 100;

        ArrayList<AudioDTO> audioDTOs = new ArrayList<AudioDTO>();
        ArrayList<LikeEntity> arrayList = new ArrayList<LikeEntity>();
        try {
            LikeDAO likeDAO = Factory.getInstance().getLikeDAO();
            arrayList.addAll(likeDAO.getMostLikes((count * (page - 1)), count));
            System.out.println(arrayList.toString());
            for(int i = 0; i < arrayList.size(); i++){
                audioDTOs.add(new AudioDTO(Factory.getInstance().getAudioDAO().getById(arrayList.get(i).getAudioId()), userid));
            }
        } catch (Exception e) {
//            log.info("Audio Get: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)audioDTOs){}).build();
    }
}
