/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Elize
 */

public class EmailSenderClass {
    
    private final String applicationEmailAddress = "TamTamMeetingNotifier@gmail.nl";
    @Resource(name = "mail/TamTamMeetingEmailService")
    private Session mailSession;
    
    public void sendEmail(String recipientEmailAddress){
              
        try{
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmailAddress));
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        message.setSubject("An customer has arrived for you at " + dateFormat.format(date));
        message.setContent("<h1>A customer has arrived for you!</h1>", "text/html" );
        Transport.send(message);
        
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    
}
