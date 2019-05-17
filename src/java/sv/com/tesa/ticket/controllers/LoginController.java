/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.models.LoginModel;

/**
 *
 * @author eduar
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Logger LOGGER = Logger.getLogger(LoginController.class);
            /* TODO output your page here. You may use following sample code. */
            String correo;
            String password;
            correo = request.getParameter("usuario");
            password = request.getParameter("password");
            LoginModel user = new LoginModel();
            LoginBean usuario = user.validar(correo, password);
            RequestDispatcher rd;
            HttpSession sesion = request.getSession(false);
            try {
                if (sesion.getAttribute("rol") != null) {
                    if (sesion.getAttribute("rol").equals("Administrador")) {
                        rd = request.getRequestDispatcher("Admin/admin.jsp");
                        rd.forward(request, response);
                    } else if (sesion.getAttribute("rol").equals("Jefe de área funcional")) {
                        rd = request.getRequestDispatcher("Area/Funcional/Jefes/inicio.jsp");
                        rd.forward(request, response);
                    } else if (sesion.getAttribute("rol").equals("Empleado de área funcional")) {
                        rd = request.getRequestDispatcher("Area/Funcional/Empleado/inicio.jsp");
                        rd.forward(request, response);
                    } else if (sesion.getAttribute("rol").equals("Jefe de desarrollo")) {
                        rd = request.getRequestDispatcher("Area/Desarrollo/Jefes/inicio.jsp");
                        rd.forward(request, response);
                    } else if (sesion.getAttribute("rol").equals("Programador")) {
                        rd = request.getRequestDispatcher("Area/Desarrollo/Empleado/inicio.jsp");
                        rd.forward(request, response);
                    }
                } else {
                    if (LoginBean.getRol() != null) {
                        sesion = request.getSession(true);
                        sesion.setAttribute("id", LoginBean.getId());
                        sesion.setAttribute("rol", LoginBean.getRol());
                        sesion.setAttribute("nombre", LoginBean.getNombre());
                        sesion.setAttribute("correo", LoginBean.getCorreo());
                        sesion.setAttribute("jefe", LoginBean.getJefe());
                        sesion.setAttribute("departamento", LoginBean.getDepartamento());
                        request.setAttribute("fracaso", usuario.getError());
                        if (sesion.getAttribute("rol").equals("Administrador")) {
                            rd = request.getRequestDispatcher("Admin/admin.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Jefe de área funcional")) {
                            rd = request.getRequestDispatcher("Area/Funcional/Jefes/inicio.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Empleado de área funcional")) {
                            rd = request.getRequestDispatcher("Area/Funcional/Empleado/inicio.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Jefe de desarrollo")) {
                            rd = request.getRequestDispatcher("Area/Desarrollo/Jefes/inicio.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Programador")) {
                            rd = request.getRequestDispatcher("Area/Desarrollo/Empleado/inicio.jsp");
                            rd.forward(request, response);

                        }
                    } else {
                        request.setAttribute("fracaso", usuario.getError());
                        LOGGER.error(usuario.getError());
                        rd = request.getRequestDispatcher("index.jsp");
                        rd.forward(request, response);
                    }

                }
            } catch (IOException | ServletException e) {
                LOGGER.error(e.getMessage());
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class).error(ex.getMessage());
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
