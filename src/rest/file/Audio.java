package rest.file;

import DAO.AudioDAO;
import Entity.AudioEntity;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/audio")
public class Audio {

    private final static Logger log =  Logger.getLogger("com.audiostorage.report");

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudios(@QueryParam("count") int count,
                              @QueryParam("page") int page) {
        if(count > 100) count = 100;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio.addAll(aDAO.getSomeAudios((count * (page - 1)), count));
        } catch (Exception e) {
            log.info("Audio Get: exception");
        }

        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("criterion") String criterion/*,
                           @QueryParam("count") int count,
                           @QueryParam("page") int page*/) {
        criterion = StringUtil.parse(criterion);
        int count = 5;
        int page = 1;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        try {
            audio.addAll( aDAO.search(criterion, (count * (page - 1)), count) );
        } catch (Exception e) {
            log.info("Audio Search: exception");
        }
        if (audio.size() == 0) {
            String[] parts = criterion.split(" ");
            List<AudioEntity>[] lists = new List[parts.length];
            for (int i = 0; i < parts.length; i ++) {
                lists[i] = (List)aDAO.search(parts[i], (count * (page - 1)), count);
            }
            boolean flag;
            for (int i = 1; i < parts.length; i ++) {
                for (int j = 0; j < parts.length; j ++) {
                    flag = false;
                    for (int q = 0; q < parts.length; q ++) {
                        if (lists[0].get(j).getName().equals(lists[1].get(q).getName())) {
                            flag = true;
                            continue;
                        }
                    }
                    if (!flag) {
                        lists[0].remove(j);
                    }
                }
            }
            audio = lists[0];
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }

    @GET
    @Path("/year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response year(@QueryParam(value = "year") int year) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
            audio = audioDAO.getByYear(year);
        } catch (Exception e) {
            log.info("Audio Year: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>) audio) {}).build();
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userid(@QueryParam(value = "id") int id) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
            audio = audioDAO.getByUserId(id);
        } catch (Exception e) {
            log.info("Audio User: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>) audio) {}).build();
    }

    @GET
    @Path("/genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userid(@QueryParam(value = "genre") String genre) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO audioDAO = Factory.getInstance().getAudioDAO();
            audio = audioDAO.getByGenre(genre);
        } catch (Exception e) {
            log.info("Audio Genre: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>) audio) {}).build();
    }

    @GET
    @Path("/getbyartist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbArtist(@QueryParam("artist") String artist) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio = aDAO.getByName(artist);
        } catch(Exception e) {
            log.info("Audio Artist: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }

    @GET
    @Path("/getbyalbum")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbAlbum(@QueryParam("album") String album) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio = aDAO.getByAlbum(album);
        } catch(Exception e) {
            log.info("Audio Album: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }

    @GET
    @Path("/getbyname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbName(@QueryParam("name") String name) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio = aDAO.getByName(name);
        } catch(Exception e) {
            log.info("Audio Name: exception");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }

    @GET
    @Path("/getbyid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@QueryParam("id") int id) {
        AudioEntity audio = null;
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio = aDAO.getById(id);
        } catch(Exception e) {
            log.info("Audio ByID: exception");
        }
        return Response.ok().entity(audio).build();
    }

}