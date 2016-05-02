/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Elize
 */
@Path("meetings")
public class MeetingsService {    
    
    @GET   
    @Path("getAllMeetings")
    @Produces("application/json")
    public String getAllMeetings(){
        JSONObject json = new JSONObject();
        json.put("name", "Hello world!");
       return json.toJSONString();
    }  
    
    
    @GET
    @Path("getMeetingById")
    @Produces("application/json")
    public String getMeetingById(@QueryParam("meetingId") String meetingId){
        JSONObject json = new JSONObject();
        json.put("id", meetingId);        
        return json.toJSONString();
    }
    
    //TODO test this
    @PUT
    @Consumes("application/json")
    @Path("putMeeting")
    public String putMeeting(String meeting){
        JSONParser parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(meeting);
        } catch (ParseException ex) {
            return (new JSONObject().put("message", "Not a JSON object!").toString());
        }
        
        return json.toJSONString();
    }
    
}
