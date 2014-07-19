package BusinessLogic;

import DAO.SessionDAO;
import DAO.UserDAO;
import Entity.UserEntity;
import util.Factory;

public class UserLogic {
    private static UserDAO uDao = Factory.getInstance().getUserDAO();
    public UserLogic() {}

    public static UserEntity authorization(String login, String pass) {
        int id = uDao.loginPassword(login, pass);
        if (id == -1) {
            return null;
        }
        else {
            return uDao.getById(id);
        }
    }

    public static int haveKey(String key) {
        SessionDAO sDao = Factory.getInstance().getSessionDAO();
        return sDao.haveKey(key);

    }

    public static String uid() {
        Key key = new Key();
        return key.getValue();
    }
}
