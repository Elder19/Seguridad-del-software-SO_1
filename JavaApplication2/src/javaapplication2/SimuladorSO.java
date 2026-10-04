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
    private final planificacorDeProcesos  planificadorDeProcesos;



   
    /*---------------- CONSTRUCTOR ----------------*/

    public SimuladorSO() {
        this.encendido = false;
        this.lector = new Lectorarchivos();
        this.gestorProceso = new GestorProceso();
        this.memory = new Memory(Integer.parseInt(lector.leerConfig("memoriaRam") ),Double.parseDouble(lector.leerConfig("porcentajeSO"))
);
        this.disco = new Disco( Integer.parseInt( lector.leerConfig("memoriaVirtual")), 
                Integer.parseInt(lector.leerConfig("disco"))
);
        this.cpu = new CPU();
        this.despachador =new Despachador(gestorProceso,cpu);
        this.planificadorDeProcesos = new planificacorDeProcesos ("FIFO");
       
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

    int pid = 
            planificadorDeProcesos
                    .seleccionarSiguiente(memory);

    if (pid == -1) {
        cpu.reiniciar();
        return null;
    }

    Proceso proceso =
            despachador.despacharSiguiente(
                    pid,
                    memory
            );
   

    return proceso;
}


   /*---------------------------EJECUCION--------------------------------*/
    /*
     * Ejecuta solamente UNA instrucción.*/
    public boolean ejecutarSiguienteInstruccion() {

    validarEncendido();

    Proceso proceso =
            gestorProceso.getEjecucion();

    if (proceso == null) {
        throw new IllegalStateException(
                "No hay ningún proceso despachado en la CPU."
        );
    }

    int limite =
            proceso.getBcp().getBase()
            + proceso.getBcp().getTamanio();

    if (cpu.getPC() >= limite) {
        return false;
    }
    

    cpu.ejecutarInstruccion(
            memory,
            proceso
    );
    
    return cpu.getPC() < limite;
}

/*---------------------GESTION DE MEMORIA----------------*/

/* Cambia la RAM y conserva el último tamaño del disco */
public void cambiarMemoria(int nuevoTamanio) {
    int tamanioDisco = disco.getMemoriaTotal();
    double so = Double.parseDouble(lector.leerConfig("porcentajeSO"));

    memory = new Memory(nuevoTamanio, so);

    gestorProceso.reiniciarProcesos();
    cpu.reiniciar();

    disco = new Disco(
        Integer.parseInt(lector.leerConfig("memoriaVirtual")),
        tamanioDisco
    );
}

/* Cambia el disco y conserva el último tamaño de la RAM */
public void cambiarAlmacenamiento(int nuevoTamanio) {
    int tamanioMemoria = memory.getEspacio();
    double so = Double.parseDouble(lector.leerConfig("porcentajeSO"));

    disco = new Disco(
        Integer.parseInt(lector.leerConfig("memoriaVirtual")),
        nuevoTamanio
    );

    gestorProceso.reiniciarProcesos();
    cpu.reiniciar();

    memory = new Memory(
        tamanioMemoria,
        so
    );
}
    
    

    public void reiniciarSistema() {
        memory.limpiarMemoria();
        gestorProceso.reiniciarProcesos();
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


    public Disco getDisco() {
    return disco;
}
    
    public void finalizarProcesoActual() {
        validarEncendido();

        Proceso proceso = gestorProceso.getEjecucion();
        if (proceso == null) {
            return;
        }

        gestorProceso.terminarActual();
        memory.liberarProceso(proceso);
    }
}