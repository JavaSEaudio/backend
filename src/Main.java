
import DAO.AudioDAO;
import DAO.LikeDAO;
import DAO.UserDAO;
import Entity.AudioEntity;
import Entity.LikeEntity;
import Entity.UserEntity;
import DAO.util.Factory;
import org.apache.log4j.Logger;
import util.Crypto;
import util.StringUtil;

import java.util.List;

public class Main {
    public static void main(final String[] args) throws Exception {
        createDB();
        return;
    }

    private static void createDB(){
        try {
            AudioDAO aDAO = Factory.getInstance().getAudioDAO();
            UserDAO uDao = Factory.getInstance().getUserDAO();

            uDao.add(new UserEntity("Admin", Crypto.MD5("zky"), "admin@email.com", 2));
            uDao.add(new UserEntity("Moderator", Crypto.MD5("moder"), "moder@gmail.com", 1));
            uDao.add(new UserEntity("User", Crypto.MD5("user"), "user@gmail.com", 0));

            aDAO.add(new AudioEntity("Numb", "Linkin Park", "Numb", "Alternate rock"));
            aDAO.add(new AudioEntity("Highway to hell", "AC/DC", "Highway to hell", "rock"));
            aDAO.add(new AudioEntity("TNT", "AC/DC", "TNT", "rock"));
            aDAO.add(new AudioEntity("Bad Boys", "Bob Marley", "Inner Circle", "reggae"));
            aDAO.add(new AudioEntity("Radioactive", "Imagine Dragons", "Radioactive", "indie"));
            aDAO.add(new AudioEntity("Eye of the tiger", "Journey", "Eye of the tiger", "rock"));
            aDAO.add(new AudioEntity("In the jungle", "Lion King", "In the jungle", "cartoon"));
            aDAO.add(new AudioEntity("Crazy Train", "Ozzy Osbourne", "Tribute", "heavy metal"));
            aDAO.add(new AudioEntity("We will rock you", "Queen", "News the world", "rock"));
            aDAO.add(new AudioEntity("Another one bites the dust", "Queen", "The game", "rock"));
            aDAO.add(new AudioEntity("In The End", "Linkin Park", "Hybrid Theory", "Alternate rock"));
            aDAO.add(new AudioEntity("Young And Beautiful", "Lana Del Ray", "Water Tower", "Baroque pop"));
            aDAO.add(new AudioEntity("Old Yellow Bricks", "Arctic Monkeys", "Favourite Worst Nightmare", " Psyhodelic rock"));
            aDAO.add(new AudioEntity("Fuck U", "Placebo", "Ashtray Heart", "Alternate rock"));
            aDAO.add(new AudioEntity("Creep", "Radiohead", " Pablo Honey", "Alternate rock"));
            aDAO.add(new AudioEntity("Cant Stop", "Red Hot Chili Peppers", " By the Way", "Alternate rock"));
            aDAO.add(new AudioEntity("Die, Die My Darling", "Metallica", "Elektra", "Heavy metal"));
            aDAO.add(new AudioEntity("Matilda", "Alt - J", "One Awesome wave", "Psyho pop"));
            aDAO.add(new AudioEntity("Natural Blues", "Moby", "Play", "Folk"));
            aDAO.add(new AudioEntity("Everyday Robots", "Damon Albarn ", "Everyday Robots", "Folk"));
        } catch (Exception e) {
            System.out.println("DB create exception");
            return;
        }
        System.out.println("DB create success");
    }
}
