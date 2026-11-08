public class Configuracion {

    private int numRecetas;
    private int ultimaOpcion;

    public Configuracion(int numRecetas,int ultimaOpcion){
        this.numRecetas=numRecetas;
        this.ultimaOpcion=ultimaOpcion;
    }

    public int getNumRecetas() {
        return numRecetas;
    }

    public void setNumRecetas(int numRecetas) {
        this.numRecetas = numRecetas;
    }

    public int getUltimaOpcion() {
        return ultimaOpcion;
    }

    public void setUltimaOpcion(int ultimaOpcion) {
        this.ultimaOpcion = ultimaOpcion;
    }
}
