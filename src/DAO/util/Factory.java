package DAO.util;

import DAO.AudioDAO;
import DAO.UserDAO;
import DAO.SessionDAO;
import DAO.LikeDAO;

public class Factory {
    private static Factory instance = null;
    private static AudioDAO audioDAO = null;
    private static UserDAO userDAO = null;
    private static SessionDAO sessionDAO = null;
    private static LikeDAO likeDAO = null;
    private static DAO dao = null;


    private Factory(){}

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
    public SessionDAO getSessionDAO(){
        if (sessionDAO == null){
            sessionDAO = new SessionDAO();
        }
        return sessionDAO;
    }

    public LikeDAO getLikeDAO() {
        if (likeDAO == null){
            likeDAO = new LikeDAO();
        }
        return likeDAO;
    }

    public DAO getDao() {
        if (dao == null){
            dao = new DAO();
        }
        return dao;
    }
}
