/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication2;

/**
 *
 * @author elder
 */
public class Disco {
    private final int memoriaVirtual; 
    private int  MemoriaTotal;
    private final int totalIndices; 
 
    private final IndicePrograma[] indices; /*donde estan los programas ubicacion*/
    private final Object[] archivos;/*almacen de progra mas intruccion por posicion*/
    private final Object[] virtual;/*memoria virtual*/
    
    
    
    public Disco(int memoriaVirtual,int memoriaTotal, int totalIndices){
        this.MemoriaTotal=memoriaTotal; 
        this.memoriaVirtual=memoriaVirtual; 
        this.totalIndices=totalIndices; 
        indices = new IndicePrograma[totalIndices];
        virtual = new Object [memoriaVirtual];
        archivos= new Object [memoriaTotal-(totalIndices+memoriaVirtual)];
        if(archivos.length<=0){
         throw new IllegalArgumentException(
                    "La distribución del disco no es válida"
            );}
        
        
    }

    public int getMemoriaVirtual() {
        return memoriaVirtual;
    }

    public int getMemoriaTotal() {
        return MemoriaTotal;
    }

    public void setMemoriaTotal(int MemoriaTotal) {
        this.MemoriaTotal = MemoriaTotal;
    }
    public IndicePrograma[] getIndices() {
    return indices.clone();
    }

    public Object[] getArchivos() {
        return archivos.clone();
    }

    public Object[] getVirtual() {
        return virtual.clone();
    }
    
    
    public void CargarPrograma(Programa programa){
        int tamanio= programa.getTamanio(); 
      int inicio = buscarbloque(programa.getTamanio());
        if (inicio == -1) {
            throw new IllegalStateException(
                "No hay memoria suficiente para almacenar el programa."
            );
        }
        int posicionIndice = buscarIndiceLibre();
        if (posicionIndice == -1) {
        throw new IllegalStateException(
                "No hay espacio disponible en el índice de archivos."
        );
    }  
// Guarda cada instrucción en una posición.
    for (int i = 0; i < tamanio; i++) {

        archivos[inicio + i] =
                programa.getInstrucciones().get(i);
    }

    // Registra dónde quedó almacenado el programa.
    indices[posicionIndice] =
            new IndicePrograma(
                    programa.getNombre(),
                    inicio,
                    tamanio
            );
        }
        
    
    private int buscarIndiceLibre() {

    for (int i = 0; i < indices.length; i++) {

        if (indices[i] == null) {
            return i;
        }
    }

    return -1;
}
    
    private int buscarbloque(int tamanio) {

    int mejorInicio = -1;
    int mejorTamanio = Integer.MAX_VALUE;

    int inicioActual = -1;
    int tamanioActual = 0;

    for (int i = 0; i <= archivos.length; i++) {
        if (i < archivos.length && archivos[i] == null) {

            if (tamanioActual == 0) {
                inicioActual = i;
            }
            tamanioActual++;

        } else {

            /*
             * Terminó un bloque libre.
             * Comprueba si el programa cabe y si
             * este bloque es mejor que el anterior.
             */
            if (tamanioActual >= tamanio&& tamanioActual < mejorTamanio) {

                mejorInicio = inicioActual;
                mejorTamanio = tamanioActual;
            }

            // Reinicia para buscar el siguiente bloque.
            inicioActual = -1;
            tamanioActual = 0;
        }
    }

    return mejorInicio;
}
    
   
    
}
