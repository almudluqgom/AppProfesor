package info.androidhive.sqlite.model;

public class Profesor {

    String idProfesor;
    String NombreApell;
    int IDFoto;

    public Profesor(){}
    public Profesor(String idProfesor, String nombreApell, int IDFoto) {
        this.idProfesor = idProfesor;
        this.NombreApell = nombreApell;
        this.IDFoto = IDFoto;
    }

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreApell() {
        return NombreApell;
    }

    public void setNombreApell(String nombreApell) {
        NombreApell = nombreApell;
    }

    public int getIDFoto() {
        return IDFoto;
    }

    public void setIDFoto(int IDFoto) {
        this.IDFoto = IDFoto;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", NombreProf='" + NombreApell + '\'' +
                ", IDFoto='" + IDFoto + '\'' +
                '}';
    }
}
