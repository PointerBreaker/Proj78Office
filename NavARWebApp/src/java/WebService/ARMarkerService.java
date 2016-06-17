/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.ArMarkers;
import Database.ArMarkersNeighbors;
import Database.DatabaseManager;
import Database.MeetingRooms;
import Dijkstra.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONObject;

/**
 *
 * @author Elize
 */
@Path("ARMarkers")
public class ARMarkerService {
    
    @GET
    @Path("getAllARMarkers")
    public String getAllARMarkers(){       
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNamedQuery("ArMarkers.findAll");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        return JSONManager.getJSONObjectByList(results, "ARmarkers").toJSONString();
    }
    
    private List<ArMarkers> getAllARMarkersInternal() {
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("SELECT * FROM ar_markers");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();

        List<ArMarkers> markers = new ArrayList<>();
        for(Object result : results) {
            Object[] columns = (Object[])result;
            
            markers.add(new ArMarkers(Integer.valueOf(columns[0].toString()), columns[1].toString()));
        }
        
        return markers;
    }
    
    private List<ArMarkersNeighbors> getAllARMarkersNeighborsInternal() {
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("SELECT * FROM ar_markers_neighbors");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();

        List<ArMarkersNeighbors> neighbors = new ArrayList<>();
        for(Object result : results) {
            Object[] columns = (Object[])result;
            
            neighbors.add(new ArMarkersNeighbors(Integer.valueOf(columns[2].toString()), Integer.valueOf(columns[0].toString()), Integer.valueOf(columns[1].toString())));
        }
        
        return neighbors;
    }
    
     private List<MeetingRooms> getAllMeetingRooms() {
        EntityManager em = DatabaseManager.getNewEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("SELECT * FROM meeting_rooms");
        List results = q.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();

        List<MeetingRooms> rooms = new ArrayList<>();
        for(Object result : results) {
            Object[] columns = (Object[])result;
            
            rooms.add(new MeetingRooms(Integer.valueOf(columns[0].toString()), columns[1].toString(), Integer.valueOf(columns[2].toString())));
        }
        
        return rooms;
    }
        
    @GET
    @Path("findPathToMarker")
    public String findPathToMarker(@QueryParam("startId") String startId, @QueryParam("meetingRoomId") String meetingRoomId){
        Graph graph = new Graph();
        
        List<ArMarkers> markers = getAllARMarkersInternal();
        
        Map<Integer, Vertex> vMap = new HashMap<>();
        Map<String, String> idMap = new HashMap<>();
        
        for(ArMarkers marker : markers) {
            Vertex v = new Vertex(marker.getArMarkerId().toString());
            
            vMap.put(marker.getArMarkerId(), v);
            graph.addVertex(v, true);
            
            idMap.put(marker.getArMarkerId().toString(), marker.getArMarkerImageId());
        }
        
        List<ArMarkersNeighbors> neighbors = getAllARMarkersNeighborsInternal();
        
        for(ArMarkersNeighbors neighbor : neighbors) {
            graph.addEdge(vMap.get(neighbor.getArMarkerId()), vMap.get(neighbor.getArMarkerNeighbor()));
        }
        
        String endId = null;
        for(MeetingRooms room : getAllMeetingRooms()) {
            if(room.getMeetingRoomId().toString().equals(meetingRoomId)) {
                endId = ((Integer)room.getArMarkerId()).toString();
                break;
            }
        }
        
        List<String> path = new ArrayList<>();
        
        for(Vertex v : new Dijkstra(graph, startId).getPathTo(endId)) {
            path.add(idMap.get(v.getLabel()));
        }
        
        JSONObject json = new JSONObject();
        json.put("path", path);
        
        return json.toJSONString();
    }
}
