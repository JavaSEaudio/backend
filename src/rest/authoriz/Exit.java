package rest.authoriz;

import DAO.SessionDAO;
import org.apache.log4j.Logger;
import util.Factory;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/exit")
public class Exit {
    @GET
    public Response exit(@CookieParam(value = "name") String uid) {
        try {
            SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
            sessionDAO.delete(uid);
        } catch (Exception e) {
            System.out.println("Do not deleted cookies from DB");
        }
        Cookie cook = new Cookie("name", "");
        NewCookie cookie = new NewCookie(cook, "", 0, false);

        Logger.getLogger("com.vaannila.admin").error("To admin log1__from web");
        System.out.println("Deleted cookies");

        Logger.getLogger("com.vaannila.report").error("To report log2__from web");
        return Response.ok().cookie(cookie).build();
    }
}
