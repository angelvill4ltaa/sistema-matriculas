<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Estudiante" %>

<%
Estudiante usuario = (Estudiante) session.getAttribute("usuario");

if (usuario == null || usuario.getRol() == null || !usuario.getRol().equals("ADMIN")) {
    response.sendRedirect("login.jsp");
    return;
}

List<Estudiante> lista = (List<Estudiante>) request.getAttribute("lista");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Estudiantes</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-custom">
  <div class="container">
      <a class="navbar-brand" href="menu.jsp">Sistema Matrículas</a>
  </div>
</nav>

<div class="container">

<h2 class="mb-4">Gestión de Estudiantes</h2>

<div class="text-end mb-3">
   <a href="menu.jsp" class="btn btn-secondary">
       <i class="fa fa-arrow-left"></i> Regresar 
   </a>
</div>

<input type="text"
       id="buscador"
       class="form-control mb-3"
       placeholder="Buscar estudiante por nombre, apellido o correo.">
       
 <%
String msg = request.getParameter("msg");
if ("registrado".equals(msg)) {
%>
<div class="alert alert-success">
    Estudiante registrado correctamente
</div>
<%
}
%>
    
<script>
function confirmarEliminar(id) {

    Swal.fire({
        title: '¿Eliminar estudiante?',
        text: 'Esta acción no se puede deshacer',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'

    }).then((result) => {

        if(result.isConfirmed) {
            window.location.href = "Estudiantes?accion=eliminar&id=" + id;
        }

    });

}
</script>
       
<%
Estudiante est = (Estudiante) request.getAttribute("estudiante");
boolean editando = est != null;
%>

<form action="Estudiantes" method="post" class="row g-3 mb-4">

    <input type="hidden" name="accion" value="<%= editando ? "actualizar" : "registrar" %>">

    <% if(editando){ %>
        <input type="hidden" name="id" value="<%= est.getId() %>">
    <% } %>

    <div class="col-md-3">
        <input class="form-control"
               type="text"
               name="nombre"
               placeholder="Nombre"
               required
               value="<%= editando ? est.getNombre() : "" %>">
    </div>

    <div class="col-md-3">
        <input class="form-control"
               type="text"
               name="apellidos"
               placeholder="Apellidos"
               required
               value="<%= editando ? est.getApellidos() : "" %>">
    </div>

    <div class="col-md-3">
        <input class="form-control"
               type="email"
               name="correo"
               placeholder="Correo"
               required
               value="<%= editando ? est.getCorreo() : "" %>">
    </div>

    <% if(!editando){ %>
    <div class="col-md-2">
        <input class="form-control"
               type="password"
               name="clave"
               placeholder="Clave"
               required>
    </div>
    <% } %>

    <div class="col-md-1 d-grid">
        <button class="btn <%= editando ? "btn-warning" : "btn-primary" %>">
            <%= editando ? "Actualizar" : "Registrar" %>
        </button>
    </div>
</form>

<table class="table table-bordered table-hover align-middle">

<thead class="table-light">
<tr>
    <th>ID</th>
    <th>Nombres</th>
    <th>Apellidos</th>
    <th>Correo</th>
    <th class="text-center">Acciones</th>
</tr>
</thead>

<tbody>

<%
if(lista != null){
   for(Estudiante u : lista){
%>

<tr>
    <td><%=u.getId()%></td>
    <td><%=u.getNombre()%></td>
    <td><%=u.getApellidos()%></td>
    <td><%=u.getCorreo()%></td>
    <td class="text-center">
        <a class="btn btn-sm btn-warning"
       href="Estudiantes?accion=editar&id=<%=u.getId()%>">
       Editar
        </a>        
        <a class="btn btn-sm btn-danger"
           onclick="confirmarEliminar(<%=u.getId()%>)">
           <i class="fa fa-trash"></i>
        </a>
    </td>
</tr>

<%
   }
}
%>

</tbody>
</table>

</div>

<script>
document.getElementById("buscador").addEventListener("keyup", function () {

    let filtro = this.value.toLowerCase();
    let filas = document.querySelectorAll("tbody tr");

    filas.forEach(fila => {

        let texto = fila.textContent.toLowerCase();

        fila.style.display = texto.includes(filtro) ? "" : "none";

    });

});
</script>

</body>
</html>
