<%@page import="model.Pago"%>
<%@page import="model.MetodoPago"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%@ page import="model.Matricula" %>
<%@ page import="model.Estudiante" %>
<%@ page import="model.Curso" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
Estudiante usuarioSesion = (Estudiante) session.getAttribute("usuario");
if (usuarioSesion == null) {
    response.sendRedirect("login.jsp");
    return;
}
boolean esAdmin = "ADMIN".equals(usuarioSesion.getRol());
%>

<%
List<Curso> cursos = (List<Curso>) request.getAttribute("cursos");
List<Matricula> lista = (List<Matricula>) request.getAttribute("lista");
List<Estudiante> estudiantes = (List<Estudiante>) request.getAttribute("estudiantes");
List<MetodoPago> metodos = (List<MetodoPago>) request.getAttribute("metodos");
Map<Integer, List<Pago>> pagosPorMatricula =
    (Map<Integer, List<Pago>>) request.getAttribute("pagosPorMatricula");
String msg = request.getParameter("msg");
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Matrículas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-custom">
  <div class="container">
      <a class="navbar-brand" href="menu.jsp">Sistema Matrículas</a>
  </div>
</nav>

<div class="container mt-4">
<h3><%= esAdmin ? "Gestión de Matrículas" : "Mis Matrículas" %></h3>

<div class="text-end mb-3">
   <a href="menu.jsp" class="btn btn-secondary">
       <i class="fa fa-arrow-left"></i> Regresar 
   </a>
</div>

<% if (esAdmin) { %>
<form action="Matriculas" method="post" class="row g-3 align-items-end mb-4">
    <input type="hidden" name="tipo" value="matricula">

    <div class="col-md-4">
        <select name="usuario_id" class="form-select" required>
            <option value="">Seleccione Estudiante</option>
            <% for(Estudiante e : estudiantes){ %>
                <option value="<%= e.getId() %>">
                    <%= e.getNombre() %> <%= e.getApellidos() %>
                </option>
            <% } %>
        </select>
    </div>

    <div class="col-md-4">
        <select name="curso_id" class="form-select" required>
            <option value="">Seleccione Curso</option>
            <% for(Curso c : cursos){ %>
                <option value="<%= c.getId() %>"><%= c.getNombre() %></option>
            <% } %>
        </select>
    </div>

    <div class="col-md-2">
        <button class="btn btn-primary w-100">Matricular</button>
    </div>
</form>
<% } %>

<table class="table table-bordered align-middle">
<thead class="table-light">
<tr>
<th>ID</th>
<th>Estudiante</th>
<th>Curso</th>
<th>Fecha</th>
<th>Estado</th>
<% if (esAdmin) { %>
<th class="text-center">Acción</th>
<th class="text-center">Pago</th>
<% } %>
</tr>
</thead>

