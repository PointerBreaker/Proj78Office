/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;


import Database.Companies;
import Database.DatabaseManager;
import WebService.JSONManager;
import static Database.DatabaseManager.getNewEntityManager;
import Database.Employees;
import Database.Meetings;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Meetings.findAll");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();        
        return JSONManager.getJSONObjectByList(results, "meetings").toJSONString();
    }  
    
    
    @GET
    @Path("getMeetingByCode")
    @Produces("application/json")
    public String getMeetingByMeetingCode(@QueryParam("meetingCode") String meetingCode){            
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Meetings.findByMeetingCode");
        q.setParameter("meetingCode", meetingCode);
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        JSONObject json = JSONManager.getJSONObjectByList(results, "meetings");

        
        Companies c = DatabaseManager.getCompanyById((int) json.get("company_id"));
        if(c != null){
            try{
                JSONParser jsonParser = new JSONParser();
                JSONObject companyJSON = (JSONObject) jsonParser.parse(c.toString());
                json.put("company_details", companyJSON);      
            } catch (ParseException ex) {
                Logger.getLogger(MeetingsService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
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
        Meetings newMeeting = Meetings.createNewMeetingByJSONObject(json);
        
        if(newMeeting != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(newMeeting);     
            em.getTransaction().commit();
            em.clear();
            em.close();
            return (json.put("succes", "true")).toString();
        }else{
            return (json.put("succes", "false")).toString();
        }
        
    }    
}
