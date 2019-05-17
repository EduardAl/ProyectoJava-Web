<%-- 
Document   : index
Created on : 03-22-2019, 08:58:49 AM
Author     : eduar
--%>
<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recuperación de cuenta</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body class="my-login-page">
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-md-center align-items-center h-100">
                    <div class="card-wrapper">
                        <div class="brand align-self-xl-center" style="width: 150px; height: 150px; margin-bottom: 10px" >
                            <img style="width: 170px; height: 170px; margin-left: -11px" src="img/tesa.jpg" alt="logo">
                        </div>
                        <div class="card fat">
                            <div class="card-body">
                                <h4 class="card-title">Recuperación de cuenta</h4>
                                <form action="ResetPassword?op=email" method="post" class="my-login-validation" novalidate="">
                                    <div class="form-group">
                                        <label for="email">Correo electrónico</label>
                                        <input id="email" type="email" class="form-control" name="email" value="" required autofocus>
                                        <div class="invalid-feedback">
                                            Dirección de correo requerido.
                                        </div>
                                        <div class="invalid-feedback d-block">
                                            <%
                                                if (request.getAttribute("Error") != null && request.getAttribute("Error") != "null") {
                                                    out.print("<div class=\"alert alert-danger\">" + request.getAttribute("Error") + "<br></div>");
                                                } else {
                                                    out.print("<div class=\"alert alert-success\">" + request.getAttribute("Mensaje") + "<br></div>");
                                                }
                                            %>
                                        </div>
                                        <div class="form-text text-muted">
                                            Al dar clic en "Enviar mail" enviaremos un link de recuperación a su correo electrónico.
                                        </div>
                                    </div>

                                    <div class="form-group m-0">
                                        <button type="submit" class="btn btn-primary btn-block">
                                            Enviar mail
                                        </button>
                                    </div>
                                </form>
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