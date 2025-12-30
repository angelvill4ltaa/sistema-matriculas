package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static final String URL = "jdbc:mysql://localhost:3306/CursosDB";
	private static final String USUARIO = "root";
	private static final String CLAVE = "mysql";
	
	public static Connection getConexion() {
		Connection conexion = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conexion;
	}
}


