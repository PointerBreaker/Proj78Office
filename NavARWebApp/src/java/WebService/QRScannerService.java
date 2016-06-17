/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Database.DatabaseManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONObject;

/**
 *
 * @author Julian
 */
@Path("QRScanner")
public class QRScannerService {

    private static Map<String, Boolean> cache = new HashMap<String, Boolean>();

    @GET
    @Path("checkQRStatus")
    public String checkQRStatus(@QueryParam("code") String code) {
        int status = cache.containsKey(code) && cache.get(code) ? 1 : 0;

        JSONObject returnJSON = new JSONObject();
        returnJSON.put("status", status);

        return returnJSON.toJSONString();
    }

    @GET
    @Path("setQRStatus")
    public String setQRStatus(@QueryParam("code") String code) {
        cache.put(code, true);
        
        return "";
    }

    @GET
    @Path("unsetQRStatus")
    public String unsetQRStatus(@QueryParam("code") String code) {
        cache.put(code, false);
        
        return "";
    }
}
