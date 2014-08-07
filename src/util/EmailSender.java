package util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class EmailSender {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public  static void sendPassword(String emailAdress, String restoringPassword) {
        String mailBody = "Test email AudioStoraje j8 Password restore "+" Your new password is:  "+restoringPassword + "<br><br> Regards, <br>Audio Storage Admin";
        try {
            generateAndSendEmail(emailAdress, mailBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static void generateAndSendEmail(String emailAdress, String mBody) throws MessagingException {
        new ThreadFuck3(emailAdress, mBody).start();
    }
}

