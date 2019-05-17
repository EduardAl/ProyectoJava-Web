<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TimeLine</title>
        <link href="${pageContext.request.contextPath}/assets/css/timeline.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <c:set var="val" value="0"/>
        <div class="timeline">
            <c:forEach items="${sessionScope.listarEventos}" var="evento">
                <c:choose> 
                    <c:when test="${val == '0'}">
                        <div class="container left">
                            <div class="content">
                                <h2>${evento.creationDate}</h2>
                                <p>Descripcion: ${evento.description}</p>
                                <p>Estado: ${evento.estado}</p>
                                <p>Porcentaje: ${evento.percent}</p>
                            </div>
                        </div>
                                <c:set var="val" value="1"/>
                    </c:when>
                    <c:otherwise>
                        <div class="container right">
                            <div class="content">
                                <h2>${evento.creationDate}</h2>
                                <p>Descripcion: ${evento.description}</p>
                                <p>Estado: ${evento.estado}</p>
                                <p>Porcentaje: ${evento.percent}</p>
                            </div>
                        </div>
                                <c:set var="val" value="0"/>
                    </c:otherwise>
                </c:choose>


            </c:forEach>
        </div>

    </body>
</html>
