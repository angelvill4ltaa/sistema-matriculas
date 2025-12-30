package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Estudiante;
import dao.Conexion;

public class UsuarioDAO {

    public Estudiante validarLogin(String correo, String clave) {
        Estudiante u = null;
        String sql = "SELECT * FROM Usuarios WHERE Correo=? AND Clave=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("correo"),
                    rs.getString("clave"),
                    rs.getString("rol")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }

    public int registrar(Estudiante u) {
        int id = 0;
        String sql = "INSERT INTO Usuarios (nombre, apellidos, correo, clave, rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getClave());
            ps.setString(5, "ESTUDIANTE");

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) id = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public List<Estudiante> listar() {

        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection cn = Conexion.getConexion();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("correo"),
                    rs.getString("clave")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Usuarios WHERE id=?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}


