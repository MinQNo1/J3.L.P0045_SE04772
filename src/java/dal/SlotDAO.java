/**
 * GalleryDAO.java
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
import model.Slot;

/**
 * SlotDAO.<br>
 *
 * <pre>
 * Class handling with data in database
 * In this class, it handle the process below.
 *
 * .getGalleries.
 *
 *
 * </pre>
 *
 * @author AM
 * @version 1.0
 */
public class SlotDAO {

        public List<Slot> getSlots() throws Exception {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Slot> slots = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [timetable].[dbo].[slots]";
            //open connection
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //add resultset items to result list
            while (rs.next()) {
                Slot slot = new Slot();
                slot.setId(rs.getInt("id"));
                slot.setName(rs.getString("name"));
                slot.setStartTime(rs.getTime("startTime"));
                slot.setEndTime(rs.getTime("endTime"));
                slots.add(slot);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.closeConnection(rs, ps, conn);
        }
        return slots;
    }
    

}
