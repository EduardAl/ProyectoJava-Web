<%-- 
    Document   : nuevaPeticion
    Created on : 05-13-2019, 10:39:57 AM
    Author     : eduar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/cabecera.jsp"/>
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/Area/Funcional/Jefes/menu.jsp"/>
        <div class="container-fluid">
            <form class="p-2" action="${pageContext.request.contextPath}/request?op=insertar" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="title">Titulo:</label>
                    <input class="form-control" type="text" placeholder="Ingrese un titulo" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="request-type">Tipo de peticion:</label>
                    <select class="form-control" id="request-type" name="request-type" required>
                        <c:forEach items="${requestScope.listaTipoPeticiones}" var="tipoPeticiones">
                            <option value="${tipoPeticiones.key}">${tipoPeticiones.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="description">Descripcion:</label>
                    <textarea class="form-control" rows="5" name="description" id="description" required placeholder="Ingrese una descripcion"></textarea>
                </div>
                <div class="form-group">
                    <label for="file">Documento:</label>
                    <input type="file" class="form-control-file" required id="file" name="file" onchange="mostrarErrorExtencionDocumento(event)">
                    <small class="form-text text-muted">
                        Solo se perminten archivos en formato: pdf, doc y docx
                    </small>
                </div> 
                <input class="btn btn-success" type="submit" value="Guardar">
            </form>
        </div>
    </body>
    <jsp:include page="/footer.jsp"/>
</html>
