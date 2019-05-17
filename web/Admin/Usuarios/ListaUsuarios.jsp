<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/menu.jsp"/>
        <div class="container col-11" style="max-width: 80%">
            <div class="row mt-4 ml-3">
                <h3>Lista de usuarios</h3>
            </div>
            <div>
                <div class="col-md-12 table-responsive-sm mt-4">
                    <a type="button" class="btn btn-dark" style="background-color: #043E60" href="${pageContext.request.contextPath}/usuarios?op=nuevo"><span class="oi oi-plus"></span> Nuevo usuario</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover mt-4">
                        <thead style="background-color:#3D3F46 ;color:white; text-align: center">
                            <tr>
                                <th>Codigo de usuario</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Departamento</th>
                                <th class="w-auto">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listarUsuarios}" var="usuario">
                                <tr>
                                    <td style="text-align: center">${usuario.id}</td>
                                    <td>${usuario.fname}</td>
                                    <td>${usuario.lname}</td>
                                    <td>${usuario.email}</td>
                                    <td>${usuario.rol}</td>
                                    <td>${usuario.department}</td>
                                    <td align="center" class="w-auto">
                                        <div class="btn-group">
                                            <a style="margin-left: 5px" class="btn btn-warning" data-toggle="tooltip" data-html="true" title="Modificar" data-placement="bottom" href="${pageContext.request.contextPath}/usuarios?op=obtener&id=${usuario.id}"><em class="oi oi-document text-white"></em></a>
                                            <a style="margin-left: 5px" class="btn btn-danger" data-toggle="tooltip" data-html="true" title="Eliminar" data-placement="bottom" href="javascript:eliminar(${usuario.id})"><em class="oi oi-trash text-white"></em></a>

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
    <jsp:include page="/footer.jsp"/>
        <script>
            <c:if test="${not empty exito}">
            alertify.success('${exito}');
                <c:set var="exito" value="" scope="session" />
            </c:if>
            <c:if test="${not empty fracaso}">
            alertify.error('${fracaso}');
                <c:set var="fracaso" value="" scope="session" />
            </c:if>
                function eliminar(id) {
                alertify.confirm("¿Realmente decea eliminar a este empleado?", function (e) {
                    if (e) {
                        location.href = "${pageContext.request.contextPath}/usuarios?op=eliminar&id=" + id;
                    }
                });

            }
        </script>
    </body>
</html>
