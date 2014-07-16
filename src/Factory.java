import DAO.AudioDAO;
import DAO.UserDAO;

public class Factory {
    private static Factory instance = null;
    private static AudioDAO audioDAO = null;
    private static UserDAO userDAO = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }
    public AudioDAO getAudioDAO(){
        if (audioDAO == null){
            audioDAO = new AudioDAO();
        }
        return audioDAO;
    }
    public UserDAO getUserDAO(){
        if (userDAO == null){
            userDAO = new UserDAO();
        }
        return userDAO;
    }
}
