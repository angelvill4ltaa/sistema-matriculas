package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Curso;
import dao.Conexion;

public class CursoDAO {

	public List<Curso> listar() {

        List<Curso> lista = new ArrayList<>();
        String sql = """
        	    SELECT c.id, c.codigo, c.nombre, c.descripcion,
                c.docente_id, c.horario, c.cupos, c.estado,
                d.nombres, d.apellidos
         FROM cursos c
         JOIN docentes d ON c.docente_id = d.id
         WHERE c.estado = 1
     """;

        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
            	Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("docente_id"),
                        rs.getString("horario"),
                        rs.getInt("cupos"),
                        rs.getBoolean("estado")
                    );
            	
            	curso.setDocenteNombre(
                        rs.getString("nombres") + " " + rs.getString("apellidos")
                );
            	lista.add(curso);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void registrar(Curso c) {

    	String codigoGenerado = generarCodigo();
    	
        String sql = """
            INSERT INTO cursos
            (codigo, nombre, descripcion, docente_id, horario, cupos)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

        	ps.setString(1, codigoGenerado);
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDescripcion());
            ps.setInt(4, c.getDocenteId());
            ps.setString(5, c.getHorario());
            ps.setInt(6, c.getCupos());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void actualizar(Curso c) {

        String sql = """
            UPDATE cursos
            SET nombre = ?,
                descripcion = ?,
                docente_id = ?,
                horario = ?,
                cupos = ?
            WHERE id = ?
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getDocenteId());
            ps.setString(4, c.getHorario());
            ps.setInt(5, c.getCupos());
            ps.setInt(6, c.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    public Curso buscarPorId(int id) {

        String sql = """
            SELECT * FROM cursos WHERE id = ?
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Curso(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getInt("docente_id"),
                    rs.getString("horario"),
                    rs.getInt("cupos"),
                    rs.getBoolean("estado")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    

    public void eliminar(int id) {

        String sql = "UPDATE cursos SET estado = 0 WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generarCodigo() {

        String codigo = "CUR-01";
        String sql = "SELECT MAX(id) FROM cursos";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int ultimoId = rs.getInt(1);
                codigo = "CUR-" + String.format("%02d", ultimoId + 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return codigo;
    }
    
    
    public boolean tieneCupos(int cursoId) {
        String sql = "SELECT cupos FROM cursos WHERE id = ? AND estado = 1";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, cursoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("cupos") > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void restarCupo(int cursoId) {
        String sql = "UPDATE cursos SET cupos = cupos - 1 WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, cursoId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void sumarCupo(int cursoId) {
        String sql = "UPDATE cursos SET cupos = cupos + 1 WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, cursoId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    
    
    
    
    
    
    
}

