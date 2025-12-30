package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import model.Estudiante;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if (
            uri.endsWith("login.jsp") ||
            uri.endsWith("Login") ||
            uri.endsWith("Logout") ||
            uri.contains("/css/") ||
            uri.contains("/js/") ||
            uri.contains("/img/") ||
            uri.contains("/bootstrap")
        ) {
            chain.doFilter(request, response);
            return;
        }

        Estudiante usuario = null;
        if (session != null) {
            usuario = (Estudiante) session.getAttribute("usuario");
        }

        if (usuario == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        boolean esAdmin = "ADMIN".equals(usuario.getRol());

        if (
            uri.contains("Cursos") ||
            uri.contains("Estudiantes") ||
            uri.contains("Reportes")
        ) {
            if (!esAdmin) {
                res.sendRedirect(req.getContextPath() + "/menu.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
