/**
 * PictureDAO.java
 * All Rights Reserved.
 * Copyright(c) by QuyenNV
 */
package dal;

import context.DBContext;
import dto.TimeTableDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.TimeTable;

public class TimeTableDAO {

    public List<TimeTableDto> pagging(int pageIndex, int pageSize, Date startTime, Date endTime) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        List<TimeTableDto> list = new ArrayList<>();
        //get value between start to end
        int start = (pageIndex - 1) * pageSize + 1;
        int end = pageIndex * pageSize;
        String sql = "select * "
                + "from (select ROW_NUMBER() over (order by tt.id ASC) as No, tt.id, tt.date, sl.name, "
                + "sl.startTime, sl.endTime, cc.name as cc_name, tc.name as tc_name, ro.name as ro_name "
                + "from [timetable].[dbo].[timeTable] tt "
                + "INNER JOIN [timetable].[dbo].[slots] sl ON sl.id = tt.slot "
                + "INNER JOIN [timetable].[dbo].[classCourse] cc ON cc.id = tt.classCourseId "
                + "INNER JOIN [timetable].[dbo].[rooms] ro ON ro.id = tt.roomId "
                + "INNER JOIN [timetable].[dbo].[teachers] tc ON tc.id = tt.teacherId) as x "
                + "where No between ? and ?";
        if (startTime != null) {
            sql += " and x.date >= ?";
        }
        if (endTime != null) {
            sql += " and x.date <= ?";
        }
        sql += " order by x.date, x.startTime";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setObject(i++, start);
            ps.setObject(i++, end);
            if (startTime != null) {
                ps.setObject(i++, startTime);
            }
            if (endTime != null) {
                ps.setObject(i, endTime);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                TimeTableDto dto = new TimeTableDto();
                dto.setId(rs.getInt("id"));
                dto.setDate(dto.toDate(rs.getDate(3)));
                dto.setSlot(rs.getString(4));
                dto.setStartTime(dto.toDate(rs.getTime(5)));
                dto.setEndTime(dto.toDate(rs.getTime(6)));
                dto.setClassCourse(rs.getString(7));
                dto.setTeacher(rs.getString(8));
                dto.setRoomId(rs.getString(9));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return list;
    }

    public int countSearch(Date startTime, Date endTime) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        int count = 0;
        //get value between start to end
        String sql = "select count(*) from [timetable].[dbo].[timeTable] tt where 1 = 1";
        if (startTime != null) {
            sql += " and tt.date >= ?";
        }
        if (endTime != null) {
            sql += " and tt.date <= ?";
        }
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, startTime);
            ps.setObject(2, endTime);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return count;
    }

    public int countAll() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        int count = 0;
        //get value between start to end
        String sql = "select count(*) from [timetable].[dbo].[timeTable] tt";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return count;
    }

    public boolean insertTimeTable(TimeTable timeTable) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        String sql = "Insert into [timetable].[dbo].[timeTable](date, slot, classCourseId, teacherId, roomId) values (?,?,?,?,?)";
        boolean inserted;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, timeTable.getDate());
            ps.setObject(2, timeTable.getSlot());
            ps.setObject(3, timeTable.getClassCourseId());
            ps.setObject(4, timeTable.getTeacherId());
            ps.setObject(5, timeTable.getRoomId());
            inserted = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            db.closeConnection(null, ps, conn);
        }
        return inserted;
    }

    public boolean updateTimeTable(TimeTable timeTable) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        String sql = "Update [timetable].[dbo].[timeTable] "
                + "set date = ?, slot = ?, classCourseId = ?, teacherId = ?, roomId = ? "
                + "where id = ?";
        boolean updated;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, timeTable.getDate());
            ps.setObject(2, timeTable.getSlot());
            ps.setObject(3, timeTable.getClassCourseId());
            ps.setObject(4, timeTable.getTeacherId());
            ps.setObject(5, timeTable.getRoomId());
            ps.setObject(6, timeTable.getId());
            updated = ps.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            db.closeConnection(null, ps, conn);
        }
        return updated;
    }

    public boolean deleteTimeTable(int id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        String sql = "Delete from [timetable].[dbo].[timeTable] where id = ?";
        boolean deleted;
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, id);
            deleted = ps.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            db.closeConnection(null, ps, conn);
        }
        return deleted;
    }

    public TimeTable getOne(int id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        String sql = "Select * from [timetable].[dbo].[timeTable] where id = ?";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                TimeTable timeTable = new TimeTable();
                timeTable.setId(rs.getInt(1));
                timeTable.setDate(rs.getDate(2));
                timeTable.setSlot(rs.getInt(3));
                timeTable.setClassCourseId(rs.getInt(4));
                timeTable.setTeacherId(rs.getInt(5));
                timeTable.setRoomId(rs.getInt(6));
                return timeTable;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return null;
    }
    
    public boolean existedTimetable(TimeTable timeTable) throws Exception{
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        String sql = "Select count(*) from [timetable].[dbo].[timeTable] "
                + "where (date = ? and slot = ? and roomId = ?) and (teacherId = ? or classCourseId = ?)";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setObject(1, timeTable.getDate());
            ps.setObject(2, timeTable.getSlot());
            ps.setObject(3, timeTable.getRoomId());
            ps.setObject(4, timeTable.getTeacherId());
            ps.setObject(5, timeTable.getClassCourseId());
//            ps.setObject(6, timeTable.getId());
            rs = ps.executeQuery();
            while(rs.next()){
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return false;
    }
}
