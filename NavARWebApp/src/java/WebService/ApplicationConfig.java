/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Elize
 */
@ApplicationPath("NavARWebService")
public class ApplicationConfig extends Application{
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(WebService.ARMarkerService.class);
        resources.add(WebService.CompanyService.class);
        resources.add(WebService.CustomerCheckInService.class);
        resources.add(WebService.EmployeesService.class);
        resources.add(WebService.MeetingRoomService.class);
        resources.add(WebService.MeetingsService.class);
        resources.add(WebService.RouteService.class);
        resources.add(WebService.QRScannerService.class);
    }
}
