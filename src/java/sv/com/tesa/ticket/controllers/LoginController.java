/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.com.tesa.ticket.beans.Login;
import sv.com.tesa.ticket.models.User;

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
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String correo;
            String password;
            correo = request.getParameter("usuario");
            password = request.getParameter("clave");
            User user = new User();
            Login usuario = user.validar(correo, password);
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
                    } else if (sesion.getAttribute("rol").equals("efe de desarrollo")) {
                        rd = request.getRequestDispatcher("Area/Desarrollo/Jefes/inicio.jsp");
                        rd.forward(request, response);
                    } else if (sesion.getAttribute("rol").equals("Programador")) {
                        rd = request.getRequestDispatcher("Area/Desarrollo/Empleado/inicio.jsp");
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println("entra1");
                    if (usuario.getRol() != null) {
                        sesion = request.getSession(true);
                        sesion.setAttribute("id", usuario.getId());
                        sesion.setAttribute("rol", usuario.getRol());
                        sesion.setAttribute("nombre", usuario.getNombre());
                        sesion.setAttribute("correo", usuario.getCorreo());
                        sesion.setAttribute("jefe", usuario.getJefe());
                        sesion.setAttribute("departamento", usuario.getDepartamento());
                        if (sesion.getAttribute("rol").equals("Administrador")) {
                            System.out.println("entra2");
                            rd = request.getRequestDispatcher("Admin/admin.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Jefe de área funcional")) {
                            System.out.println("entra3");
                            rd = request.getRequestDispatcher("Area/Funcional/Jefes/inicio.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Empleado de área funcional")) {
                            System.out.println("entra4");
                            rd = request.getRequestDispatcher("Area/Funcional/Empleado/inicio.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("efe de desarrollo")) {
                            System.out.println("entra5");
                            rd = request.getRequestDispatcher("Area/Desarrollo/Jefes/inicio.jsp");
                            rd.forward(request, response);
                        } else if (sesion.getAttribute("rol").equals("Programador")) {
                            System.out.println("entra6");
                            rd = request.getRequestDispatcher("Area/Desarrollo/Empleado/inicio.jsp");
                            rd.forward(request, response);

                        }
                    } else {
                        System.out.println("entra7");
                        sesion.setAttribute("Error", usuario.getError());
                        rd = request.getRequestDispatcher("index.jsp");
                        rd.forward(request, response);
                    }

                }
            } catch (IOException | ServletException e) {
                System.out.println("entra8");
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("entra9");
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
