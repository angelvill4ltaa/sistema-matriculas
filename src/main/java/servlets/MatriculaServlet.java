package servlets;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CursoDAO;
import dao.MatriculaDAO;
import dao.MetodoPagoDAO;
import dao.PagoDAO;
import dao.EstudianteDAO;
import model.Estudiante;
import model.Matricula;
import model.Pago;

@WebServlet("/Matriculas")
public class MatriculaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MatriculaDAO matriculaDAO = new MatriculaDAO();
    private CursoDAO cursoDAO = new CursoDAO();
    private EstudianteDAO estudianteDAO = new EstudianteDAO();
    private MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
    private PagoDAO pagoDAO = new PagoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Estudiante usuario = (Estudiante) sesion.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String rol = usuario.getRol();
        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {

            List<Matricula> lista;

            if ("ADMIN".equals(rol)) {
                lista = matriculaDAO.listar();
            } else {
                lista = matriculaDAO.listarPorEstudiante(usuario.getId());
            }

            Map<Integer, List<Pago>> pagosPorMatricula = new HashMap<>();

            for (Matricula m : lista) {
                pagosPorMatricula.put(
                    m.getId(),
                    pagoDAO.listarPorMatricula(m.getId())
                );
            }

            request.setAttribute("lista", lista);
            request.setAttribute("pagosPorMatricula", pagosPorMatricula);

            if ("ADMIN".equals(rol)) {
                request.setAttribute("cursos", cursoDAO.listar());
                request.setAttribute("estudiantes", estudianteDAO.listar());
                request.setAttribute("metodos", metodoPagoDAO.listar());
            }

            request.getRequestDispatcher("matriculas.jsp").forward(request, response);
            return;
        }

        if ("anular".equals(accion)) {

            if (!"ADMIN".equals(rol)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            int id = Integer.parseInt(request.getParameter("id"));
            Matricula m = matriculaDAO.obtenerPorId(id);

            matriculaDAO.anular(id);
            pagoDAO.anularPorMatricula(id);
            cursoDAO.sumarCupo(m.getCursoId());

            response.sendRedirect("Matriculas?msg=anulado");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession sesion = request.getSession();
        Estudiante usuario = (Estudiante) sesion.getAttribute("usuario");

        if (usuario == null || !"ADMIN".equals(usuario.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String tipo = request.getParameter("tipo");
    
        if ("pago".equals(tipo)) {
            try {
                int matriculaId = Integer.parseInt(request.getParameter("matriculaId"));

                List<Pago> pagos = pagoDAO.listarPorMatricula(matriculaId);
                if (pagos != null && !pagos.isEmpty()) {
                    response.sendRedirect("Matriculas?msg=ya_pagado");
                    return;
                }

                double monto = Double.parseDouble(request.getParameter("monto"));
                int metodoId = Integer.parseInt(request.getParameter("metodoId"));
                String referencia = request.getParameter("referencia");

                Pago p = new Pago();
                p.setMatriculaId(matriculaId);
                p.setMonto(monto);
                p.setMetodoId(metodoId);
                p.setReferencia(referencia);
                p.setEstado("Pagado"); 
                p.setFechaPago(new java.util.Date());

                if (pagoDAO.registrar(p)) {
                    response.sendRedirect("Matriculas?msg=pagado");
                } else {
                    response.sendRedirect("Matriculas?msg=error_pago");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("Matriculas?msg=error_pago");
            }
            return;
        }

        int usuarioId = Integer.parseInt(request.getParameter("usuario_id"));
        int cursoId = Integer.parseInt(request.getParameter("curso_id"));

        if (!cursoDAO.tieneCupos(cursoId)) {
            response.sendRedirect("Matriculas?msg=sin_cupos");
            return;
        }

        Matricula m = new Matricula(usuarioId, cursoId);
        boolean ok = matriculaDAO.registrar(m);

        if (ok) {
            cursoDAO.restarCupo(cursoId);
            response.sendRedirect("Matriculas?msg=ok");
        } else {
            response.sendRedirect("Matriculas?msg=dup");
        }
    }
}
