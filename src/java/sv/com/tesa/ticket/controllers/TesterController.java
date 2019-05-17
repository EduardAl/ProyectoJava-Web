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
import org.apache.log4j.Priority;
import sv.com.tesa.ticket.beans.CaseBean;
import sv.com.tesa.ticket.models.TesterModel;
/**
 *
 * @author eduar
 */
@WebServlet(name = "TesterController", urlPatterns = {"/tester"})
public class TesterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    TesterModel testerModel = new TesterModel();
    static Logger log = Logger.getLogger(TesterController.class.getName());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Empleado de área funcional")) {
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
                case "aceptar":
                    verificarEstadoCaso(request, response);
                    break;
                case "denegar":
                    verificarEstadoCaso(request, response);
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
            request.setAttribute("listarCasos", testerModel.listarCasosTester());
            request.getRequestDispatcher("/Area/Funcional/Empleado/listaCasos.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }
    
    private void verificarEstadoCaso(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaseBean caso = new CaseBean();
            caso.setId(request.getParameter("id"));
            String estado = testerModel.regresarEstado(caso);
            if (estado.equals("Esperando aprobación del área solicitante")) {
                String operacion = request.getParameter("op");
                if (operacion.equals("aceptar")) {
                    testerModel.aceptarCaso(caso);
                    request.setAttribute("MensajeExito", "El caso ha sido aceptado, ya no se te"
                            + " mostrara en tus casos pendientes");
                    request.setAttribute("listarCasos", testerModel.listarCasosTester());
                    request.getRequestDispatcher("/Area/Funcional/Empleado/listaCasos.jsp").forward(request, response);
                }
                if (operacion.equals("denegar")) {
                    caso.setId(request.getParameter("id"));
                    caso.setComentario(request.getParameter("comentario"));
                    testerModel.denegarCaso(caso);
                    request.setAttribute("MensajeDenegado", "El caso ha sido denegado, ya no se te"
                            + " mostrara en tus casos pendientes");
                    request.setAttribute("listarCasos", testerModel.listarCasosTester());
                    request.getRequestDispatcher("/Area/Funcional/Empleado/listaCasos.jsp").forward(request, response);
                }
            } else {
                String op = request.getParameter("op");
                if (op.equals("aceptar")) {
                    request.setAttribute("ErrorModificar", "Solo se puede modificar una peticion rechazada");
                    request.setAttribute("listarCasos", testerModel.listarCasosTester());
                    request.getRequestDispatcher("/Area/Funcional/Empleado/listaCasos.jsp").forward(request, response);
                }
                if (op.equals("denegar")) {
                    request.setAttribute("ErrorEliminar", "Solo se puede eliminar una peticion rechazada");
                    request.setAttribute("listarCasos", testerModel.listarCasosTester());
                    request.getRequestDispatcher("/Area/Funcional/Empleado/listaCasos.jsp").forward(request, response);
                }
            }
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

}
