<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Curso" %>
<%@ page import="model.Docente" %>
<%@ page import="java.util.List" %>

<%
Curso curso = (Curso) request.getAttribute("curso");
List<Docente> docentes = (List<Docente>) request.getAttribute("docentes");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Curso</title>
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

<h3>Editar Curso</h3>

<form action="Cursos" method="post" class="row g-3">

<input type="hidden" name="accion" value="actualizar">
<input type="hidden" name="id" value="<%=curso.getId()%>">

<div class="col-md-6">
    <label>Nombre</label>
    <input name="nombre" class="form-control"
           value="<%=curso.getNombre()%>" required>
</div>

<div class="col-md-6">
    <label>Horario</label>
    <input name="horario" class="form-control"
           value="<%=curso.getHorario()%>">
</div>

<div class="col-md-12">
    <label>Descripción</label>
    <textarea name="descripcion" class="form-control"><%=curso.getDescripcion()%></textarea>
</div>

<div class="col-md-6">
    <label>Docente</label>
    <select name="docente_id" class="form-select" required>
        <% for(Docente d : docentes){ %>
            <option value="<%=d.getId()%>"
                <%= d.getId() == curso.getDocenteId() ? "selected" : "" %>>
                <%= d.getNombres() + " " + d.getApellidos() %>
            </option>
        <% } %>
    </select>
</div>

<div class="col-md-6">
    <label>Vacantes</label>
    <input type="number" name="cupos" class="form-control"
           value="<%=curso.getCupos()%>" required>
</div>

<div class="col-12">
    <button class="btn btn-success">Actualizar</button>
    <a href="Cursos" class="btn btn-secondary">Cancelar</a>
</div>

</form>

</div>
</body>
</html>