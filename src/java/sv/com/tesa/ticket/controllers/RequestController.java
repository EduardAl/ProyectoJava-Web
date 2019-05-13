/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.models.RequestModel;
import sv.com.tesa.ticket.beans.RequestBean;

/**
 *
 * @author eduar
 */
@WebServlet(name = "RequestController", urlPatterns = {"/request"})
public class RequestController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    static Logger log = Logger.getLogger(DepartmentController.class.getName());
    private RequestModel requestModel = new RequestModel();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Jefe de área funcional")) {
                    request.setAttribute("Error", "Error de usuario.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("Error", "Debe iniciar sesión.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            
            String operacion = request.getParameter("op");
            switch (operacion) {
                case "listar":
                    listar(request, response);
                    break;
                case "nuevo":
                    //nuevo(request, response);
                    break;
                case "insertar":
                    //insertar(request, response);
                    break;
                case "obtener":
                    //obtener(request, response);
                    break;
                case "modificar":
                    //modificar(request, response);
                    break;
                case "eliminar":
                    //eliminar(request, response);
                    break;
                default:
                    throw new AssertionError();
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
    
    private void listar(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
            request.getRequestDispatcher("/Area/Funcional/Jefes/listarPeticiones.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

}
