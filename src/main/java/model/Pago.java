package model;

import java.util.Date;

public class Pago {

    private int id;
    private int matriculaId;
    private double monto;
    private int metodoId;
    private String estado;
    private Date fechaPago;
    private String referencia;
    private String nombreMetodo;

    public Pago() {
    }

    public Pago(int id, int matriculaId, double monto, int metodoId, String estado, Date fechaPago, String referencia) {
        this.id = id;
        this.matriculaId = matriculaId;
        this.monto = monto;
        this.metodoId = metodoId;
        this.estado = estado;
        this.fechaPago = fechaPago;
        this.referencia = referencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(int matriculaId) {
        this.matriculaId = matriculaId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getMetodoId() {
        return metodoId;
    }

    public void setMetodoId(int metodoId) {
        this.metodoId = metodoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

	public String getNombreMetodo() {
		return nombreMetodo;
	}

	public void setNombreMetodo(String nombreMetodo) {
		this.nombreMetodo = nombreMetodo;
	}
    
}

