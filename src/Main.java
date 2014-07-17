import DAO.AudioDAO;
import DAO.UserDAO;
import Entity.AudioEntity;
import Entity.UserEntity;
import util.HibernateUtil;

import java.util.List;

public class Main {



    public static void main(final String[] args) throws Exception {
        /**
         * При работе с ДАО обращатся только через фабрику!!!
         */
        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        UserDAO uDao = Factory.getInstance().getUserDAO();
        UserEntity user = new UserEntity("user143", "pfsgf", "ug6g@gmail.om");
        AudioEntity audio = new AudioEntity("Name2", "Artist2", "Album2","cheta2");


        // Использовать при создании таблицы

        /*UserEntity user0 = new UserEntity("user0", "pass0", "usr0@gmail.com");
        UserEntity user1 = new UserEntity("user1", "pass1", "usr1@gmail.com");
        UserEntity user2 = new UserEntity("user2", "pass2", "usr2@gmail.com");
        UserEntity user3 = new UserEntity("user3", "pass3", "usr3@gmail.com");
        UserEntity user4 = new UserEntity("user4", "pass4", "usr4@gmail.com");
        UserEntity user5 = new UserEntity("user5", "pass5", "usr5@gmail.com");
        UserEntity user6 = new UserEntity("user6", "pass6", "usr6@gmail.com");
        uDao.add(user0);
        uDao.add(user1);
        uDao.add(user2);
        uDao.add(user3);
        uDao.add(user4);
        uDao.add(user5);
        uDao.add(user6);*/

        //aDAO.add(audio);
        //uDao.add(user);
        {
            List<UserEntity> listU;
            List<AudioEntity> listA;
            listU = uDao.getAll();
            for (UserEntity c : listU)
                System.out.println(c.getId() + ", " + c.getLogin() + ", " + c.getEmail());
            listA = aDAO.getAll();
            for (AudioEntity c : listA)
                System.out.println(c.getArtist() + " " + c.getAlbum());
        }
        {
            System.out.println(uDao.loginPassword("usdfgder1", "pass1"));
        }
        List<UserEntity> listU = uDao.getAll();
        for (UserEntity c : listU)
            System.out.println(c.getId() + ", " + c.getLogin() + ", " + c.getEmail());



        //System.out.println(uDao.loginPassword("user1", "pass1"));
        //HibernateUtil.getSessionFactory().close();
        return;
    }
}
