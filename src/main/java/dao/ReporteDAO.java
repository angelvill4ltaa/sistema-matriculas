package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

	public List<Object[]> alumnosPorCurso() {
	    List<Object[]> lista = new ArrayList<>();
	    String sql = """
            SELECT c.nombre, COUNT(m.id)
            FROM cursos c
            LEFT JOIN matriculas m ON c.id = m.curso_id
            GROUP BY c.id
            ORDER BY COUNT(m.id) DESC
        """;

	    try (Connection cn = Conexion.getConexion();
	         PreparedStatement ps = cn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            lista.add(new Object[]{
	                rs.getString(1),
	                rs.getInt(2)
	            });
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}


    public List<Object[]> ingresosPorCurso() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
        	    SELECT c.nombre, IFNULL(SUM(p.monto),0)
        	    FROM cursos c
        	    LEFT JOIN matriculas m ON c.id = m.curso_id
        	    LEFT JOIN pagos p 
        	        ON p.matricula_id = m.id
        	       AND p.estado = 'Pagado'
        	    GROUP BY c.id
        	""";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString(1),
                    rs.getDouble(2)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public int totalMatriculas() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM matriculas";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public double totalIngresos() {
        double total = 0;
        String sql = """
            SELECT IFNULL(SUM(monto),0)
            FROM pagos
            WHERE estado = 'Pagado'
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
        
}
