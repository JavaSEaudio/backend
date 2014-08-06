package rest.catalog;

import BusinessLogic.Sessions;
import DAO.AudioDAO;
import DAO.util.Factory;
import DTO.AlbumsDTO;
import DTO.ArtistDTO;
import DTO.AudioDTO;
import DTO.GetListDTO;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/artist")
public class Artist {
    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbArtist(@QueryParam("count") Integer count,
                               @QueryParam("page") Integer page
    ) {
        if(count == null) count = 10;
        if(page == null) page = 1;
        if(count > 100) count = 100;
        List<ArtistDTO> artistDTO = new ArrayList<ArtistDTO>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            artistDTO = GetListDTO.getListArtistDTO(aDAO.getArtistAll(count*(page-1), count));
        } catch(Exception e) {}
        return Response.ok(new GenericEntity<ArrayList<ArtistDTO>>
                ((ArrayList<ArtistDTO>)artistDTO){}).build();
    }

    @GET
    @Path("/getalbums")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbAlbums(@QueryParam("artist") String artist,
                               @QueryParam("count") Integer count,
                               @QueryParam("page") Integer page
    ) {
        if(count == null) count = 10;
        if(page == null) page = 1;
        if(count > 100) count = 100;
        List<AlbumsDTO> artistDTO = new ArrayList<AlbumsDTO>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            artistDTO = GetListDTO.getListAlbumsDTO(
                    aDAO.getbyArtistAlbum(artist, count * (page - 1), count)
            );
        } catch(Exception e) {}
        return Response.ok(new GenericEntity<ArrayList<AlbumsDTO>>
                ((ArrayList<AlbumsDTO>)artistDTO){}).build();
    }

    @GET
    @Path("/getaudio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getbAudio(@CookieParam("name") String uid,
                              @QueryParam("artist") String artist,
                              @QueryParam("album") String album,
                              @QueryParam("count") Integer count,
                              @QueryParam("page") Integer page
    ) {
        int userid = Sessions.uid(uid);
        if(count == null) count = 10;
        if(page == null) page = 1;
        if(count > 100) count = 100;
        List<AudioDTO> artistDTO = new ArrayList<AudioDTO>();
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            artistDTO = GetListDTO.getListAudioDTO(aDAO.getbyAlbumTracks(
                    album, artist, count * (page - 1), count
            ), userid);

        } catch(Exception e) {}
        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>
                ((ArrayList<AudioDTO>)artistDTO){}).build();
    }
}
