/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.DatabaseManager;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author School
 */
@Path("ARMarkers")
public class ARMarkerService {
    
    @GET
    @Path("getAllARMarkers")
    public String getAllARMarkers(){
        EntityManager em = DatabaseManager.getNewEntityManager();
        Query q = em.createNamedQuery("ArMarkers.findAll");
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(q.getResultList(), "ARmarkers").toJSONString();
    }
}
