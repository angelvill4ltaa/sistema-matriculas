package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Estudiante;
import dao.EstudianteDAO;


@WebServlet("/Estudiantes")
public class EstudianteServlet extends HttpServlet {

    EstudianteDAO dao = new EstudianteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {

            List<Estudiante> lista = dao.listar();
            request.setAttribute("lista", lista);

            request.getRequestDispatcher("estudiantes.jsp")
                   .forward(request, response);
            return;
        }

        else if (accion.equals("eliminar")) {

            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("Estudiantes");

        } else if (accion.equals("editar")) {

            int id = Integer.parseInt(request.getParameter("id"));
            Estudiante u = dao.obtenerPorId(id);

            request.setAttribute("estudiante", u);

            List<Estudiante> lista = dao.listar();
            request.setAttribute("lista", lista);

            request.getRequestDispatcher("estudiantes.jsp")
                   .forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {

            Estudiante u = new Estudiante(
                0,
                request.getParameter("nombre"),
                request.getParameter("apellidos"),
                request.getParameter("correo"),
                request.getParameter("clave")
            );

            dao.registrar(u);
            
            response.sendRedirect("Estudiantes?msg=registrado");
            return;
        }

        else if ("actualizar".equals(accion)) {

            Estudiante u = new Estudiante(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("nombre"),
                request.getParameter("apellidos"),
                request.getParameter("correo"),
                ""
            );

            dao.actualizar(u);
        }

        response.sendRedirect("Estudiantes");
    }
}
