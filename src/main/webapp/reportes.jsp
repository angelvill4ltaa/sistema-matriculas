<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reportes</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<style>
.card { border-radius: 12px; }
</style>
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-custom">
  <div class="container">
      <a class="navbar-brand" href="menu.jsp">Sistema Matrículas</a>
  </div>
</nav>

<div class="container mt-4">

    <h3>Reportes</h3>

    <div class="text-end mb-3">
        <a href="menu.jsp" class="btn btn-secondary">
            <i class="fa fa-arrow-left"></i> Regresar
        </a>
    </div>

    <div class="row text-center mb-4">
        <div class="col-md-6 col-lg-3 mb-2">
            <div class="card shadow p-3">
                <h6>Matrículas Totales</h6>
                <h4>${totalMatriculas}</h4>
            </div>
        </div>
        <div class="col-md-6 col-lg-3 mb-2">
            <div class="card shadow p-3">
                <h6>Ingresos Totales</h6>
                <h4>S/ ${totalIngresos}</h4>
            </div>
        </div>
    </div>

    <div class="row mb-4">
        <div class="col-md-6">
            <div class="card shadow p-3">
                <h5>Alumnos por Curso</h5>
                <canvas id="graficoAlumnos"></canvas>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card shadow p-3">
                <h5>Ingresos por Curso</h5>
                <canvas id="graficoIngresos"></canvas>
            </div>
        </div>
    </div>

    <h5>Alumnos por curso</h5>
    <table class="table table-bordered">
        <tr>
            <th>Curso</th>
            <th>Total</th>
        </tr>

        <c:forEach var="r" items="${alumnosCurso}">
            <tr>
                <td>${r[0]}</td>
                <td>${r[1]}</td>
            </tr>
        </c:forEach>

        <tr class="table-secondary fw-bold">
            <td>Total de matrículas</td>
            <td>${totalMatriculas}</td>
        </tr>
    </table>

    <h5>Ingresos por curso</h5>
    <table class="table table-bordered">
        <tr>
            <th>Curso</th>
            <th>Total</th>
        </tr>

        <c:forEach var="r" items="${ingresosCurso}">
            <tr>
                <td>${r[0]}</td>
                <td>S/. ${r[1]}</td>
            </tr>
        </c:forEach>

        <tr class="table-secondary fw-bold">
            <td>Total de ingresos</td>
            <td>S/. ${totalIngresos}</td>
        </tr>
    </table>

</div>

<script>
const cursos = [
<c:forEach var="r" items="${alumnosCurso}" varStatus="s">
"${r[0]}"${!s.last ? "," : ""}
</c:forEach>
];

const alumnos = [
<c:forEach var="r" items="${alumnosCurso}" varStatus="s">
${r[1]}${!s.last ? "," : ""}
</c:forEach>
];

const ingresos = [
<c:forEach var="r" items="${ingresosCurso}" varStatus="s">
${r[1]}${!s.last ? "," : ""}
</c:forEach>
];

new Chart(document.getElementById('graficoAlumnos'), {
    type: 'bar',
    data: {
        labels: cursos,
        datasets: [{
            label: 'Cantidad de Alumnos',
            data: alumnos
        }]
    }
});

new Chart(document.getElementById('graficoIngresos'), {
    type: 'bar',
    data: {
        labels: cursos,
        datasets: [{
            label: 'Ingresos (S/.)',
            data: ingresos
        }]
    }
});
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.min.js"></script>
</body>
</html>

