<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="author" content="Kodinger">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Reset Password</title>
        <jsp:include page="/cabecera.jsp"/>

    </head>
    <body class="my-login-page">
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-md-center align-items-center h-100">
                    <div class="card-wrapper">
                        <div class="brand align-self-xl-center" style="width: 150px; height: 150px; margin-bottom: 10px" >
                            <img style="width: 170px; height: 170px; margin-left: -11px;" src="img/tesa.jpg" alt="logo">
                        </div>
                        <div class="card fat">
                            <div class="card-body">
                                <h4 class="card-title">Recuperación de contraseña</h4>
                                <form action="ResetPassword?op=change" method="post" class="my-login-validation" novalidate="">
                                    <div class="form-group">
                                        <label for="new-password">Nueva contraseña</label>
                                        <input id="passwd" type="password" class="form-control" name="passwd" required autofocus data-eye>
                                        <div class="invalid-feedback">
                                            Contraseña requerida
                                        </div>
                                        <div class="invalid-feedback d-block">
                                        </div>
                                        <div class="form-text text-muted">
                                            Asegúrese que la nueva contraseña sea segura y fácil de recordar
                                        </div>
                                        
                                    </div>
                                    <% session.setAttribute("id", request.getParameter("id"));%>
                                    <div class="form-group m-0">
                                        <button type="submit" class="btn btn-primary btn-block">
                                            Cambiar contraseña
                                        </button>
                                    </div>
                                </form>
                                            <%
                                                if (request.getAttribute("Error") != null && request.getAttribute("Error") != "null") {
                                                    out.print("<div class=\"alert alert-danger\">" + request.getAttribute("Error") + "<br></div>");
                                                }
                                            %>
                            </div>
                        </div>
                        <div class="footer">
                            Copyright &copy; 2019 &mdash; Telecomunicaciones Salvadoreñas 
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/my-login.js"></script>
    </body>
</html>