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
        } catch(Exception e) {
            System.out.println("Trouble");
        }
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


        List<AlbumsDTO> albumDTO = new ArrayList<AlbumsDTO>();

        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
//            if(artist == null) {
//                albumDTO = aDAO.getAlbums(count * (page - 1), count);
//            }
            albumDTO = GetListDTO.getListAlbumsDTO(
                    aDAO.getbyArtistAlbum(artist, count * (page - 1), count), artist
            );
        } catch(Exception e) {}
        return Response.ok(new GenericEntity<ArrayList<AlbumsDTO>>
                ((ArrayList<AlbumsDTO>)albumDTO){}).build();
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
        List<AudioDTO> audioDTO = new ArrayList<AudioDTO>();
        if(album == null){
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            audioDTO = GetListDTO.getListAudioDTO(aDAO.getByArtist(
                    artist, count * (page - 1), count
            ), userid, count * (page - 1));
        } else {
            try {
                AudioDAO aDAO = Factory.getInstance().getAudioDAO();
                audioDTO = GetListDTO.getListAudioDTO(aDAO.getbyAlbumTracks(
                        album, artist, count * (page - 1), count
                ), userid, count * (page - 1));

            } catch (Exception e) {
            }
        }
        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>
                ((ArrayList<AudioDTO>)audioDTO){}).build();
    }
}
