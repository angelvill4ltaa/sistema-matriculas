<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Estudiante" %>
<%@ page import="model.Curso" %>

<%
Estudiante usuario = (Estudiante) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
}
boolean esAdmin = "ADMIN".equals(usuario.getRol());
%>
<!DOCTYPE html>
<html lang="es-PE">
<head>
<meta charset="UTF-8">
<title>Panel de Control</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/styles.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-custom shadow">
    <div class="container">
        <a class="navbar-brand" href="#">Sistema de matriculas</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarMenu">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="#" id="btnLogout"><i class="fa-solid fa-right-from-bracket"></i>Cerrar Sesión</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">

    <div class="welcome-text">
        <h3>
           Bienvenido, <%= usuario.getNombre() %> <%= usuario.getApellidos() %> 
</h3>
        
    </div>

    <div class="row g-4">

    <% if (esAdmin) { %>
    <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="dashboard-card" onclick="location.href='Estudiantes'">
            <i class="fa-solid fa-user-graduate"></i>
            <h5>Estudiantes</h5>
        </div>
    </div>

    <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="dashboard-card" onclick="location.href='Cursos'">
            <i class="fa-solid fa-book"></i>
            <h5>Cursos</h5>
        </div>
    </div>

    <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="dashboard-card" onclick="location.href='Matriculas'">
            <i class="fa-solid fa-clipboard-list"></i>
            <h5>Matrículas</h5>
        </div>
    </div>

    <div class="col-lg-3 col-md-6 col-sm-6">
        <div class="dashboard-card" onclick="location.href='Reportes'">
            <i class="fa-solid fa-chart-column"></i>
            <h5>Reportes</h5>
        </div>
    </div>
    <% } %>

    <% if (!esAdmin) { %>
    <div class="col-lg-3">
    <div class="dashboard-card" onclick="location.href='Matriculas'">
        <i class="fa-solid fa-clipboard-list"></i>
        <h5>Mis Matrículas</h5>
    </div>
   </div>
   <% } %>

</div> 
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>

<script>
document.getElementById('btnLogout').addEventListener('click', function (e) {
    e.preventDefault();

    Swal.fire({
        title: '¿Cerrar sesión?',
        text: 'Se cerrará tu sesión actual',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Salir',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#d33'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = 'Logout';
        }
    });
});
</script>

</body>
</html>