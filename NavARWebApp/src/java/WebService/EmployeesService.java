/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.Companies;
import Database.DatabaseManager;
import Database.Employees;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        Query q = em.createNamedQuery("Employees.findAll");
        return JSONManager.getJSONObjectByList(q.getResultList(), "employees").toJSONString();
    }
    
    @GET
    @Path("getEmployeeById")
    public String getEmployeeById(@QueryParam("employeeId") int employeeId ){        
        JSONObject json = new JSONObject();
        EntityManager em = DatabaseManager.getNewEntityManager();
        Query q = em.createNamedQuery("Employees.findByEmployeeId");
        q.setParameter("employeeId", employeeId);
        return JSONManager.getJSONObjectByList(q.getResultList(), "employees").toJSONString();
    }
    
    
    //TODO test this
    @PUT
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
            em.persist(employee);          
            return (returnJsonObject.put("succes", "true")).toString();
        }else{
            return (returnJsonObject.put("succes", "false")).toString();
        }        
    }
    
    
}
