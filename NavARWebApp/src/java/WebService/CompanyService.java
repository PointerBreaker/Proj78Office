/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.Companies;
import Database.DatabaseManager;
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
@Path("Companies")
public class CompanyService {
    
    @GET
    @Path("getAllCompanies")
    public String getAllCompanies(){
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Companies.findAll");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(results, "companies").toJSONString();
    }
    
    @GET
    @Path("getCompanyById")
    public String getCompanyByID(@QueryParam("companyId") int companyId ){
        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Companies.findByCompanyId");
        q.setParameter("companyId", companyId);                       
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(results, "companies").toJSONString();
    }
    
    
    //TODO test this
    
    //TODO werkt niet
    @GET
    @Path("putCompanyByJSON")
    public String putCompanyByJSON(@QueryParam("jsonCompany")String companyJSONString){       
        JSONObject returnJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(companyJSONString);
            
        } catch (ParseException ex) {       
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
            returnJsonObject.put("succes", "false");
            return returnJsonObject.toJSONString();
        }
        
        Companies company = Companies.createNewCompanyByJSON(jsonObject);                         
        if(company != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(company);   
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
    @Path("putCompany")
    public String putCompany(@QueryParam("name")String name, @QueryParam("email_address")String email_address){
        
        JSONObject returnJsonObject = new JSONObject();
        if(name == null && email_address == null){
            returnJsonObject.put("reason", "no parameters!");
            JSONManager.getErrorMessageJSON(returnJsonObject);
            return returnJsonObject.toJSONString();
        }
        Companies company = new Companies();
        if(name != null){
            company.setName(name);
        }
        if(email_address != null){
            company.setEmailAddress(email_address);
        }
        if(company != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(company);   
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
    
}
