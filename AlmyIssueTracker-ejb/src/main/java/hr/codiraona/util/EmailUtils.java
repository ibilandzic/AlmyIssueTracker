/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.codiraona.util;

import hr.codiraona.model.Ticket;
import hr.codiraona.model.Users;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author iva.bilandzic
 */
public class EmailUtils {

    private static final String AUTH_USER = "siljo22222@gmail.com";
    private static final String AUTH_PASSWORD = "2903985330171";
    private static final String SERVER_ADDRESS = "smtp.gmail.com";
    

    public static boolean sendNotification(Users user, String subject, String msg) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        try {

            Session session = Session.getInstance(props, null);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-replay@trius.hr"));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject(subject);
            message.setText(msg);
            

            Transport transport = session.getTransport("smtp");
            transport.connect(SERVER_ADDRESS, AUTH_USER, AUTH_PASSWORD);
            transport.sendMessage(message,message.getAllRecipients());

            return true;

        } catch (MessagingException e) {
            
          //check less secure option on gmail
          
          e.getStackTrace();
            return false;
        }
    }
  
    
    

}
