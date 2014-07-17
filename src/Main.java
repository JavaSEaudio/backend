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
         * Не забываем менять create на update при повторном запуске,
         * а также дисконектить после выполнения
         */
        AudioDAO aDAO = Factory.getInstance().getAudioDAO();
        UserDAO uDao = Factory.getInstance().getUserDAO();

        //Желательно изменять при каждом новом вызове или комментить
        UserEntity user = new UserEntity("user1", "pass1", "us1@m.com");
        AudioEntity audio = new AudioEntity("Name1", "Artist1", "Album1","cheta1");
        aDAO.add(audio);
        uDao.add(user);

        // Использовать при создании таблицы
        uDao.add(new UserEntity("user2", "pass2", "us2@m.com"));
        uDao.add(new UserEntity("user3", "pass3", "us3@m.com"));
        uDao.add(new UserEntity("user4", "pass4", "us4@m.com"));
        uDao.add(new UserEntity("user5", "pass5", "us5@m.com"));
        uDao.add(new UserEntity("user6", "pass6", "us6@m.com"));
        uDao.add(new UserEntity("user7", "pass7", "us7@m.com"));
        uDao.add(new UserEntity("user8", "pass8", "us8@m.com"));
        aDAO.add(new AudioEntity("Name2", "Artist2", "Album2","cheta2"));
        aDAO.add(new AudioEntity("Name3", "Artist3", "Album3","cheta3"));
        aDAO.add(new AudioEntity("Name4", "Artist4", "Album4","cheta4"));
        aDAO.add(new AudioEntity("Name5", "Artist5", "Album5","cheta5"));
        aDAO.add(new AudioEntity("Name6", "Artist6", "Album6","cheta6"));
        aDAO.add(new AudioEntity("Name7", "Artist7", "Album7","cheta7"));
        aDAO.add(new AudioEntity("Name8", "Artist8", "Album8","cheta8"));


        /**
         * Запрос проверки пароля и логина
         */
        {
            System.out.println(uDao.loginPassword("usdfgder1", "pass1"));
        }

        /**
         * Изменение записей
         */
        {
            user = uDao.getById(3);
            user.setLogin("Changed!!!");
            uDao.change(user);
            //------------------------
            audio = aDAO.getById(3);
            audio.setArtist("Changed!!!");
            aDAO.change(audio);
        }
        /**
         * Удаление записей
         */
        {
            uDao.delete(6);
            aDAO.delete(6);
            user = uDao.getById(7);
            audio = aDAO.getById(7);
            uDao.delete(user);
            aDAO.delete(audio);
        }

        /**
         * Вывод базы данных в консоль
         */
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

        return;
    }
}
