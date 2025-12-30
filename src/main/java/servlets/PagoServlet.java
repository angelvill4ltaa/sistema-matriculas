package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MetodoPagoDAO;
import dao.PagoDAO;
import model.Pago;
import model.MetodoPago;

@WebServlet("/PagoServlet")
public class PagoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private PagoDAO pagoDAO = new PagoDAO();
    private MetodoPagoDAO metodoDAO = new MetodoPagoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	 List<MetodoPago> metodos = metodoDAO.listar();
         request.setAttribute("metodos", metodos);

         List<Pago> pagos = pagoDAO.listar();
         request.setAttribute("pagos", pagos);
    	
        request.getRequestDispatcher("pago.jsp").forward(request, response);
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		 try {
	            int matriculaId = Integer.parseInt(request.getParameter("matriculaId"));
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

        boolean ok = pagoDAO.registrar(p);

        if(ok) {
            response.sendRedirect("PagoServlet?msg=ok");
        } else {
            response.sendRedirect("PagoServlet?msg=error");
        }

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("PagoServlet?msg=error");
    }
}
}
