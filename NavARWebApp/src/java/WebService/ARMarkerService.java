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
import org.json.simple.JSONArray;
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
            
            neighbors.add(new ArMarkersNeighbors(Integer.valueOf(columns[2].toString()), Integer.valueOf(columns[0].toString()), Integer.valueOf(columns[1].toString()), columns[3].toString()));
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
        Map<String, Map<String, String>> dirMap = new HashMap<>();
        
        for(ArMarkers marker : markers) {
            Vertex v = new Vertex(marker.getArMarkerId().toString());
            
            vMap.put(marker.getArMarkerId(), v);
            graph.addVertex(v, true);
            
            idMap.put(marker.getArMarkerId().toString(), marker.getArMarkerImageId());
        }
        
        List<ArMarkersNeighbors> neighbors = getAllARMarkersNeighborsInternal();
        
        for(ArMarkersNeighbors neighbor : neighbors) {
            graph.addEdge(vMap.get(neighbor.getArMarkerId()), vMap.get(neighbor.getArMarkerNeighbor()));
            
            String id = ((Integer)neighbor.getArMarkerId()).toString();
            String neighborId = ((Integer)neighbor.getArMarkerNeighbor()).toString();
            String direction = neighbor.getDirection();
            
            if(!dirMap.containsKey(id)) {
                dirMap.put(id, new HashMap<>());
            }
            
            dirMap.get(id).put(neighborId, direction);
        }
        
        String endId = null;
        for(MeetingRooms room : getAllMeetingRooms()) {
            if(room.getMeetingRoomId().toString().equals(meetingRoomId)) {
                endId = ((Integer)room.getArMarkerId()).toString();
                break;
            }
        }
        
        List<JSONArray> path = new ArrayList<>();
        
        List<Vertex> vertexPath = new Dijkstra(graph, startId).getPathTo(endId);
        
        for (int i = 0; i < vertexPath.size(); i++) {
            Vertex v1 = vertexPath.get(i);

            JSONArray arr = new JSONArray();

            arr.add(idMap.get(v1.getLabel()));

            if(i == vertexPath.size() - 1) {
                arr.add(null);
            }
            else {
                arr.add(dirMap.get(v1.getLabel()).get(vertexPath.get(i + 1).getLabel()));
            }
            
            path.add(arr);
        }
      
        JSONObject json = new JSONObject();
        json.put("path", path);
        
        return json.toJSONString();
    }
}
