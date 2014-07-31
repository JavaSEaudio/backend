package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

        private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$");

        public static boolean minLength(String str, int len)  { //throws IllegalArgumentException
            Pattern pattern = Pattern.compile("\\s");                                   //contain whitespace
            Matcher matcher = pattern.matcher(str);
            boolean found = matcher.find();

            if(!found && !str.contains(" ")) {
                if (str.length() > len) {
            return true;    //throw new IllegalArgumentException();
                }
            }
            return false;
        }

        public static boolean maxLength(String str, int len)  {
            Pattern pattern = Pattern.compile("\\s");                                   //contain whitespace
            Matcher matcher = pattern.matcher(str);
            boolean found = matcher.find();

            if(!found && !str.contains(" ")) {
                if (str.length() < len) {
                    return true;    //throw new IllegalArgumentException();
                }
            }
            return false;
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

            if( !minMaxLength(email, 4 , 225)){
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
         return true;
    }

    public static boolean isValidUuid(String uuid) {
            return UUID_PATTERN.matcher(uuid).matches();
        }

    public static String parse(String data) {
        String s = data;
        String filter = "";//"[^\\w\\s]";//[^'\\-!@#$%^&*()] ";
        String patternstring = "";//"((\\w*\\s)*)";
        Pattern pattern = Pattern.compile(patternstring);
        Matcher m = pattern.matcher(data);

        data = data.replaceAll(filter, "");
        StringBuilder sb = new StringBuilder(data);
        int q = 0;
        int w = 0;
        if (data.charAt(0) == ' ') {

            w = q + 1;
            while (sb.charAt(w) == ' ') {
                sb.deleteCharAt(w);
            }
            q ++;
            sb.deleteCharAt(0);
        }
        for (int i = 0; i < sb.length() - 1; i ++) {
            int j = 0;
            if (sb.charAt(i) == ' ') {
                j =  i + 1;
                while (sb.charAt(j) == ' ') {
                    sb.deleteCharAt(j);
                }
            }
        }
        return sb.toString();
    }
}

