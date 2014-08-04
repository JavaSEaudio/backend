package rest.service;

import DAO.SessionDAO;
import DAO.util.Factory;
import DTO.AudioDTO;
import DTO.AudioListDTO;
import DTO.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Path("/list")
public class MyList {
    @GET
    @Path("/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mylist(@QueryParam("id") Integer id,
                           @CookieParam("name") String uid,
                           @QueryParam("count") Integer count,
                           @QueryParam("page") Integer page) {
        if(id == null) {
            SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
            int userid = sessionDAO.haveKey(uid);
            if(userid == -1)
                return Response.status(400).build();
        }
        if(count == null) count = 10;
        if(page == null) page = 1;

        ArrayList<AudioDTO> arrayList;
        ArrayList<AudioDTO> list =
                (ArrayList<AudioDTO>)
                        AudioListDTO.getListAudioDTO(Factory.getInstance().getAudioDAO().getByUserId(id), id);
        if(count * (page-1) >= list.size())
            return Response.ok(null).build();
        if(count * page >= list.size()){
            arrayList = new ArrayList<AudioDTO>(list.subList(count*(page-1),list.size()));
            return Response.ok(new GenericEntity<ArrayList<AudioDTO>>(arrayList){}).build();
        }
        arrayList = new ArrayList<AudioDTO>(list.subList(count*(page-1), count*page));
        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>(arrayList){}).build();
    }
    @Path("/buy")
    public Response buylist(@CookieParam("name") String uid,
                           @QueryParam("count") Integer count,
                           @QueryParam("page") Integer  page) {

        if(count == null) count = 10;
        if(page == null) page = 1;
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1)
            return Response.status(400).build();
        UserDTO userDTO = new UserDTO(Factory.getInstance().getUserDAO().getById(userid));
        List<AudioDTO> list = new LinkedList<AudioDTO>();

        for(int id : userDTO.getBuyListArray()) {
            list.add(new AudioDTO(Factory.getInstance().getAudioDAO().getById(id), userid));
        }
        return Response.ok(new GenericEntity<ArrayList<AudioDTO>>((ArrayList<AudioDTO>)list.subList(count*(page-1) ,count*page)){}).build();
    }
}
