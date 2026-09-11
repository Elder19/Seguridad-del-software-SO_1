package javaapplication2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
public class SimuladorSO {

    private boolean encendido;

    private final Lectorarchivos lector;
    private final GestorProceso gestorProceso;
    private Memory memory;
    private final Scanner scanner;
    private final CPU cpu;
    private final Despachador despachador;
    public SimuladorSO() {

        this.encendido = false; /*para simular el encender*/
        this.lector = new Lectorarchivos();
        this.gestorProceso = new GestorProceso();
        this.memory = new Memory(); /*memoria por defecto*/
        this.scanner = new Scanner(System.in);
         this.cpu = new CPU();
         this.despachador =
            new Despachador(
                    gestorProceso,
                    cpu
            );

    }

    public void encender() {

        encendido = true;

        System.out.println("Sistema Operativo encendido");

        solicitarPrograma();
    }

    public void apagar() {

        encendido = false;

        System.out.println("Sistema Operativo apagado");
    }

    public boolean isEncendido() {
        return encendido;
    }

    public void solicitarPrograma() {

        if (!encendido) {
            System.out.println("El sistema operativo está apagado.");
            return;
        }

        System.out.print("Ingrese la ruta del archivo ASM: ");

        String ruta = "src/Imagenes/asam.asm"; //scanner.nextLine().trim();

        File archivo = new File(ruta);

        cargarPrograma(archivo, archivo.getName());
    }

    public void cargarPrograma(File archivo, String nombre) {

        if (!encendido) {
            System.out.println("El sistema operativo está apagado.");
            return;
        }

        try {

            List<String> lineas = lector.leerArchivo(archivo);

            Programa programa = new Programa(nombre, lineas);

            System.out.println("\nContenido leído:");
            programa.print();

            PrepararPrograma(programa);/*esto debe ser un boton */

        } catch (IOException e) {

            System.out.println(
                    "Error al leer el archivo: " + e.getMessage()
            );
        }
    }

    /*
     * Prepara el proceso para ejecutarse.
     * Todavía no ejecuta instrucciones en la CPU.
     */
    public void PrepararPrograma(Programa programa) {

        if (!encendido) {
            System.out.println("El sistema operativo está apagado.");
            return;
        }

        // 1. Crea el proceso en estado NEW.
        Proceso proceso = gestorProceso.crearProceso(programa);

        // 2. Intenta cargar su BCP y sus instrucciones.
        boolean cargado = memory.cargarProceso(proceso);

        // 3. Solo entra en Ready cuando tiene memoria asignada.
        if (cargado) {

            gestorProceso.ponerEnReady(proceso);

            System.out.println(
                    "\nProceso " + proceso.getBcp().getPid()
                    + " cargado y agregado a Ready."
            );

        } else {

            System.out.println(
                    "\nNo hay espacio suficiente para el proceso "
                    + proceso.getBcp().getPid()
                    + ". No se agregó a Ready."
            );
        }

        gestorProceso.imprimirEstados();
        memory.imprimirMemoria();
    }
    
    
    
    /*---------------------------CAMBIOS DE MEMORIA-------------------------------------------------*/

public void reiniciarSistema() {
    gestorProceso.BorrarProcesos();
    memory.limpiarMemoria();
}


public void cambiarMemoria() {

    while (true) {

        String entrada = JOptionPane.showInputDialog(
                null,
                "Ingrese el nuevo tamaño de memoria\n"
                + "El tamaño mínimo es 128:",
                "Cambiar memoria",
                JOptionPane.QUESTION_MESSAGE
        );

        // Si presiona CANCELAR
        if (entrada == null) {

            System.out.println("Cambio de memoria cancelado.");
            return;
        }

        try {

            int nuevoTamanio = Integer.parseInt(entrada);
            Memory nuevaMemoria = new Memory(nuevoTamanio);
            reiniciarSistema();

            memory = nuevaMemoria;

            JOptionPane.showMessageDialog(
                    null,
                    "Memoria cambiada a "
                    + nuevoTamanio
                    + " posiciones.",
                    "Memoria",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Debe ingresar un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
} 
/*
public void ejecutarProceso() {

  
    
    Proceso proceso =
            despachador.despacharSiguiente(memory);

    if (proceso == null) {
        return;
    }

    // La CPU ejecuta todo el programa
    cpu.ejecutarTodo(
            memory,
            proceso
    );

    cpu.printCPU();
}
*/

}