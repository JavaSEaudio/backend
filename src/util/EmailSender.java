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


    private static void generateAndSendEmail(String emailAdress, String mBody) throws MessagingException {
    String mailBody = mBody;
//Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587"); // TLS Port
        mailServerProperties.put("mail.smtp.auth", "true"); // Enable Authentication
        mailServerProperties.put("mail.smtp.starttls.enable", "true"); // Enable StartTLS
        mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println("Mail Server Properties have been setup successfully..");

//Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        //generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("xcbv"));       //кому
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(emailAdress));
        generateMailMessage.setSubject("AudioStorage J8");
        String emailBody = mailBody;
        try {
            generateMailMessage.setFrom(new InternetAddress("studrada.science@gmail.com", "Audiostorage.com(localhost:888)  - Admin"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

//Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");
        // Enter your correct gmail UserID and Password
        transport.connect("smtp.gmail.com", "studrada.science@gmail.com", "science165");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}

