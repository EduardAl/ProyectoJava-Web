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
        <div class="container mt-4">
            <br>
            <div class="card" style=" background-color: #F6F6F6;max-width: 60%; margin-top: 85px; margin-left: 200px">
                <div class="card-header rounded" style="background:#3D3F46;margin:-25px 35px 35px 35px; max-width: 90%;">
                    <h4 class="card-title text-white mt-2">Nuevo Departamento</h4>
                </div>
                <div class="card-body ml-4">
                    <div class=" col-md-7">
                        <form role="form" style="margin-right: -290px" action="${pageContext.request.contextPath}/departamentos?op=insertar" method="POST">
                            <div class="well well-sm" style="font-size: 18px"><strong><span class="glyphicon glyphicon-asterisk"></span>Campos requeridos</strong></div>
                            <div class="row  mt-4">
                                <div class="col-md-4 ">
                                    <div class="form-group">
                                        <label for="id">ID:</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="id" id="id"  placeholder="Ingrese el id" required>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row  mt-4">
                                <div class="col-md-10 ">
                                    <div class="form-group">
                                        <label for="nombre">Nombre del departamento:</label>
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="nombre" id="nombre"  placeholder="Ingrese el nombre" required>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-4 mb-4">
                                <button type="submit" style="margin-left: 170px" class="btn btn-info" value=" Guardar" name="Guardar"><span class="oi oi-circle-check"></span>  Guardar </button>
                                <a class="btn btn-danger ml-4" href="#"><span class="oi oi-circle-x"></span>Cancelar </a>
                            </div>
                        </form>
                    </div>
                </div>  
            </div>           
        </div>

    </body>
</html>

