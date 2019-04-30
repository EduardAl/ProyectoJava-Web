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
        <title>Inicio de sesión</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    </head>
    <%

        if (request.getAttribute("Error") != null) {
    %>

    <body>
        <div class="alert alert-danger">
            <strong>Error!</strong><%=request.getAttribute("Error")%>
            <br>
        </div>
        <%
            }//Findelif
%>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-4 col-sm-offset-4">
                    <h2>Inicio de sesión</h2>

                    <form action="login" method="post">
                        <div class="form-group">
                            <label for="usuario">Usuario</label>
                            <input type="text" class="form-control" id="usuario" placeholder="Usuario" name="usuario" required>
                        </div>
                        <div class="form-group">
                            <label for="clave">Password</label>
                            <input type="password" class="form-control" id="clave" placeholder="Password" name="clave" required>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-lg btn-primary btn-block" type="submit" name="btnIniciar">Iniciar sesión</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
