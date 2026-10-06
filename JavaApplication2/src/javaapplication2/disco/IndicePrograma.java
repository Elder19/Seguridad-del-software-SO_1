package javaapplication2.disco;

public class IndicePrograma {
    private String nombre;
    private int direccion;
    private int tamanio;

    public IndicePrograma(
            String nombre,
            int direccion,
            int tamanio) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.tamanio = tamanio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDireccion() {
        return direccion;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

}
