/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author School
 */
@Path("CustomerCheckInService")
public class CustomerCheckInService {
    
    //TODO do somthing with the scanned meetingID?
    @PUT
    @Path("putScannedMeetingId")
    public String putScannedMeetingId(@QueryParam("meetingId") int meetingId){
        return "Hello QR-scanner!";    
    }
            
}
