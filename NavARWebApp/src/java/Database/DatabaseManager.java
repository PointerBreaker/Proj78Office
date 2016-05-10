/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Elize
 */
public class DatabaseManager {
    
    private static EntityManagerFactory emf = null;
    /**
     * 
     * @return true if succesful
     */
    public static boolean connectToDatabase(){
        emf = Persistence.createEntityManagerFactory("NavARWebAppPU");
        if(emf != null && emf.isOpen()){
            return true;
        }else{
            return false;
        }
    }
    
    private static void checkConnectionAndConnect(){
        if(emf == null || emf.isOpen()){
            connectToDatabase();
        }
    }
    
    private static EntityManagerFactory getCurrentEntityManagerFactory(){
        checkConnectionAndConnect();
        return emf;
    }
    
    public static EntityManager getNewEntityManager(){
        return getCurrentEntityManagerFactory().createEntityManager();
    }
    
}
