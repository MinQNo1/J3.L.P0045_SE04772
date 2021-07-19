/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import com.sun.xml.wss.util.DateUtils;
import context.DBContext;
import dal.*;
import dto.TimeTableDto;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CourseClass;
import model.Room;
import model.Slot;
import model.Teacher;
import model.TimeTable;

/**
 *
 * @author QuyenNV
 */
public class TimeTableController extends HttpServlet {

    private TimeTableDAO timeTableDAO;
    private SlotDAO slotDAO;
    private TeacherDAO teacherDAO;
    private CourseClassDAO courseClassDAO;
    private RoomDAO roomDAO;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insert(request, response);
                    break;
                case "/delete":
                    delete(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    update(request, response);
                    break;
            }
        } catch (Exception e) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(TimeTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            DBContext context = new DBContext();
            context.setParams(request);
            slotDAO = new SlotDAO();
            courseClassDAO = new CourseClassDAO();
            teacherDAO = new TeacherDAO();
            roomDAO = new RoomDAO();
            List<Slot> slots = slotDAO.getSlots();
            request.setAttribute("slots", slots);
            List<CourseClass> courseClasses = courseClassDAO.getCourseClass();
            request.setAttribute("courseClasses", courseClasses);
            List<Teacher> teachers = teacherDAO.getTeachers();
            request.setAttribute("teachers", teachers);
            List<Room> rooms = roomDAO.getRooms();
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("Form.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Fail initiate form.");
            request.getRequestDispatcher("Form.jsp").forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            DBContext context = new DBContext();
            context.setParams(request);
            timeTableDAO = new TimeTableDAO();
            slotDAO = new SlotDAO();
            courseClassDAO = new CourseClassDAO();
            teacherDAO = new TeacherDAO();
            roomDAO = new RoomDAO();
            int id = Integer.parseInt(request.getParameter("id"));
            if (timeTableDAO.getOne(id) != null) {
                TimeTable timeTable = timeTableDAO.getOne(id);
                request.setAttribute("timeTable", timeTable);
                List<Slot> slots = slotDAO.getSlots();
                request.setAttribute("slots", slots);
                List<CourseClass> courseClasses = courseClassDAO.getCourseClass();
                request.setAttribute("courseClasses", courseClasses);
                List<Teacher> teachers = teacherDAO.getTeachers();
                request.setAttribute("teachers", teachers);
                List<Room> rooms = roomDAO.getRooms();
                request.setAttribute("rooms", rooms);
                request.getRequestDispatcher("Form.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Record not found, try again late");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Record not found, try again late");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            DBContext context = new DBContext();
            context.setParams(request);
            timeTableDAO = new TimeTableDAO();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(request.getParameter("date"));
            int slotId = Integer.parseInt(request.getParameter("slot"));
            int classCourseId = Integer.parseInt(request.getParameter("courseClass"));
            int teacherId = Integer.parseInt(request.getParameter("teacher"));
            int roomId = Integer.parseInt(request.getParameter("room"));
            TimeTable timeTable = new TimeTable(date, slotId, classCourseId, teacherId, roomId);
            if(timeTableDAO.existedTimetable(timeTable)){
                request.setAttribute("existedError", "Only one class course or teacher on one class per day");
                request.setAttribute("timeTable", timeTable);
                request.getRequestDispatcher("Form.jsp").forward(request, response);
            } else {
                timeTableDAO.insertTimeTable(timeTable);
                response.sendRedirect("home");
            }
            
        } catch (Exception e) {
            request.setAttribute("error", "Fail to insert time table, try again late");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            DBContext context = new DBContext();
            context.setParams(request);
            timeTableDAO = new TimeTableDAO();
            Map<String, String[]> params = request.getParameterMap();
            int id = Integer.parseInt(request.getParameter("id"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(request.getParameter("date"));
            int slotId = Integer.parseInt(request.getParameter("slot"));
            int roomId = Integer.parseInt(request.getParameter("room"));
            int teacherId = Integer.parseInt(request.getParameter("teacher"));
            int classCourseId = Integer.parseInt(request.getParameter("courseClass"));
            if (timeTableDAO.getOne(id) != null) {
                TimeTable timeTable = new TimeTable(id, date, slotId, classCourseId, teacherId, roomId);
                if (timeTableDAO.existedTimetable(timeTable)) {
                    request.setAttribute("existed", "");
                    request.setAttribute("timeTable", timeTable);
                    response.sendRedirect("new");
                } else {
                    timeTableDAO.updateTimeTable(timeTable);
                }
                response.sendRedirect("home");
            } else {
                request.setAttribute("error", "Record not found, try again late");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Fail to update time table, try again late");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            DBContext context = new DBContext();
            context.setParams(request);
            int id = Integer.parseInt(request.getParameter("id"));
            timeTableDAO = new TimeTableDAO();
            TimeTable tt = timeTableDAO.getOne(id);
            if (tt != null) {
                timeTableDAO.deleteTimeTable(id);
                response.sendRedirect("home");
            } else {
                request.setAttribute("error", "Record not found, try again late");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cannot delete record, try again late");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

}
