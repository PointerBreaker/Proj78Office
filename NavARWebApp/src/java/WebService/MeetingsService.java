/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;


import Database.DatabaseManager;
import WebService.JSONManager;
import static Database.DatabaseManager.getNewEntityManager;
import Database.Meetings;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        EntityManager em = DatabaseManager.getNewEntityManager();
        Query q = em.createNamedQuery("Meetings.findAll");
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(q.getResultList(), "meetings").toJSONString();
    }  
    
    
    @GET
    @Path("getMeetingById")
    @Produces("application/json")
    public String getMeetingById(@QueryParam("meetingId") int meetingId){
        JSONObject json = new JSONObject();        
        EntityManager em = DatabaseManager.getNewEntityManager();
        Query q = em.createNamedQuery("Meetings.findByMeetingId");
        q.setParameter("meetingId", meetingId);
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(q.getResultList(), "meetings").toJSONString();
        
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
        Meetings newMeeting = Meetings.createNewMeetingByJSONObject(json);
        
        if(newMeeting != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.persist(newMeeting);     
            em.clear();
            em.close();
            return (json.put("succes", "true")).toString();
        }else{
            return (json.put("succes", "false")).toString();
        }
        
    }    
}
