import db.UserEntity;
import db.UserOperation;

import java.util.List;

public class Main {



    public static void main(final String[] args) throws Exception {
        UserEntity c1 = new UserEntity("user10", "pass10", "user10@gmail.com");


        UserOperation uo = new UserOperation();

        uo.add(c1);

        List<UserEntity> list = uo.getAll();

        list = uo.getAll();
        for (UserEntity c : list)
            System.out.println(c.getId() + ", " + c.getLogin() + ", " + c.getEmail());
    }
}
