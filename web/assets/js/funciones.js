function verificarEstadoRequest()
{
    var estado = document.getElementById("estadoPeticion");
    if (estado === "En desarrollo") 
    {
        event.preventDefault();
        alert("No se puede eliminar porque ya se esta desarollando");
    }
}

