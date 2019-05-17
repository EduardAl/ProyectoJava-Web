<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="/cabecera.jsp"/>
    <script language = "JavaScript" type = "text/JavaScript" > 
        function abrirVentana() 
        { 
        alert("${requestScope.error}");
        } 
        $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus')
        })
    </script> 
    <body onload="">
        <jsp:include page="menu.jsp"/>
        <div class="container col-11">
            <div class="row">
                <h3>Lista de bitacoras</h3>
            </div>
            <div> 
                Caso ${requestScope.singleCaseBean.id}
            </div>
            <div>
                <div class="col-md-12 table-responsive-sm">
                    <a type="button" class="btn btn-dark" data-toggle="modal"
                       data-target="#modaladd" href="#"><span class="oi oi-plus"></span> Nueva bitacora</a>
                    <br><br>
                    <table class="table table-striped table-bordered table-hover mt-4 ">
                        <thead style="background-color:#3D3F46 ;color:white">
                            <tr>
                                <th>Id Bitacora</th>

                                <th>Comentario</th>
                                <th>Porcentaje</th>

                                <th>Fecha Creado</th>
                                <th class="w-auto">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.listarBitacoras}" var="bitacora">
                                <tr>
                                    <td>${bitacora.id}</td>

                                    <td>${bitacora.commentary}</td>
                                    <td>${bitacora.percent}</td>
                                    <td>${bitacora.createdAt}</td>
                                    <td align="center" class="w-auto">
                                        <div class="btn-group">
                                            <a style="margin-left: 5px" class="btn btn-warning" data-toggle="tooltip" data-html="true" title="Modificar" data-placement="bottom" href="${pageContext.request.contextPath}/usuarios?op=obtener&id=${usuario.id}"><em class="oi oi-document text-white"></em></a>
                                            <a style="margin-left: 5px" class="btn btn-danger" data-toggle="tooltip" data-html="true" title="Eliminar" data-placement="bottom" href="${pageContext.request.contextPath}/usuarios?op=eliminar&id=${usuario.id}"><em class="oi oi-trash text-white"></em></a>
                                            <script>
                                                $(document).ready(function () {
                                                    $('[data-toggle="tooltip"]').tooltip();
                                                });
                                            </script>
                                            <button type="button" class="btn btn-primary" 
                                                    data-toggle="modal"
                                                    data-target="#myModal"
                                                    onclick="loadToModal('${bitacora.id}', '${bitacora.commentary}');"
                                                    >
                                                Ver Datos
                                            </button>
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



        <div class="modal fade modal-md" id="myModal">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title" id="idbitacora"></h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <p id="comentario"></p>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                    </div>

                </div>


            </div>
        </div>



        <div class="modal fade" id="modaladd">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title" id="idbitacora"></h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>

                    <!-- Modal body -->
                    <div class="modal-body">
                        <div class="container container-fluid">
                            <div id="form-add" class="row">
                                <form method="post" action="${pageContext.request.contextPath}/bitacoras?op=agregar">

                                    <input class="form-control" type="hidden" name="case_id" value="${requestScope.singleCaseBean.id}" />

                                    <div class="form-group">
                                        <label for="comentario">Comentario:</label>
                                        <!--Material textarea-->
                                        <div class="form-group">
                                            <textarea rows="5" id="comment" name="comment" class="form-control" rows="3" placeholder="Ingresa el comentario" required></textarea>

                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <label for="percent">Porcentaje:</label>
                                        <div class="input-group">
                                            <input type="number" class="form-control" min="0.00" step="1" name="percent" id="percent"  placeholder="Ingresa el porecentaje" required>
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input type="submit" value="agregar" />
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Modal footer -->
                    <div class="modal-footer">


                        <div class="row">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                        </div>

                    </div>

                </div>


            </div>
        </div>




        <script>
            function loadToModal(idbitacora, comentario) {
                $("#idbitacora").text(idbitacora);
                $("#comentario").text(comentario);
            }
        </script>


        <script src="assets/js/jquery.min.js"></script>            
    </body>
    <jsp:include page="/footer.jsp"/>
</html>
