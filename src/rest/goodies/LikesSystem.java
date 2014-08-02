package rest.goodies;

import DAO.SessionDAO;
import DAO.util.Factory;
import Entity.LikeEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/like")
public class LikesSystem {
    @GET
    @Path("/putlike")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putlike(@CookieParam("name") String uid,
                            @QueryParam(value = "id") int id) {
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        int userid = sessionDAO.haveKey(uid);
        if(userid == -1) {
            return Response.ok("false").build();
        }
        LikeEntity likeEntity = null;
        try {
            likeEntity = Factory.getInstance().getLikeDAO().getByAudio(id);
        } catch(Exception e){
            likeEntity = new LikeEntity(id);
            Factory.getInstance().getLikeDAO().add(likeEntity);
            likeEntity.addUserIds(userid);
            likeEntity.setCounts(likeEntity.getCounts() + 1);
            Factory.getInstance().getLikeDAO().change(likeEntity);
            return Response.ok("true").build();
        }
        if(likeEntity == null) {
            likeEntity = new LikeEntity(id);
            Factory.getInstance().getLikeDAO().add(likeEntity);
            likeEntity.addUserIds(userid);
            likeEntity.setCounts(likeEntity.getCounts() + 1);
            Factory.getInstance().getLikeDAO().change(likeEntity);
            return Response.ok("true").build();
        }
        if(!check(userid, likeEntity)){
            likeEntity.addUserIds(userid);
            likeEntity.setCounts(likeEntity.getCounts() + 1);
            Factory.getInstance().getLikeDAO().change(likeEntity);
            return Response.ok("true").build();
        }
        return Response.ok("false").build();
    }

    public static boolean check(int userid, int id) {
        LikeEntity like = null;
        try {
            like = Factory.getInstance().getLikeDAO().getByAudio(id);
        } catch (Exception e){
            return false;
        }
        if(like == null)
            return false;

        for (int i : like.addUserIdsArray()) {
            if (i == userid) return true;
        }
        System.out.println("4");
        return false;
    }

    public static int count(int id) {
        try{
            return Factory.getInstance().getLikeDAO().getByAudio(id).getCounts();
        } catch (Exception e){
            return 0;
        }
    }


    private boolean check(int id, LikeEntity like) {
        if(like == null) return false;
        for (int i : like.addUserIdsArray()) {
            if (i == id) return true;
        }
        return false;
    }



}
