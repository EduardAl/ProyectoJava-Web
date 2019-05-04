<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de usuarios</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <script language = "JavaScript" type = "text/JavaScript" > 
        function abrirVentana() 
        { 
            alert("${requestScope.error}");
        } 
    </script> 
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container col-11">
            <div class="row">
                <h3>Lista de usuarios</h3>
            </div>
            <div>
                <div class="col-md-12">
                    <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/usuarios?op=nuevo"><span class="oi oi-plus"></span> Nuevo usuario</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover mt-4 ">
                        <thead style="background-color:#3D3F46 ;color:white">
                            <tr>
                                <th>Codigo de usuario</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Departamento</th>
                                <th>Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listarUsuarios}" var="usuario">
                                <tr>
                                    <td>${usuario.id}</td>
                                    <td>${usuario.fname}</td>
                                    <td>${usuario.lname}</td>
                                    <td>${usuario.email}</td>
                                    <td>${usuario.rol}</td>
                                    <td>${usuario.department}</td>
                                    <td>
                                        <div class="btn-group">
                                            <a style="margin-left: 5px" class="btn btn-primary" href="${pageContext.request.contextPath}/usuarios?op=obtener&id=${usuario.id}"><span class="oi oi-document">&nbsp;Editar</span></a>
                                            <a style="margin-left: 5px" class="btn btn-danger" href="${pageContext.request.contextPath}/usuarios?op=eliminar&id=${usuario.id}"><span class="oi oi-trash">&nbsp;Eliminar</span></a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>                    
        </div> 
        <c:if test = "${requestScope.error != null}">
            <script>abrirVentana();</script>
        </c:if>
    </body>
</html>
