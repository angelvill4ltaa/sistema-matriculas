package model;

public class Estudiante {

    private int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String clave;
    private String rol;
	
    public Estudiante(int id, String nombre, String apellidos, String correo, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }
    
    public Estudiante(int id, String nombre, String apellidos, String correo, String clave) {
        this(id,nombre,apellidos,correo,clave,"ESTUDIANTE");
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
}


    
