package model;

import java.sql.Timestamp;

public class Matricula {

    private int id;
    private int usuarioId;
    private int cursoId;
    private String estudiante;
    private String curso;
    private Timestamp fecha;
    private String estado;

    public Matricula() {
    }
    
    public Matricula(int id, int usuarioId, int cursoId,
            String estudiante, String curso,
            Timestamp fecha, String estado) {
this.id = id;
this.usuarioId = usuarioId;
this.cursoId = cursoId;
this.estudiante = estudiante;
this.curso = curso;
this.fecha = fecha;
this.estado = estado;
}

public Matricula(int usuarioId, int cursoId) {
this.usuarioId = usuarioId;
this.cursoId = cursoId;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getUsuarioId() {
	return usuarioId;
}

public void setUsuarioId(int usuarioId) {
	this.usuarioId = usuarioId;
}

public int getCursoId() {
	return cursoId;
}

public void setCursoId(int cursoId) {
	this.cursoId = cursoId;
}

public String getEstudiante() {
	return estudiante;
}

public void setEstudiante(String estudiante) {
	this.estudiante = estudiante;
}

public String getCurso() {
	return curso;
}

public void setCurso(String curso) {
	this.curso = curso;
}

public Timestamp getFecha() {
	return fecha;
}

public void setFecha(Timestamp fecha) {
	this.fecha = fecha;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}
}    

