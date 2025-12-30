<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("usuario") != null) {
        response.sendRedirect("menu.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es-PE">
<head>
<meta charset="UTF-8">
<title>Iniciar Sesión</title>
<link rel="stylesheet" href="css/login.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
</head>
<body>
<div class="login-wrapper">
<div class="login-card">

    <h2>Iniciar Sesión</h2>

    <% if (request.getAttribute("mensaje") != null) { %>
        <div id="alertServlet" class="alert alert-danger text-center alert-message show" role="alert">
            <%= request.getAttribute("mensaje") %>
        </div>
    <% } %>

    <div id="alertJS" class="alert alert-warning text-center alert-message">
        Rellena todos los campos correctamente
    </div>

    <form id="loginForm" action="Login" method="post" onsubmit="return validarCampos();">
        <div class="form-floating">
            <input type="email" id="correo" name="txtUsuario" class="form-control" placeholder="Correo" required>
            <label for="correo"><i class="fa-solid fa-envelope me-2"></i>Correo</label>
        </div>

        <div class="form-floating">
            <input type="password" id="clave" name="txtClave" class="form-control" placeholder="Contraseña" required>
            <label for="clave"><i class="fa-solid fa-lock me-2"></i>Contraseña</label>
            <i class="fa-solid fa-eye toggle-password" id="togglePassword"></i>
        </div>

        <button type="submit" class="btn btn-login w-100 mb-3">Ingresar</button>

        <div class="options">
            <a href="#" onclick="olvido(); return false;">Olvidé mi contraseña</a>
        </div>
    </form>
</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    const alertaJS = document.getElementById('alertJS');
    const alertaServlet = document.getElementById('alertServlet');
    const togglePassword = document.getElementById('togglePassword');
    const passwordField = document.getElementById('clave');

    if(alertaServlet) {
        setTimeout(() => {
            alertaServlet.classList.remove('show');
        }, 4000);
    }

    togglePassword.addEventListener('click', () => {
        const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordField.setAttribute('type', type);
        togglePassword.classList.toggle('fa-eye-slash');
    });

    function validarCampos() {
        const correo = document.getElementById('correo').value.trim();
        const clave = document.getElementById('clave').value.trim();

        if (correo === "" || clave === "") {
            alertaJS.innerText = "Rellena todos los campos";
            alertaJS.classList.add('show');
            setTimeout(() => alertaJS.classList.remove('show'), 3000);
            return false; 
        }

        if (!correo.match(/^\S+@\S+\.\S+$/)) {
            alertaJS.innerText = "Ingresa un correo válido";
            alertaJS.classList.add('show');
            setTimeout(() => alertaJS.classList.remove('show'), 3000);
            return false;
        }

        alertaJS.classList.remove('show');
        return true;
    }

    function olvido() {
        Swal.fire({
            icon: 'info',
            title: 'Recuperar contraseña',
            text: 'Te enviaremos un código a tu correo para recuperar la contraseña.'
        });
    }
</script>
</body>
</html>
