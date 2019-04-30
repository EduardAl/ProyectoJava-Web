<%-- 
    Document   : Inicio
    Created on : 03-22-2019, 01:34:51 PM
    Author     : eduar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<% HttpSession sesion = request.getSession(false); 
    if(sesion.getAttribute("nombre") != null)
    {
        sesion.getAttribute("nombre");
        if(!sesion.getAttribute("rol").toString().equals("Jefe de área funcional"))
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
        <title>Inicio</title>
    </head>
    <body>
        <h1>Ha iniciado sesión como <% out.print(sesion.getAttribute("rol")); %> </h1>
    </body>
</html>
