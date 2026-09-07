package javaapplication2;

public class Memory {

    private int espacio;
    private String[] memoria;
    private int espacioSO;
    private int  direcciondisponible;
    

    
    /*recibe la memoria de parametro validando su capacidad para iniciar*/
    public Memory(int espacio) {
        if (espacio < 128) {
            throw new IllegalArgumentException(
                "El tamaño mínimo de memoria es 128"
            );
        }

        this.espacio = espacio;
        this.espacioSO= espacio/4;// guarda la cuarta parte de la memoria para el SO
        this.memoria = new String[espacio];
        this.direcciondisponible=espacioSO; 
    }
    /*metodo que recibe una cantidad de esapcio para modificar la memoria actual.*/
    public void setMemory(int nuevoEspacio) {

        if (nuevoEspacio < 128) {
            throw new IllegalArgumentException(
                "El tamaño mínimo de memoria es 128"
            );
        }

        this.espacio = nuevoEspacio;
        this.espacioSO= nuevoEspacio/4;
        this.memoria = new String[nuevoEspacio];
        this.direcciondisponible = this.espacioSO;
    }

    public int getEspacio() {
        return espacio;
    }

    public String[] getMemoria() {
        return memoria;
    }
    public void guardarInstruccion(String instruccion){
        if (direcciondisponible<espacio){
             memoria[direcciondisponible]= instruccion;
             direcciondisponible++;
             
        } else {

        throw new IllegalArgumentException(
            "No hay espacio disponible en memoria"
        );
    }}
    
    /* metodo para borrar toda la memoria*/
    public void limpiarMemoria() {
      this.memoria = new String[this.espacio];
      this.direcciondisponible = this.espacioSO;
    }
    /*metodo para limpiar posiciones de memoria despues de ejecutarse para cargar otras intrucciones si se lleno la memoria*/
     public void limpiarPosMemoria(int posicion) {
      if (posicion >= espacioSO && posicion < espacio) {
        memoria[posicion] = null;
}
    }
    /*informa a la clase lector.asm si puede seguir cargando instrucciones o debe esperar*/
    public boolean espacioDisponible() {
    return direcciondisponible < memoria.length;
}
    

    
     
        
}
