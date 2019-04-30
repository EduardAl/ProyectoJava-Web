<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de departamentos</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container">
            <div class="row">
                <h3>Lista de departamentos</h3>
            </div>
            <div class="row">
                <div class="col-md-10">
                    <a type="button" class="btn btn-primary btn-md" href="${pageContext.request.contextPath}/departamentos?op=nuevo">Nuevo departamento</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>Codigo de departamento</th>
                                <th>Departamento</th>

                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.listarDepartamentos}" var="departamento">
                            <tr>
                                    <td>${departamento.id}</td>
                                    <td>${departamento.nombreDept}</td>
                                    <td>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/departamentos?op=obtener&id=${departamento.id}"><span class="glyphicon glyphicon-edit"></span> Editar</a>
                                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/departamentos?op=eliminar&id=${departamento.id}"><span class="glyphicon glyphicon-trash"></span> Eliminar</a>
                                    </td>
                            </tr>
                        </c:forEach>
                                

                        </tbody>
                    </table>
                </div>
                
            </div>                    
        </div> 
    </body>
</html>
