package model;

public class Curso {
	
	private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private int docenteId;
    private String docenteNombre;
    private String horario;
    private int cupos;
    private boolean estado;

    public Curso(int id, String codigo, String nombre, String descripcion,
            int docenteId, String horario, int cupos, boolean estado) {
       this.id = id;
       this.codigo = codigo;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.docenteId = docenteId;
       this.horario = horario;
       this.cupos = cupos;
       this.estado = estado;
 }
	
    public Curso(String nombre, String descripcion,
            int docenteId, String horario, int cupos) {
   this(0, null, nombre, descripcion, docenteId, horario, cupos, true);
}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDocenteId() {
		return docenteId;
	}

	public void setDocenteId(int docenteId) {
		this.docenteId = docenteId;
	}

	public String getDocenteNombre() {
		return docenteNombre;
	}

	public void setDocenteNombre(String docenteNombre) {
		this.docenteNombre = docenteNombre;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public int getCupos() {
		return cupos;
	}

	public void setCupos(int cupos) {
		this.cupos = cupos;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
    
}


