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
@Table(name = "meeting_rooms")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeetingRooms.findAll", query = "SELECT m FROM MeetingRooms m"),
    @NamedQuery(name = "MeetingRooms.findByMeetingRoomId", query = "SELECT m FROM MeetingRooms m WHERE m.meetingRoomId = :meetingRoomId"),
    @NamedQuery(name = "MeetingRooms.findByName", query = "SELECT m FROM MeetingRooms m WHERE m.name = :name"),
    @NamedQuery(name = "MeetingRooms.findByArMarkerId", query = "SELECT m FROM MeetingRooms m WHERE m.arMarkerId = :arMarkerId")})
public class MeetingRooms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "meeting_room_id")
    private Integer meetingRoomId;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ar_marker_id")
    private int arMarkerId;

    public MeetingRooms() {
    }

    public MeetingRooms(Integer meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public MeetingRooms(Integer meetingRoomId, int arMarkerId) {
        this.meetingRoomId = meetingRoomId;
        this.arMarkerId = arMarkerId;
    }

    public Integer getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(Integer meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArMarkerId() {
        return arMarkerId;
    }

    public void setArMarkerId(int arMarkerId) {
        this.arMarkerId = arMarkerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingRoomId != null ? meetingRoomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeetingRooms)) {
            return false;
        }
        MeetingRooms other = (MeetingRooms) object;
        if ((this.meetingRoomId == null && other.meetingRoomId != null) || (this.meetingRoomId != null && !this.meetingRoomId.equals(other.meetingRoomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("meeting_room_id", meetingRoomId);
        json.put("name", name);
        json.put("ar_marker_id", arMarkerId);
        return json.toJSONString();
    }
    
}
