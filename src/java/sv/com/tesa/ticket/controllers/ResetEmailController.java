/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.tesa.ticket.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.com.tesa.ticket.beans.Usuarios;
import sv.com.tesa.ticket.models.UsersModel;

/**
 *
 * @author eduar
 */
@WebServlet(name = "ResetEmailController", urlPatterns = {"/ResetPassword"})
public class ResetEmailController extends HttpServlet {

    Logger LOGGER = Logger.getLogger(ResetEmailController.class);
    UsersModel modelo = new UsersModel();

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
            String operacion = request.getParameter("op");
            switch (operacion) {
                case "email":
                    emailRecover(request, response);
                    break;
                case "change":
                    password(request, response);
                    break;
                default:
                    throw new AssertionError();
            }

        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void emailRecover(HttpServletRequest request, HttpServletResponse response) {
        try {

            StringBuffer url = request.getRequestURL();
            String uri = request.getRequestURI();
            String ctx = request.getContextPath();
            String base = url.substring(0, url.length() - uri.length() + ctx.length()) + "/";
            System.out.println(base);
            String email = request.getParameter("email");
            Usuarios usuarios = modelo.obtenerUsuarioConEmail(email);
            if (usuarios.getId() != null && usuarios.getId() != 0) {
                String destinatario = email; //A quien le quieres escribir.
                String asunto = "Recuperación de cuenta.";
                String cuerpo = "<!DOCTYPE html>\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                        + " \n"
                        + "<head>\n"
                        + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
                        + "  <title>Email de recuperación</title>\n"
                        + "  <style type=\"text/css\">\n"
                        + "  body {margin: 0; padding: 0; min-width: 100%!important;}\n"
                        + "  img {height: auto;}\n"
                        + "  .content {width: 100%; max-width: 600px;}\n"
                        + "  .header {padding: 40px 30px 20px 30px;}\n"
                        + "  .innerpadding {padding: 30px 30px 30px 30px;}\n"
                        + "  .borderbottom {border-bottom: 1px solid #f2eeed;}\n"
                        + "  .subhead {font-size: 15px; color: #ffffff; font-family: sans-serif; letter-spacing: 10px;}\n"
                        + "  .h1, .h2, .bodycopy {color: #153643; font-family: sans-serif;}\n"
                        + "  .h1 {font-size: 33px; line-height: 38px; font-weight: bold;}\n"
                        + "  .h2 {padding: 0 0 15px 0; font-size: 24px; line-height: 28px; font-weight: bold;}\n"
                        + "  .bodycopy {font-size: 16px; line-height: 22px;}\n"
                        + "  .button {text-align: center; font-size: 18px; font-family: sans-serif; font-weight: bold; padding: 0 30px 0 30px;}\n"
                        + "  .button a {color: #ffffff; text-decoration: none;}\n"
                        + "  .footer {padding: 20px 30px 15px 30px;}\n"
                        + "  .footercopy {font-family: sans-serif; font-size: 14px; color: #ffffff;}\n"
                        + "  .footercopy a {color: #ffffff; text-decoration: underline;}\n"
                        + "\n"
                        + "  @media only screen and (max-width: 550px), screen and (max-device-width: 550px) {\n"
                        + "  body[yahoo] .hide {display: none!important;}\n"
                        + "  body[yahoo] .buttonwrapper {background-color: transparent!important;}\n"
                        + "  body[yahoo] .button {padding: 0px!important;}\n"
                        + "  body[yahoo] .button a {background-color: #e05443; padding: 15px 15px 13px!important;}\n"
                        + "  body[yahoo] .unsubscribe {display: block; margin-top: 20px; padding: 10px 50px; background: #2f3942; border-radius: 5px; text-decoration: none!important; font-weight: bold;}\n"
                        + "  }\n"
                        + "\n"
                        + "  /*@media only screen and (min-device-width: 601px) {\n"
                        + "    .content {width: 600px !important;}\n"
                        + "    .col425 {width: 425px!important;}\n"
                        + "    .col380 {width: 380px!important;}\n"
                        + "    }*/\n"
                        + "\n"
                        + "  </style>\n"
                        + "</head>\n"
                        + "\n"
                        + "<body yahoo bgcolor=\"#f6f8f1\">\n"
                        + "<table width=\"100%\" bgcolor=\"#f6f8f1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n"
                        + "<tr>\n"
                        + "  <td>\n"
                        + "    <!--[if (gte mso 9)|(IE)]>\n"
                        + "      <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                        + "        <tr>\n"
                        + "          <td>\n"
                        + "    <![endif]-->     \n"
                        + "    <table bgcolor=\"#ffffff\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                        + "      <tr>\n"
                        + "        <td bgcolor=\"#000000\" class=\"header\">\n"
                        + "          <table width=\"70\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">  \n"
                        + "            <tr>\n"
                        + "              <td height=\"70\" style=\"padding: 0 20px 20px 0;\">\n"
                        + "                <img class=\"fix\" src=\"https://lh3.googleusercontent.com/LYPE0PuIreXHPGXxZmWL23AC2M4Uh6cb9jPyWAdZbyNqkNSOLe9rdnQxeX0hLxeCMrY6lTZQMQi-FaO2Gl8fIMlRGK9mMlqm-EsLSlRA8SGcxbI8l7Ozvx-Ux24iEtSYkdH7-s0uOt0Ipm3mKOlSpMKT4q3jou2QpeueJRsTPzt7M4LsHS0on9OjomvvBWZfjQ-qNUGHUwpGdDe8bQvRTQgAzm8PUZHxBxrp0H8F-xpgDMUryBixhI8gmgaAxbMR_Q6uiMU2vmKGkGl_9xzKNJiiVWlTAMHdY1ZWK5mJqFtgTE7bZvGSVigisuNEuUkHM7LQBl4wwyZ9eEnnlE9xg4PHRG4jfwy8X1u4dsYf9JvocvyzGQpp73tC0bMZ7KURwRgnFCuwLYyGJVnl-KjPRFmUhZxht0nOXXHfZXAPVTrb0xtWfASHDbHwvuRBihPLk6_LI-AuNxklf9Z-RNWk1xiM2iuHMP_jbv4vYvAihNNHOI9jfp5f3zDd8q3xGkXR2jlM6-W95wF9pYEsJwL87xywQS7FHFEnV1_TFvYMpbbqeP8R4BXRkhHCJxvJiJyfWNFax0r9H5QipQo_fCdRtGyKQLvYCa1U-Kz9fw4hnzaf6LDb-Xn3xjqB79UnTJ6hus1DkYtwHQ88Zqsjb7d2cikCMyYXKVXf=s500-no\" width=\"70\" height=\"70\" border=\"0\" alt=\"\" />\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "          </table>\n"
                        + "          <!--[if (gte mso 9)|(IE)]>\n"
                        + "            <table width=\"425\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                        + "              <tr>\n"
                        + "                <td>\n"
                        + "          <![endif]-->\n"
                        + "          <table class=\"col425\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 425px;\">  \n"
                        + "            <tr>\n"
                        + "              <td height=\"70\">\n"
                        + "                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                        + "                  <tr>\n"
                        + "                    <td class=\"subhead\" style=\"padding: 0 0 0 3px; color: silver\";>\n"
                        + "                      TESA\n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                  <tr>\n"
                        + "                    <td class=\"h1\" style=\"padding: 5px 0 0 0; color: cornflowerblue;\">\n"
                        + "                        Recuperación de cuenta.\n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </table>\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "          </table>\n"
                        + "          <!--[if (gte mso 9)|(IE)]>\n"
                        + "                </td>\n"
                        + "              </tr>\n"
                        + "          </table>\n"
                        + "          <![endif]-->\n"
                        + "        </td>\n"
                        + "      </tr>\n"
                        + "      <tr>\n"
                        + "        <td class=\"innerpadding borderbottom\">\n"
                        + "          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                        + "            <tr>\n"
                        + "              <td class=\"h2\" style=\"color: black\">\n"
                        + "                Telecomunicaciones salvadoreñas.\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "            <tr>\n"
                        + "              <td class=\"bodycopy\">\n"
                        + "                Recientemente se ha solicitado un enlace de recuperación de contraseña para tu cuenta.\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "          </table>\n"
                        + "        </td>\n"
                        + "      </tr>\n"
                        + "      <tr>\n"
                        + "        <td class=\"innerpadding borderbottom\">\n"
                        + "          \n"
                        + "          <!--[if (gte mso 9)|(IE)]>\n"
                        + "            <table width=\"380\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
                        + "              <tr>\n"
                        + "                <td>\n"
                        + "          <![endif]-->\n"
                        + "          <table class=\"col380\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 380px;\">  \n"
                        + "            <tr>\n"
                        + "              <td>\n"
                        + "                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                        + "                  <tr>\n"
                        + "                    <td class=\"bodycopy\">\n"
                        + "                        Si fuiste tú, sigue este enlace para recuperar tu cuenta:\n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                  <tr>\n"
                        + "                    <td style=\"padding: 20px 0px 0px 0px;\" align=\"center\">\n"
                        + "                      <table class=\"button\" bgcolor=\"cornflowerblue\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                        + "                        <tr>\n"
                        + "                          <td class=\"button\" height=\"45\">\n"
                        + "                            <a href=\"" + base + "reset.jsp?id=" + usuarios.getId() + "\">Recuperar cuenta</a>\n"
                        + "                          </td>\n"
                        + "                        </tr>\n"
                        + "                      </table>\n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </table>\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "          </table>\n"
                        + "          <!--[if (gte mso 9)|(IE)]>\n"
                        + "                </td>\n"
                        + "              </tr>\n"
                        + "          </table>\n"
                        + "          <![endif]-->\n"
                        + "        </td>\n"
                        + "      </tr>\n"
                        + "      <tr>\n"
                        + "        <td class=\"innerpadding bodycopy\">\n"
                        + "            Si no fuiste tú, ignora este correo electrónico.<br> Tu cuenta no sufrirá ningún cambio.\n"
                        + "        </td>\n"
                        + "      </tr>\n"
                        + "      <tr>\n"
                        + "          <td class=\"innerpadding bodycopy\">\n"
                        + "            Este correo ha sido generado automáticamente, por favor no responder.\n"
                        + "            </td>\n"
                        + "      </tr>\n"
                        + "      <tr>\n"
                        + "        <td class=\"footer\" bgcolor=\"#44525f\">\n"
                        + "          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                        + "            <tr>\n"
                        + "              <td align=\"center\" class=\"footercopy\">\n"
                        + "                &reg; Equipo de sopote, Telecomunicaciones Salvadoreñas 2019.<br/>\n"
                        + "                <a href=\"#\" class=\"unsubscribe\"><font color=\"#ffffff\">Visitar sitio web </font></a> \n"
                        + "                <span class=\"hide\"></span>\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "            <tr>\n"
                        + "              <td align=\"center\" style=\"padding: 20px 0 0 0;\">\n"
                        + "                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                        + "                  <tr>\n"
                        + "                    <td width=\"37\" style=\"text-align: center; padding: 0 10px 0 10px;\">\n"
                        + "                      <a href=\"https://github.com/EduardAl/ProyectoJava-Web\">\n"
                        + "                        <img style=\"bgcolor: purple\" src=\"https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png\" width=\"37\" height=\"37\" alt=\"GitHub\" border=\"0\" />\n"
                        + "                      </a>\n"
                        + "                    </td>\n"
                        + "                  </tr>\n"
                        + "                </table>\n"
                        + "              </td>\n"
                        + "            </tr>\n"
                        + "          </table>\n"
                        + "        </td>\n"
                        + "      </tr>\n"
                        + "    </table>\n"
                        + "    <!--[if (gte mso 9)|(IE)]>\n"
                        + "          </td>\n"
                        + "        </tr>\n"
                        + "    </table>\n"
                        + "    <![endif]-->\n"
                        + "    </td>\n"
                        + "  </tr>\n"
                        + "</table>\n"
                        + "\n"
                        + "<!--analytics-->\n"
                        + "<script src=\"http://code.jquery.com/jquery-1.10.1.min.js\"></script>\n"
                        + "<script src=\"http://tutsplus.github.io/github-analytics/ga-tracking.min.js\"></script>\n"
                        + "</body>\n"
                        + "</html>";
                request.setAttribute("usuario", usuarios);
                sendMail(destinatario, asunto, cuerpo);
                request.setAttribute("Mensaje", "Correo enviado exitosamente.");
                request.getRequestDispatcher("/forgot.jsp").forward(request, response);
            } else {
                request.setAttribute("Error", "El usuario ingresado no existe.");
                request.getRequestDispatcher("/forgot.jsp").forward(request, response);
            }
        } catch (ServletException | IOException ex) {

            LOGGER.error(ex.getMessage());
            try {
                request.setAttribute("Error", "El usuario ingresado no existe.");
                try {
                    request.getRequestDispatcher("/forgot.jsp").forward(request, response);
                } catch (IOException ex1) {
                    LOGGER.error(ex1.getMessage());
                }
            } catch (ServletException ex1) {
                LOGGER.error(ex1.getMessage());
            }
        }
    }

