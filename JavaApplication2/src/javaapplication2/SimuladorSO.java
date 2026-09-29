package javaapplication2;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimuladorSO {

    private boolean encendido;
    private final Lectorarchivos lector;
    private final GestorProceso gestorProceso;
    private Memory memory;
    private final CPU cpu;
    private Disco disco;
    private final Despachador despachador;



    /*
     * Proceso que actualmente está utilizando la CPU.
     */
    private Proceso procesoActual;


    /*---------------- CONSTRUCTOR ----------------*/

    public SimuladorSO() {
        this.encendido = false;
        this.lector = new Lectorarchivos();
        this.gestorProceso = new GestorProceso();
        this.memory = new Memory(Integer.parseInt(lector.leerConfig("memoriaRam") ),Double.parseDouble(lector.leerConfig("porcentajeSO"))
);
        this.disco = new Disco( Integer.parseInt( lector.leerConfig("memoriaVirtual")), 
                Integer.parseInt(lector.leerConfig("disco")),Integer.parseInt(lector.leerConfig("indices"))
);
        this.cpu = new CPU();
        this.despachador =new Despachador(gestorProceso,cpu);
        this.procesoActual = null;
    }

/*encender y apagar*/
    public void encender() {
        encendido = true;
    }
    public void apagar() {
        encendido = false;
    }
    public boolean isEncendido() {
        return encendido;
    }


    
/*----------------------------------------------------*/
 
    
/*
 * Recibe el archivo seleccionado por la interfaz.
 * Lee y valida el archivo.
 * Crea el objeto Programa.
 * Lo almacena en disco.
 * NO crea un proceso.
 * NO lo carga en RAM.
 */
public void cargarPrograma(File archivo) throws IOException {

    List<String> instrucciones =
            lector.leerArchivo(archivo);

    disco.cargarPrograma(
            archivo.getName(),
            instrucciones
    );
}

    
/*-----------------PREPARAR PROGRAMA----------------------------------------*/

    /* Convierte el programa en un proceso.*/
    public Proceso prepararPrograma(Programa programa) {

       validarEncendido();

       if (programa == null) {
           throw new IllegalArgumentException("Debe proporcionar un programa.");
       }

       Proceso proceso = gestorProceso.crearProceso(programa);

       // Primero intenta cargarlo en RAM
       if (memory.cargarProceso(proceso)) {
           gestorProceso.ponerEnReady(proceso);
           return proceso;
       }
     

       throw new IllegalStateException(
           "No hay espacio suficiente en RAM para ejecucion espere a liberar algun programa."
       );
   }


  /*---------------------dESPACHADOR-------------------------------*/
    /*
     * Toma el siguiente proceso de READY y carga su contexto en la CPU.*/
    public Proceso despacharSiguiente() {
        validarEncendido();
        procesoActual =despachador.despacharSiguiente( memory);
        return procesoActual;
    }


   /*---------------------------EJECUCION--------------------------------*/
    /*
     * Ejecuta solamente UNA instrucción.*/
    public boolean ejecutarSiguienteInstruccion() {

    validarEncendido();

    if (procesoActual == null) {
        throw new IllegalStateException(
                "No hay ningún proceso despachado en la CPU."
        );
    }

    int limite = procesoActual.getBcp().getBase()
            + procesoActual.getBcp().getTamanio();

    if (cpu.getPC() >= limite) {
        return false;
    }

    cpu.ejecutarInstruccion(memory, procesoActual);

    return cpu.getPC() < limite;
}



    public void ejecutarProcesoCompleto() {
        validarEncendido();
        if (procesoActual == null) {
            throw new IllegalStateException(
                    "No hay ningún proceso "
                    + "despachado en la CPU."
            );
        }
        cpu.ejecutarTodo(memory,procesoActual);
    }


  /*---------------------GESTION DE MEMORIA----------------*/
    /*recibe el numero entero del nuevo tamaño de memoria*/
    public void cambiarMemoria(int nuevoTamanio) {
        double so= Double.parseDouble(lector.leerConfig("porcentajeSO"));
        Memory nuevaMemoria =new Memory(nuevoTamanio,so);
        gestorProceso.BorrarProcesos();
        memory =nuevaMemoria;
        procesoActual = null;
        cpu.reiniciar();
    }
    
    /*recibe el numero entero del nuevo tamaño de memoria*/
    public void cambiarAlmacenamiento(int nuevoTamanio) {
       
     

     disco = new Disco(
        Integer.parseInt(lector.leerConfig("memoriaVirtual")),
        nuevoTamanio,
        Integer.parseInt(lector.leerConfig("indices"))
    );

        gestorProceso.BorrarProcesos();
        memory.limpiarMemoria();
  
        procesoActual = null;
        cpu.reiniciar();
    }

    public void reiniciarSistema() {

        gestorProceso.BorrarProcesos();

        memory.limpiarMemoria();

        procesoActual = null;

        cpu.reiniciar();
    }

    private void validarEncendido() {

        if (!encendido) {

            throw new IllegalStateException(
                    "El sistema operativo está apagado."
            );
        }
    }


   

    public Memory getMemory() {

        return memory;
    }


    public CPU getCpu() {

        return cpu;
    }


    public GestorProceso getGestorProceso() {

        return gestorProceso;
    }


    public Proceso getProcesoActual() {

        return procesoActual;
    }
    
    public Disco getDisco() {
    return disco;
}
    
    public void finalizarProcesoActual() {

    validarEncendido();

    if (procesoActual == null) {
        throw new IllegalStateException("No hay ningún proceso en ejecución.");
    }

    Proceso procesoFinalizado = procesoActual;

    gestorProceso.terminarActual();
    memory.liberarProceso(procesoFinalizado);

    procesoActual = null;
}
}