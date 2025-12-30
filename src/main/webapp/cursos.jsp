<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Curso" %>
<%@ page import="model.Estudiante" %>
<%@ page import="model.Docente" %>

<%
Estudiante usuario = (Estudiante) session.getAttribute("usuario");
if (usuario == null || !"ADMIN".equals(usuario.getRol())) {
    response.sendRedirect("login.jsp");
    return;
}

List<Curso> lista = (List<Curso>) request.getAttribute("lista");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cursos</title>
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

    <div class="container mt-4">

<h3>Gestión de Cursos</h3>

<div class="text-end mb-3">
   <a href="menu.jsp" class="btn btn-secondary">
       <i class="fa fa-arrow-left"></i> Regresar 
   </a>
</div>

<a href="Cursos?accion=nuevo" class="btn btn-primary mb-3">
    + Nuevo Curso
</a>

<table class="table table-bordered table-hover align-middle text-center">
<thead>
<tr>
<th>ID</th>
<th>Código</th>
<th>Nombre</th>
<th>Descripcion</th>
<th>Docente</th>
<th>Horario</th>
<th>Vacantes</th>
<th>Acción</th>
</tr>
</thead>

<tbody>
<% for(Curso c : lista){ %>
<tr>
<td><%=c.getId()%></td>
<td><%=c.getCodigo()%></td>
<td><%=c.getNombre()%></td>
<td><%=c.getDescripcion()%></td>
<td><%=c.getDocenteNombre()%></td>
<td><%=c.getHorario()%></td>
<td><%=c.getCupos()%></td>
<td>
<a href="Cursos?accion=editar&id=<%=c.getId()%>" 
   class="btn btn-warning btn-sm">
   Editar
</a>
<button class="btn btn-danger btn-sm"
        onclick="confirmarEliminar(<%=c.getId()%>)">
    Eliminar
</button>
</td>
</tr>
<% } %>
</tbody>

</table>
</div>

<script>
function confirmarEliminar(id) {
    Swal.fire({
        title: '¿Eliminar curso?',
        text: 'Esta acción eliminara el curso',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location = 'Cursos?accion=eliminar&id=' + id;
        }
    });
}
</script>

</body>
</html>