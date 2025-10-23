package Entidades;
import java.sql.Timestamp;
public class Transaccion {
	private int id;
    private int clienteId;
    private int tiqueteId;
    private double monto;
    private boolean esReservaOrganizador;
    private Timestamp fechaCompra;

    public Transaccion() {}

    public Transaccion(int clienteId, int tiqueteId, double monto, boolean esReservaOrganizador) {
        this.clienteId = clienteId;
        this.tiqueteId = tiqueteId;
        this.monto = monto;
        this.esReservaOrganizador = esReservaOrganizador;
    }

    public int getId() {
    	return id; 
    	}
    public void setId(int id) {
    	this.id = id; 
    	}

    public int getClienteId() {
    	return clienteId;
    	}
    public void setClienteId(int clienteId) {
    	this.clienteId = clienteId; 
    	}
    

    public int getTiqueteId() {
    	return tiqueteId; 
    	}
    public void setTiqueteId(int tiqueteId) {
    	this.tiqueteId = tiqueteId; 
    	}

    public double getMonto() {
    	return monto; 
    	}
    public void setMonto(double monto) {
    	this.monto = monto; 
    	}

    public boolean isEsReservaOrganizador() {
    	return esReservaOrganizador; 
    	}
    public void setEsReservaOrganizador(boolean esReservaOrganizador) {
    	this.esReservaOrganizador = esReservaOrganizador; 
    	}

    public Timestamp getFechaCompra() {
    	return fechaCompra; 
    	}
    
    public void setFechaCompra(Timestamp fechaCompra) {
    	this.fechaCompra = fechaCompra; 
    	}
}
