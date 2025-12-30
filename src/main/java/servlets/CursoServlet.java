package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Curso;
import dao.CursoDAO;
import dao.DocenteDAO;

@WebServlet("/Cursos")
public class CursoServlet extends HttpServlet {

    private CursoDAO cursoDAO = new CursoDAO();
    private DocenteDAO docenteDAO = new DocenteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {
        	
            request.setAttribute("lista", cursoDAO.listar());
            request.getRequestDispatcher("cursos.jsp").forward(request, response);

        } else if (accion.equals("nuevo")) {

            request.setAttribute("docentes", docenteDAO.listar());
            request.getRequestDispatcher("cursoNuevo.jsp").forward(request, response);
            
        }    else if (accion.equals("eliminar")) {

                int id = Integer.parseInt(request.getParameter("id"));
                cursoDAO.eliminar(id);
                response.sendRedirect("Cursos");
                
        } else if ("editar".equals(accion)) {

            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("curso", cursoDAO.buscarPorId(id));
            request.setAttribute("docentes", docenteDAO.listar());
            request.getRequestDispatcher("cursoEditar.jsp").forward(request, response);
        }
            } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String accion = request.getParameter("accion");
    	
    	if ("registrar".equals(accion)) {
    	
        Curso c = new Curso(
            request.getParameter("nombre"),
            request.getParameter("descripcion"),
            Integer.parseInt(request.getParameter("docente_id")),
            request.getParameter("horario"),
            Integer.parseInt(request.getParameter("cupos"))
        );

        cursoDAO.registrar(c);

    	} else if ("actualizar".equals(accion)) {

            Curso c = new Curso(
                Integer.parseInt(request.getParameter("id")),
                null,
                request.getParameter("nombre"),
                request.getParameter("descripcion"),
                Integer.parseInt(request.getParameter("docente_id")),
                request.getParameter("horario"),
                Integer.parseInt(request.getParameter("cupos")),
                true
            );

            cursoDAO.actualizar(c);
        }

        response.sendRedirect("Cursos");
    }
}


