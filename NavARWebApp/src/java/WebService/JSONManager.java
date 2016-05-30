/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Elize
 */
public class JSONManager {
    
    public static JSONObject getJSONObjectByList(List list, String arrayName){
        
        if(!list.isEmpty()){
            try{  
                JSONObject json = new JSONObject();    
                JSONArray jsonArray = new JSONArray();        
                for(Object object: list){
                    JSONParser parser = new JSONParser();
                    jsonArray.add((JSONObject) parser.parse(object.toString()));
                }
                json.put(arrayName, jsonArray);        
                return json;
            } catch (ParseException ex) {
                Logger.getLogger(JSONManager.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        return getErrorMessageJSON();
        
    }
    
    private static JSONObject getErrorMessageJSON(){
        JSONObject json = new JSONObject();
        json.put("succes", "false");
        return json;
    }
    
}
