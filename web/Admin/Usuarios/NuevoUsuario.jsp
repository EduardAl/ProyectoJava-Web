<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Nuevo usuario</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container mt-4">
                <br>
                <div class="card" style=" background-color: #F6F6F6;max-width: 90%; margin-top: 35px; margin-left: 50px">
                    <div class="card-header rounded" style="background:#3D3F46;margin:-25px 35px 35px 35px; max-width: 90%;">
                        <h4 class="card-title text-white mt-2">Nuevo Usuario</h4>
                        <p class="card-category"></p>
                    </div>
                    <div class="card-body ml-4">
                        <div class=" col-md-7">
                            <form role="form" style="margin-right: -290px" action="${pageContext.request.contextPath}/usuarios?op=insertar" method="POST">
                                <div class="well well-sm" style="font-size: 18px"><strong><span class="glyphicon glyphicon-asterisk"></span>Campos requeridos</strong></div>
                                <div class="row  mt-4">
                                    <div class="col-md-8 ">
                                        <div class="form-group">
                                            <label for="rol">Rol:</label>
                                            <div class="input-group">
                                                <select name="rol" id="rol" class="form-control">
                                                    <c:forEach items= "${requestScope.listarRoles}" var="roles">
                                                        <option value ="${roles.id}">${roles.rol}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-3">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="nombre">Nombre:</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" style="max-width: 100%"name="nombre" id="nombre"  placeholder="Ingrese el nombre" required>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="apellido">Apellido</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="apellido" id="apellido"  placeholder="Ingrese el apellido" required>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-3">
                                    <div class="col-md-8">
                                        <div class="form-group">
                                            <label for="correo">Correo:</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="correo" id="correo"  placeholder="Ingresa el correo" required>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-3">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="jefe">Jefe:</label>
                                            <div class="input-group">
                                                <select name="jefe" id="jefe" class="form-control">
                                                    <c:forEach items= "${requestScope.listarEmpleados}" var="jefes">
                                                        <option value ="${jefes.id}">${jefes.nombre}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="departamento">Departamento:</label>
                                            <div class="input-group">
                                                <select name="departamento" id="departamento" class="form-control">
                                                    <c:forEach items= "${requestScope.listarDepartamentos}" var="departamento">
                                                        <option value ="${departamento.id}">${departamento.nombreDept}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                            </div>
                                        </div>          
                                    </div>
                                </div>
                                <div class="row mt-4 mb-4">
                                    <button type="submit" class="btn btn-info"  style="margin-left: 300px" value="Guardar" name="Guardar"><span class="oi oi-circle-check"></span>  Guardar </button>
                                    <a class="btn btn-danger ml-4" href="${pageContext.request.contextPath}/usuarios?op=listar"><span class="oi oi-circle-x"></span>  Cancelar </a>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
        </div>
    </div>
    <script>
        $('#jefe').select2();
        $('#departamento').select2();

    </script>
</body>
</html>

