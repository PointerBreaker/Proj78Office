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
import org.json.simple.JSONObject;

/**
 *
 * @author Elize
 */

public class EmailSenderClass {
    
    private final String applicationEmailAddress = "navar.tamtam@gmail.com";
    private final String applicationEmailPassword = "TamTamTam";
    private final String applicationEmailHost = "smtp.gmail.com";
    
    
    public void sendEmailToEmployee(Employees employee, Companies company, Meetings meeting, MeetingRooms meetingRoom){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String message = "<h1>Hello " + employee.getName() +"! </h1> <p>A client from "+ company.getName() + " has arrived at " + dateFormat.format(date) + ". <br>"
                    + "Your meeting will start at " + dateFormat.format(meeting.getTime()) + " in " + meetingRoom.getName() + "."
                    + "</p>";
        String subject = "Your client has arrived!";
        String recieverEmailAddress = employee.getEmailAddress();        
        this.sendEmail(recieverEmailAddress, subject, message);       
    }
    
    public void sendEmailToCustomer(Employees employee, Companies company, Meetings meeting, MeetingRooms meetingRoom){
        /*
        If one of the attributes is null and not all the information is there, 
        the email should not be send at all.        
        */
        if(employee == null || company == null || meeting == null || meetingRoom == null){
            return;
        }        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");       
        String message = "<h1>Hello customer! </h1> <p> You have a new meeting with " + employee.getName() + " from Tam/Tam planned! <br> " 
                    + "Your meeting will start at " + dateFormat.format(meeting.getTime()) + " in " + meetingRoom.getName() + ". <br>"
                    + employee.getName() + " is looking forward to seeing you! For questions you can send an E-mail to " + employee.getEmailAddress() + "."
                    + "</p>";
        String subject = "You have a new meeting planned!";
        String recieverEmailAddress = company.getEmailAddress();    
        this.sendEmail(recieverEmailAddress, subject, message);        
    }
    
    
    private void sendEmail(String recieverEmailAddress, String subject, String messageToSend){   
        
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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverEmailAddress));
            message.setSubject(subject);
            message.setContent(messageToSend, "text/html");
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(applicationEmailHost, applicationEmailAddress, applicationEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSenderClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
