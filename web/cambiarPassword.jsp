<div class="modal fade" id="myModal">
    <div class="modal-dialog modal-md">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Cambiar contraseña</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <form id="cambio" action="${pageContext.request.contextPath}/usuarios?op=password" method="post" class="my-login-validation" novalidate="" >
                    <div class="form-group">
                        <div>
                            <label for="old-password">Contraseña actual</label>
                            <input id="old-password" type="password" class="form-control" name="oldPassword" required autofocus data-eye>
                            <div class="invalid-feedback">
                                Contraseña requerida
                            </div>
                        </div>
                        <div>
                            <label for="passwd">Nueva contraseña</label>
                            <input id="passwd" type="password" class="form-control" name="passwd" required autofocus data-eye>
                            <div class="invalid-feedback">
                                Contraseña requerida
                            </div>
                        </div>
                        <div class="form-text text-muted">
                            Asegúrese que la nueva contraseña sea segura y fácil de recordar
                        </div>
                    </div>
                    <% request.setAttribute("id", request.getParameter("id"));%>
                    <div class="form-group m-0">
                        <button id="aceptar" type="submit" class="btn btn-primary btn-block">
                            Cambiar contraseña
                        </button>
                    </div>
                </form>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
            </div>

        </div>

        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/my-login.js"></script>
    </div>
</div>
