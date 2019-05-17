/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.com.tesa.ticket.beans.EventBean;
import sv.com.tesa.ticket.models.EventModel;

/**
 *
 * @author Rodrigo
 */
@WebServlet(name = "EventController", urlPatterns = {"/event"})
public class EventController extends HttpServlet {

    static Logger log = Logger.getLogger(EventController.class.getName());
    EventModel eventModel = new EventModel();

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
            throws ServletException, IOException {
        response.setContentType("text/html; charset=Latin1");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") == null && sesion.getAttribute("nombre") == "null") {
                request.setAttribute("Error", "Debe iniciar sesión.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            String userRol = "";
            String userId = sesion.getAttribute("id").toString();
            if (sesion.getAttribute("rol").equals("Jefe de área funcional")) {
                userRol = "2";
            } else if (sesion.getAttribute("rol").equals("Empleado de área funcional")) {
                userRol = "3";
            } else if (sesion.getAttribute("rol").equals("efe de desarrollo")) {
                userRol = "4";
            } else if (sesion.getAttribute("rol").equals("Programador")) {
                userRol = "5";
            }
            
            try {
            request.setAttribute("listarEventos", eventModel.listarEvents(userRol, userId));
            request.getRequestDispatcher("/Area/Funcional/Jefes/eventos.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
        }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
