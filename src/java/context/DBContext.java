/**
 * DBContext.java
 * All Rights Reserved.
 * Copyright(c) by QuyenNV
 */
package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

/**
 * DBContext.<br>
 *
 * <pre>
 * Class handling with data in database
 * In this class, it handle the process below.
 *
 * .getConnection
 * .closeConnection
 *
 * </pre>
 *
 * @author AM
 * @version 1.0
 */
public class DBContext {

    /**
     * get connection
     * <pre>
     *method get connection to databasee
     * In this class, it handle the process below.
     *
     * processing order:
     *      1.1 in case method return connection
     *          1.1.1 return a object connection to get data from databasee
     *      1.2 in case method has exception
     *          1.2.1 throw exception
     *
     * handing exception:
     *      show exception in console
     * </pre>
     *
     * @return 
     * @throws Exception
     */
    
    private String serverName;
    private String dbName;
    private String portNumber;
    private String username;
    private String password;
    public String images;
    public String calendar;
    public String timeIcon;
    
    InitialContext initial;
    Context context;

    public DBContext() throws Exception {
        try {
            initial = new InitialContext();
            Object obj = initial.lookup("java:comp/env");
            context = (Context) obj;
            serverName = context.lookup("serverName").toString();
            dbName = context.lookup("dbName").toString();
            portNumber = context.lookup("portNumber").toString();
            username = context.lookup("username").toString();
            password = context.lookup("password").toString();
            images = context.lookup("images").toString();
            calendar = context.lookup("calendar").toString();
            timeIcon = context.lookup("timeIcon").toString();
        } catch (Exception ex){
            throw ex;
        }
    }
    
    public String getImagePath(){
        return images;
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, username, password);
    }
    
    public void closeConnection(ResultSet rs, PreparedStatement ps, Connection con) throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
        }
        if (ps != null && !ps.isClosed()) {
            ps.close();
        }
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
    
    public void setParams(HttpServletRequest request){
        request.setAttribute("images", images);
        request.setAttribute("timeIcon", timeIcon);
        request.setAttribute("calendarIcon", this.calendar);
    }
}
