<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Jefe de desarrollo</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/Area/Desarrollo/Empleado/inicio.jsp"><span class="oi oi-home"></span> Inicio<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="oi oi-people"></span>
                    Peticiones
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/case?op=listarRequests">
                        <span class="oi oi-person"></span>Ver Peticiones</a>
                    <div class="dropdown-divider"></div>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="oi oi-hard-drive"></span> Casos
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/bitacoras?op=listarCasos">
                        <span class="oi oi-plus"></span> Ver Casos</a> 
                    <div class="dropdown-divider"></div>
                </div>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="oi oi-person" title="login" aria-hidden="true"></span> Mi Cuenta
                </a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="#">
                            <span class="oi oi-cog"></span> Editar</a>
                    </li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cerrarSesion.jsp">
                            <span class="oi oi-account-logout"></span> Cerrar Sesión</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
