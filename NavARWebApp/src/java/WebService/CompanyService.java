/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.Companies;
import Database.DatabaseManager;
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
@Path("Companies")
public class CompanyService {
    
    @GET
    @Path("getAllCompanies")
    public String getAllCompanies(){
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Companies.findAll");
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(q.getResultList(), "companies").toJSONString();
    }
    
    @GET
    @Path("getCompanyById")
    public String getCompanyByID(@QueryParam("companyId") int companyId ){
        
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Companies.findByCompanyId");
        q.setParameter("companyId", companyId);
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(q.getResultList(), "companies").toJSONString();
    }
    
    
    //TODO test this
    @PUT
    @Path("putCompany")
    public String putCompany(String companyJSONString){
        JSONObject returnJsonObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(companyJSONString);
            
        } catch (ParseException ex) {            
            Logger.getLogger(CompanyService.class.getName()).log(Level.SEVERE, null, ex);
            return (returnJsonObject.put("succes", "false")).toString();
        }
        
        Companies company = Companies.createNewCompanyByJSON(jsonObject);                         
        if(company != null){
            EntityManager em = DatabaseManager.getNewEntityManager();
            em.getTransaction().begin();
            em.persist(company);   
            em.getTransaction().commit();
            em.clear();
            em.close();
            return (returnJsonObject.put("succes", "true")).toString();
        }else{
            return (returnJsonObject.put("succes", "false")).toString();
        }        
    }
    
}
