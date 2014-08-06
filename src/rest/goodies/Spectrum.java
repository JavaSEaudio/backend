package rest.goodies;

import BusinessLogic.SpectorAudio;
import DAO.SessionDAO;
import DAO.util.Factory;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 05.08.2014.
 */
@Path("/spect")
public class Spectrum {
    @GET
    @Path("/fft")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFFT(@CookieParam("name") String userid,
                           @QueryParam("idAudio") int id){
         int userID;
          try {
             SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
             userID = sessionDAO.haveKey(userid);
         } catch (Exception e) {
             //log.info("GETSPECTRUM: some troubles with getting cookie id");
             return Response.ok().status(400).build();
          }
          if (userID == -1) {
             // log.info("GETSPECTRUM: not access");
              return Response.ok().status(400).build();
          }
        String path = "C://upload//audio//"+id+".mp3";
        System.out.println(path);
        try {
            double [] FFTmas = SpectorAudio.getSpectrum(path);
             ArrayList<Double> array = new ArrayList<>();
            for(Double s : FFTmas)
            {
                array.add(s);
            }

            return  Response.status(200).entity(array).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok().status(500).build();
    }
}
