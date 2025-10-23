package Entidades;
import java.sql.Date;

public class Evento {
	private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private int organizadorId;
    private int venueId;


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
    public String getDescripcion(){
    	return descripcion;
    	} 
    public void setDescripcion(String descripcion){
    	this.descripcion=descripcion;
    	}
    public Date getFecha(){return fecha;
    } 
    public void setFecha(Date fecha){
    	this.fecha=fecha;
    	}
    public int getOrganizadorId(){
    	return organizadorId;
    	} 
    public void setOrganizadorId(int o){
    	this.organizadorId=o;
    	}
    public int getVenueId(){
    	return venueId;
    	} 
    public void setVenueId(int v){
    	this.venueId=v;
    	}
}
