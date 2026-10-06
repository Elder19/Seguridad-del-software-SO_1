package javaapplication2;

import javaapplication2.programa.Programa;
import javaapplication2.programa.Instruccion;
import javaapplication2.programa.Lectorarchivos;
import javaapplication2.disco.IndicePrograma;
import javaapplication2.disco.Disco;
import javaapplication2.memoria.Memory;
import javaapplication2.procesos.planificacorDeProcesos;
import javaapplication2.procesos.Despachador;
import javaapplication2.procesos.GestorProceso;
import javaapplication2.procesos.Proceso;
import javaapplication2.procesos.BCP;
import javaapplication2.cpu.CPU;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SimuladorSO {

    private boolean encendido;
    private final Lectorarchivos lector;
    private final GestorProceso gestorProceso;
    private Memory memory;
    private final CPU cpu;
    private Disco disco;
    private final Despachador despachador;
    private final planificacorDeProcesos planificadorDeProcesos;

    //almacena cuando un proceso queda en espera de entrada de teclado. 
    private final Map<Integer, Long> inicioEsperaTeclado = new HashMap<>();

    //duracion de espera de teclado para mostrar en nterfaz
    
    private final Map<Integer, Long> ultimaEsperaTecladoSegundos = new HashMap<>();
    /*---------------- CONSTRUCTOR ----------------*/

    public SimuladorSO() {
        this.encendido = false;
        this.lector = new Lectorarchivos();
        this.gestorProceso = new GestorProceso();
        this.memory = new Memory(Integer.parseInt(lector.leerConfig("memoriaRam")),
                Double.parseDouble(lector.leerConfig("porcentajeSO")));
        this.disco = new Disco(Integer.parseInt(lector.leerConfig("memoriaVirtual")),
                Integer.parseInt(lector.leerConfig("disco")));
        this.cpu = new CPU();
        this.despachador = new Despachador(gestorProceso, cpu);
        this.planificadorDeProcesos = new planificacorDeProcesos("FIFO");

    }

    /* encender y apagar */
    public void encender() {
        encendido = true;
    }

    public void apagar() {
        encendido = false;
    }

    public boolean isEncendido() {
        return encendido;
    }

    /*
     * Recibe el archivo seleccionado por la interfaz.
     * Lee y valida el archivo.
     * Crea el objeto Programa.
     * Lo almacena en disco.
     * NO crea un proceso.
     * NO lo carga en RAM.
     */
    public void cargarPrograma(File archivo) throws IOException {

        if (archivo == null) {
            throw new IllegalArgumentException(
                    "Debe proporcionar un archivo.");
        }

        for (IndicePrograma indice : disco.getIndicesProgramas()) {

            if (indice.getNombre()
                    .equalsIgnoreCase(
                            archivo.getName())) {

                throw new IllegalStateException(
                        "Ya existe un programa llamado \""
                                + archivo.getName()
                                + "\" en el disco.");
            }
        }

        List<String> instrucciones = lector.leerArchivo(archivo);

        disco.cargarPrograma(
                archivo.getName(),
                instrucciones);
    }

    /*-----------------PREPARAR PROGRAMA----------------------------------------*/

    //crea el programa para cargarlo a ram con su bcp e instrucciones llamado por boton ejecutar programa 
    public Proceso prepararPrograma(Programa programa) {

        validarEncendido();

        if (programa == null) {
            throw new IllegalArgumentException(
                    "Debe proporcionar un programa."
            );
        }
        // valida si la bcp tiene espacio
        if (!memory.puedeGuardarBCP()) {

            throw new IllegalStateException(
                    "No hay espacio disponible para guardar el BCP."
            );
        }
        
        
           //valida si las intrucciones caben con normalidad
        if (memory.puedeCargarInstrucciones(
                programa)) {

            Proceso proceso =
                    gestorProceso.crearProceso(
                            programa
                    );

            if (!memory.cargarProceso(
                    proceso)) {

                throw new IllegalStateException(
                        "No fue posible cargar el proceso en RAM."
                );
            }
            gestorProceso.ponerEnReady(
                    proceso
            );
            memory.actualizarBCP(
                    proceso
            );
            return proceso;
        }
        
       //si no cabe en memoria ram valida si puede enviarlo a virtual 
        if (!disco.cabeVirtual(
                programa)) {

            throw new IllegalStateException(
                    "El programa no cabe en RAM ni en memoria virtual."
            );
        }
        //hay espacio crea el proceso
        Proceso proceso =
                gestorProceso.crearProceso(
                        programa
                );
        // guarda el bcp en la ram memoria kernel 
        if (!memory.guardarBCPProceso(
                proceso)) {

            throw new IllegalStateException(
                    "No fue posible guardar el BCP en RAM."
            );
        }

      //lo carga en virtual
        if (!disco.cargarEnVirtual(proceso)) {

            throw new IllegalStateException(
                    "No fue posible cargar el proceso en memoria virtual."
            );
        }
        if (!gestorProceso.suspenderProceso(
                proceso.getBcp().getPid())) {

            throw new IllegalStateException(
                    "No fue posible suspender el proceso."
            );
        }

        memory.actualizarBCP(
                proceso
        );

        return proceso;
    }
    
    //solicita el nombre del archivo.asm por medio de la seleccion en interfaz con boton eliminar

    public void EliminarArchivo(IndicePrograma indice) {

        if (indice == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un programa.");
        }

        Programa programa = disco.obtenerPrograma(indice);
        if (gestorProceso.tieneProcesosActivos(programa)) {

            throw new IllegalStateException(
                    "No se puede eliminar el programa porque tiene procesos activos.");
        }

        for (Object[] trabajo : getMemory().obtenerTrabajos()) {

            String nombreTrabajo = String.valueOf(trabajo[1]);

            if (indice.getNombre().equals(nombreTrabajo)) {

                throw new IllegalStateException(
                        "No se puede eliminar \""
                                + nombreTrabajo
                                + "\" porque está en la cola de trabajos.");
            }
        }
        disco.eliminarArchivo(
                programa.getNombre());
    }

    /*---------------------dESPACHADOR-------------------------------*/
    //llama al planificador de procesos y pregunta quien puede ejecutarse 
    public Proceso despacharSiguiente() {

        validarEncendido();

        if (gestorProceso.getEjecucion() != null) {

            return gestorProceso.getEjecucion();
        }

        int pid = planificadorDeProcesos.seleccionarSiguiente(
                        memory);

        if (pid == -1) {
            cpu.reiniciar();// limpia registros del cpu 

            return null;
        }
        return despachador.despacharSiguiente(pid,memory);
    }

    /*---------------------------EJECUCION--------------------------------*/
    //ejecuta una unica intrucciion con el boton ejecutar intruccion 
    public boolean ejecutarSiguienteInstruccion() {

        validarEncendido();

        Proceso proceso = gestorProceso.getEjecucion();//proceso actual en cpu

        if (proceso == null) {
            throw new IllegalStateException(
                    "No hay ningún proceso despachado en la CPU.");
        }

        int limite = proceso.getBcp().getBase()
                + proceso.getBcp().getTamanio();

        if (cpu.getPC() >= limite) {
            return false;
        }

        try {

            cpu.ejecutarInstruccion(
                    memory,
                    disco,
                    proceso);

        } catch (Exception e) {
            finalizarProcesoActual();//ya no hay intrucciones que ejecutar 
            throw e;
        }
        //si esta esperando entrada teclado y completo sus pesos se pasa a espera y libera cpu
        if (proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA
                && "INT 09H".equalsIgnoreCase(proceso.getBcp().getIR())) {

            int pid = proceso.getBcp().getPid();
            //inicia el contador de la entrada
            inicioEsperaTeclado.putIfAbsent(
                    pid,
                    System.currentTimeMillis());

            gestorProceso.bloquearActual();
            memory.actualizarBCP(proceso);
            despacharSiguiente();
            return true;//avisa a interfaz que se refresque porque solo cambio el estado dle proceso
        }
        // si se finaliza el proceso lo madna a terminar de cerrar 
        if (cpu.isProgramaFinalizado()|| proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.FINALIZADO) {
            finalizarProcesoActual();
            return false;
        }
        return cpu.getPC() < limite;
    }

  // completa la solicitut de teclado no renueva el peso solo sigue 
    public long completarEntradaTeclado(int pid, int valor) {

        validarEncendido();

        if (valor < 0 || valor > 255) {
            throw new IllegalArgumentException(
                    "El valor debe estar entre 0 y 255.");
            
        }
        //recibe el pid del proceso para saber a quien asignarle
        Proceso proceso = gestorProceso.buscarProcesoPorPid(pid);

        if (proceso == null) {
            throw new IllegalArgumentException(
                    "No existe el proceso con PID " + pid + ".");
        }

        BCP bcp = proceso.getBcp();

        if (bcp.getEstadoProceso() != BCP.EstadoProceso.EN_ESPERA) {

            throw new IllegalStateException(
                    "El proceso no está esperando una entrada.");
        }

        if (!"INT 09H".equalsIgnoreCase(bcp.getIR())) {
            throw new IllegalStateException(
                    "El proceso no está detenido por una INT 09H.");
        }

        //calcula el tiempo de la interrupcion 
        long ahora = System.currentTimeMillis();
        Long inicio = inicioEsperaTeclado.remove(pid);
        long esperaMilisegundos = inicio == null
                ? 0
                : Math.max(0, ahora - inicio);
        long esperaSegundos = esperaMilisegundos == 0
                ? 0
                : (esperaMilisegundos + 999) / 1000;

        ultimaEsperaTecladoSegundos.put(
                pid,
                esperaSegundos);

        bcp.setTiempoEmpleado(
                bcp.getTiempoEmpleado()
                        + esperaSegundos);
        bcp.setDX(valor);
        bcp.setPC(
                bcp.getPC() + 1);

        if (!gestorProceso.desbloquearProceso(pid)) {
            throw new IllegalStateException(
                    "No fue posible desbloquear el proceso.");
        }

        memory.actualizarBCP(proceso);

        if (gestorProceso.getEjecucion() == null) {
            despacharSiguiente();
        }

        return esperaSegundos;
    }

    public long getUltimaEsperaTecladoSegundos(int pid) {

        return ultimaEsperaTecladoSegundos
                .getOrDefault(pid, 0L);
    }

    /*---------------------GESTION DE MEMORIA----------------*/

    /* Cambia la RAM y conserva el último tamaño del disco */
    public void cambiarMemoria(int nuevoTamanio) {
        int tamanioDisco = disco.getMemoriaTotal();
        double so = Double.parseDouble(lector.leerConfig("porcentajeSO"));

        memory = new Memory(nuevoTamanio, so);

        gestorProceso.reiniciarProcesos();
        inicioEsperaTeclado.clear();
        ultimaEsperaTecladoSegundos.clear();
        cpu.reiniciar();

        disco = new Disco(
                Integer.parseInt(lector.leerConfig("memoriaVirtual")),
                tamanioDisco);
    }

    /* Cambia el disco y conserva el último tamaño de la RAM */
    public void cambiarAlmacenamiento(int nuevoTamanio) {
        int tamanioMemoria = memory.getEspacio();
        double so = Double.parseDouble(lector.leerConfig("porcentajeSO"));

        disco = new Disco(
                Integer.parseInt(lector.leerConfig("memoriaVirtual")),
                nuevoTamanio);

        gestorProceso.reiniciarProcesos();
        inicioEsperaTeclado.clear();
        ultimaEsperaTecladoSegundos.clear();
        cpu.reiniciar();

        memory = new Memory(
                tamanioMemoria,
                so);
    }
     //borra todos los registros e informacion 
    public void reiniciarSistema() {
        memory.limpiarMemoria();
        gestorProceso.reiniciarProcesos();
        inicioEsperaTeclado.clear();
        ultimaEsperaTecladoSegundos.clear();
        cpu.reiniciar();
    }

    private void validarEncendido() {

        if (!encendido) {

            throw new IllegalStateException(
                    "El sistema operativo está apagado.");
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
    //cambia el estado del proceso para terminarlo 
    public void finalizarProcesoActual() {
        validarEncendido();

        Proceso proceso = gestorProceso.getEjecucion();
        if (proceso == null) {
            return;
        }
        
        gestorProceso.terminarActual();
        memory.liberarProceso(proceso);
        intentarRecuperarSuspendido();
        
    }
    //Valida que haya memoria suficiente para recuperar los archivos 
    private boolean intentarRecuperarSuspendido() {

        Proceso proceso =
                gestorProceso.obtenerSiguienteSuspendido();

        if (proceso == null) {
            return false;
        }
        
        if (!disco.estaEnVirtual(
                proceso.getBcp().getPid())) {

            return false;
        }

        //si puede cargar recupera el programa
        if (!memory.puedeCargarInstrucciones(
                proceso.getPrograma())) {
            return false;
        }

        List<Instruccion> instrucciones =
                disco.obtenerInstruccionesVirtuales(
                        proceso
                );
        
        if (instrucciones.isEmpty()) {

            throw new IllegalStateException(
                    "No se encontraron las instrucciones virtuales del PID "
                    + proceso.getBcp().getPid()
                    + "."
            );
        }
        //guardar las intrucciones en la memoria 
        if (!memory.cargarInstruccionesSuspendido(
                proceso,
                instrucciones)) {
            return false;
        }
        
        if (!gestorProceso.reanudarProceso(
                proceso.getBcp().getPid())) {

            throw new IllegalStateException(
                    "No se pudo reanudar el proceso PID "
                    + proceso.getBcp().getPid()
                    + "."
            );
        }
        memory.actualizarBCP(
                proceso
        );
        if (!disco.liberarDeVirtual(
                proceso)) {

            throw new IllegalStateException(
                    "El proceso PID "
                    + proceso.getBcp().getPid()
                    + " fue cargado en RAM, pero no se pudo liberar de memoria virtual."
            );
        }

        return true;
    }
}
