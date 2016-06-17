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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julian
 */
@Entity
@Table(name = "ar_markers_neighbors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArMarkersNeighbors.findAll", query = "SELECT a FROM ArMarkersNeighbors a"),
    @NamedQuery(name = "ArMarkersNeighbors.findByArMarkerId", query = "SELECT a FROM ArMarkersNeighbors a WHERE a.arMarkerId = :arMarkerId"),
    @NamedQuery(name = "ArMarkersNeighbors.findByArMarkerNeighbor", query = "SELECT a FROM ArMarkersNeighbors a WHERE a.arMarkerNeighbor = :arMarkerNeighbor"),
    @NamedQuery(name = "ArMarkersNeighbors.findById", query = "SELECT a FROM ArMarkersNeighbors a WHERE a.id = :id")})
public class ArMarkersNeighbors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ar_marker_id")
    private int arMarkerId;
    @Basic(optional = false)
    @Column(name = "ar_marker_neighbor")
    private int arMarkerNeighbor;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "direction")
    private String direction;
    
    public ArMarkersNeighbors() {
    }

    public ArMarkersNeighbors(Integer id) {
        this.id = id;
    }

    public ArMarkersNeighbors(Integer id, int arMarkerId, int arMarkerNeighbor, String direction) {
        this.id = id;
        this.arMarkerId = arMarkerId;
        this.arMarkerNeighbor = arMarkerNeighbor;
        this.direction = direction;
    }

    public int getArMarkerId() {
        return arMarkerId;
    }

    public void setArMarkerId(int arMarkerId) {
        this.arMarkerId = arMarkerId;
    }

    public int getArMarkerNeighbor() {
        return arMarkerNeighbor;
    }

    public void setArMarkerNeighbor(int arMarkerNeighbor) {
        this.arMarkerNeighbor = arMarkerNeighbor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArMarkersNeighbors)) {
            return false;
        }
        ArMarkersNeighbors other = (ArMarkersNeighbors) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "image.ArMarkersNeighbors[ id=" + arMarkerId + ", " + "neighbor=" + arMarkerNeighbor + ", direction=" + direction + "]";
    }
    
}
