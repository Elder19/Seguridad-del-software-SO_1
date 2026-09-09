package javaapplication2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
public class SimuladorSO {

    private boolean encendido;

    private Lectorarchivos lector;
    private Programa programa;

    public SimuladorSO() {

        this.encendido = false;
        this.lector = new Lectorarchivos();
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
    public void solicitarPrograma() {
        if (!encendido) {

            System.out.println(
                    "El sistema operativo está apagado."
            );

            return;
        }
       Scanner scanner = new Scanner(System.in);

       System.out.print("Ingrese la ruta del archivo ASM: ");

       String ruta = scanner.nextLine();

      /* File archivo = new File(ruta);*/
        File archivo = new File(
                "src/Imagenes/asam.asm"
        );
       String nombre = archivo.getName();

       cargarPrograma(archivo, nombre);
   }
    public void cargarPrograma(File archivo, String nombre) {
     
        try {

            List<String> lineas =
                    lector.leerArchivo(archivo);

            System.out.println("\nContenido leído:");

          /*  for (String linea : lineas) {

                System.out.println(linea);
            }  */
            Programa programa = new Programa(nombre, lineas);/*crea el programa*/
            programa.print();

        } catch (IOException e) {

            System.out.println(
                    "Error al leer el archivo:"
            );

            System.out.println(
                    e.getMessage()
            );
        }
    
         
    }

    public boolean isEncendido() {
        return encendido;
    }
/*--------------------PROGAMA---------------------------------------------------------------*/
    
    
}