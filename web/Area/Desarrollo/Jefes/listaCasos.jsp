<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista Casos</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="/Area/Desarrollo/Jefes/menu.jsp"/>

        <div class="container-fluid">
            <div class="m-2 row">
                <div class="col text-center">
                    <h3>Lista de Casos</h3>
                </div>
            </div>
            <div class="m-3 container-fluid">
                <div class="table-responsive-xl">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Titulo</th>
                                <th scope="col">Descripcion</th>
                                <th scope="col">Documento</th>
                                <th scope="col">Porcentaje de Avance</th>
                                <th scope="col">Programador Asignado</th>
                                <th scope="col">Estado del caso</th>
                                <th scope="col">Fecha limite de entrega</th>
                                <th class="w-auto">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listaCasos}" var="casos">
                                <tr>
                                    <td scope="col">${casos.id}</td>
                                    <td scope="col">${casos.titulo}</td>
                                    <td scope="col">${casos.descripcion}</td>
                                    <td scope="col">
                                        <a href="${pageContext.request.contextPath}/case?op=obtener&file=${casos.fileDir}" class="btn btn-link m-1" ><span class="oi oi-file"></span>Descargar Documento</a>
                                    </td>
                                    <td scope="col">${casos.avance}</td>
                                    <td scope="col">${casos.asignadoA}</td>
                                    <td scope="col">${casos.estado}</td>
                                    <td scope="col">${casos.limite}</td>
                                    <td align="center" class="w-auto">
                                        <div class="btn-group">
                                            <a  title="detalles" class="btn btn-info m-1" href="javascript:detalles(${casos.id})"><span class="oi oi-zoom-in text-white"></span></a>
                                                <c:choose>
                                                    <c:when test="${casos.estado == 'Finalizado'}">

                                                    <div class="modal fade" role="dialog" id="modal${casos.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                                        <div class="modal-dialog modal-dialog-centered" role="document">
                                                            <div class="modal-content">
                                                                <form method="POST" class="justify-content-center" action="${pageContext.request.contextPath}/case?op=tester"> 
                                                                    <div class="modal-header">
                                                                        <h5 class="modal-title" id="exampleModalLongTitle">Asignar tester</h5>
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span>
                                                                        </button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <div class="form-group">
                                                                            <label for="idc">Id del caso</label>
                                                                            <input type="text" class="form-control" name="idc" id="idc" value="${casos.id}" readonly>
                                                                        </div>

                                                                        <div class="form-group">
                                                                            <label for="empleados">Tester asignado:</label>
                                                                            <select class="form-control" id="empleados" name="empleados">
                                                                                <c:forEach items="${requestScope.listarEmpleados}" var="empleados">
                                                                                    <option value="${empleados.id}">${empleados.fname} ${empleados.lname}</option>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="submit" class="btn btn-success">Guardar</button>
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <a title="Asignar tester" class="btn btn-info m-1" data-toggle1="tooltip" data-html="true" data-placement="bottom" data-toggle="modal" data-target="#modal${casos.id}"><span class="oi oi-person text-white"></span>
                                                    </a>

                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div><jsp:include page="/footer.jsp"/>
        <script>
            <c:if test="${not empty exito}">
            alertify.success('${exito}');
                <c:set var="exito" value="" scope="session" />
            </c:if>
            <c:if test="${not empty fracaso}">
            alertify.error('${fracaso}');
                <c:set var="fracaso" value="" scope="session" />
            </c:if>
        </script>
        <script>
            $(document).ready(function () {
                $('[data-toggle1="tooltip"]').tooltip();
            });
            function detalles(id)
            {
                $.ajax({
                    url: "${pageContext.request.contextPath}/caso?op=individualRequest&id=" + id,
                    type: "GET",
                    success: function (data) {
                        console.log(data);
                        $('#codigo').text(data.codigo);
                        $('#nombre').text(data.nombre);
                        $('#tipo').text(data.tipo);
                        $('#departamento').text(data.departamento);
                        $('#descripcion').text(data.descripcion);
                        $('#creado').text(data.creado);
                        $('#comentario').text(data.comentario);
                        $('#estado').text(data.estado);
                        $('#fechac').text(data.fechac);
                        $('#fechaa').text(data.fechaa);
                        $('#modal').modal('show');
                    },
                    error: function (data) {
                        console.log(data);
                    }
                });
            }
        </script>
    </body>
</html>
