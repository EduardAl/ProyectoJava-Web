<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar usuario</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container">
            <div class="row">
                <h3>Editar usuario</h3>
            </div>
            <div class="row">
                <div class=" col-md-7">
                    <form role="form" action="${pageContext.request.contextPath}/usuarios?op=modificar" method="POST">
                        <div class="well well-sm"><strong><span class="oi oi-aperture"></span> Campos requeridos</strong></div>
                        <div class="form-group">
                            <label for="id">ID:</label>
                            <div class="input-group">
                                <input type="number" readonly class="form-control" name="id" id="id" value="${usuario.id}">
                                <span class="input-group-addon"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="rol">Rol:</label>
                            <div class="input-group">
                                <select name="rol" id="rol" class="form-control" >
                                    <c:forEach items= "${requestScope.listarRoles}" var="roles">
                                        <c:choose>
                                            <c:when  test = "${requestScope.usuario.rol == roles.rol}">
                                                <option value ="${roles.id}" selected>${roles.rol}</option>
                                            </c:when>    
                                            <c:otherwise>
                                                <option value ="${roles.id}">${roles.rol}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <span class="input-group-lg"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="nombre" id="nombre"  value="${usuario.fname}" required> 
                                <span class="input-group-addon"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="apellido">Apellido</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="apellido" id="apellido"  value="${usuario.lname}" required>
                                <span class="input-group-addon"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="correo">Correo:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="correo" id="correo"  value="${usuario.email}" required>
                                <span class="input-group-addon"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="jefe">Jefe:</label>
                            <div class="input-group">
                                <select name="jefe" id="jefe" class="form-control">
                                    <c:forEach items= "${requestScope.listarEmpleados}" var="jefes">
                                        <c:choose>
                                            <c:when  test = "${requestScope.usuario.chief == jefes.nombre}">
                                                <option value ="${jefes.id}" selected>${jefes.nombre}</option>
                                            </c:when>    
                                            <c:otherwise>
                                                <option value ="${jefes.id}">${jefes.nombre}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <span class="input-group-addon"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="departamento">Departamento:</label>
                            <div class="input-group">
                                <select name="departamento" id="departamento" class="form-control">
                                    <c:forEach items= "${requestScope.listarDepartamentos}" var="departamento">
                                        <c:choose>
                                            <c:when  test = "${requestScope.usuario.department == departamento.nombreDept}">
                                                <option value ="${departamento.id}" selected>${departamento.nombreDept}</option>
                                            </c:when>    
                                            <c:otherwise>
                                                <option value ="${departamento.id}">${departamento.nombreDept}</option>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                    </c:forEach>
                                </select>
                                <span class="input-group-addon"><span class="oi oi-aperture"></span></span>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-info" value="Guardar" name="Guardar"><span class="oi oi-circle-check"></span>  Guardar </button>
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/usuarios?op=listar"><span class="oi oi-circle-x"></span>  Cancelar </a>
                    </form>
                </div>
            </div>  
        </div>
        <script>
            $('#jefe').select2();            
            $('#departamento').select2();
            $('#rol').select2();
            </script>
    </body>
</html>

