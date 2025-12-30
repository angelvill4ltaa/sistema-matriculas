package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Pago;

public class PagoDAO {

	public boolean registrar(Pago p) {
        String sql = "INSERT INTO pagos (matricula_id, monto, metodo_id, estado, fecha_pago, referencia) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, p.getMatriculaId());
            ps.setDouble(2, p.getMonto());
            ps.setInt(3, p.getMetodoId());
            ps.setString(4, p.getEstado());
            ps.setTimestamp(5, new java.sql.Timestamp(p.getFechaPago().getTime()));
            ps.setString(6, p.getReferencia());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public List<Pago> listar() {

	    List<Pago> lista = new ArrayList<>();

	    String sql = """
	        SELECT p.*, mp.nombre AS metodo_nombre
	        FROM pagos p
	        JOIN matriculas m ON p.matricula_id = m.id
	        LEFT JOIN metodopago mp ON p.metodo_id = mp.id
	        WHERE p.estado = 'Pagado'
	          AND m.estado <> 'Anulada'
	        ORDER BY p.id ASC
	    """;

	    try (Connection cn = Conexion.getConexion();
	         PreparedStatement ps = cn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Pago p = new Pago(
	                rs.getInt("id"),
	                rs.getInt("matricula_id"),
	                rs.getDouble("monto"),
	                rs.getInt("metodo_id"),
	                rs.getString("estado"),
	                rs.getTimestamp("fecha_pago"),
	                rs.getString("referencia")
	            );
	            p.setNombreMetodo(rs.getString("metodo_nombre"));
	            lista.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}



    public List<Pago> listarPorMatricula(int matriculaId) {
        List<Pago> lista = new ArrayList<>();

        String sql = """
            SELECT p.*, m.nombre AS metodo_nombre
            FROM pagos p
            LEFT JOIN metodopago m ON p.metodo_id = m.id
            WHERE p.matricula_id = ?
              AND p.estado <> 'Anulado'
            ORDER BY p.id ASC
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, matriculaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pago p = new Pago(
                    rs.getInt("id"),
                    rs.getInt("matricula_id"),
                    rs.getDouble("monto"),
                    rs.getInt("metodo_id"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_pago"),
                    rs.getString("referencia")
                );
                p.setNombreMetodo(rs.getString("metodo_nombre"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    
    public void anularPorMatricula(int matriculaId) {

        String sql = """
            UPDATE pagos
            SET estado = 'Anulado',
                fecha_pago = NULL
            WHERE matricula_id = ?
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, matriculaId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

