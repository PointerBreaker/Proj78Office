/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.Companies;
import Database.DatabaseManager;
import Database.Employees;
import Database.MeetingRooms;
import Database.Meetings;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Elize
 */
@Path("CustomerCheckInService")
public class CustomerCheckInService {
    
    @GET
    @Path("putScannedMeetingCode")
    public String putScannedMeetingId(@QueryParam("meetingCode") String meetingCode){
        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Meetings.findByMeetingCode");
        q.setParameter("meetingCode", meetingCode);
        JSONObject meetings = JSONManager.getJSONObjectByList(q.getResultList(), "meetings");
        if(meetings.get("success").equals("true")){
            JSONArray meetingsArray = (JSONArray) meetings.get("meetings");
            for(int i = 0; i < meetingsArray.size(); i++){
                JSONObject meeting = (JSONObject) meetingsArray.get(i);
                int employeeId = ((Long) meeting.get("employee_id")).intValue();
                int companyId = ((Long) meeting.get("company_id")).intValue();
                int meetingRoomId = ((Long) meeting.get("meeting_room_id")).intValue();
                Meetings meetingObject = Meetings.createNewMeetingByJSONObject(meeting);
                Employees employee = DatabaseManager.getEmployeeById(employeeId);
                Companies company = DatabaseManager.getCompanyById(companyId);
                MeetingRooms meetingRoom = DatabaseManager.getMeetingRoomById(meetingRoomId);               
                
                if(employee != null && company != null && meetingObject != null && meetingRoom != null){
                    EmailSenderClass e = new EmailSenderClass();
                    e.sendEmail(employee, company, meetingObject, meetingRoom);
                }
                
            }         
        }
            em.getTransaction().commit();
            em.clear();
            em.close();    
        
        return "Hello QR-scanner!";    
    }
    
//    private Employees getEmployeeById(int employeeId){
//        
//        EntityManager em = DatabaseManager.getNewEntityManager();
//        Query q2 = em.createNamedQuery("Employees.findByEmployeeId");
//        q2.setParameter("employeeId", employeeId);
//        JSONObject employees = JSONManager.getJSONObjectByList(q2.getResultList(), "employees");
//        em.clear();
//        em.close();        
//        
//        if(employees.get("success").equals("false")){
//            return null;
//        }
//        return Employees.createEmployeeByJson((JSONObject) ((JSONArray)employees.get("employees")).get(0));        
//    }
//            
//    private Companies getCompanyById(int companyId){ 
//        
//        EntityManager em = DatabaseManager.getNewEntityManager();
//        Query q2 = em.createNamedQuery("Companies.findByCompanyId");
//        q2.setParameter("companyId", companyId);
//        List results = q2.getResultList();
//        JSONObject companies = JSONManager.getJSONObjectByList(results, "companies");
//        em.clear();
//        em.close();
//            
//        if(companies.get("success").equals("false")){
//            return null;
//        }       
//        
//        return Companies.createNewCompanyByJSON((JSONObject) ((JSONArray)companies.get("companies")).get(0));        
//    }
//    
//    private MeetingRooms getMeetingRoomById(int meetingRoomId){
//        EntityManager em = DatabaseManager.getNewEntityManager();
//        Query q2 = em.createNamedQuery("MeetingRooms.findByMeetingRoomId");
//        q2.setParameter("meetingRoomId", meetingRoomId);
//        JSONObject meetingRooms = JSONManager.getJSONObjectByList(q2.getResultList(), "meetingRooms");
//        em.clear();
//        em.close();
//        
//        if(meetingRooms.get("success").equals("false")){
//            return null;
//        }
//        
//        return MeetingRooms.createMeetingRoomByJSON((JSONObject) ((JSONArray)meetingRooms.get("meetingRooms")).get(0));        
//    }
    
}
