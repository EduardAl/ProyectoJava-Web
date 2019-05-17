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
        <div class="container" style="max-width: 80%">
            <div class="row mt-4">
                <h3>Lista de departamentos</h3>
            </div>
            <div class="row">
                <div class="col-md-12 table-responsive-sm mt-4">
                    <a type="button" class="btn btn-dark" style="background-color: #043E60" href="${pageContext.request.contextPath}/departamentos?op=nuevo"><span class="oi oi-plus"></span> Nuevo departamento</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover mt-4" style="max-width: 90%; margin-left:60px">
                        <thead style="background-color:#3D3F46 ;color:white;text-align: center">
                            <tr>
                                <th style="width: 35%">Codigo de departamento</th>
                                <th>Departamento</th>
                                <th style="width: 15%">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listarDepartamentos}" var="departamento">
                                <tr class="text-center">
                                    <td>${departamento.id}</td>
                                    <td >${departamento.nombreDept}</td>
                                    <td align="center" class="w-auto">
                                        <div class="btn-group">
                                            <a  class="btn btn-warning" data-toggle="tooltip" data-html="true" title="Modificar" data-placement="bottom"  href="${pageContext.request.contextPath}/departamentos?op=obtener&id=${departamento.id}"><em class="oi oi-document text-white"></em></a>
                                            <a style="margin-left: 15px" class="btn btn-danger" data-toggle="tooltip" data-html="true" title="Eliminar" data-placement="bottom" href="${pageContext.request.contextPath}/departamentos?op=eliminar&id=${departamento.id}"><em class="oi oi-trash text-white"></em></a>
                                            <script>
                                                $(document).ready(function () {
                                                    $('[data-toggle="tooltip"]').tooltip();
                                                });
                                            </script>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>                    
        </div> 
    </body>
    <jsp:include page="/footer.jsp"/>
</html>
