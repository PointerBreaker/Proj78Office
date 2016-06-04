/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.Companies;
import Database.Employees;
import Database.MeetingRooms;
import Database.Meetings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
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
    
    private final String applicationEmailAddress = "navar.tamtam@gmail.com";
    private final String applicationEmailPassword = "TamTamTam";
    private final String applicationEmailHost = "smtp.gmail.com";
    
    public void sendEmail(Employees employee, Companies company, Meetings meeting, MeetingRooms meetingRoom){     
        try{
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", applicationEmailHost);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.user", applicationEmailAddress);
            props.put("mail.smtp.password", applicationEmailPassword);
            props.put("mail.smtp.auth", "true");
            Session mailSession = Session.getDefaultInstance(props);
            
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(applicationEmailAddress));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(employee.getEmailAddress()));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            message.setSubject("Your client has arrived!");
            message.setContent("<h1>Hello " + employee.getName() +",a client from "+ company.getName() + "has arrived for your at " + dateFormat.format(date) + ". <br>"
                    + "Your meeting will start at " + meeting.getTime() + " in " + meetingRoom.getName() + "."
                    + "</h1>", "text/html");

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(applicationEmailHost, applicationEmailAddress, applicationEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
