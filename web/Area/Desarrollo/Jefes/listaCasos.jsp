<%-- 
    Document   : listaCasos
    Created on : 05-16-2019, 03:11:15 PM
    Author     : eduar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Casos</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/Area/Desarrollo/Jefes/menu.jsp"/>
        <div class="container-fluid">
            <div class="m-2 row">
                <div class="col text-center">
                    <h3>Lista de Casos</h3>
                </div>
            </div>
            <div class="m-3 container-fluid">
                <div class="table-responsive-xl">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Titulo</th>
                                <th scope="col">Descripcion</th>
                                <th scope="col">Documento</th>
                                <th scope="col">Porcentaje de Avance</th>
                                <th scope="col">Programador Asignado</th>
                                <th scope="col">Estado del caso</th>
                                <th scope="col">Fecha limite de entrega</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listaCasos}" var="casos">
                                <tr>
                                    <td scope="col">${casos.id}</td>
                                    <td scope="col">${casos.titulo}</td>
                                    <td scope="col">${casos.descripcion}</td>
                                    <td scope="col">
                                        <a href="${pageContext.request.contextPath}/case?op=obtener&file=${casos.fileDir}" class="btn btn-link m-1"><span class="oi oi-file"></span>Descargar Documento</a>
                                    </td>
                                    <td scope="col">${casos.avance}</td>
                                    <td scope="col">${casos.asignadoA}</td>
                                    <td scope="col">${casos.estado}</td>
                                    <td scope="col">${casos.limite}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <jsp:include page="/footer.jsp"/>
    </body>
</html>
