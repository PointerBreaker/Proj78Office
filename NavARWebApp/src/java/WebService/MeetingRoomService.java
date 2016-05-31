/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.DatabaseManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Elize
 */
@Path("MeetingRoomService")
public class MeetingRoomService {
    
    @GET
    @Path("getAllMeetingRooms")
    public String getAllMeetingRooms(){
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("MeetingRooms.findAll");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(results, "meetingrooms").toJSONString();
    }
    
}
