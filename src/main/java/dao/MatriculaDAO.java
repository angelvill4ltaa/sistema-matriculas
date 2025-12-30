package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Matricula;

public class MatriculaDAO {

	public List<Matricula> listar() {

        List<Matricula> lista = new ArrayList<>();

        String sql = """
            SELECT m.id,
                   u.id AS usuario_id,
                   c.id AS curso_id,
                   CONCAT(u.nombre,' ',u.apellidos) AS estudiante,
                   c.nombre AS curso,
                   m.fecha_matricula,
                   m.estado
            FROM matriculas m
            JOIN usuarios u ON m.usuario_id = u.id
            JOIN cursos c ON m.curso_id = c.id
            ORDER BY m.id ASC
        """;

        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Matricula(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("curso_id"),
                    rs.getString("estudiante"),
                    rs.getString("curso"),
                    rs.getTimestamp("fecha_matricula"),
                    rs.getString("estado")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
	
	//Estudiantes 
	public List<Matricula> listarPorEstudiante(int usuarioId) {

	    List<Matricula> lista = new ArrayList<>();

	    String sql = """
	        SELECT m.id,
	               u.id AS usuario_id,
	               c.id AS curso_id,
	               CONCAT(u.nombre,' ',u.apellidos) AS estudiante,
	               c.nombre AS curso,
	               m.fecha_matricula,
	               m.estado
	        FROM matriculas m
	        JOIN usuarios u ON m.usuario_id = u.id
	        JOIN cursos c ON m.curso_id = c.id
	        WHERE u.id = ?
	        ORDER BY m.id ASC
	    """;

	    try (Connection cn = Conexion.getConexion();
	         PreparedStatement ps = cn.prepareStatement(sql)) {

	        ps.setInt(1, usuarioId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            lista.add(new Matricula(
	                rs.getInt("id"),
	                rs.getInt("usuario_id"),
	                rs.getInt("curso_id"),
	                rs.getString("estudiante"),
	                rs.getString("curso"),
	                rs.getTimestamp("fecha_matricula"),
	                rs.getString("estado")
	            ));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}


	public boolean registrar(Matricula m) {

	    if (existeMatricula(m.getUsuarioId(), m.getCursoId())) {
	        return false;
	    }

	    String sql = """
	        INSERT INTO matriculas(usuario_id, curso_id)
	        VALUES (?, ?)
	    """;

	    try (Connection cn = Conexion.getConexion();
	         PreparedStatement ps = cn.prepareStatement(sql)) {

	        ps.setInt(1, m.getUsuarioId());
	        ps.setInt(2, m.getCursoId());
	        ps.executeUpdate();
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}

    
    public boolean existeMatricula(int usuarioId, int cursoId) {

        String sql = """
            SELECT COUNT(*) 
            FROM matriculas 
            WHERE usuario_id = ? 
              AND curso_id = ?
              AND estado <> 'Anulada'
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, cursoId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public void anular(int id) {

        String sql = "UPDATE matriculas SET estado='Anulada' WHERE id=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Matricula obtenerPorId(int id) {
        String sql = "SELECT * FROM matriculas WHERE id = ?";
        Matricula m = null;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new Matricula();
                m.setId(rs.getInt("id"));
                m.setCursoId(rs.getInt("curso_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }



}


