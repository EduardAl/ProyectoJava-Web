<%-- 
    Document   : Admin
    Created on : 03-22-2019, 12:38:27 PM
    Author     : eduar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
    HttpSession sesion = request.getSession(false);
    if(sesion.getAttribute("nombre") != null)
    {
        sesion.getAttribute("nombre");
        if(!sesion.getAttribute("rol").toString().equals("Administrador"))
        {
            sesion.setAttribute("Error", "Error de usuario.");
            response.sendRedirect("../index.jsp");
        }
    }
    else
    {
        sesion.setAttribute("Error", "Debe iniciar sesión.");
        response.sendRedirect("../index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/cabecera.jsp"/>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
    </body>
</html>
