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
@Path("routes")
public class RouteService {
    
     
    @GET
    @Path("getRouteToMeetingroom")
    public String getRouteToMeetingRoom(@QueryParam("meetingroomID") String meetingroomID){
        JSONObject json = new JSONObject();
        json.put("GET", "Route To Meetingroom " + meetingroomID);
        return json.toJSONString();
    }
    
    //TODO by route?
    
}
