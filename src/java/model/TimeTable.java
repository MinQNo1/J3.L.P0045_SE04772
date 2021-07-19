
package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTable {
    
    private int id;
    
    private Date date;
    
    private int slot;
    
    private int classCourseId;
    
    private int teacherId;
    
    private int roomId;

    public TimeTable() {
    }
    
    public TimeTable(Date date, int slot, int classCourseId, int teacherId, int roomId) {
        this.date = date;
        this.slot = slot;
        this.classCourseId = classCourseId;
        this.teacherId = teacherId;
        this.roomId = roomId;
    }

    public TimeTable(int id, Date date, int slot, int classCourseId, int teacherId, int roomId) {
        this.id = id;
        this.date = date;
        this.slot = slot;
        this.classCourseId = classCourseId;
        this.teacherId = teacherId;
        this.roomId = roomId;
    }

    public String toDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy");
        return sdf.format(date);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getClassCourseId() {
        return classCourseId;
    }

    public void setClassCourseId(int classCourseId) {
        this.classCourseId = classCourseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    
    
}