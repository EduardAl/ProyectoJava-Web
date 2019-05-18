<%-- 
    Document   : Inicio
    Created on : 03-22-2019, 01:34:51 PM
    Author     : eduar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/cabecera.jsp"/>
        <title>Inicio</title>
    </head>
    <body>
        <jsp:include page="/Area/Funcional/Jefes/menu.jsp"/>/
        <a class="dropdown-item" href="${pageContext.request.contextPath}/event">
            <span class="oi oi-person"></span>  Timeline</a>
    </body>
        <jsp:include page="/footer.jsp"/>
</html>
