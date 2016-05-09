/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Elize
 */
public class DatabaseManager {
    
    /**
     * 
     * @return true if succesful
     */
    public boolean connectToDatabase(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NavARWebAppPU");
        return false;
    }
    
    
    
}
