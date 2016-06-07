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
    
    //TODO test this
    @PUT
    @Consumes("application/json")
    @Path("putEmployee")
    public String putEmployee(String employeeJSONString){
        JSONObject returnJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(employeeJSONString);            
        } catch (ParseException ex) {            
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
            return (returnJsonObject.put("succes", "false")).toString();
        }
        
        Employees employee = Employees.createEmployeeByJson(jsonObject);
                             
        if(employee != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            em.clear();
            em.close();
            return (returnJsonObject.put("succes", "true")).toString();
        }else{
            return (returnJsonObject.put("succes", "false")).toString();
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
    @Path("getMeetingroomIdsByEmployeeId")
    public String getMeetingroomIdsByEmployeeId(@QueryParam("employeeId") int employeeId){        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Meetings.findMeetingRoomIdByEmployee");
        q.setParameter("employeeId", employeeId);
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();        
        JSONObject json = JSONManager.getJSONObjectByList(results, "meetingRooms");
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
}
