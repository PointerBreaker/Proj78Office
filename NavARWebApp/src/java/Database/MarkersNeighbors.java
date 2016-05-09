/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rianne
 */
@Entity
@Table(name = "markers_neighbors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarkersNeighbors.findAll", query = "SELECT m FROM MarkersNeighbors m"),
    @NamedQuery(name = "MarkersNeighbors.findByNeighbor1", query = "SELECT m FROM MarkersNeighbors m WHERE m.markersNeighborsPK.neighbor1 = :neighbor1"),
    @NamedQuery(name = "MarkersNeighbors.findByNeighbor2", query = "SELECT m FROM MarkersNeighbors m WHERE m.markersNeighborsPK.neighbor2 = :neighbor2")})
public class MarkersNeighbors implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MarkersNeighborsPK markersNeighborsPK;

    public MarkersNeighbors() {
    }

    public MarkersNeighbors(MarkersNeighborsPK markersNeighborsPK) {
        this.markersNeighborsPK = markersNeighborsPK;
    }

    public MarkersNeighbors(int neighbor1, int neighbor2) {
        this.markersNeighborsPK = new MarkersNeighborsPK(neighbor1, neighbor2);
    }

    public MarkersNeighborsPK getMarkersNeighborsPK() {
        return markersNeighborsPK;
    }

    public void setMarkersNeighborsPK(MarkersNeighborsPK markersNeighborsPK) {
        this.markersNeighborsPK = markersNeighborsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (markersNeighborsPK != null ? markersNeighborsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarkersNeighbors)) {
            return false;
        }
        MarkersNeighbors other = (MarkersNeighbors) object;
        if ((this.markersNeighborsPK == null && other.markersNeighborsPK != null) || (this.markersNeighborsPK != null && !this.markersNeighborsPK.equals(other.markersNeighborsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MarkersNeighbors[ markersNeighborsPK=" + markersNeighborsPK + " ]";
    }
    
}
