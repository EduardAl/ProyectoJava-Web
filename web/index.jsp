<%-- 
    Document   : index
    Created on : 03-22-2019, 08:58:49 AM
    Author     : eduar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de sesión</title>
        <jsp:include page="/cabecera.jsp"/>
    </head>
    <body class="my-login-page" style="height: 100vh;
          background: linear-gradient(91deg, rgba(3,46,71,1) 0%,rgba(5,64,96,0.9) 35%, rgba(101,227,253,1) 100%);">
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-md-center h-100">
                    <div class="card-wrapper">
                        <div class="brand align-self-xl-center" style="width: 150px; height: 150px; margin-bottom: 10px" >
                            <img style="width: 170px; height: 170px; margin-left: -11px;" src="img/tesa.jpg" alt="logo">
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">Login</h4>
                                <form action="login" method="post" class="my-login-validation" novalidate="">
                                    <div class="form-group">
                                        <label for="email">Correo Electrónico</label>
                                        <input id="usuario" type="email" class="form-control" name="usuario" value="" required autofocus>
                                        <div class="invalid-feedback">
                                            Correo no válido
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="password">Password
                                            <a href="forgot.jsp" class="float-right">
                                                ¿Olvidó su contraseña?
                                            </a>
                                        </label>
                                        <input id="password" type="password" class="form-control" name="password" required data-eye>
                                        <div class="invalid-feedback">
                                            La contraseña es requerida
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <button class="btn btn-lg btn-primary btn-block" type="submit" name="btnIniciar">Iniciar sesión</button>
                                        <div class="invalid-feedback">
                                            La contraseña es requerida
                                        </div>
                                    </div>
                                    <div class="mt-4 text-center">
                                        ¿No tienes una cuenta?, <a href="register.html">¡Regístrate!</a>
                                    </div>
                                    <br>
                                    <div class="invalid-feedback d-block">
                                        <%
                                            HttpSession sesion = request.getSession(false);
                                            if (sesion.getAttribute("rol") != null && sesion.getAttribute("rol") != "null") {
                                                if (request.getAttribute("Error") != null && request.getAttribute("Error") != "null") {
                                                    out.print("<div class=\"alert alert-danger\">" + request.getAttribute("Error") + "<br></div>");
                                                } else {
                                                    response.sendRedirect("login");
                                                }//Findelif
                                            } else if (request.getAttribute("Error") != null && sesion.getAttribute("Error") != "null") {
                                                out.print("<div class=\"alert alert-danger\">" + request.getAttribute("Error") + "<br></div>");
                                            } else if (request.getAttribute("Success") != null && sesion.getAttribute("Success") != "null") {
                                                out.print("<div class=\"alert alert-success\">" + request.getAttribute("Success") + "<br></div>");
                                            }
                                        %>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="footer">
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
                            </script>
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
