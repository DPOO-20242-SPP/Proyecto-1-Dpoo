package Entidades;

public class Tiquete {
	private int id;
    private String tipo; 
    private String codigo;
    private int localidadId;
    private Integer asientoNum; 
    private Integer paqueteId;  
    private boolean vendido;
    private Integer propietarioId; 


    public int getId(){
    	return id;
    	}
    public void setId(int id){
    	this.id=id;
    	}
    public String getTipo(){
    	return tipo;
    	} 
    public void setTipo(String tipo){
    	this.tipo=tipo;
    	}
    public String getCodigo(){
    	return codigo;
    	} 
    public void setCodigo(String codigo){
    	this.codigo=codigo;
    	}
    public int getLocalidadId(){
    	return localidadId;
    	}
    public void setLocalidadId(int l){
    	this.localidadId=l;
    	}
    public Integer getAsientoNum(){
    	return asientoNum;
    	} 
    public void setAsientoNum(Integer a){
    	this.asientoNum=a;
    	}
    public Integer getPaqueteId(){
    	return paqueteId;
    	} 
    public void setPaqueteId(Integer p){
    	this.paqueteId=p;
    	}
    public boolean isVendido(){
    	return vendido;
    	} 
    public void setVendido(boolean v){
    	this.vendido=v;
    	}
    public Integer getPropietarioId(){
    	return propietarioId;
    	} 
    public void setPropietarioId(Integer p){
    	this.propietarioId=p;
    	}
}
