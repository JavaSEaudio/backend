
import DAO.AudioDAO;
import DAO.LikeDAO;
import DAO.UserDAO;
import Entity.AudioEntity;
import Entity.LikeEntity;
import Entity.UserEntity;
import DAO.util.Factory;
import com.google.code.mp3fenge.Mp3Fenge;
import org.apache.log4j.Logger;
import util.Crypto;
import util.Cut;
import util.StringUtil;
import util.ThreadFuck2;

import java.io.File;
import java.util.ArrayList;
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
            aDAO.add(new AudioEntity("Smoke on the Water", "Deep Purple", "Machine Head", ""));
            aDAO.add(new AudioEntity("Highway to hell", "AC/DC", "Highway to hell", "rock"));
            aDAO.add(new AudioEntity("Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV", ""));
            aDAO.add(new AudioEntity("TNT", "AC/DC", "TNT", "rock"));
            aDAO.add(new AudioEntity("Time", "Pink Floyd", "The Dark Side of the Moon", ""));
            aDAO.add(new AudioEntity("Bad Boys", "Bob Marley", "Inner Circle", "reggae"));
            aDAO.add(new AudioEntity("Bohemian Rhapsody", "Queen", "A Night At The Opera", ""));
            aDAO.add(new AudioEntity("Radioactive", "Imagine Dragons", "Radioactive", "indie"));
            aDAO.add(new AudioEntity("Paranoid", "Black Sabbath", "Paranoid", ""));
            aDAO.add(new AudioEntity("Eye of the tiger", "Journey", "Eye of the tiger", "rock"));
            aDAO.add(new AudioEntity("Hell Patrol", "Judas Priest", "Painkiller", ""));
            aDAO.add(new AudioEntity("In the jungle", "Lion King", "In the jungle", "cartoon"));
            aDAO.add(new AudioEntity("Aces High", "Iron Maiden", "Powerslave", ""));
            aDAO.add(new AudioEntity("Crazy Train", "Ozzy Osbourne", "Tribute", "heavy metal"));
            aDAO.add(new AudioEntity("Tush", "ZZ Top", "The Best of ZZ Top", ""));
            aDAO.add(new AudioEntity("We will rock you", "Queen", "News the world", "rock"));
            aDAO.add(new AudioEntity("It's a Long Way to the Top", "AC/DC", "T.N.T", ""));
            aDAO.add(new AudioEntity("Another one bites the dust", "Queen", "The game", "rock"));
            aDAO.add(new AudioEntity("Poison", "Alice Cooper", "Trash", ""));
            aDAO.add(new AudioEntity("In The End", "Linkin Park", "Hybrid Theory", "Alternate rock"));
            aDAO.add(new AudioEntity("Hell Bells", "AC/DC", "Back in Black", "rock"));
            aDAO.add(new AudioEntity("Young And Beautiful", "Lana Del Ray", "Water Tower", "Baroque pop"));
            aDAO.add(new AudioEntity("Highway to Hell", "AC/DC", "Back in Black", "rock"));
            aDAO.add(new AudioEntity("Old Yellow Bricks", "Arctic Monkeys", "Favourite Worst Nightmare", " Psyhodelic rock"));
            aDAO.add(new AudioEntity("Thunderstruck", "AC/DC", "Back in Black", "rock"));
            aDAO.add(new AudioEntity("Fuck U", "Placebo", "Ashtray Heart", "Alternate rock"));
            aDAO.add(new AudioEntity("I Need a Dollar", "Aloe Blacc", "single", "soul"));
            aDAO.add(new AudioEntity("Creep", "Radiohead", " Pablo Honey", "Alternate rock"));
            aDAO.add(new AudioEntity("Black Knight", "Deep Purple", "Deep Purple In Rock", "rock"));
            aDAO.add(new AudioEntity("Cant Stop", "Red Hot Chili Peppers", " By the Way", "Alternate rock"));
            aDAO.add(new AudioEntity("Fireball", "Deep Purple", "Deep Purple In Rock", "rock"));
            aDAO.add(new AudioEntity("Die, Die My Darling", "Metallica", "Elektra", "Heavy metal"));
            aDAO.add(new AudioEntity("Smoke On The Water", "Deep Purple", "Deep Purple In Rock", "rock"));
            aDAO.add(new AudioEntity("Matilda", "Alt - J", "One Awesome wave", "Psyho pop"));
            aDAO.add(new AudioEntity("Crazy", "Gnarls Barkley", "The Odd Couple", "soul"));
            aDAO.add(new AudioEntity("Natural Blues", "Moby", "Play", "Folk"));
            aDAO.add(new AudioEntity("Run", "Gnaris Barkley", "The Odd Couple", "soul"));
            aDAO.add(new AudioEntity("Everyday Robots", "Damon Albarn ", "Everyday Robots", "Folk"));
            aDAO.add(new AudioEntity("Bang", "Gorky Park", "stare", "rock"));
            aDAO.add(new AudioEntity("Moscow Calling", "Gorky Park", "stare", "rock"));
            aDAO.add(new AudioEntity("My Generation", "Gorky Park", "stare", "rock"));
            aDAO.add(new AudioEntity("Such a beautiful place", "John Barry", "single", "instrumental"));
            aDAO.add(new AudioEntity("Billie Jean", "Michael Jackson", "Dangerous", "pop"));
            aDAO.add(new AudioEntity("Earth Song", "Michael Jackson", "Dangerous", "pop"));
            aDAO.add(new AudioEntity("Thriller", "Michael Jackson", "Dangerous", "pop"));
            aDAO.add(new AudioEntity("Send me an Angel", "Scorpions", "Face the Heat", "heavy metal"));
            aDAO.add(new AudioEntity("Time", "Scorpions", "Face the Heat", "heavy metal"));
            aDAO.add(new AudioEntity("Wind of Change", "Scorpions", "Face the Heat", "heavy metal"));
            aDAO.add(new AudioEntity("Come Together", "The Beatles", "Let It Be", "rock n roll"));
            aDAO.add(new AudioEntity("Help!", "The Beatles", "Let It Be", "rock n roll"));
            aDAO.add(new AudioEntity("Imagine", "The Beatles", "Let It Be", "rock n roll"));
            aDAO.add(new AudioEntity("Yellow Submarine", "The Beatles", "Let It Be", "rock n roll"));
            aDAO.add(new AudioEntity("Yesterday", "The Beatles", "Let It Be", "rock n roll"));
            aDAO.add(new AudioEntity("20 Years", "The Civil Wars", "Barton Hollow", "folk"));
























        } catch (Exception e) {
            System.out.println("DB create exception");
            return;
        }
        System.out.println("DB create success");
    }


}
