package rest.file;

import DAO.AudioDAO;
import Entity.AudioEntity;
import DAO.util.Factory;
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


    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAudios(@QueryParam("count") int count,
                              @QueryParam("page") int page) {
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio.addAll(aDAO.getSomeAudios((count * (page - 1)), count));
        } catch (Exception e) {
             System.out.println("Exception in get audio @search");
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
        int count = 3;
        int page = 1;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audio.addAll( aDAO.search(criterion, (count * (page - 1)), count) );
        } catch (Exception e) {
            System.out.println("Exception in get audio @get");
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
            System.out.println("Success search audio @year");
        } catch (Exception e) {
            System.out.println("Exception in search audio @year");
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
            System.out.println("Success search audio @user");
        } catch (Exception e) {
            System.out.println("Exception in search audio @user");
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
            System.out.println("Success search audio @genre");
        } catch (Exception e) {
            System.out.println("Exception in search audio @genre");
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
            System.out.println("Error while getting audio by artist /getbyartist");
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
            System.out.println("Error while getting audio by album /getbyalbum");
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
            System.out.println("Error while getting audio by name /getbyname");
        }
        return Response.ok(new GenericEntity<ArrayList<AudioEntity>>((ArrayList<AudioEntity>)audio){}).build();
    }

}