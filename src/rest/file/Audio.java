package rest.file;

import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.SessionDAO;
import DAO.TagDAO;
import DTO.AudioDTO;
import DTO.GetListDTO;
import Entity.AudioEntity;
import DAO.util.Factory;
import Entity.TagEntity;
import org.apache.log4j.Logger;
import util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/audio")
public class Audio {

    private final static Logger log =  Logger.getLogger("com.audiostorage.report");

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudios(@QueryParam("count") int count,
                              @QueryParam("page") int page,
                              @CookieParam("name") String uid
    ) {
        int userid = Sessions.uid(uid);
        if(count > 100) count = 100;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        ArrayList<AudioDTO> audioDTOs = new ArrayList<AudioDTO>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio.addAll(aDAO.getSomeAudios((count * (page - 1)), count));
            audioDTOs = (ArrayList<AudioDTO>) GetListDTO.getListAudioDTO(audio, userid);

        } catch (Exception e) {
            log.info("Audio Get: exception");
        }

        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)audioDTOs){}).build();
    }

    @POST
    @Path("/search")
//    @Consumes({"application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@FormParam("criterion") String criterion,
                           @CookieParam("name") String uid,
                           @FormParam("count") Integer count,
                           @FormParam("page") Integer page) {

        if(count == null) count = 10;
        if(page == null) page = 1;
        if(count > 100) count = 100;
        if(criterion == null){
            return Response.status(405).entity("null").build();
        }
        if (criterion.charAt(0) == '#') {
            ArrayList<String> tags = StringUtil.tagParse(criterion);
            SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
            int userID = sessionDAO.haveKey(uid);
            List<AudioEntity> audio = new ArrayList<AudioEntity>();
            ArrayList<AudioDTO> audioDTOs = new ArrayList<AudioDTO>();
            TagDAO tDAO = Factory.getInstance().getTagDAO();
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            List<TagEntity> tagEntities = new ArrayList<TagEntity>();
            //System.out.println(tags.size());
            if (tags.size() == 1){
                for (int i = 0; i < tDAO.getByName(tags.get(0)).addAudioIdsArray().size(); i ++) {
                    audio.add(aDAO.getById(tDAO.getByName(tags.get(0)).addAudioIdsArray().get(i)));
                }
                audioDTOs = (ArrayList<AudioDTO>)GetListDTO.getListAudioDTO(audio,userID);
                return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)audioDTOs){}).build();
            }
            for (int i = 0; i < tags.size(); i ++) {
                System.out.println(tDAO.getById(1).getName());
                tagEntities.add(tDAO.getByName(tags.get(i)));
                //tagEntities.add(tDAO.getById(1));
            }
            List<Integer>[] lists = new List[tags.size()];
            //System.out.println(tags.size());
            for (int i = 0; i < tags.size(); i ++) {
                System.out.println(tagEntities.get(i).getName());
                lists[i] = tagEntities.get(i).addAudioIdsArray();
            }
            boolean flag;
            for (int i = 1; i < tags.size(); i ++) {
                for (int j = 0; j < lists[0].size(); j ++) {
                    flag = false;
                    for (int q = 0; q < lists[i].size(); q ++) {
                        if (lists[0].get(j) == lists[i].get(q)) {
                            flag = true;
                            continue;
                        }
                    }
                    if (!flag) {
                        lists[0].remove(j);
                    }
                }
            }
            for (int i = 0; i < lists[0].size(); i ++) {
                audio.add(aDAO.getById(lists[0].get(i)));
            }
            audioDTOs = (ArrayList<AudioDTO>)GetListDTO.getListAudioDTO(audio,userID);
            return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)audioDTOs){}).build();

        } else {
            SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
            int userID = sessionDAO.haveKey(uid);
            List<AudioEntity> audio = new ArrayList<AudioEntity>();
            ArrayList<AudioDTO> audioDTOs = new ArrayList<AudioDTO>();
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
            }if (audio.size() == 0) {
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
            audioDTOs = (ArrayList<AudioDTO>)GetListDTO.getListAudioDTO(audio,userID);
            return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)audioDTOs){}).build();
        }
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
    public Response getById(@QueryParam("id") int id,
                            @CookieParam("name") String uid) {
        int userid = Sessions.uid(uid);
        AudioEntity audio = null;
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio = aDAO.getById(id);
        } catch(Exception e) {
            log.info("Audio ByID: exception");
        }
        AudioDTO audioDTO = new AudioDTO(audio, userid);
        return Response.ok().entity(audioDTO).build();
    }

    @GET
    @Path("/free")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFree(@QueryParam("count") int count,
                            @QueryParam("page") int page,
                            @CookieParam("name") String uid
    ) {
        int userid = Sessions.uid(uid);
        if(count > 100) count = 100;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        ArrayList<AudioDTO> audioDTOs = new ArrayList<AudioDTO>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio.addAll(aDAO.getFree((count * (page - 1)), count));
            audioDTOs = (ArrayList<AudioDTO>) GetListDTO.getListAudioDTO(audio, userid);
            log.info("Audio Free: success");
        } catch (Exception e) {
            log.info("Audio Free: exception");
        }

        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)audioDTOs){}).build();
    }

}