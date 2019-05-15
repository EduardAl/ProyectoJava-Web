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
        <title>Peticiones</title>
    </head>
    <body>
        <div>
            <jsp:include page="/Area/Funcional/Jefes/menu.jsp"/>
        </div>
        <c:if test="${ErrorModificar != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <h4 class="alert-heading">Error al modificar</h4>
                ${ErrorModificar}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${MensajeExito != null}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <h4 class="alert-heading">Borrado Exitoso</h4>
                ${MensajeExito}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${ErrorEliminar != null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <h4 class="alert-heading">Error al Eliminar</h4>
                ${ErrorEliminar}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="container-fluid">
            <div class="m-2 row">
                <div class="col text-center">
                    <h3>Lista de peticiones</h3>
                </div>
            </div>
            <div class="m-3 container-fluid">
                <%= application.getServerInfo() %>
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
                                <th scope="col">Documento</th>
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
                                        <a href="${pageContext.request.contextPath}/request?op=obtener&file=${peticiones.fileDir}" class="btn btn-link m-1"><span class="oi oi-file"></span>Descargar Documento</a>
                                    </td>
                                    <td scope="col">
                                        <div class="btn-group">
                                            <a class="btn btn-danger m-1 text-white" href="${pageContext.request.contextPath}/request?op=eliminar&id=${peticiones.id}">Eliminar</a>
                                            <a class="btn btn-info m-1 text-white" href="${pageContext.request.contextPath}/request?op=modificar&id=${peticiones.id}">Modificar</a>
                                        </div>
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
