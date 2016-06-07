/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import WebService.JSONManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Elize
 */
public class DatabaseManager {
    
    private static EntityManagerFactory emf = null;
    /**
     * 
     * @return true if succesful
     */
    public static boolean connectToDatabase(){
        emf = Persistence.createEntityManagerFactory("NavARWebAppPU3");
        if(emf != null && emf.isOpen()){
            return true;
        }else{
            return false;
        }
    }
    
    private static void checkConnectionAndConnect(){
        if(emf == null || !emf.isOpen()){
            connectToDatabase();
        }
    }
    
    private static EntityManagerFactory getCurrentEntityManagerFactory(){
        checkConnectionAndConnect();
        return emf;
    }
    
    public static EntityManager getNewEntityManager(){
        return getCurrentEntityManagerFactory().createEntityManager();
    }
    
    public static Employees getEmployeeById(int employeeId){        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q2 = em.createNamedQuery("Employees.findByEmployeeId");
        q2.setParameter("employeeId", employeeId);
        JSONObject employees = JSONManager.getJSONObjectByList(q2.getResultList(), "employees");
        em.getTransaction().commit();
        em.clear();
        em.close();        
        if(employees.get("success").equals("false")){
            return null;
        }
        return Employees.createEmployeeByJson((JSONObject) ((JSONArray)employees.get("employees")).get(0));        
    }
            
    public static Companies getCompanyById(int companyId){ 
        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q2 = em.createNamedQuery("Companies.findByCompanyId");
        q2.setParameter("companyId", companyId);
        List results = q2.getResultList();
        JSONObject companies = JSONManager.getJSONObjectByList(results, "companies");
        em.getTransaction().commit();
        em.clear();
        em.close();
            
        if(companies.get("success").equals("false")){
            return null;
        }       
        
        return Companies.createNewCompanyByJSON((JSONObject) ((JSONArray)companies.get("companies")).get(0));        
    }
    
    public static MeetingRooms getMeetingRoomById(int meetingRoomId){
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q2 = em.createNamedQuery("MeetingRooms.findByMeetingRoomId");
        q2.setParameter("meetingRoomId", meetingRoomId);
        JSONObject meetingRooms = JSONManager.getJSONObjectByList(q2.getResultList(), "meetingRooms");
        em.getTransaction().commit();
        em.clear();
        em.close();
        
        if(meetingRooms.get("success").equals("false")){
            return null;
        }
        
        return MeetingRooms.createMeetingRoomByJSON((JSONObject) ((JSONArray)meetingRooms.get("meetingRooms")).get(0));        
    }
    
}
