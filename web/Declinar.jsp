<%-- 
    Document   : Declinar
    Created on : 05-04-2019, 03:31:09 PM
    Author     : Reyes Alexander
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/cabecera.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Declinar Request</title>
        <jsp:include page="/menu.jsp" />
    </head>
    <body>
        <div class="container-fluid">
            <div class="row offset-md-4">
                <h1>Declinar Request</h1>
            </div>
        </div>
        <div class="container" >
            <br>
            <div class="row offset-md-3">                
                    <form role="form" method="POST">
                        <div class="form-group">
                            <center><label for="caso">Caso</label></center>
                            <center><input name="caso" disabled="true" value="numero del caso" /></center>
                        </div>
                        <div class="form-group">
                            <center><label for="observacion">Observación</label></center>                              
                            <textarea rows="3" cols="60" class="form-control" name="observacion" id="observacion" required></textarea>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-asterisk"></span></span>
                        </div>
                        <center><button type="submit" class="btn btn-danger" value="denegar" name="denegar"><span class="oi oi-circle-x"></span> Denegar </button></center>
                    </form>                
            </div>
        </div>
    </body>
</html>
