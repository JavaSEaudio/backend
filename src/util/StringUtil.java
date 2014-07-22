package util;

/**
 * Created by Андрей on 21.07.2014.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

        private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$");

        public static boolean minLength(String str, int len)  { //throws IllegalArgumentException
            if(str != null && !str.isEmpty()) {
                if (str.length() < len) {
            return true;    //throw new IllegalArgumentException();
                }
            }
            return false;
        }
//
        public static boolean maxLength(String str, int len)  {
            if(str != null && !str.isEmpty()) {
                if (str.length() > len) {
                    return true;    //throw new IllegalArgumentException();
                }
            }
            return false;
        }

    public static boolean minMaxLength(String str, int minLen, int maxLen)  {       //@return true if string is valid
        Pattern pattern = Pattern.compile("\\s");                                   //contain whitespace
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

//        public static void validEmail(String email) throws IllegalArgumentException {
//            minLength(email, 4);
//            maxLength(email, 255);
//            if (!email.contains("@") || StringUtils.containsWhitespace(email)) {
//                throw new IllegalArgumentException();
//            }
//        }

        public static boolean isValidUuid(String uuid) {
            return UUID_PATTERN.matcher(uuid).matches();
        }

    }

