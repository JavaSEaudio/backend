import DAO.AudioDAO;
import Entity.AudioEntity;

import org.apache.log4j.Priority;
import util.Factory;
import org.apache.log4j.Logger;

public class Main {

    //private static Logger logger = Logger.getLogger(Main.class);

//    private static Logger log = Logger.getLogger( Main.class );
//    private static Logger connectionsLog = Logger.getLogger( "connections." + Main.class.getName() );
//    private static Logger stacktracesLog = Logger.getLogger( "stacktraces." + Main.class.getName() );
//    private static Logger httpLog = Logger.getLogger("http." + Main.class.getName());
//   private static Logger httpLog = Logger.getLogger( "http." + Main.class.getName() );
    public static void main(final String[] args) throws Exception {

//        stacktracesLog.info("TESSSSSSSSSSTTTTTTT1");
//        stacktracesLog.warn("TESSSSSSSSSSTTTTTTT2");
//
//        log.debug("TESSSSSSSSSSTTTTTTT3");
//        connectionsLog.error("TESSSSSSSSSSTTTTTTT4");
//        httpLog.info("TESSSSSSSSSSTTTTTTT5");
        Logger.getLogger("com.vaannila.admin").error("To admin log1");
        Logger.getLogger("com.vaannila.report").error("To report log2");


        /**
         * При работе с ДАО обращатся только через фабрику!!!
         * Не забываем менять create на update при повторном запуске,
         * а также дисконектить после выполнения
         */

//        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        //UserDAO uDao = Factory.getInstance().getUserDAO();
        //SessionDAO sDao = Factory.getInstance().getSessionDAO();

        //Желательно изменять при каждом новом вызове или комментить
        //UserEntity user = new UserEntity("root", "root", "admin@gmail.com", "Sasha", "Coding");
        //AudioEntity audio = new AudioEntity("TestTrack", "TestArtist", "TestAlbum","TestCheta");
        //SessionEntity sess = new SessionEntity(7, "UniQueKey10007");

//        aDAO.add(audio);
//        uDao.add(user);
        //sDao.sDao(sess);

        // Использовать при создании таблицы

//
//        uDao.add(new UserEntity("user2", "pass2", "us2@m.com", "", ""));
//        uDao.add(new UserEntity("user3", "pass3", "us3@m.com", "", ""));
//        uDao.add(new UserEntity("user4", "pass4", "us4@m.com", "", ""));
//        uDao.add(new UserEntity("user5", "pass5", "us5@m.com", "", ""));
//        uDao.add(new UserEntity("user6", "pass6", "us6@m.com", "", ""));
//        uDao.add(new UserEntity("user7", "pass7", "us7@m.com", "", ""));
//        uDao.add(new UserEntity("Привет", "пароль", "при8@m.com", "", ""));
//        aDAO.add(new AudioEntity("Name30", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name31", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name32", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name33", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name34", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name35", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name36", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name37", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name38", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name39", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name40", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name41", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name42", "Artis", "Album","cheta"));
//        aDAO.add(new AudioEntity("Name43", "Artis", "Album","cheta"));

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

//



        System.out.println("END");
        return;
    }
}