    private static void sendMail(String destinatario, String asunto, String cuerpo) {
// Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        String from = "tesa.recover@gmail.com";
        final String username = "tesa.recover@gmail.com";//change accordingly
        final String password = "tesa1234";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));

            // Set Subject: header field
            message.setSubject("Recuperación de cuenta.");

            // Send the actual HTML message, as big as you like
            message.setContent(cuerpo, "text/html");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            Logger.getLogger(ResetEmailController.class).error(e.getMessage());
        }
    }

    private void password(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sesion = request.getSession(false);
        try {
            if (modelo.modificarPassword(Integer.parseInt((String) sesion.getAttribute("id")), request.getParameter("passwd"))) {
                try {
                    request.getRequestDispatcher("/cerrarSesion.jsp").forward(request, response);
                } catch (ServletException | IOException ex) {
                    try {
                        Logger.getLogger(ResetEmailController.class).error(ex.getMessage());
                        request.setAttribute("Error", "Ocurrió 2 un problema.");
                        request.getRequestDispatcher("/reset.jsp?id=" + sesion.getAttribute("id")).forward(request, response);
                    } catch (ServletException | IOException ex1) {
                        Logger.getLogger(ResetEmailController.class).error(ex1.getMessage());
                    }
                }
            } else {
                request.setAttribute("Error", "Ocurrió un problema.");
                try {
                    request.getRequestDispatcher("/reset.jsp?id=" + sesion.getAttribute("id")).forward(request, response);
                } catch (ServletException | IOException ex) {
                    Logger.getLogger(ResetEmailController.class).error(ex.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("Error", "Usuario no encontrado.");
            try {
                request.getRequestDispatcher("/reset.jsp?id=" + sesion.getAttribute("id")).forward(request, response);
            } catch (ServletException | IOException ex) {
                Logger.getLogger(ResetEmailController.class).error(ex.getMessage());
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
