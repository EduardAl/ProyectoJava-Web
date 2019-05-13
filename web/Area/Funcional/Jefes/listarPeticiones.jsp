<%-- 
    Document   : listarPeticiones
    Created on : 05-07-2019, 03:03:08 PM
    Author     : eduar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/cabecera.jsp"/>
        <script src="${pageContext.request.contextPath}/assets/js/funciones.js" type="text/javascript"></script>
        <title>Peticiones</title>
    </head>
    <body>
        <div>
            <jsp:include page="/Area/Funcional/Jefes/menu.jsp"/>
        </div>
        <div class="container-fluid">
            <div class="m-2 row">
                <div class="col text-center">
                    <h3>Lista de peticiones</h3>
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
                                <th scope="col">Departamento solicitante</th>
                                <th scope="col">Tipo de solicitud</th>
                                <th scope="col">Estado de la solicitud</th>
                                <th scope="col">Operaciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listarPeticiones}" var="peticiones">
                                <tr>
                                    <td scope="col">${peticiones.id}</td>
                                    <td scope="col">${peticiones.titulo}</td>
                                    <td scope="col">${peticiones.descripcion}</td>
                                    <td scope="col">${peticiones.departamento}</td>
                                    <td scope="col">${peticiones.tipoPeticion}</td>
                                    <td id="estadoPeticion" scope="col">${peticiones.estado}</td>
                                    <td scope="col">
                                        <a class="btn btn-danger p-1 text-white"><span class="oi oi-trash"></span>Eliminar</a>
                                        <a class="btn btn-info p-1 text-white"><span class="oi oi-file"></span>Modificar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
