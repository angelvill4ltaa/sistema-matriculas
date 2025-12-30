<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Docente" %>

<%
List<Docente> docentes = (List<Docente>) request.getAttribute("docentes");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Curso</title>
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
 <h3>Registrar Curso</h3>

<form action="Cursos" method="post" class="row g-3">

<input type="hidden" name="accion" value="registrar">


    <div class="col-md-6">
        <input name="nombre" class="form-control" placeholder="Nombre" required>
    </div>

    <div class="col-md-6">
        <input name="horario" class="form-control" placeholder="Horario">
    </div>

    <div class="col-md-12">
        <textarea name="descripcion" class="form-control" placeholder="Descripción"></textarea>
    </div>

    <div class="col-md-6">
        <select name="docente_id" class="form-control" required>
            <option value="">Seleccione Docente</option>
            <% for(Docente d : docentes){ %>
                <option value="<%= d.getId() %>">
                    <%= d.getNombres() %> <%= d.getApellidos() %>
                </option>
            <% } %>
        </select>
    </div>

    <div class="col-md-6">
        <input type="number" name="cupos" class="form-control" placeholder="Cupos" required>
    </div>

    <div class="col-md-12">
        <button class="btn btn-success">Guardar</button>
        <a href="Cursos" class="btn btn-secondary">Cancelar</a>
    </div>
</form>

</div>
</body>
</html>