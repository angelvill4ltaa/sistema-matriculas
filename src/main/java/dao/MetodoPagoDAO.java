package dao;

import model.MetodoPago;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import dao.Conexion;


public class MetodoPagoDAO {

	public List<MetodoPago> listar() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM MetodoPago";

        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new MetodoPago(
                    rs.getInt("id"),
                    rs.getString("nombre")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

