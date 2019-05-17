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
import sv.com.tesa.ticket.beans.DepartmentBean;
import sv.com.tesa.ticket.models.AdminBossModel;
import sv.com.tesa.ticket.models.AdminDeptModel;

/**
 *
 * @author eduar
 */
@WebServlet(name = "DepartmentController", urlPatterns = {"/departamentos"})
public class DepartmentController extends HttpServlet {

    static Logger log = Logger.getLogger(DepartmentController.class.getName());
    AdminDeptModel adminDeptModel = new AdminDeptModel();
    AdminBossModel adminBossModel = new AdminBossModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Administrador")) {
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
                    nuevo(request, response);
                    break;
                case "insertar":
                    insertar(request, response);
                    break;
                case "obtener":
                    obtener(request, response);
                    break;
                case "modificar":
                    modificar(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
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
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException ex) {
            java.util.logging.Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
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
            request.setAttribute("listarDepartamentos", adminDeptModel.listarDept());
            request.getRequestDispatcher("/Admin/Departamentos/ListaDepartamentos.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/Admin/Departamentos/NuevoDepartamento.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            java.util.logging.Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertar(HttpServletRequest request, HttpServletResponse response) {
        try {
            DepartmentBean departmentBean = new DepartmentBean();
            departmentBean.setId(request.getParameter("id"));
            departmentBean.setNombreDept(request.getParameter("nombre"));

            if (adminDeptModel.ingresarDept(departmentBean)) {
                request.setAttribute("listarDepartamentos", adminDeptModel.listarDept());
                request.getRequestDispatcher("/Admin/Departamentos/ListaDepartamentos.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            java.util.logging.Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtener(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            DepartmentBean departmentBean = adminDeptModel.obtenerDepatartamento(id);
            request.setAttribute("departamento", departmentBean);
            request.getRequestDispatcher("/Admin/Departamentos/EditarDepartamento.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            java.util.logging.Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void modificar(HttpServletRequest request, HttpServletResponse response) {
        try {
            DepartmentBean departmentBean = new DepartmentBean();

            departmentBean.setNombreDept(request.getParameter("nombre"));
            departmentBean.setId(request.getParameter("id"));
            if (adminDeptModel.modificarDept(departmentBean)) {
                request.setAttribute("listarDepartamentos", adminDeptModel.listarDept());
                request.getRequestDispatcher("/Admin/Departamentos/ListaDepartamentos.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            java.util.logging.Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) {
        if (adminDeptModel.eliminarDepartamento(request.getParameter("id"))) {
            try {
                request.setAttribute("listarDepartamentos", adminDeptModel.listarDept());
                request.getRequestDispatcher("/Admin/Departamentos/ListaDepartamentos.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                java.util.logging.Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

        }
    }

}
