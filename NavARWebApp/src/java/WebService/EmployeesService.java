/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONObject;

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
        json.put("GET", "All employees");
        return json.toJSONString();
    }
    
    @GET
    @Path("getEmployeeById")
    public String getEmployeeById(@QueryParam("employeeId") String employeeId ){
        JSONObject json = new JSONObject();
        json.put("GET", "Employee " + employeeId);
        return json.toJSONString();
    }
    
    //TODO put employee?
    
}
