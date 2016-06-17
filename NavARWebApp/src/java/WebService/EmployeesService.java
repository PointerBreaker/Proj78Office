/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.Companies;
import Database.DatabaseManager;
import Database.Employees;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Elize
 */
@Path("Employees")
public class EmployeesService {
    
    @GET
    @Path("getAllEmployees")
    public String getAllEmployees(){
        JSONObject json = new JSONObject();
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Employees.findAll");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(results, "employees").toJSONString();
    }
    
    @GET
    @Path("getEmployeeById")
    public String getEmployeeById(@QueryParam("employeeId") int employeeId ){        
        JSONObject json = new JSONObject();
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Employees.findByEmployeeId");
        q.setParameter("employeeId", employeeId);
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(results, "employees").toJSONString();
    }
    
   
    @GET    
    @Path("putEmployeeByJSON")
    public String putEmployeeByJSON(@QueryParam("jsonEmployee")String employeeJSONString){
        JSONObject returnJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(employeeJSONString);            
        } catch (ParseException ex) {            
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
            returnJsonObject.put("succes", "false");
            return returnJsonObject.toJSONString();
        }
        
        Employees employee = Employees.createEmployeeByJson(jsonObject);
                             
        if(employee != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            em.clear();
            em.close();            
            returnJsonObject.put("success", "true");
            return returnJsonObject.toJSONString();
        }else{
            returnJsonObject.put("success", "false");
            return returnJsonObject.toJSONString();
        }        
    }
    
    //TODO werkt niet
    @GET    
    @Path("putEmployee")
    public String putEmployee(@QueryParam("name")String name,
                              @QueryParam("emailAddress") String emailAddress, 
                              @QueryParam("password") String password,
                              @QueryParam("salt") String salt){
        
        JSONObject returnJsonObject = new JSONObject();        
        Employees employee = new Employees();
        if(name != null){
            employee.setName(name);
        }
        if(emailAddress != null){
            employee.setEmailAddress(emailAddress);
        }
        if(password != null){
            employee.setPassword(password);
        }
        if(salt != null){
            employee.setSalt(salt);
        }
        
                             
        if(employee != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            em.clear();
            em.close();            
            returnJsonObject.put("success", "true");
            return returnJsonObject.toJSONString();
        }else{
            returnJsonObject.put("success", "false");
            return returnJsonObject.toJSONString();
        }        
    }
    
    @GET
    @Path("LogIn")
    public String logIn(@QueryParam("name")String name, @QueryParam("password") String password){        
        JSONObject json = new JSONObject();
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Employees.findByPasswordAndName");
        q.setParameter("name", name);
        q.setParameter("password", password);
        List results = q.getResultList();
        if(results.isEmpty()){
            json.put("success", "false");
        }else{
            json = JSONManager.getJSONObjectByList(results, "employees");
        }
        em.getTransaction().commit();
        em.clear();
        em.close();        
        return json.toJSONString();
    }   
    
    @GET
    @Path("getMeetingsByEmployeeId")
    public String getMeetingroomIdsByEmployeeId(@QueryParam("employeeId") int employeeId){        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Meetings.findMeetingRoomIdByEmployee");
        q.setParameter("employeeId", employeeId);
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();        
        
        //TODO change this
        JSONObject json = JSONManager.getJSONObjectByList(results, "meetingRooms");
        return json.toJSONString();
    }
}
