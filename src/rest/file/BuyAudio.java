package rest.file;

import DAO.AudioDAO;
        import DAO.SessionDAO;
        import DAO.UserDAO;
        import DAO.util.Factory;
        import DTO.UserDTO;
        import Entity.AudioEntity;
        import Entity.UserEntity;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
        import javax.ws.rs.core.MediaType;
        import javax.ws.rs.core.Response;

@Path("/buy")
public class BuyAudio {
    private final static Logger log =  Logger.getLogger("com.audiostorage.report");
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response buyAudio(@QueryParam(value = "audioID") int audioID,
                             @CookieParam(value = "name") String uid) {
        UserDAO uDAO = Factory.getInstance().getUserDAO();
        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        int userID;
        try {
            SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
            userID = sessionDAO.haveKey(uid);
        } catch (Exception e) {
            log.info("BUY: some troubles with getting cookie id");
            return Response.ok().status(400).build();
        }
        if (userID == -1) {
            log.info("BUY: not access");
            return Response.ok().status(400).build();
        }
        else {
            try {
                UserEntity buyer = uDAO.getById(userID);
                AudioEntity audio = aDAO.getById(audioID);
                double price = audio.getPrice();
                if (buyer.getMoney() < price) {
                    log.info("BUY: not enough money");
                    return Response.ok().status(400).build();
                } else {
                    UserEntity vendor = uDAO.getById(audio.getUserid());
                    UserDTO buyerDTO = new UserDTO(buyer);
                    buyerDTO.addBuylist(audioID);
                    buyer.setDRO(buyerDTO);
                    buyer.setMoney(buyer.getMoney() - price);
                    vendor.setMoney(vendor.getMoney() + price);
                    uDAO.change(buyer);
                    uDAO.change(vendor);
                    log.info("BUY: success");
                    return Response.ok().status(200).build();
                }

            } catch(Exception e) {
                log.info("BUY: trouble in money transaction");
                return Response.ok().status(400).build();
            }
        }
    }
}