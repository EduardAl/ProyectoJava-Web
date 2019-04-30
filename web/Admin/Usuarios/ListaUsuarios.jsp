<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de usuarios</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <script language="JavaScript" type="text/JavaScript"> 
        function abrirVentana() 
        { 
         alert("${requestScope.error}");
        } 
</script> 
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container">
            <div class="row">
                <h3>Lista de usuarios</h3>
            </div>
            <div class="row">
                <div class="col-md-10">
                    
                    <a type="button" class="btn btn-primary btn-md" href="${pageContext.request.contextPath}/usuarios?op=nuevo">Nuevo usuaro</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>Codigo de usuario</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Departamento</th>

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
                                    <td class="col-md-2">
                                        <a class="btn btn-primary col-md-12" href="${pageContext.request.contextPath}/usuarios?op=obtener&id=${usuario.id}"><span class="glyphicon glyphicon-edit"></span> Editar</a>
                                        <a class="btn btn-danger col-md-12" href="${pageContext.request.contextPath}/usuarios?op=eliminar&id=${usuario.id}"><span class="glyphicon glyphicon-trash"></span> Eliminar</a>
                                    </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>                    
        </div> 
                <c:if test = "${requestScope.error != null}">
                        <script>abrirVentana()</script>
                    </c:if>
    </body>
</html>
