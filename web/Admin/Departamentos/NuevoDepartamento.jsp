<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Nuevo Departamento</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container">
            <div class="row">
                <h3>Nuevo Departamento</h3>
            </div>
            <div class="row">
                <div class=" col-md-7">
                    <form role="form" action="${pageContext.request.contextPath}/departamentos?op=insertar" method="POST">
                        <div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Campos requeridos</strong></div>
                        <div class="form-group">
                            <label for="id">ID:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="id" id="id"  placeholder="Ingrese el id" required>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Nombre del departamento:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="nombre" id="nombre"  placeholder="Ingrese el nombre" required>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-info" value=" Guardar" name="Guardar"><span class="oi oi-circle-check"></span>  Guardar </button>
                        <a class="btn btn-danger" href="#"><span class="oi oi-circle-x"></span>  Cancelar </a>
                    </form>
                </div>
            </div>  
        </div>
    </body>
</html>

