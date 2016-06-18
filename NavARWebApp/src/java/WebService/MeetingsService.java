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
import Database.MeetingRooms;
import Database.Meetings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
        return json.toJSONString();        
    }
    

    //Put doesnt work :/
    @GET
    @Path("putMeeting")
    public String putMeeting(@QueryParam("meetingRoomId") int meeting_room_id,
                            @QueryParam("companyId") int company_id,
                            @QueryParam("employeeId") int employee_id,
                            @QueryParam("time") String time,
                            @QueryParam("meetingCode") String meeting_code  
                            ){      
        try{
            JSONObject json = new JSONObject();      
        if(meeting_room_id == 0 && company_id == 0 && 
                employee_id == 0 && time == null && meeting_code == null ){
            JSONManager.getErrorMessageJSON(json);
            json.put("reason", "No parameters for input!");
            return json.toJSONString();
        }
        Meetings newMeeting = new Meetings();
       
        if(meeting_room_id != 0){
            newMeeting.setMeetingRoomId(meeting_room_id);
        }
        if(company_id != 0){
            newMeeting.setCompanyId(company_id);
        }
        if(employee_id != 0){
            newMeeting.setEmployeeId(employee_id);
        }
        if(time != null && !time.equals("")){
            try{            
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
            newMeeting.setTime(format.parse(time));
            }catch (java.text.ParseException ex) {
                ex.printStackTrace();
                JSONManager.getErrorMessageJSON(json);    
                json.put("reason", "Not the right time format: " + time);
                return json.toJSONString();
            }  
        }        
        
        if(meeting_code != null && !meeting_code.equals(null)){
            meeting_code = meeting_code.replace("_", " ");
            newMeeting.setMeetingCode(meeting_code);        
        }                
       
        if(newMeeting != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(newMeeting);  
            em.getTransaction().commit();
            em.clear();
            em.close();   
            
            //Only if all the parameters are provided the email should be send.
            if(company_id != 0 && employee_id != 0 && meeting_room_id != 0 ){
                Companies company = DatabaseManager.getCompanyById(company_id);
                Employees employee = DatabaseManager.getEmployeeById(employee_id); 
                MeetingRooms meetingroom = DatabaseManager.getMeetingRoomById(meeting_room_id); 
                EmailSenderClass e = new EmailSenderClass();
                e.sendEmailToCustomer(employee, company, newMeeting, meetingroom);            
            }          
            
            JSONObject jsonReturn = new JSONObject();
            jsonReturn.put("success", "true");
            return jsonReturn.toJSONString();
        }else{
            JSONObject jsonReturn = new JSONObject();
            jsonReturn.put("success", "false");
            return jsonReturn.toJSONString();
        }        
        }catch(Exception ex){
            System.out.println(ex);
            JSONObject jsonReturn = new JSONObject();
            jsonReturn.put("success", "false");
            return jsonReturn.toJSONString();        
        }       
    }    
        
    
}
