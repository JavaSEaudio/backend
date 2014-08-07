package rest.service;

import DAO.TagDAO;
import DAO.util.Factory;
import Entity.TagEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class PopularTags {
    @GET
    @Path("/tags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response popularTags(@QueryParam("count") Integer count) {
        try {
            TagDAO tDAO = Factory.getInstance().getTagDAO();
            List<TagEntity> tags = tDAO.getPopularTags(count);
            return Response.ok(new GenericEntity<List<TagEntity>>((List<TagEntity>)tags){}).build();
        } catch(Exception e) {
            return Response.ok().status(405).build();
        }

    }
}
