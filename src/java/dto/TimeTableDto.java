
package dto;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTableDto {
    
    private int id;
    
    private String date;
    
    private String slot;
    
    private String startTime;
    
    private String endTime;
    
    private String classCourse;
    
    private String teacher;
    
    private String roomId;

    public TimeTableDto() {
    }

    public TimeTableDto(int id, String date, String slot, String startTime, String endTime, String classCourse, String teacher, String roomId) {
        this.id = id;
        this.date = date;
        this.slot = slot;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classCourse = classCourse;
        this.teacher = teacher;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getClassCourse() {
        return classCourse;
    }

    public void setClassCourse(String classCourse) {
        this.classCourse = classCourse;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String toDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy");
        return sdf.format(date);
    }
    
    public String toDate(Time time){
        return time.toString().substring(0, 5).replace(":", "h") + "'";
//        return sdf.format(time).replace(":", "h") + "'";
    }
}