<tbody>
<% for(Matricula m : lista){ 
   List<Pago> pagos = pagosPorMatricula.get(m.getId());
   
   boolean estaPagado = false;

if (pagos != null) {
    for (Pago p : pagos) {
        if ("Pagado".equalsIgnoreCase(p.getEstado())) {
            estaPagado = true;
            break;
        }
    }
}
%>

<tr>
<td><%= m.getId() %></td>
<td><%= m.getEstudiante() %></td>
<td><%= m.getCurso() %></td>
<td><%= sdf.format(m.getFecha()) %></td>
<td><%= m.getEstado() %></td>
<% if (esAdmin) { %>
<td class="text-center">
    <% if (!"Anulada".equals(m.getEstado())) { %>
        <a href="Matriculas?accion=anular&id=<%= m.getId() %>"
           class="btn btn-sm btn-danger me-1 btn-anular">
           Anular
        </a>
    <% } %>

    <% if (!"Anulada".equals(m.getEstado()) && !estaPagado) { %>
        <button class="btn btn-sm btn-success me-1"
                data-bs-toggle="modal"
                data-bs-target="#modalPago<%= m.getId() %>">
            Pagar
        </button>
    <% } %>

    <% if (estaPagado) { %>
        <button class="btn btn-sm btn-secondary"
                data-bs-toggle="collapse"
                data-bs-target="#pagos<%= m.getId() %>">
            Ver 
        </button>
    <% } %>
</td>
<% } %>

<td class="text-center">
    <% if ("Anulada".equals(m.getEstado())) { %>
        <span class="badge bg-secondary fs-6 px-1 py-1">Anulada</span>
    <% } else if (estaPagado) { %>
        <span class="badge bg-success fs-6 px-1 py-1">Pagado</span>
    <% } else { %>
        <span class="badge bg-warning fs-6 px-1 py-1">Pendiente</span>
    <% } %>
</td>
</tr>
<% if (esAdmin) { %>
<tr class="collapse bg-light" id="pagos<%= m.getId() %>">
<td colspan="7">
<table class="table table-sm table-bordered mb-0">
<thead class="table-secondary">
<tr>
<th>ID</th>
<th>Monto</th>
<th>Método</th>
<th>Referencia</th>
<th>Fecha</th>
</tr>
</thead>
<tbody>
<% if (pagos != null) {
   for(Pago p : pagos){
       if (!"Pagado".equalsIgnoreCase(p.getEstado())) continue;
%>
<tr>
<td><%= p.getId() %></td>
<td><%= p.getMonto() %></td>
<td><%= p.getNombreMetodo() %></td>
<td><%= p.getReferencia() %></td>
<td>
<%= (p.getFechaPago() != null) ? sdf.format(p.getFechaPago()) : "—" %>
</td>

</tr>
<% }} %>
</tbody>
</table>
</td>
</tr>
<% } %>
<% } %>
</tbody>
</table>
<% if (esAdmin) { %>
<% for(Matricula m : lista){ %>
<div class="modal fade" id="modalPago<%= m.getId() %>" tabindex="-1">
  <div class="modal-dialog">
    <form class="modal-content" action="Matriculas" method="post">
      <input type="hidden" name="tipo" value="pago">
      <input type="hidden" name="matriculaId" value="<%= m.getId() %>">

      <div class="modal-header">
        <h5 class="modal-title">Registrar pago – <%= m.getCurso() %></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>

      <div class="modal-body">
        <input type="number" name="monto" step="0.01"
               class="form-control mb-2" placeholder="Monto" required>

        <select name="metodoId" class="form-select mb-2" required>
          <option value="">Método de pago</option>
          <% for (MetodoPago mp : metodos) { %>
            <option value="<%= mp.getId() %>"><%= mp.getNombre() %></option>
          <% } %>
        </select>

        <input type="text" name="referencia"
               class="form-control" placeholder="Referencia">
      </div>

      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
            Cancelar
        </button>
        <button class="btn btn-success">Registrar pago</button>
      </div>
    </form>
  </div>
</div>
<% } %>
<% } %>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.min.js"></script>

<script>
document.querySelectorAll('.btn-anular').forEach(btn => {
    btn.addEventListener('click', e => {
        e.preventDefault();
        const url = btn.href;
        Swal.fire({
            title: '¿Está seguro?',
            text: 'Se anulará la matrícula seleccionada',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: 'Sí, anular'
        }).then(r => {
            if (r.isConfirmed) location.href = url;
        });
    });
});
</script>

<script>
<% if ("ok".equals(msg)) { %>
Swal.fire('Éxito','Matrícula registrada','success');
<% } %>
<% if ("dup".equals(msg)) { %>
Swal.fire('Atención','El estudiante ya está matriculado','warning');
<% } %>
<% if ("anulado".equals(msg)) { %>
Swal.fire('OK','Matrícula anulada','success');
<% } %>
<% if ("pagado".equals(msg)) { %>
Swal.fire('Éxito','Pago registrado','success');
<% } %>
<% if ("error_pago".equals(msg)) { %>
Swal.fire('Error','No se pudo registrar el pago','error');
<% } %>
<% if ("ya_pagado".equals(msg)) { %>
Swal.fire('Atención','Esta matrícula ya tiene un pago registrado','warning');
<% } %>
<% if ("sin_cupos".equals(msg)) { %>
Swal.fire('Sin cupos','Este curso ya no tiene vacantes','error');
<% } %>
</script>

</body>
</html>
