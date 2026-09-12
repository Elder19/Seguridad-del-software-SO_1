package javaapplication2;

import java.util.ArrayList;
import java.util.List;

public class Programa {

    private String nombre;

    private final List<Instruccion> instrucciones =
            new ArrayList<>();
    
    public Programa(String nombre, List<String> lineas) {
        this.nombre = nombre;
        for (String linea : lineas) {
            Instruccion instruccion =Instruccion.desdeTexto(linea );
            instrucciones.add(instruccion);
        }
    }
    public String getNombre() {
        return nombre;
    }


    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }


    public int getTamanio() {
        return instrucciones.size();
    }
    
        public void print() {

        System.out.println(
                "Programa: " + nombre
        );

        System.out.println(
                "Cantidad de instrucciones: "
                + instrucciones.size()
        );

        for (int i = 0;
                i < instrucciones.size();
                i++) {

            System.out.println(
                    (i + 1)
                    + ": "
                    + instrucciones.get(i)
            );
        }
    }

}