package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Docente;

public class DocenteDAO {
	
	public List<Docente> listar() {

	    List<Docente> lista = new ArrayList<>();
	    String sql = "SELECT id, nombres, apellidos FROM docentes";

	    try (Connection cn = Conexion.getConexion();
	    		PreparedStatement ps = cn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            lista.add(new Docente(
	                rs.getInt("id"),
	                rs.getString("nombres"),
	                rs.getString("apellidos")
	            ));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return lista;
	}

}
