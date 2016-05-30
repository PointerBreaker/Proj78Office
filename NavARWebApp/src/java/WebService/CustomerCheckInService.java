/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Elize
 */
@Path("CustomerCheckInService")
public class CustomerCheckInService {
    
    //TODO do somthing with the scanned meetingID?
    @GET
    @Path("putScannedMeetingCode")
    public String putScannedMeetingId(@QueryParam("meetingCode") String meetingCode){
        EmailSenderClass e = new EmailSenderClass();
        e.sendEmail("mizzcoollizz@gmail.com");
        return "Hello QR-scanner!";    
    }
            
}
