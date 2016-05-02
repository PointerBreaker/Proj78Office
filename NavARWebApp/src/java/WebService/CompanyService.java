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
@Path("Companies")
public class CompanyService {
    
    @GET
    @Path("getAllCompanies")
    public String getAllCompanies(){
        JSONObject json = new JSONObject();
        json.put("GET", "All companies");
        return json.toJSONString();
    }
    
    @GET
    @Path("getCompanyByID")
    public String getCompanyByID(@QueryParam("companyId") String companyId ){
        JSONObject json = new JSONObject();
        json.put("GET", "companyId " + companyId);
        return json.toJSONString();
    }
    
    //TODO put company?
}
