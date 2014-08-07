package util;

import DAO.util.Factory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[а-яА-ЯёЁa-zA-Z0-9_-]{3,15}$");       //("^[a-z0-9_-]{3,15}$");

    public static boolean valid(String login){
        if(Factory.getInstance().getUserDAO().getByEmail(login) != null) return false;
        if(Factory.getInstance().getUserDAO().getByLogin(login) != null) return false;
        if(Factory.getInstance().getTmpUserDAO().isEmail(login) != null) return false;
        if(Factory.getInstance().getTmpUserDAO().isLogin(login) != null) return false;
        return true;
    }

    public static boolean minMaxLength(String str, int minLen, int maxLen)  {       //@return true if string is valid
        Pattern pattern = Pattern.compile("\\s");                                   //----contain whitespace
        Matcher matcher = pattern.matcher(str);
        boolean found = matcher.find();

        if(!found && !str.contains(" ")) {
            if (str.length()> minLen) {
                if (str.length() < maxLen) {
                    return true;    //throw new IllegalArgumentException();
                }
            }
        }
        return false;
    }
    // @return true if email is valid
    public static boolean validEmail(String email)  {

        if( !minMaxLength(email, 3 , 225)){
            return false;
        }  else
        if (!email.contains("@") ) {
            return false;
        }  else
            return true;
    }

    public static boolean validRegistration(String login, String passOne, String passTwo, String email){
        if (!passOne.equals(passTwo)){
            return false;
        } else
        if (!StringUtil.minMaxLength(passOne, 2 , 225)){
            return false;
        } else
        if (!StringUtil.minMaxLength(login, 2 , 225)){
            return false;
        } else
        if (!StringUtil.validEmail(email)){
            return false;
        }
        if(!validLogin(login) ){
            return false;
        }
        return true;
    }
    public static boolean validLogin (String login){
        Matcher matcher = USERNAME_PATTERN.matcher(login);
        boolean found = matcher.find();
        if(!found){
            return false;
        }
        return true;
    }

    public static boolean isValidUuid(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static String parse(String data) {
        String filter = "[[^#а-яА-ЯёЁa-zA-Z0-9\\s]+$]";
        data = data.replaceAll(filter, "");
        StringBuilder sb = new StringBuilder(data);
        int q = 0;
        int w;
        if (data.charAt(0) == ' ') {
            w = q + 1;
            while (sb.charAt(w) == ' ') {
                sb.deleteCharAt(w);
            }
            q ++;
            sb.deleteCharAt(0);
        }
        for (int i = 0; i < sb.length() - 1; i ++) {
            int j;
            if (sb.charAt(i) == ' ') {
                j =  i + 1;
                while (sb.charAt(j) == ' ') {
                    sb.deleteCharAt(j);
                }
            }
        }
        return sb.toString();
    }

    public static ArrayList<String> tagParse(String data) {
        ArrayList<String> tagsArray = new ArrayList<>();
        for (int i = 0; i < data.length() - 1; i++) {
            String temp = "";
            if (data.charAt(i) == '#') {
                i++;
                if (i >= data.length()) return tagsArray;
                while (data.charAt(i) != '#') {
                    if (data.charAt(i) == ' ' || data.charAt(i) == ',' || data.charAt(i) == '.') break;
                    temp += data.charAt(i);
                    i++;
                    if (i >= data.length()) break;
                }
            }
            if (!temp.equals("")) {
                i--;
                tagsArray.add(temp);
            }
        }
        return tagsArray;
    }

    public static String fromRusToEng(String data) {
        String result = "";
        char[] rus = {'й','ц','у','к','е','н','г','ш','щ','з','х','ъ','ф','ы','в','а','п','р','о','л','д','ж','э','я','ч','с','м','и','т','ь','б','ю','.'};
        char[] eng = {'q','w','e','r','t','y','u','i','o','p','[',']','a','s','d','f','g','h','j','k','l',';','\'','z','x','c','v','b','n','m',',','.','/'};
        char[] array = data.toCharArray();
        for (int i = 0; i < array.length; i ++) {
            for (int j = 0; j < rus.length; j ++) {
                if (array[i] == rus[j]) {
                    array[i] = eng[j];
                }
            }
            result += array[i];
        }
        return result;
    }
}