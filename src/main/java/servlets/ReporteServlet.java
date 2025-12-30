package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReporteDAO;

@WebServlet("/Reportes")
public class ReporteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ReporteDAO reporteDAO = new ReporteDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("alumnosCurso", reporteDAO.alumnosPorCurso());
        request.setAttribute("ingresosCurso", reporteDAO.ingresosPorCurso());
        
        request.setAttribute("totalMatriculas", reporteDAO.totalMatriculas());
        request.setAttribute("totalIngresos", reporteDAO.totalIngresos());

        request.getRequestDispatcher("reportes.jsp").forward(request, response);
	 }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doGet(request, response);
	}

}
