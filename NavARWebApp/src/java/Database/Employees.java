/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.simple.JSONObject;

/**
 *
 * @author Rianne
 */
@Entity
@Table(name = "employees") 
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
    @NamedQuery(name = "Employees.findByEmployeeId", query = "SELECT e FROM Employees e WHERE e.employeeId = :employeeId"),
    @NamedQuery(name = "Employees.findByName", query = "SELECT e FROM Employees e WHERE e.name = :name"),
    @NamedQuery(name = "Employees.findByPassword", query = "SELECT e FROM Employees e WHERE e.password = :password"),
    @NamedQuery(name = "Employees.findByPasswordAndName", query = "SELECT e FROM Employees e WHERE e.password = :password AND e.name = :name"),
    @NamedQuery(name = "Employees.findBySalt", query = "SELECT e FROM Employees e WHERE e.salt = :salt")})
public class Employees implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "employee_id")
    private Integer employeeId;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "salt")
    private String salt;
    @Size(max = 45)
    @Column(name = "email_address")
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Employees() {
    }

    public Employees(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("employee_id", employeeId);
        jsonObject.put("name", name);
        jsonObject.put("email_address", emailAddress);
        return jsonObject.toJSONString();        
    }
    
    public static Employees createEmployeeByJson(JSONObject jsonObject){
        if(!jsonObject.containsKey("name")
           && !jsonObject.containsKey("password")
           && !jsonObject.containsKey("salt")
           && !jsonObject.containsKey("email_address")                
                ){    
        return null;
        }
        
        Employees newEmployee = new Employees();
        newEmployee.setEmployeeId(((Long) jsonObject.get("employee_id")).intValue());
        if(jsonObject.containsKey("name")){            
            newEmployee.setName((String) jsonObject.get("name"));
        }
        if(jsonObject.containsKey("password")){
            newEmployee.setPassword((String) jsonObject.get("password"));
        }
        if(jsonObject.containsKey("salt")){
           newEmployee.setSalt((String) jsonObject.get("salt"));   
        }
        if(jsonObject.containsKey("email_address")){
            newEmployee.emailAddress = (String) jsonObject.get("email_address");
        }        
        return newEmployee;
    }
    
}
