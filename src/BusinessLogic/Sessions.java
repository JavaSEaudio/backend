package BusinessLogic;

import DAO.SessionDAO;
import DAO.util.Factory;

public class Sessions {
    public static int uid(String uid){
        SessionDAO sessionDAO = Factory.getInstance().getSessionDAO();
        return sessionDAO.haveKey(uid);
    }
}
