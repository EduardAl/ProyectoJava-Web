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
import sv.com.tesa.ticket.beans.CaseBean;
import sv.com.tesa.ticket.beans.LoginBean;
import sv.com.tesa.ticket.beans.RequestBean;
import sv.com.tesa.ticket.beans.SingleCaseBean;
import sv.com.tesa.ticket.beans.SingleRequestBean;
import static sv.com.tesa.ticket.controllers.RequestController.log;
import sv.com.tesa.ticket.models.CasesModel;
import sv.com.tesa.ticket.models.RequestModel;
import sv.com.tesa.ticket.models.UsersModel;

/**
 *
 * @author eduar
 */
@WebServlet(name = "CaseController", urlPatterns = {"/case"})
public class CaseController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private RequestModel requestModel = new RequestModel();
    private CasesModel caseModel = new CasesModel();
    static Logger log = Logger.getLogger(CaseController.class.getName());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
                    try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Jefe de desarrollo")) {
                    log.error("Error de tipo de usuario.");
                    request.setAttribute("Error", "Error de usuario.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("Error", "Debe iniciar sesión.");
                log.error("Debe iniciar sesión.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            String operacion = request.getParameter("op");
            switch (operacion) {
                case "listarRequests":
                    listarRequest(request, response);
                    break;
                case "nuevo":
                    nuevoCaso(request, response);
                    break;
                case "listarCasos":
                    listarCasos(request, response);
                    break;
                case "modificar":
                    //verificarEstadoRequest(request, response);
                    break;
                case "eliminar":
                    //verificarEstadoRequest(request, response);
                    break;
                case "denegar":
                    denegarPeticion(request, response);
                    break;
                case "obtener":
                    obtenerDocPeticion(request, response);
                    break;
                case "tester":
                    asignarTester(request, response);
                    break;
                case "individualCase":
                    detalles(request, response);
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

    private void listarRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listarPeticiones", requestModel.listarPeticiones());
            request.setAttribute("listarEmpleados", caseModel.listarEmpleadosACargo());
            request.getRequestDispatcher("/Area/Desarrollo/Jefes/listarPeticiones.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }
    
    private void denegarPeticion(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            RequestBean peticion = new RequestBean();
            peticion.setId(Integer.parseInt(request.getParameter("id")));
            peticion.setCommentary(request.getParameter("comentary"));
            requestModel.denegarPeticion(peticion);
            request.setAttribute("ExitoDenegar", "La peticion fue denegada");
            listarRequest(request,response);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
    }
    
    private void nuevoCaso(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                
                CaseBean caso = new CaseBean(); 
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                
                //Obtiene el directorio de context.xml
                ServletContext context = getServletContext();
                String storeLocation = context.getInitParameter("FileLocation");
                
                List items = upload.parseRequest(request);
                Iterator iterador = items.iterator();
                while(iterador.hasNext())
                {
                    FileItem item = (FileItem)iterador.next();
                    
                    //Esto lee todo los input de tipo file
                    if (!item.isFormField()) 
                    {
                        String fileName = item.getName();
                        File path = new File(storeLocation);
                        //Si no existe se crea el directorio
                        if (!path.exists()) {
                            path.mkdirs();
                        }
                        int randomNum = ThreadLocalRandom.current().nextInt(0, 9999);
                        String dirFile = randomNum + fileName;
                        String dirPc = storeLocation + File.separator + dirFile;
                        caso.setFileDir(dirFile);
                        //Se crea el documento a guardar
                        File uploadedFile  = new File(dirPc);
                        //se guarda
                        item.write(uploadedFile);
                    }
                    //Aqui se procesan los demas tipos de input del form
                    else
                    {
                        if (item.getFieldName().equals("idp")) 
                        {
                            caso.setIdSolicitud(Integer.parseInt(item.getString()));
                        }
                        if (item.getFieldName().equals("descript")) 
                        {
                            caso.setDescripcion(item.getString());
                        }
                        if (item.getFieldName().equals("empleados")) 
                        {
                            caso.setEmpleadoAsignado(Integer.parseInt(item.getString()));
                        }
                        if (item.getFieldName().equals("fecha")) 
                        {
                            caso.setFechaLimite(item.getString());
                        }
                    }
                }
                int randomId = ThreadLocalRandom.current().nextInt(0, 1000);
                caso.setId(String.valueOf(randomId));
                caso.setDepartamento(LoginBean.getDepartamento());
                System.out.println(caseModel.ingresarCaso(caso));
                //Dejar aca, no se que hace
                response.reset();
                listarRequest(request,response);
            }
        } catch (Exception ex) {
            log.error("Error: " + ex.getMessage());
        }
    }
    
    private void obtenerDocPeticion(HttpServletRequest request, HttpServletResponse response) {
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
            while(fileIn.read(outputByte, 0, 4096) != -1)
            {
                    out.write(outputByte, 0, 4096);
            }
            fileIn.close();
            out.flush();
            out.close();
            
            
        } catch (IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }
    
    private void listarCasos(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            UsersModel usersModel = new UsersModel();
            HttpSession sesion = request.getSession(false);
            request.setAttribute("listarEmpleados", usersModel.listarUsuariosDepartamento((String) sesion.getAttribute("departamento")));
            request.setAttribute("listaCasos", caseModel.listarCasos());
            request.getRequestDispatcher("/Area/Desarrollo/Jefes/listaCasos.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error: " + ex.getMessage());
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
            while(fileIn.read(outputByte, 0, 4096) != -1)
            {
                    out.write(outputByte, 0, 4096);
            }
            fileIn.close();
            out.flush();
            out.close();
            
            
        } catch (IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }

    private void asignarTester(HttpServletRequest request, HttpServletResponse response) {
        String idCaso = request.getParameter("idc");
        Integer idTester = Integer.parseInt(request.getParameter("empleados"));
        if(caseModel.addTester(idCaso, idTester))
        {
            request.setAttribute("exito", "Tester cambiado con éxito");
            listarCasos(request, response);
        }
        else
        {
            request.setAttribute("fracaso", "Ocurrió un error al cambiar tester.");
            listarCasos(request, response);
        }
    }

    private void detalles(HttpServletRequest request, HttpServletResponse response) {
        try {
            SingleCaseBean caso = new SingleCaseBean();
            caso.setId(request.getParameter("id"));
            System.out.println("Entra");
            SingleCaseBean singleCaseBean = caseModel.listarCaso(caso);
            try (PrintWriter out = response.getWriter()) {
                response.setContentType("json");
                
                JSONObject member = new JSONObject();
                member.put("codigo", String.valueOf(singleCaseBean.getId()));
                member.put("nombre", singleCaseBean.getTitulo());
                member.put("asignado", singleCaseBean.getAsignadoA());
                member.put("estado", singleCaseBean.getEstado());
                member.put("descripcion", singleCaseBean.getDescripcion());                
                member.put("porcentaje", singleCaseBean.getAvance().toString());
                member.put("tester", singleCaseBean.getTester());
                member.put("fechac", singleCaseBean.getFechaCreacion());
                member.put("fechaa", singleCaseBean.getUltimoCambio());
                member.put("creado", singleCaseBean.getCreadoPor());
                member.put("fechalim", singleCaseBean.getLimite());
                member.put("produc", singleCaseBean.getProduccion());
                String json = member.toString();
                out.write(json);
            }
        } catch (IOException ex) {
            log.error("Error: " + ex.getMessage());
        }
    }
}
