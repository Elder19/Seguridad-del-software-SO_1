/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication2;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author elder
 */
public class Programa {

     private String nombre; 
     private List<String> instrucciones =new ArrayList<>();
     
    public Programa(String nombre, List<String> instrucciones) {

        this.nombre = nombre;
        this.instrucciones = new ArrayList<>(instrucciones);
    }

public void print() {

    System.out.println("Programa: " + nombre);
    System.out.println("Cantidad de instrucciones: " + instrucciones.size());

    for (int i = 0; i < instrucciones.size(); i++) {

        System.out.println(
                (i + 1) + ": " + instrucciones.get(i)
        );
    }
}

    public String getNombre() {
        return nombre;
    }

    public List<String> getInstrucciones() {
        return instrucciones;
    }

    public int getTamanio() {
        return instrucciones.size();
    }
} 
     

