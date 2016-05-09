/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rianne
 */
@Embeddable
public class MarkersNeighborsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "neighbor_1")
    private int neighbor1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "neighbor_2")
    private int neighbor2;

    public MarkersNeighborsPK() {
    }

    public MarkersNeighborsPK(int neighbor1, int neighbor2) {
        this.neighbor1 = neighbor1;
        this.neighbor2 = neighbor2;
    }

    public int getNeighbor1() {
        return neighbor1;
    }

    public void setNeighbor1(int neighbor1) {
        this.neighbor1 = neighbor1;
    }

    public int getNeighbor2() {
        return neighbor2;
    }

    public void setNeighbor2(int neighbor2) {
        this.neighbor2 = neighbor2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) neighbor1;
        hash += (int) neighbor2;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarkersNeighborsPK)) {
            return false;
        }
        MarkersNeighborsPK other = (MarkersNeighborsPK) object;
        if (this.neighbor1 != other.neighbor1) {
            return false;
        }
        if (this.neighbor2 != other.neighbor2) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.MarkersNeighborsPK[ neighbor1=" + neighbor1 + ", neighbor2=" + neighbor2 + " ]";
    }
    
}
