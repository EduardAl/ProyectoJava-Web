<%-- 
    Document   : listarPeticiones
    Created on : 05-15-2019, 10:03:26 AM
    Author     : eduar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <jsp:include page="/cabecera.jsp"/>
        <title>Listar</title>
    </head>
    <body>
        <jsp:include page="/Area/Desarrollo/Jefes/menu.jsp"/>
        <c:if test="${ExitoDenegar!= null}">
            <div class="alert alert-info alert-info fade show" role="alert">
                <h4 class="alert-heading">Exito al denegar</h4>
                ${ExitoDenegar}
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
                                <th scope="col">Documento Peticion</th>
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
                                        <a href="${pageContext.request.contextPath}/case?op=obtener&file=${peticiones.fileDir}" class="btn btn-link m-1"><span class="oi oi-file"></span>Descargar Documento</a>
                                    </td>
                                    <td scope="col">
                                        <div class="modal fade" role="dialog" id="modal${peticiones.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <form method="POST" class="justify-content-center" action="${pageContext.request.contextPath}/case?op=denegar"> 
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLongTitle">Denegar Peticion</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="form-group">
                                                                <label for="id">Id:</label>
                                                                <input type="text" class="form-control" name="id" id="id" value="${peticiones.id}" readonly>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="comentary">Comentario:</label>
                                                                <textarea id="commentary" class="form-control" rows="5"></textarea>
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

                                        <div class="modal fade" role="dialog" id="modal-acept${peticiones.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <form method="POST" class="justify-content-center" action="${pageContext.request.contextPath}/case?op=nuevo" enctype="multipart/form-data"> 
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLongTitle">Aceptar Peticion</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="form-group">
                                                                <label for="idp">Id de la peticion:</label>
                                                                <input type="text" class="form-control" name="idp" id="idp" value="${peticiones.id}" readonly>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="descript">Comentario:</label>
                                                                <textarea id="descript" name="descript" id="descript" rows="5" class="form-control"></textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="file">Documento:</label>
                                                                <input type="file" class="form-control-file" id="file" required name="file" onchange="mostrarErrorExtencionDocumento(event)">
                                                                <small class="form-text text-muted">
                                                                    Solo se perminten archivos en formato: pdf, doc y docx
                                                                </small>
                                                            </div>

                                                            <div class="form-group">
                                                                <label for="empleados">Programador asignado:</label>
                                                                <select class="form-control" id="empleados" name="empleados">
                                                                    <c:forEach items="${requestScope.listarEmpleados}" var="empleados">
                                                                        <option value="${empleados.key}">${empleados.value}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            
                                                            <div class="form-group">
                                                                
                                                                <label for="fecha">Fecha de entrega:</label>
                                                                <fmt:formatDate var="fechaMin" value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd"/>
                                                                <input type="date" class="form-control" name="fecha" id="fecha" min="${fechaMin}">
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
                                                            
                                        <div class="btn-group">
                                            <c:choose>
                                                <c:when test="${peticiones.estado == 'En espera de respuesta'}">
                                                    <button class="btn btn-danger m-1" data-toggle="modal" data-target="#modal${peticiones.id}">
                                                        Denegar
                                                    </button>
                                                    <button class="btn btn-success m-1" data-toggle="modal" data-target="#modal-acept${peticiones.id}">
                                                        Aceptar
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-danger m-1" data-toggle="modal" data-target="#modal${peticiones.id}" disabled>
                                                        Denegar
                                                    </button>
                                                    <button class="btn btn-success m-1" data-toggle="modal" data-target="#modal-acept${peticiones.id}" disabled>
                                                        Aceptar
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
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
