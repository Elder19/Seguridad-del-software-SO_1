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
        this.memory = new Memory();
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
    /*Recibe el archivo seleccionado por la interfaz.
     * Lee el archivo y crea el objeto Programa.
     *  NO crea un proceso.
     *  NO lo carga en memoria.
     */
    public Programa cargarPrograma(File archivo)throws IOException {validarEncendido();
        if (archivo == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un archivo."
            );
        }
        List<String> lineas =lector.leerArchivo( archivo);
        return new Programa(archivo.getName(),lineas);
    }

    
/*-----------------PREPARAR PROGRAMA----------------------------------------*/

    /* Convierte el programa en un proceso.*/
    public Proceso prepararPrograma( Programa programa) {
        validarEncendido();
        if (programa == null) {
            throw new IllegalArgumentException(
                    "Debe proporcionar un programa."
            );
        }
        /*
         * Crear proceso.
         */
        Proceso proceso =gestorProceso.crearProceso(programa);

        /*
         * BCP → zona SO
         * instrucciones → zona usuario
         */
        boolean cargado =
                memory.cargarProceso(proceso);
        if (!cargado) {
            throw new IllegalStateException(
                    "No hay espacio suficiente "
                    + "en memoria para cargar el proceso."
            );
        }
        gestorProceso.ponerEnReady(proceso);
        return proceso;
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
                    "No hay ningún proceso "
                    + "despachado en la CPU."
            );
        }
        /*Comprobar si ya termin*/
        if (cpu.getPC()>= procesoActual.getBcp().getTamanio()) {
            return false;
        }
        cpu.ejecutarInstruccion(memory,procesoActual);
        return cpu.getPC() < procesoActual.getBcp() .getTamanio();
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
        Memory nuevaMemoria =new Memory(nuevoTamanio);
        gestorProceso.BorrarProcesos();
        memory =nuevaMemoria;
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
}