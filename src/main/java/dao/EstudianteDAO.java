package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Estudiante;
import dao.Conexion;

public class EstudianteDAO {

    public List<Estudiante> listar() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE rol='ESTUDIANTE'";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                lista.add(new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("rol")
                ));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    public void registrar(Estudiante u) {
        String sql = "INSERT INTO Usuarios(nombre,apellidos,correo,clave, rol) VALUES(?,?,?,?,?)";

        try(Connection cn = Conexion.getConexion();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getClave());
            ps.setString(5, "ESTUDIANTE");

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void actualizar(Estudiante u){

        String sql = "UPDATE Usuarios SET nombre=?, apellidos=?, correo=? WHERE id=?";

        try(Connection cn = Conexion.getConexion();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellidos());
            ps.setString(3, u.getCorreo());
            ps.setInt(4, u.getId());

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Estudiante obtenerPorId(int id) {
        Estudiante u = null;
        String sql = "SELECT * FROM usuarios WHERE id=?";

        try(Connection cn = Conexion.getConexion();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                u = new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("correo"),
                    rs.getString("clave")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return u;
    }



    public void eliminar(int id){

        String sql = "DELETE FROM Usuarios WHERE id=? AND rol='ESTUDIANTE'";

        try(Connection cn = Conexion.getConexion();
            PreparedStatement ps = cn.prepareStatement(sql)){

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

