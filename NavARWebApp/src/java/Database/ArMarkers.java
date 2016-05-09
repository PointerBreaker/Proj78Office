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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rianne
 */
@Entity
@Table(name = "ar_markers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArMarkers.findAll", query = "SELECT a FROM ArMarkers a"),
    @NamedQuery(name = "ArMarkers.findByArMarkerId", query = "SELECT a FROM ArMarkers a WHERE a.arMarkerId = :arMarkerId")})
public class ArMarkers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ar_marker_id")
    private Integer arMarkerId;

    public ArMarkers() {
    }

    public ArMarkers(Integer arMarkerId) {
        this.arMarkerId = arMarkerId;
    }

    public Integer getArMarkerId() {
        return arMarkerId;
    }

    public void setArMarkerId(Integer arMarkerId) {
        this.arMarkerId = arMarkerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (arMarkerId != null ? arMarkerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArMarkers)) {
            return false;
        }
        ArMarkers other = (ArMarkers) object;
        if ((this.arMarkerId == null && other.arMarkerId != null) || (this.arMarkerId != null && !this.arMarkerId.equals(other.arMarkerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.ArMarkers[ arMarkerId=" + arMarkerId + " ]";
    }
    
}
