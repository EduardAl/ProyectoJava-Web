<%-- 
    Document   : nuevaPeticion
    Created on : 05-13-2019, 10:39:57 AM
    Author     : eduar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/cabecera.jsp"/>
        <title>Modificar Peticion</title>
    </head>
    <body>
        <jsp:include page="/Area/Funcional/Jefes/menu.jsp"/>
        <div class="container-fluid">
            <h2 class="text-center m-1">Modificar Peticion</h2>
            <form class="justify-content-center m-1" action="${pageContext.request.contextPath}/request?op=modificarBase" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="id">ID:</label>
                    <input type="number" class="form-control" id="id" name ="id" value="${peticion.id}" readonly>
                </div>
                <div class="form-group ">
                    <label for="title">Titulo:</label>
                    <input class="form-control" type="text" id="title" name="title" value="${peticion.titulo}" required>
                </div>
                <div class="form-group">
                    <label for="request-type">Tipo de peticion:</label>
                    <select class="form-control" id="request-type" name="request-type" required>
                        <c:forEach items="${requestScope.listaTipoPeticiones}" var="tipoPeticiones">
                            <c:choose>
                                <c:when test="${tipoPeticiones.value == peticion.tipoPeticion}">
                                    <option value="${tipoPeticiones.key}" selected>${tipoPeticiones.value}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${tipoPeticiones.key}">${tipoPeticiones.value}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="description">Descripcion:</label>
                    <textarea class="form-control" rows="5" name="description" id="description" required placeholder="Ingrese una descripcion">${peticion.descripcion}</textarea>
                </div>
                <div class="form-group">
                    <label for="file">Documento:</label>
                    <input type="file" class="form-control-file" id="file" required name="file" onchange="mostrarErrorExtencionDocumento(event)">
                    <small class="form-text text-muted">
                        Solo se perminten archivos en formato: pdf, doc y docx
                    </small>
                </div>
                <input type="hidden" id="hidden-file" name="hidden-file" value="${peticion.fileDir}">
                <input class="btn btn-success" type="submit" value="Guardar">
            </form>
        </div>
    </body>
    <jsp:include page="/footer.jsp"/>
</html>
