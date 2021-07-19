/**
 * ContactDAO.java
 * All Rights Reserved.
 * Copyright(c) by QuyenNV
 */
package dal;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CourseClass;

public class CourseClassDAO {

    public List<CourseClass> getCourseClass() throws Exception {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CourseClass> courses = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [timetable].[dbo].[classCourse]";
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //get system setting result set
            while (rs.next()) {
                CourseClass cc = new CourseClass();
                cc.setId(rs.getInt("id"));
                cc.setName(rs.getString("name"));
                courses.add(cc);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return courses;
    }

}
