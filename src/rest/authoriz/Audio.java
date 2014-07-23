package rest.authoriz;

import DAO.AudioDAO;
import Entity.AudioEntity;
import util.Factory;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/audio")
public class Audio {

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudios(@QueryParam("count") int count,
                              @QueryParam("page") int page) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        audio.addAll(aDAO.getSomeAudios((count * (page - 1)), count));

        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }
}