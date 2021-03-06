    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/cambiarPassword.jsp"/>
    </head>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#"><% out.print(session.getAttribute("rol")); %></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp"><span class="oi oi-home"></span> Inicio<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="oi oi-people"></span>
                    Usuarios
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios?op=nuevo">
                        <span class="oi oi-person"></span>  Registrar Usuarios</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios?op=listar">
                        <span class="oi oi-menu"></span>  Ver usuarios</a>    
                    <div class="dropdown-divider"></div>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="oi oi-hard-drive"></span> Departamentos
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/departamentos?op=nuevo">
                        <span class="oi oi-plus"></span> Nuevo departamento</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/departamentos?op=listar">
                        <span class="oi oi-grid-two-up"></span> Ver departamentos</a>    
                    <div class="dropdown-divider"></div>
                </div>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="oi oi-person" title="login" aria-hidden="true"></span> <% out.print(session.getAttribute("nombre"));%>
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" data-toggle="modal" data-target="#myModal">
                            <span class="oi oi-cog" ></span> Editar</a>
                    </li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cerrarSesion.jsp">
                            <span class="oi oi-account-logout"></span> Cerrar Sesi�n</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>

</nav>