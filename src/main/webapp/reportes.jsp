<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reportes</title>
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
    <h3>Reportes</h3>

    <div class="text-end mb-3">
        <a href="menu.jsp" class="btn btn-secondary">
            <i class="fa fa-arrow-left"></i> Regresar
        </a>
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

</body>
</html>
