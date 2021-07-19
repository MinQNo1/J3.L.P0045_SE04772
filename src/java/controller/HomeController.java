/**
 * GalleryController.java
 * All Rights Reserved.
 * Copyright(c) by QuyenNV
 */
package controller;

import context.DBContext;
import dal.CourseClassDAO;
import dal.SlotDAO;
import dal.TeacherDAO;
import dal.TimeTableDAO;
import dto.TimeTableDto;
import java.io.IOException;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CourseClass;
import model.Slot;
import model.TimeTable;

/**
 * HomeController<br>
 *
 * <pre>
 * Class handling with data in database
 * In this class, it handle the process below.
 *
 * get properties of galleries
 * get properties of contacts
 *
 * </pre>
 *
 *
 * @author AM
 * @version 1.0
 *
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    /**
     * put all the properties of contact and galleries to servlet.<br>
     *
     * <pre>
     *
     * method receives id from jsp then get data from databse and put it to servlet
     * in case of method can't receive id ,handling exception.
     *
     * processing order:
     *      1.1 in case method has id
     *          1.1.1 create object with data get from database then send to jsp
     *      1.2 in case method hasn't id
     *          1.2.1 throw exception
     *
     * handing exception:
     *      redirect to error jsp
     * </pre>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try {
            DBContext context = new DBContext();
            context.setParams(request);
            TimeTableDAO dao = new TimeTableDAO();
            String page = request.getParameter("page");
            String startDateRaw = request.getParameter("startDate");
            String endDateRaw = request.getParameter("endDate");
            Date startDate = null;
            Date endDate = null;
            int indexPage = 1;
            
            //Check if request page is number
            if (page != null) {
                try {
                    indexPage = Integer.parseInt(page);
                } catch (NumberFormatException e) {
                    indexPage = -1;
                }
            }
            if(startDateRaw != null){
                try {
                    startDate = Date.valueOf(startDateRaw);
                    request.setAttribute("startDate", startDate);
                } catch (DateTimeParseException e) {
                    request.setAttribute("startDateError", "Start date format is not correct");
                }
            }
            if(endDateRaw != null){
                try {
                    endDate = Date.valueOf(endDateRaw);
                    request.setAttribute("endDate", endDate);
                } catch (DateTimeParseException e) {
                    request.setAttribute("endDateError", "End date format is not correct");
                }
            }
            int pageSize = 10;
            //check if requested page is valid
            if (indexPage != -1) {
                int rowCount = 0;
                if(startDate != null || endDate != null){
                    rowCount = dao.countSearch(startDate, endDate);
                }else{
                    rowCount = dao.countAll();
                }
                int maxPage = (int) Math.ceil((double)rowCount / pageSize);

                if (indexPage <= maxPage) {
                    List<TimeTableDto> list = dao.pagging(indexPage, pageSize, startDate, endDate);
                    request.setAttribute("list", list);
                    request.setAttribute("maxPage", maxPage);
                    request.setAttribute("pageIndex", indexPage);
                } else {
                    request.setAttribute("mess", "Page index is out of range");
                }
            } else {
                request.setAttribute("mess", "Page index must not include character");
            }

            request.setAttribute("imagePath", context.getImagePath());
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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

}
