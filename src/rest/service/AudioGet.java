package rest.service;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("get")
public class AudioGet {
    @GET
    @Path("/audio")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAudio(@QueryParam(value = "id") int id) {
        String path = "C://upload//audio//"+id+".mp3";
        File file = new File(path);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();

    }
    @GET
    @Path("/image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getImage(@QueryParam(value = "id") int id) {
        String path = "C://upload//image//"+id+".jpg";
        File file = new File(path);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
                .build();
    }
    @GET
    @Path("/avatar")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAvatar(@QueryParam(value = "id") int id) {
        String path = "C://upload//user//"+id+".jpg";
        File file;
        try {
            file = new File(path);
        } catch (Exception e) {
            file = new File("C://upload//user//0.jpg");
        }
        if (!file.exists() || file.isDirectory()) {
            file = new File("C://upload//user//0.jpg");
        }
        System.out.println(file);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName()+ "\"" ) //optional
                .build();
    }
}
