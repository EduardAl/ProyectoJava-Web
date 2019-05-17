/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.com.tesa.ticket.beans.EmployeeBean;
import sv.com.tesa.ticket.beans.Usuarios;
import sv.com.tesa.ticket.models.AdminBossModel;
import sv.com.tesa.ticket.models.AdminDeptModel;
import sv.com.tesa.ticket.models.UsersModel;

/**
 *
 * @author eduar
 */
@WebServlet(name = "UserController", urlPatterns = {"/usuarios"})
public class UserController extends HttpServlet {

    static Logger log = Logger.getLogger(UserController.class.getName());
    UsersModel modelo = new UsersModel();
    AdminDeptModel adminDeptModel = new AdminDeptModel();
    AdminBossModel adminBossModel = new AdminBossModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=Latin1");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Administrador")) {
                    request.setAttribute("Error", "Error de usuario.");
                    try {
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } catch (ServletException ex) {
                        java.util.logging.Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                request.setAttribute("Error", "Debe iniciar sesión.");
                try {
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (ServletException ex) {
                    log.error(ex.getMessage());
                }
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
                case "password":
                    password(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
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
            request.setAttribute("listarUsuarios", modelo.listarUsuarios());
            request.getRequestDispatcher("/Admin/Usuarios/ListaUsuarios.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listarRoles", adminBossModel.listarRoles());
            request.setAttribute("listarDepartamentos", adminDeptModel.listarDept());
            request.setAttribute("listarEmpleados", adminBossModel.listarEmpleados());
            request.getRequestDispatcher("/Admin/Usuarios/NuevoUsuario.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void insertar(HttpServletRequest request, HttpServletResponse response) {
        try {
            EmployeeBean employeeBean = new EmployeeBean();
            employeeBean.setApellido(request.getParameter("apellido"));
            employeeBean.setDepartamento(request.getParameter("departamento"));
            employeeBean.setEmail(request.getParameter("correo"));
            if (request.getParameter("jefe") != null && !request.getParameter("jefe").equals("")) {
                employeeBean.setJefe(Integer.parseInt(request.getParameter("jefe")));
            } else {
                employeeBean.setJefe(0);
            }
            employeeBean.setNombre(request.getParameter("nombre"));
            employeeBean.setRol(Integer.parseInt(request.getParameter("rol")));

            if (adminBossModel.ingresarEmpleado(employeeBean)) {
                request.setAttribute("usuario", employeeBean);
                request.setAttribute("listarUsuarios", modelo.listarUsuarios());
                request.getRequestDispatcher("/Admin/Usuarios/ListaUsuarios.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void obtener(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Usuarios usuarios = modelo.obtenerUsuario(id);
            request.setAttribute("usuario", usuarios);
            request.setAttribute("listarRoles", adminBossModel.listarRoles());
            request.setAttribute("listarDepartamentos", adminDeptModel.listarDept());
            request.setAttribute("listarEmpleados", adminBossModel.listarEmpleados());
            request.getRequestDispatcher("/Admin/Usuarios/EditarUsuario.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void modificar(HttpServletRequest request, HttpServletResponse response) {
        try {
            EmployeeBean employeeBean = new EmployeeBean();
            employeeBean.setApellido(request.getParameter("apellido"));
            employeeBean.setDepartamento(request.getParameter("departamento"));
            employeeBean.setEmail(request.getParameter("correo"));
            if (request.getParameter("jefe") != null && !request.getParameter("jefe").equals("")) {
                employeeBean.setJefe(Integer.parseInt(request.getParameter("jefe")));
            } else {
                employeeBean.setJefe(0);
            }
            employeeBean.setNombre(request.getParameter("nombre"));
            employeeBean.setRol(Integer.parseInt(request.getParameter("rol")));
            employeeBean.setId(Integer.parseInt(request.getParameter("id")));
            employeeBean.setPassword(null);
            if (adminBossModel.modificarJefe(employeeBean, false)) {
                request.setAttribute("listarUsuarios", modelo.listarUsuarios());
                request.getRequestDispatcher("/Admin/Usuarios/ListaUsuarios.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) {
        if (modelo.eliminarEmpleado(Integer.parseInt(request.getParameter("id")))) {
            try {

                request.setAttribute("exito", "Usuario eliminado correctamente");
                request.setAttribute("listarUsuarios", modelo.listarUsuarios());
                request.getRequestDispatcher("/Admin/Usuarios/ListaUsuarios.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                log.error(ex.getMessage());
            }
        } else {
            try {
                request.setAttribute("fracaso", "No se puede eliminar");
                request.setAttribute("listarUsuarios", modelo.listarUsuarios());
                request.getRequestDispatcher("/Admin/Usuarios/ListaUsuarios.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private void password(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        try {
            String url1 = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            System.out.println(queryString);
            if (modelo.modificarOldPassword(Integer.parseInt(session.getAttribute("id").toString()), request.getParameter("passwd"), request.getParameter("oldPassword"))) {
                try {
                    request.setAttribute("Success", "Contraseña cambiada correctamente.");
                    request.getRequestDispatcher("/cerrarSesion.jsp").forward(request, response);
                } catch (ServletException | IOException ex) {
                    log.error(ex.getMessage());
                    request.setAttribute("Error", "Ocurrió un problema.\nNo se cambió la contraseña");
                    try {
                        request.getRequestDispatcher("/cerrarSesion.jsp").forward(request, response);
                    } catch (ServletException | IOException ex1) {
                        log.error(ex.getMessage());
                    }

                }
            } else {
                request.setAttribute("Error", "Ocurrió un problema.\nNo se cambió la contraseña");
                try {
                    request.getRequestDispatcher("/cerrarSesion.jsp").forward(request, response);
                } catch (ServletException | IOException ex1) {
                    log.error(ex1.getMessage());
                }
            }

        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
    }
}
