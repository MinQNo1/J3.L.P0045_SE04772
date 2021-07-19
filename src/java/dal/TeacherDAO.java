/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Teacher;

/**
 *
 * @author QuyenNV
 */
public class TeacherDAO {
    
    public List<Teacher> getTeachers() throws Exception{
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Teacher> teachers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [timetable].[dbo].[teachers]";
            //open connection
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //add resultset items to result list
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teachers.add(teacher);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return teachers;
    }
}
