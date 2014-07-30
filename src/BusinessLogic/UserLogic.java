package BusinessLogic;

import DAO.UserDAO;
import Entity.UserEntity;
import DAO.util.Factory;

public class UserLogic {
    private static UserDAO uDao = Factory.getInstance().getUserDAO();
    public UserLogic() {}

    public static UserEntity authorization(String login, String pass) {
        int id = uDao.loginPassword(login, pass);
        if (id == -1) {
            id = uDao.emailPassword(login, pass);
            if(id == -1) {
                return null;
            } else {
                return uDao.getById(id);
            }
        } else {
            return uDao.getById(id);
        }
    }
    public static String uid(int count) {
        String temp = "";
        for(int i=0; i<count;i++){
            int vubor =(int) (Math.random()*3+1);
            switch (vubor){
                case 1:{//генирируем Большие букви
                    temp += (char) ((int)(Math.random()*25+65));
                }
                break;
                case 2:{ //генирируем цифры
                    temp += (char) ((int)(Math.random()*9+48));
                    break;
                }
                case 3:{//Генирируем маленькие буквы
                    temp+=(char)((int)(Math.random()*25+97));
                    break;
                }
            }
        }
        return temp;
    }
}