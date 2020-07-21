
package sendemail;

import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendEmail {
    private final static String host = "smtp.gmail.com";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mailFrom = "";
        String password = "";
        String mailTo = "";
        String subject = "";
        String body = "";
        
        System.out.print("Please enter your mail adress: ");
        mailFrom = scanner.nextLine();
        System.out.print("Please enter your password: ");
        password = scanner.nextLine();
        System.out.print("Please enter receiver mail adress: ");
        mailTo = scanner.nextLine();
        System.out.print("Please add a topic: ");
        subject = scanner.nextLine();
        System.out.print("Please add a message body: ");
        body = scanner.nextLine();
        
        if(mailFrom.trim().length() == 0 || password.trim().length() == 0 
                || mailTo.trim().length() == 0 || subject.trim().length() == 0
                || body.trim().length() == 0)
            System.out.println("Please fill all informations correct");
        else{
            Properties properties = getProperties(mailFrom, password);
            sendEmail(properties, mailFrom, password, mailTo, subject, body);
        }
    }
    
    public static Properties getProperties(String mailFrom, String password){
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", mailFrom);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
    
    public static void sendEmail(Properties properties, String mailFrom, String password, String mailTo, String subject, String body){
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(mailFrom));
            InternetAddress toAddress = new InternetAddress(mailTo);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, mailFrom, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ex) {
            System.out.println("Adress Exception");
        } catch (MessagingException ex) {
            System.out.println("Messaging Exception");
        }
        
    }
}
