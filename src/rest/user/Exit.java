package rest.user;

import DAO.SessionDAO;
import DAO.util.Factory;
import org.apache.log4j.Logger;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/exit")
public class Exit {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");

    @GET
    public Response exit(@CookieParam(value = "name") String uid) {
        try {
            SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
            sessionDAO.delete(uid);
        } catch (Exception e) {
            log.info("EXIT: not deleted from DB");
        }
        Cookie cook = new Cookie("name", "");
        NewCookie cookie = new NewCookie(cook, "", 0, false);
        log.info("EXIT: deleted in browser");
        return Response.ok().cookie(cookie).build();
    }
}
