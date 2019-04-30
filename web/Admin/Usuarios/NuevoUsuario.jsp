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
        <div class="container">
            <div class="row">
                <h3>Nuevo usuario</h3>
            </div>
            <div class="row">
                <div class=" col-md-7">
                    <form role="form" action="${pageContext.request.contextPath}/usuarios?op=insertar" method="POST">
                        <div class="well well-sm"><strong><span class="glyphicon glyphicon-asterisk"></span>Campos requeridos</strong></div>
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
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="nombre" id="nombre"  placeholder="Ingrese el nombre" required>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="apellido">Apellido</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="apellido" id="apellido"  placeholder="Ingrese el apellido" required>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="correo">Correo:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="correo" id="correo"  placeholder="Ingresa el correo" required>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                            </div>
                        </div>
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
                        <input type="submit" class="btn btn-info" value="Guardar" name="Guardar">
                        <a class="btn btn-danger" href="#">Cancelar</a>
                    </form>
                </div>
            </div>  
        </div>
        <script>
            $('#jefe').select2();            
            $('#departamento').select2();

            </script>
    </body>
</html>

