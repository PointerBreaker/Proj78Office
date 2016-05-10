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
import org.json.simple.JSONArray;
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
        JSONArray jsonArray = new JSONArray();
        
        JSONObject json1 = new JSONObject();
        json1.put("meeting_id", "1");
        json1.put("meeting_room_id", "10");
        json1.put("employee_id", "101");
        json1.put("time", "10-05-2016");
        json1.put("company_id", "501");
        json1.put("meeting_code", "12356987");
        jsonArray.add(json1);
        
        JSONObject json2 = new JSONObject();
        json2.put("meeting_id", "2");
        json2.put("meeting_room_id", "7");
        json2.put("employee_id", "12401");
        json2.put("time", "10-10-2016");
        json2.put("company_id", "506");
        json2.put("meeting_code", "14588564");
        jsonArray.add(json1);
        
        
        
        json.put("meetings",jsonArray);

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
