package info.androidhive.sqlite.model;

public class Material {
    int ID;
    int cantidad;
    String nombre,descripcion;
   int idFoto;

    Material(){}

    public Material(int id,int c, String d, String n, int f){
        this.ID=id;
        this.cantidad=c;
        this.descripcion=d;
        this.nombre=n;
        this.idFoto=f;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    @Override
    public String toString() {
        return "Almacen{" +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", idFoto='" + idFoto + '\'' +
                '}';
    }
}
