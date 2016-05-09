/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rianne
 */
@Entity
@Table(name = "meetings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meetings.findAll", query = "SELECT m FROM Meetings m"),
    @NamedQuery(name = "Meetings.findByMeetingId", query = "SELECT m FROM Meetings m WHERE m.meetingId = :meetingId"),
    @NamedQuery(name = "Meetings.findByMeetingRoomId", query = "SELECT m FROM Meetings m WHERE m.meetingRoomId = :meetingRoomId"),
    @NamedQuery(name = "Meetings.findByCompanyId", query = "SELECT m FROM Meetings m WHERE m.companyId = :companyId"),
    @NamedQuery(name = "Meetings.findByEmployeeId", query = "SELECT m FROM Meetings m WHERE m.employeeId = :employeeId"),
    @NamedQuery(name = "Meetings.findByTime", query = "SELECT m FROM Meetings m WHERE m.time = :time"),
    @NamedQuery(name = "Meetings.findByMeetingCode", query = "SELECT m FROM Meetings m WHERE m.meetingCode = :meetingCode")})
public class Meetings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "meeting_id")
    private Integer meetingId;
    @Column(name = "meeting_room_id")
    private Integer meetingRoomId;
    @Column(name = "company_id")
    private Integer companyId;
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Size(max = 45)
    @Column(name = "meeting_code")
    private String meetingCode;

    public Meetings() {
    }

    public Meetings(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public Integer getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(Integer meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMeetingCode() {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode) {
        this.meetingCode = meetingCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingId != null ? meetingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meetings)) {
            return false;
        }
        Meetings other = (Meetings) object;
        if ((this.meetingId == null && other.meetingId != null) || (this.meetingId != null && !this.meetingId.equals(other.meetingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Database.Meetings[ meetingId=" + meetingId + " ]";
    }
    
}
