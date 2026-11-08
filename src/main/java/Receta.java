import java.io.Serializable;
import java.util.ArrayList;

public class Receta implements Serializable {
    private int numReceta=count;
    private String nombre;
    private ArrayList<String> ingredientes;
    private String preparacion;
    private double tiempoPreparacion;
    static int count=1;

    public Receta(String nombre,ArrayList<String> ingredientes,String preparacion,double tiempoPreparacion){
        count++;
        this.nombre=nombre;
        this.ingredientes=ingredientes;
        this.preparacion=preparacion;
        this.tiempoPreparacion=tiempoPreparacion;
    }

    public double getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(double tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public ArrayList<String>getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumReceta() {
        return numReceta;
    }

}
