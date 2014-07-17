package BusinessLogic;

import DAO.UserDAO;
import Entity.UserEntity;
import util.Factory;

/**
 * Created by Dmytry on 7/17/2014.
 */
public class UserLogic {
    private static UserDAO uDao = Factory.getInstance().getUserDAO();
    public UserLogic() {

    }

    public static UserEntity login(String login, String pass) {
        UserEntity user;
        int id = uDao.loginPassword(login, pass);
        if (id == -1) {
            return null;
        }
        else {
            return uDao.getById(id);
        }
    }

    public static String uid() {
        Key key = new Key();
        return key.getValue();
    }
}
