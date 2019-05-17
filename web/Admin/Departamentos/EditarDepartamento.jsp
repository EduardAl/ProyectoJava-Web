<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar departamento</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container mt-4">
            <br>
            <div class="card" style=" background-color: #F6F6F6;max-width: 60%; margin-top: 85px; margin-left: 200px">

                <div class="card-header rounded" style="background:#3D3F46;margin:-25px 35px 35px 35px; max-width: 90%;">
                    <h4 class="card-title text-white mt-2">Nuevo Usuario</h4>
                </div>
                <div class="card-body ml-4">
                    <div class=" col-md-7">
                        <form role="form" style="margin-right: -290px" action="${pageContext.request.contextPath}/departamentos?op=modificar" method="POST">
                            <div class="well well-sm" style="font-size: 18px"><strong><span class="glyphicon glyphicon-asterisk"></span>Campos requeridos</strong></div>
                            <div class="form-group">
                                <div class="row  mt-4">
                                    <div class="col-md-4 ">
                                        <label for="id">ID:</label>
                                        <div class="input-group">
                                            <input type="hidden" class="form-control" name="id" id="id"  value="${departamento.id}">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row  mt-4">
                                    <div class="col-md-10 ">       
                                        <div class="form-group">
                                            <label for="nombre">Nombre:</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="nombre" id="nombre"  value="${departamento.nombreDept}" required>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-4 mb-4">
                                    <button type="submit" class="btn btn-info" style="margin-left: 170px" value=" Guardar" name="Guardar"><span class="oi oi-circle-check"></span>  Guardar </button>
                                    <a class="btn btn-danger ml-4" href="${pageContext.request.contextPath}/departamentos?op=listar"><span class="oi oi-circle-x"></span>  Cancelar </a>
                                </div>
                        </form>
                    </div>
                </div>  
            </div>
        </div>
    </body>
</html>

