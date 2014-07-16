import db.AudiofileEntity;
import db.UserEntity;
import db.UserOperation;

import java.util.List;

public class Main {



    public static void main(final String[] args) throws Exception {
        UserEntity user = new UserEntity("user11", "pass11", "user11@gmail.com");
        AudiofileEntity audio = new AudiofileEntity()

        UserOperation uo = new UserOperation();
        AudiofileEntity ao = new AudiofileEntity();

        uo.add(user);

        List<UserEntity> list = uo.getAll();

        list = uo.getAll();
        for (UserEntity c : list)
            System.out.println(c.getId() + ", " + c.getLogin() + ", " + c.getEmail());
        return;
    }
}
