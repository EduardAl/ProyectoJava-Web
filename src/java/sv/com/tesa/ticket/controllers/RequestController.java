/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.models.RequestModel;
import sv.com.tesa.ticket.beans.RequestBean;
import sv.com.tesa.ticket.beans.SingleRequestBean;

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
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024 * 5;
    static Logger log = Logger.getLogger(RequestController.class.getName());
    private RequestModel requestModel = new RequestModel();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF8");
        try (PrintWriter out = response.getWriter()) {
            
HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Jefe de área funcional") && !sesion.getAttribute("rol").toString().equals("Jefe de desarrollo")) {
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
                    verificarEstadoRequest(request, response);
                    break;
                case "eliminar":
                    verificarEstadoRequest(request, response);
                    break;
                case "modificarBase":
                    modificarBase(request, response);
                    break;
                case "individualRequest":
                    getSingleRequest(request, response);
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

    private void nuevo(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listaTipoPeticiones", requestModel.listarTiposPeticion());
            request.getRequestDispatcher("/Area/Funcional/Jefes/nuevaPeticion.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

    private void insertar(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {

                RequestBean peticion = new RequestBean();
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                //Obtiene el directorio de context.xml
                ServletContext context = getServletContext();
                String storeLocation = context.getInitParameter("FileLocation");

                List items = upload.parseRequest(request);
                Iterator iterador = items.iterator();
                while (iterador.hasNext()) {
                    FileItem item = (FileItem) iterador.next();

                    //Esto lee todo los input de tipo file
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        File path = new File(storeLocation);
                        //Si no existe se crea el directorio
                        if (!path.exists()) {
                            path.mkdirs();
                        }
                        int randomNum = ThreadLocalRandom.current().nextInt(0, 9999);
                        String dirFile = randomNum + fileName;
                        String dirPc = storeLocation + File.separator + dirFile;
                        peticion.setFileDir(dirFile);
                        //Se crea el documento a guardar
                        File uploadedFile = new File(dirPc);
                        //se guarda
                        item.write(uploadedFile);
                    } //Aqui se procesan los demas tipos de input del form
                    else {
                        if (item.getFieldName().equals("title")) {
                            System.out.println(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
                            peticion.setTitle(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
                        }
                        if (item.getFieldName().equals("request-type")) {
                            peticion.setRequestType(Integer.parseInt(item.getString()));
                        }
                        if (item.getFieldName().equals("description")) {
                            peticion.setDescription(new String(item.getString().getBytes("ISO-8859-1"),"UTF-8"));
                        }
                    }
                }
                peticion.setCreatedBy(LoginBean.getId());
                peticion.setDepartment(LoginBean.getDepartamento());
                requestModel.ingresarPeticion(peticion);
                //Dejar aca, no se que hace
                response.reset();
                request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
                request.getRequestDispatcher("/Area/Funcional/Jefes/listarPeticiones.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void obtener(HttpServletRequest request, HttpServletResponse response) {
        try {
            //Obtengo el nombre del archivo
            String fileName = request.getParameter("file");

            //Esto hace que funcione, ni idea por que, lo lei en stack overflow
            response.reset();

            //Obtengo el nombre de la carpeta donde estan
            ServletContext ctx = getServletContext();
            String storeLocation = ctx.getInitParameter("FileLocation");

            //Obtengo el archivo de la carpeta
            File file = new File(storeLocation + File.separator + fileName);

            //Set el tipo de contenido de la respuesta
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", ("attachment;filename=" + fileName));
            response.setHeader("Content-Length", String.valueOf(file.length()));

            FileInputStream fileIn = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            byte[] outputByte = new byte[4096];
            //copy binary contect to output stream
            while (fileIn.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
            fileIn.close();
            out.flush();
            out.close();

        } catch (IOException ex) {
            log.error("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void verificarEstadoRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestBean peticion = new RequestBean();
            peticion.setId(Integer.parseInt(request.getParameter("id")));
            String estado = requestModel.regresarEstado(peticion);
            if (estado.equals("Solicitud rechazada")) {
                String operacion = request.getParameter("op");
                if (operacion.equals("eliminar")) {
                    eliminarArchivo(requestModel.regresarFileDir(peticion));
                    requestModel.eliminarPeticion(peticion);
                    request.setAttribute("MensajeExito", "La peticion fue Eliminada");
                    request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
                    request.getRequestDispatcher("/Area/Funcional/Jefes/listarPeticiones.jsp").forward(request, response);
                }
                if (operacion.equals("modificar")) {
                    peticion.setCreatedBy(LoginBean.getId());
                    request.setAttribute("peticion", requestModel.listarPeticionIndividual(peticion));
                    request.setAttribute("listaTipoPeticiones", requestModel.listarTiposPeticion());
                    request.getRequestDispatcher("/Area/Funcional/Jefes/modificarPeticion.jsp").forward(request, response);
                }
            } else {
                String op = request.getParameter("op");
                if (op.equals("modificar")) {
                    request.setAttribute("ErrorModificar", "Solo se puede modificar una peticion rechazada");
                    request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
                    request.getRequestDispatcher("/Area/Funcional/Jefes/listarPeticiones.jsp").forward(request, response);
                }
                if (op.equals("eliminar")) {
                    request.setAttribute("ErrorEliminar", "Solo se puede eliminar una peticion rechazada");
                    request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
                    request.getRequestDispatcher("/Area/Funcional/Jefes/listarPeticiones.jsp").forward(request, response);
                }
            }
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

    private void modificarBase(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                RequestBean peticion = new RequestBean();
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);

                //Obtiene el directorio de context.xml
                ServletContext context = getServletContext();
                String storeLocation = context.getInitParameter("FileLocation");

                List items = upload.parseRequest(request);
                Iterator iterador = items.iterator();
                while (iterador.hasNext()) {
                    FileItem item = (FileItem) iterador.next();

                    //Esto lee todo los input de tipo file
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        File path = new File(storeLocation);
                        //Si no existe se crea el directorio
                        if (!path.exists()) {
                            path.mkdirs();
                        }
                        int randomNum = ThreadLocalRandom.current().nextInt(0, 9999);
                        String dirFile = randomNum + fileName;
                        String dirPc = storeLocation + File.separator + dirFile;
                        peticion.setFileDir(dirFile);
                        //Se crea el documento a guardar
                        File uploadedFile = new File(dirPc);
                        //se guarda
                        item.write(uploadedFile);
                    } //Aqui se procesan los demas tipos de input del form
                    else {
                        if (item.getFieldName().equals("title")) {
                            peticion.setTitle(item.getString());
                        }
                        if (item.getFieldName().equals("request-type")) {
                            peticion.setRequestType(Integer.parseInt(item.getString()));
                        }
                        if (item.getFieldName().equals("description")) {
                            peticion.setDescription(item.getString());
                        }
                        if (item.getFieldName().equals("hidden-file")) {
                            eliminarArchivo(item.getString());
                        }
                        if (item.getFieldName().equals("id")) {
                            peticion.setId(Integer.parseInt(item.getString()));
                        }
                    }
                }
                requestModel.modificarPeticion(peticion);
                request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
                request.getRequestDispatcher("/Area/Funcional/Jefes/listarPeticiones.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean eliminarArchivo(String fileDir) {
        ServletContext ctx = getServletContext();
        String storeLocation = ctx.getInitParameter("FileLocation");
        File file = new File(storeLocation + File.separator + fileDir);
        return file.delete();
    }

    private void getSingleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer codigo = Integer.parseInt(request.getParameter("id"));
            SingleRequestBean requestBean = requestModel.obtenerPeticionIndividual(codigo);
            try (PrintWriter out = response.getWriter()) {
                response.setContentType("json");
                
                JSONObject member = new JSONObject();
                member.put("codigo", String.valueOf(requestBean.getId()));
                member.put("nombre", requestBean.getTitulo());
                member.put("tipo", requestBean.getTipoPeticion());
                member.put("departamento", requestBean.getDepartamento());
                member.put("descripcion", requestBean.getDescripcion());
                member.put("creado", requestBean.getCreadoPor());
                member.put("estado", requestBean.getEstado());
                member.put("fechac", requestBean.getFechaCreacion());
                String json = member.toString();
                out.write(json);
                System.out.println("JSON " + json);
            }
        } catch (IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

}
