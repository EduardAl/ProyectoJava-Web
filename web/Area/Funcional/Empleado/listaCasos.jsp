<%-- 
    Document   : listaCasos
    Created on : 05-17-2019, 11:09:15 AM
    Author     : eduar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/cabecera.jsp"/>
        <title>Casos</title>
    </head>
    <body>
        <jsp:include page="/Area/Funcional/Empleado/menu.jsp"/>
        <c:if test="${MensajeExito != null}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <h4 class="alert-heading">Caso aceptado!</h4>
                ${MensajeExito}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${MensajeDenegado != null}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <h4 class="alert-heading">Caso denegado</h4>
                ${MensajeDenegado}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="container-fluid">
            <div class="m-2 row">
                <div class="col text-center">
                    <h3>Lista de casos asignados</h3>
                </div>
            </div>
            <div class="m-3 container-fluid">
                <div class="table-responsive-xl">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Titulo</th>
                                <th scope="col">Creado Por</th>
                                <th scope="col">Programador Asignado</th>
                                <th scope="col">Fecha Limite de entrega</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Avance</th>
                                <th scope="col">Fecha de ultimo cambio</th>
                                <th scope="col">Operaciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listarCasos}" var="casos">
                                <tr>
                                    <td scope="col">${casos.id}</td>
                                    <td scope="col">${casos.titulo}</td>
                                    <td scope="col">${casos.creadoPor}</td>
                                    <td scope="col">${casos.asignadoA}</td>
                                    <td scope="col">${casos.limite}</td>
                                    <td scope="col">${casos.estado}</td>
                                    <td scope="col">${casos.avance}</td>
                                    <td scope="col">${casos.ultimoCambio}</td>
                                    <td scope="col">
                                        <div class="btn-group">
                                            <a class="btn btn-success m-1 text-white" href="${pageContext.request.contextPath}/tester?op=aceptar&id=${casos.id}">Aceptar</a>

                                            <div class="modal fade" role="dialog" id="modal-deny${caso.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered" role="document">
                                                    <div class="modal-content">
                                                        <form method="POST" class="justify-content-center" action="${pageContext.request.contextPath}/tester?op=denegar"> 
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="exampleModalLongTitle">Aceptar Peticion</h5>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="form-group">
                                                                    <label for="idp">Id de la peticion:</label>
                                                                    <input type="text" class="form-control" name="id" id="idp" value="${casos.id}" readonly>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="comentario">Comentario:</label>
                                                                    <textarea id="comentario" name="comentario" id="descript" rows="5" class="form-control"></textarea>
                                                                </div>

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn btn-success">Guardar</button>
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <button class="btn btn-danger m-1" data-toggle="modal" data-target="#modal-deny${caso.id}">Denegar</button>
                                        </div>
                                    </td>

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
