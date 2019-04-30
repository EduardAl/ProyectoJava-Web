<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Administrador</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Inicio<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Usuarios
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios?op=nuevo">
                        <span class="oi oi-info"></span>  Registrar Usuarios</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios?op=listar">
                        <span class="oi oi-menu"></span>  Ver usuarios</a>    
                    <div class="dropdown-divider"></div>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Departamentos
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/departamentos?op=nuevo">Registrar Usuarios</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/departamentos?op=listar">Ver usuarios</a>    
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