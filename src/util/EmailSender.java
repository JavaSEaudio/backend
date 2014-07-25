package util;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {



    /**
     * @author Crunchify.com
     */

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

//    public static void main(String args[]) throws AddressException, MessagingException {
//        generateAndSendEmail();
//        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
//    }
//    public  static void sendEmail(String emailAdress) throws AddressException, MessagingException {
//        String mailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
//        generateAndSendEmail(emailAdress, mailBody);
////        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
//
//    }
    public  static void sendPassword(String emailAdress, String restoringPassword) {
        String mailBody = "Test email AudioStoraje j8 Password restore"+"Your new password is:  "+restoringPassword + "<br><br> Regards, <br>Crunchify Admin";
        try {
            generateAndSendEmail(emailAdress, mailBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
//        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
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

