//import BusinessLogic.FileOperation;
import DAO.AudioDAO;
import DAO.LikeDAO;
import DAO.UserDAO;
import Entity.AudioEntity;
import Entity.LikeEntity;
import Entity.UserEntity;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.StringUtil;

import java.util.List;

public class Main {
    private final static Logger logger =  Logger.getLogger("com.audiostorage.report");
    public static void main(final String[] args) throws Exception {
        /**
         * При работе с ДАО обращатся только через фабрику!!!
         * Не забываем менять create на update при повторном запуске,
         * а также дисконектить после выполнения
         */

        logger.info("User  Not logged in");


//        FileOperation m  = new FileOperation("c://upload//audio//930");
//        System.out.println(m.getName());
////
//
//        fo.setAlbum("The WAVE");
//        fo.setArtist("Quarijo");
//        fo.setName("Luaniro");
//        fo.setComment("The best of the best!!!");
//        fo.setGenre("Relax");
//        fo.setYearOfRelease(1995);



//        System.out.println(m.getName());
//        System.out.println(m.getArtist());
//        System.out.println(m.getAlbum());
//        System.out.println(m.getComments());
//        System.out.println(m.getGenre());
//        System.out.println(m.getLyrics());
//        System.out.println(m.getLyricsBy());
//        System.out.println(m.getMusicBy());
//        System.out.println(m.getSize());
//        System.out.println(m.getLength());
//        System.out.println(m.getYear());



//        m.setTrack(0);


/**
*
* Random rand = new Random();
System.out.println("Зашли в метод");
int randomPath = rand.nextInt();
String uploadedFileLocation = "c://upload//audio//" + randomPath + "";
System.out.println("До попытки записать файл на диск");
writeToFile(uploadedInputStream, uploadedFileLocation);
System.out.println("После попытки записать файл на диск");
String output = "File uploaded to : " + uploadedFileLocation;
System.out.println(output);
System.out.println("Работа закончена");
return Response.status(200).Entity(output).build();

*/


        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        UserDAO uDao = Factory.getInstance().getUserDAO();
        LikeDAO likeDAO = Factory.getInstance().getLikeDAO();
//        SessionDAO sDao = Factory.getInstance().getSessionDAO();
//
//        Желательно изменять при каждом новом вызове или комментить
        UserEntity user = new UserEntity("root", "root", "giluruj@burstmail.info", "Sasha", "Coding");
        AudioEntity audio = new AudioEntity();
//        SessionEntity sess = new SessionEntity(7, "UniQueKey10007");

//        aDAO.delete(21);
//        aDAO.delete(22);
//        aDAO.add(audio);
        uDao.add(user);
//        likeDAO.add(new LikeEntity(1));
//        sDao.sDao(sess);
        //System.out.println(uDao.getById(-1));
//        audio = aDAO.getById(3);
//        audio.setUserid(6);


//        String ns = new String("li");
//        ns = StringUtil.parse(ns);
//        System.out.println(ns);
//        Использовать при создании таблицы

//
//        uDao.add(new UserEntity("user2", "pass2", "us2@m.com", "", ""));
//        uDao.add(new UserEntity("user3", "pass3", "us3@m.com", "", ""));
//        uDao.add(new UserEntity("user4", "pass4", "us4@m.com", "", ""));
//        uDao.add(new UserEntity("user5", "pass5", "us5@m.com", "", ""));
//        uDao.add(new UserEntity("user6", "pass6", "us6@m.com", "", ""));
//        uDao.add(new UserEntity("user7", "pass7", "us7@m.com", "", ""));
//        uDao.add(new UserEntity("Привет", "пароль", "при8@m.com", "", ""));



        aDAO.add(new AudioEntity("Numb", "Linkin Park", "Numb","Alternate rock", "/rest/get/audio?id=1", "rest/get/image?id=1"));
        aDAO.add(new AudioEntity("Highway to hell", "AC/DC", "Highway to hell","rock", "/rest/get/audio?id=2", "rest/get/image?id=2"));
        aDAO.add(new AudioEntity("TNT", "AC/DC", "TNT","rock", "/rest/get/audio?id=3", "rest/get/image?id=3"));
        aDAO.add(new AudioEntity("Bad Boys", "Bob Marley", "Inner Circle","reggae", "/rest/get/audio?id=4", "rest/get/image?id=4"));
        aDAO.add(new AudioEntity("Radioactive", "Imagine Dragons", "Radioactive","indie", "/rest/get/audio?id=5", "rest/get/image?id=5"));
        aDAO.add(new AudioEntity("Eye of the tiger", "Journey", "Eye of the tiger","rock", "/rest/get/audio?id=6", "rest/get/image?id=6"));
        aDAO.add(new AudioEntity("In the jungle", "Lion King", "In the jungle","cartoon", "/rest/get/audio?id=7", "rest/get/image?id=7"));
        aDAO.add(new AudioEntity("Crazy Train", "Ozzy Osbourne", "Tribute","heavy metal", "/rest/get/audio?id=8", "rest/get/image?id=8"));
        aDAO.add(new AudioEntity("We will rock you", "Queen", "News the world","rock", "/rest/get/audio?id=9", "rest/get/image?id=9"));
        aDAO.add(new AudioEntity("Another one bites the dust", "Queen", "The game","rock", "/rest/get/audio?id=10", "rest/get/image?id=10"));
        aDAO.add(new AudioEntity("Numb", "Linkin Park", "Numb","Alternate rock", "/rest/get/audio?id=1", "rest/get/image?id=1"));
        aDAO.add(new AudioEntity("Highway to hell", "AC/DC", "Highway to hell","rock", "/rest/get/audio?id=2", "rest/get/image?id=2"));
        aDAO.add(new AudioEntity("TNT", "AC/DC", "TNT","rock", "/rest/get/audio?id=3", "rest/get/image?id=3"));
        aDAO.add(new AudioEntity("Bad Boys", "Bob Marley", "Inner Circle","reggae", "/rest/get/audio?id=4", "rest/get/image?id=4"));
        aDAO.add(new AudioEntity("Radioactive", "Imagine Dragons", "Radioactive","indie", "/rest/get/audio?id=5", "rest/get/image?id=5"));
        aDAO.add(new AudioEntity("Eye of the tiger", "Journey", "Eye of the tiger","rock", "/rest/get/audio?id=6", "rest/get/image?id=6"));
        aDAO.add(new AudioEntity("In the jungle", "Lion King", "In the jungle","cartoon", "/rest/get/audio?id=7", "rest/get/image?id=7"));
        aDAO.add(new AudioEntity("Crazy Train", "Ozzy Osbourne", "Tribute","heavy metal", "/rest/get/audio?id=8", "rest/get/image?id=8"));
        aDAO.add(new AudioEntity("We will rock you", "Queen", "News the world","rock", "/rest/get/audio?id=9", "rest/get/image?id=9"));
        aDAO.add(new AudioEntity("Another one bites the dust", "Queen", "The game","rock", "/rest/get/audio?id=10", "rest/get/image?id=10"));
//        audio = aDAO.getById(1);
//        audio.setAlbum("SUPER");
//        aDAO.change(audio);
//        audio = aDAO.getById(21);
//        aDAO.delete(audio);


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

logger.info("END");
System.out.println("THE END");
        return;
    }
}
