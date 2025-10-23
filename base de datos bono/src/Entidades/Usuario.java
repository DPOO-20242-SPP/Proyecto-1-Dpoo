package Entidades;

public class Usuario {
	private int id;
    private String nombre;
    private String correo;
    private String password;
    private String tipo; 

    public Usuario() {}

    public Usuario(String nombre, String correo, String password, String tipo) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.tipo = tipo;
    }

    public int getId() {
    	return id; 
    	}
    public void setId(int id) { this.id = id; }

    public String getNombre() {
    	return nombre; 
    	}
    public void setNombre(String nombre) {
    	this.nombre = nombre; 
    	}

    public String getCorreo() {
    	return correo; 
    	}
    public void setCorreo(String correo) {
    	this.correo = correo; 
    	}

    public String getPassword() { 
    	return password; 
    	}
    public void setPassword(String password) {
    	this.password = password; 
    	}

    public String getTipo() {
    	return tipo;
}
    public void setTipo(String tipo) { 
    	this.tipo = tipo; 
    	}
    @Override
    public String toString() {
    	return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", tipo=" + tipo + "]";

    }

}