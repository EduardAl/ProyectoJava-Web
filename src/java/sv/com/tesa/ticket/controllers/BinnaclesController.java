/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.com.tesa.ticket.beans.BinnaclesBean;
import sv.com.tesa.ticket.models.BinnaclesModel;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import sv.com.tesa.ticket.beans.SingleCaseBean;
import sv.com.tesa.ticket.models.CasesModel;

/**
 *
 * @author vaselinux
 */
@WebServlet(name = "BinnaclesController", urlPatterns = {"/bitacoras"})
public class BinnaclesController extends HttpServlet {

    Logger LOGGER = Logger.getLogger(BinnaclesController.class);

    private BinnaclesModel binnacleModel = new BinnaclesModel();
    //= new BinnaclesModel();

    private CasesModel casesModel = new CasesModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=Latin1");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("nombre") != null && sesion.getAttribute("nombre") != "null") {
                sesion.getAttribute("nombre");
                if (!sesion.getAttribute("rol").toString().equals("Jefe de área funcional")
                        && !sesion.getAttribute("rol").toString().equals("Programador")) {
                    request.setAttribute("Error", "Error de usuario.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("Error", "Debe iniciar sesión.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            String operacion = request.getParameter("op");
            switch (operacion) {
                case "listarBitacoras":
                    listarBitacoras(request, response);
                    break;
                case "listarCasos":
                    listarCasos(request, response);
                    break;
                case "agregar":
                    agregar(request, response);
                    break;
                 case "eliminar":
                    eliminar(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
        }

    }

    private void listarBitacoras(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            if (id == null || id.equals("")) {
                id = (request.getAttribute("id").toString());
            }
            SingleCaseBean singleCaseBean = new SingleCaseBean();
            singleCaseBean.setId(id);
            singleCaseBean = casesModel.listarCaso(singleCaseBean);
            List<BinnaclesBean> list = binnacleModel.getBinnacles(id);
            for (BinnaclesBean tmp : list) {
                System.out.println("UDBLOG: " + tmp.getCommentary());
            }
            request.setAttribute("singleCaseBean", singleCaseBean);
            request.setAttribute("listarBitacoras", list);
            request.getRequestDispatcher("/Area/Desarrollo/Empleado/Bitacoras.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    private void listarCasos(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("listaCasos", casesModel.listarCasosPorDesarrollador());
            request.getRequestDispatcher("/Area/Desarrollo/Empleado/listaCasos.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void agregar(HttpServletRequest request, HttpServletResponse response) {

        BinnaclesBean binnaclesBean = new BinnaclesBean();

        binnaclesBean.setCaseId(request.getParameter("case_id"));
        binnaclesBean.setCommentary(request.getParameter("comment"));
        binnaclesBean.setPercent(Double.parseDouble(request.getParameter("percent")));

        binnacleModel.insertBinnacle(binnaclesBean);
        request.setAttribute("id", binnaclesBean.getCaseId());
        this.listarBitacoras(request, response);

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) {
        BinnaclesBean binnaclesBean = new BinnaclesBean();
            
            binnaclesBean.setCaseId(request.getParameter("case_id"));
            int id = Integer.parseInt(request.getParameter("binnacle_id"));
            binnacleModel.removeBinnacle(id);
            request.setAttribute("id", binnaclesBean.getCaseId());
            this.listarBitacoras(request, response);
    }
   
    
}
