package DAO.util;

import DAO.*;


public class Factory {
    private static Factory instance = null;
    private static AudioDAO audioDAO = null;
    private static UserDAO userDAO = null;
    private static SessionDAO sessionDAO = null;
    private static LikeDAO likeDAO = null;
    private static BannedDAO bannedDAO = null;
    private static PrivateDAO privateDAO = null;
    private static RestoreDAO restoreDAO = null;
    private static TmpUserDAO tmpUserDAO = null;
    private static CommentsDAO commentsDAO = null;
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

    public BannedDAO getBannedDAO() {
        if (bannedDAO == null){
            bannedDAO = new BannedDAO();
        }
        return  bannedDAO;
    }
    public RestoreDAO getRestoreDAO() {
        if (restoreDAO == null){
            restoreDAO = new RestoreDAO();
        }
        return  restoreDAO;
    }
    public PrivateDAO getPrivateDAO() {
        if (privateDAO == null){
            privateDAO = new PrivateDAO();
        }
        return privateDAO;
    }

    public TmpUserDAO getTmpUserDAO() {
        if (tmpUserDAO == null){
            tmpUserDAO = new TmpUserDAO();
        }
        return tmpUserDAO;
    }

    public CommentsDAO getCommentsDAO() {
        if (commentsDAO == null){
            commentsDAO = new CommentsDAO();
        }
        return commentsDAO;
    }

    public DAO getDao() {
        if (dao == null){
            dao = new DAO();
        }
        return dao;
    }
}
