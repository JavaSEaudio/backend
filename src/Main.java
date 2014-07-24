import DAO.AudioDAO;
import DAO.SessionDAO;
import DAO.UserDAO;
import Entity.AudioEntity;
import Entity.UserEntity;
import util.Factory;

public class Main {

    public static void main(final String[] args) throws Exception {
        /**
         * При работе с ДАО обращатся только через фабрику!!!
         * Не забываем менять create на update при повторном запуске,
         * а также дисконектить после выполнения
         */

//        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        UserDAO uDao = Factory.getInstance().getUserDAO();
//        SessionDAO sDao = Factory.getInstance().getSessionDAO();
//
//        Желательно изменять при каждом новом вызове или комментить
        UserEntity user = new UserEntity("root234", "roo345t", "a23dmin@gmail.com", "Sasha", "Coding");
//        AudioEntity audio = new AudioEntity("TestTrack", "TestArtist", "TestAlbum","TestCheta");
//        SessionEntity sess = new SessionEntity(7, "UniQueKey10007");

//        aDAO.add(audio);
        uDao.add(user);
//        sDao.sDao(sess);
//
//        Использовать при создании таблицы

//
//        uDao.add(new UserEntity("user2", "pass2", "us2@m.com", "", ""));
//        uDao.add(new UserEntity("user3", "pass3", "us3@m.com", "", ""));
//        uDao.add(new UserEntity("user4", "pass4", "us4@m.com", "", ""));
//        uDao.add(new UserEntity("user5", "pass5", "us5@m.com", "", ""));
//        uDao.add(new UserEntity("user6", "pass6", "us6@m.com", "", ""));
//        uDao.add(new UserEntity("user7", "pass7", "us7@m.com", "", ""));
//        uDao.add(new UserEntity("Привет", "пароль", "при8@m.com", "", ""));
//        aDAO.add(new AudioEntity("Name1", "Artis1", "Album1","cheta"));
//        aDAO.add(new AudioEntity("Name2", "Artis2", "Album2","cheta"));
//        aDAO.add(new AudioEntity("Name3", "Artis3", "Album3","cheta"));
//        aDAO.add(new AudioEntity("Name4", "Artis4", "Album4","cheta"));
//        aDAO.add(new AudioEntity("Name5", "Artis5", "Album5","cheta"));
//        aDAO.add(new AudioEntity("Name6", "Artis6", "Album6","cheta"));
//        aDAO.add(new AudioEntity("Name7", "Artis7", "Album7","cheta"));
//        aDAO.add(new AudioEntity("Name8", "Artis8", "Album8","cheta"));
//        aDAO.add(new AudioEntity("Name9", "Artis9", "Album9","cheta"));
//        aDAO.add(new AudioEntity("Name10", "Artis10", "Album10","cheta"));


//        sDao.add(new SessionEntity(2, "UniQueKey10002"));
//

        /**
         * Запрос проверки пароля и логина
         */
//        {
//            System.out.println(uDao.loginPassword("user2", "pass2"));
//        }

        /**
         * Изменение записей
         */
//        {
//            user = uDao.getById(5);
//            uDao.change(user);
//            //------------------------
//            audio = aDAO.getById(2);
//            audio.setArtist("artiist2");
//            aDAO.change(audio);
//        }
        /**
         * Удаление записей
         */
//        {
//            uDao.delete(6);

                //aDAO.delete(3);
//            user = uDao.getById(7);
//            audio = aDAO.getById(7);
//            uDao.delete(user);
//            aDAO.delete(audio);
//        }


        /**
         * Вывод базы данных в консоль
         */
//        {
//            List<UserEntity> listU;
//            List<AudioEntity> listA;
//            List<SessionEntity> listS;
//            listU = uDao.getAll();
//            for (UserEntity c : listU)
//                System.out.println(c.getId() + ", " + c.getLogin() + ", " + c.getEmail());
//            listA = aDAO.getAll();
//            for (AudioEntity c : listA)
//                System.out.println(c.getArtist() + " " + c.getAlbum());
//            listS = sDao.getAll();
//            for (SessionEntity c : listS)
//                System.out.println(c.getUserId() + " " + c.getSkey());
//

//        }


//        String login = "ямакаси300";
//        String password = "зass300";
//        System.out.println(login + " " + password);
//
//        UserLogic ul = new UserLogic();
//        user = ul.authorization(login, password);
//        if(user != null ) {
//            System.out.println("yeeeeeeeeeees");
//            System.out.println(user.getLogin());
//        }
//
//        else
//        System.out.println("noooooooooooooooo");
//
//        System.out.println("login = " +uDao.getById(1).getLogin());


//        UserDRO uDro = new UserDRO(uDao.getByLogin("user2"));
//        System.out.println(uDro.getLogin());


System.out.println("THE END");
        return;
    }
}
