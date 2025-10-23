package Entidades;



public class Localidad {
	private int id;
    private String nombre;
    private int capacidad;
    private double precio;
    private int eventoId;

    public int getId(){
    	return id;
    	} 
    public void setId(int id){
    	this.id=id;
    	}
    public String getNombre(){
    	return nombre;
    	} 
    public void setNombre(String nombre){
    	this.nombre=nombre;
    	}
    public int getCapacidad(){
    	return capacidad;
    	} 
    public void setCapacidad(int capacidad){
    	this.capacidad=capacidad;
    	}
    public double getPrecio(){
    	return precio;
    	} 
    public void setPrecio(double precio){
    	this.precio=precio;
    	}
    public int getEventoId(){
    	return eventoId;
    	} 
    public void setEventoId(int e){
    	this.eventoId=e;
    	}
}
