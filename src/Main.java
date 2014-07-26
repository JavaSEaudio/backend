//import BusinessLogic.FileOperation;
import BusinessLogic.FileOperation;
import DAO.AudioDAO;
import DAO.UserDAO;
import Entity.AudioEntity;
import Entity.UserEntity;
import util.EmailSender;
import util.Factory;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Main {
    private final static Logger logger =  Logger.getLogger("com.audiostorage.report");
    public static void main(final String[] args) throws Exception {
        /**
         * При работе с ДАО обращатся только через фабрику!!!
         * Не забываем менять create на update при повторном запуске,
         * а также дисконектить после выполнения
         */

//        FileOperation m  = new FileOperation("c://upload//audio//930.mp3");
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
String uploadedFileLocation = "c://upload//audio//" + randomPath + ".mp3";
System.out.println("До попытки записать файл на диск");
writeToFile(uploadedInputStream, uploadedFileLocation);
System.out.println("После попытки записать файл на диск");
String output = "File uploaded to : " + uploadedFileLocation;
System.out.println(output);
System.out.println("Работа закончена");
return Response.status(200).Entity(output).build();

*/


//        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
//        UserDAO uDao = Factory.getInstance().getUserDAO();
//        SessionDAO sDao = Factory.getInstance().getSessionDAO();
//
//        Желательно изменять при каждом новом вызове или комментить
//        UserEntity user = new UserEntity("root", "root", "a23dmin@gmail.com", "Sasha", "Coding");
//        AudioEntity audio = new AudioEntity("TestTrack", "TestArtist", "TestAlbum","TestCheta");
//        SessionEntity sess = new SessionEntity(7, "UniQueKey10007");

//        aDAO.add(audio);
//        uDao.add(user);
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


//
//        aDAO.add(new AudioEntity("Numb", "Linkin Park", "Numb","Alternate rock", "/file/audio/1.mp3", "/file/image/1.jpg"));
//        aDAO.add(new AudioEntity("Highway to hell", "AC/DC", "Highway to hell","rock", "/file/audio/2.mp3", "/file/image/2.jpg"));
//        aDAO.add(new AudioEntity("TNT", "AC/DC", "TNT","rock", "/file/audio/3.mp3", "/file/image/3.jpg"));
//        aDAO.add(new AudioEntity("Bad Boys", "Bob Marley", "Inner Circle","reggae", "/file/audio/4.mp3", "/file/image/4.jpg"));
//        aDAO.add(new AudioEntity("Radioactive", "Imagine Dragons", "Radioactive","indie", "/file/audio/5.mp3", "/file/image/5.jpg"));
//        aDAO.add(new AudioEntity("Eye of the tiger", "Journey", "Eye of the tiger","rock", "/file/audio/6.mp3", "/file/image/6.jpg"));
//        aDAO.add(new AudioEntity("In the jungle", "Lion King", "In the jungle","cartoon", "/file/audio/7.mp3", "/file/image/7.jpg"));
//        aDAO.add(new AudioEntity("Crazy Train", "Ozzy Osbourne", "Tribute","heavy metal", "/file/audio/8.mp3", "/file/image/8.jpg"));
//        aDAO.add(new AudioEntity("We will rock you", "Queen", "News the world","rock", "/file/audio/9.mp3", "/file/image/9.jpg"));
//        aDAO.add(new AudioEntity("Another one bites the dust", "Queen", "The game","rock", "/file/audio/10.mp3", "/file/image/10.jpg"));
//        aDAO.add(new AudioEntity("Numb", "Linkin Park", "Numb","Alternate rock", "/file/audio/1.mp3", "/file/image/1.jpg"));
//        aDAO.add(new AudioEntity("Highway to hell", "AC/DC", "Highway to hell","rock", "/file/audio/2.mp3", "/file/image/2.jpg"));
//        aDAO.add(new AudioEntity("TNT", "AC/DC", "TNT","rock", "/file/audio/3.mp3", "/file/image/3.jpg"));
//        aDAO.add(new AudioEntity("Bad Boys", "Bob Marley", "Inner Circle","reggae", "/file/audio/4.mp3", "/file/image/4.jpg"));
//        aDAO.add(new AudioEntity("Radioactive", "Imagine Dragons", "Radioactive","indie", "/file/audio/5.mp3", "/file/image/5.jpg"));
//        aDAO.add(new AudioEntity("Eye of the tiger", "Journey", "Eye of the tiger","rock", "/file/audio/6.mp3", "/file/image/6.jpg"));
//        aDAO.add(new AudioEntity("In the jungle", "Lion King", "In the jungle","cartoon", "/file/audio/7.mp3", "/file/image/7.jpg"));
//        aDAO.add(new AudioEntity("Crazy Train", "Ozzy Osbourne", "Tribute","heavy metal", "/file/audio/8.mp3", "/file/image/8.jpg"));
//        aDAO.add(new AudioEntity("We will rock you", "Queen", "News the world","rock", "/file/audio/9.mp3", "/file/image/9.jpg"));
//        aDAO.add(new AudioEntity("Another one bites the dust", "Queen", "The game","rock", "/file/audio/10.mp3", "/file/image/10.jpg"));

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

        Logger.getLogger("com.audiostorage.admin").info("To admin log1");       // admin-log WAR and ERROR only!!!!!
        Logger.getLogger("com.audiostorage.admin").warn("To admin log1");       // файл log4j.properties добавить в root артефакта!!!
        Logger.getLogger("com.audiostorage.admin").error("To admin log1");      // запустить несколько раз пока проект захавает настройки и пропадут ошибки)
        Logger.getLogger("com.audiostorage.admin").debug("To admin log1");

        Logger.getLogger("com.audiostorage.report").info("To report log2");     // report-log info, warn, error, debug ect...
        Logger.getLogger("com.audiostorage.report").warn("To report log2");
        logger.error("To report log2");
        logger.debug("To report log2");
        logger.info("asdfasdfasdfasdfasdfasdf");

//        EmailSender.sendPassword("kakaha@gmail.com", "7777");
//        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
        System.out.println("THE END");
        return;
    }
}
