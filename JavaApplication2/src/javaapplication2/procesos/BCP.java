package javaapplication2.procesos;

import java.util.ArrayList;
import java.util.List;

public class BCP {

    private static final int TAMANIO_BCP = 27;
    private final int Pid;
    boolean bandera= false; 
    
    
    
    public enum EstadoProceso {

        NUEVO,
        PREPARADO,
        EJECUCION,
        SUSPENDIDO,
        EN_ESPERA,
        FINALIZADO
    }
    
    private EstadoProceso estadoProceso;
    /*---------------- REGISTROS ----------------*/
    private int PC;
    private int AC;

    private int AX;
    private int BX;
    private int CX;
    private int DX;

    private String IR;

    /*---------------- PLANIFICACION ----------------*/

    private int ordenCola = 0;
    private int prioridad;

    /*---------------- MEMORIA ----------------*/

    private int base = -1;
    private int tamanio = 0;

    /*---------------- PILA ----------------*/

    private final Object[] pila;
    private int topePila;

    /*---------------- INFORMACION CONTABLE ----------------*/

    private int cpu;

    private java.time.LocalTime tiempoInicio;
    private long tiempoEmpleado;

    /*---------------- ARCHIVOS ----------------*/

    private final List<String> archivosAbiertos;

    /*---------------- ENLACE ----------------*/

    private int siguienteBCP;

    /*---------------- INTERRUPCIONES ----------------*/

    private int AH;
    private String AL;
    private String textoDX;

    public BCP(
            int Pid,
            EstadoProceso estadoProceso) {

        this.Pid = Pid;

        this.estadoProceso = estadoProceso;

        /* Registros */

        this.PC = 0;
        this.AC = 0;

        this.AX = 0;
        this.BX = 0;
        this.CX = 0;
        this.DX = 0;

        this.IR = null;

        /* Planificacion */

        this.ordenCola = 0;
        this.prioridad = 0;

        /* Memoria */

        this.base = -1;
        this.tamanio = 0;

        /* Pila */

        this.pila = new Object[5];

        this.topePila = 0;

        /* Informacion contable */

        this.cpu = -1;

        this.tiempoInicio = null;
        this.tiempoEmpleado = 0;

        /* Archivos */

        this.archivosAbiertos = new ArrayList<>();

        /* Enlace */

        this.siguienteBCP = -1;

        /* Interrupciones */

        this.AH = 0;

        this.AL = null;

        this.textoDX = null;
    }

    /*
     * ==================================================
     * PILA
     * ==================================================
     */

    public void push(Object valor) {

        if (topePila >= pila.length) {

            throw new IllegalStateException(
                    "Error de desbordamiento de pila.");
        }

        pila[topePila] = valor;

        topePila++;
    }

    public Object pop() {

        if (topePila == 0) {

            throw new IllegalStateException(
                    "La pila está vacía.");
        }

        topePila--;

        Object valor = pila[topePila];

        pila[topePila] = null;

        return valor;
    }

    public void agregarArchivoAbierto(
            String archivo) {

        if (!archivosAbiertos.contains(archivo)) {

            archivosAbiertos.add(
                    archivo);
        }
    }

    public void cerrarArchivo(
            String archivo) {

        archivosAbiertos.remove(
                archivo);
    }

    public int getPid() {
        return Pid;
    }

    public EstadoProceso getEstadoProceso() {
        return estadoProceso;
    }

    public int getPC() {
        return PC;
    }

    public int getAC() {
        return AC;
    }

    public int getAX() {
        return AX;
    }

    public int getBX() {
        return BX;
    }

    public int getCX() {
        return CX;
    }

    public int getDX() {
        return DX;
    }

    public String getIR() {
        return IR;
    }

    public int getOrdenCola() {
        return ordenCola;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getBase() {
        return base;
    }

    public int getTamanio() {
        return tamanio;
    }

    public Object[] getPila() {

        return pila.clone();
    }

    public int getTopePila() {
        return topePila;
    }

    public int getCpu() {
        return cpu;
    }

    public long getTiempoEmpleado() {
        return tiempoEmpleado;
    }

    public List<String> getArchivosAbiertos() {

        return new ArrayList<>(
                archivosAbiertos);
    }

    public int getSiguienteBCP() {
        return siguienteBCP;
    }

    public int getAH() {
        return AH;
    }

    public String getAL() {
        return AL;
    }

    public String getTextoDX() {
        return textoDX;
    }

    public static int getTAMANIO_BCP() {
        return TAMANIO_BCP;
    }

    public void setEstadoProceso(
            EstadoProceso estadoProceso) {

        this.estadoProceso = estadoProceso;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

    public void setAX(int AX) {
        this.AX = AX;
    }

    public void setBX(int BX) {
        this.BX = BX;
    }

    public void setCX(int CX) {
        this.CX = CX;
    }

    public void setDX(int DX) {
        this.DX = DX;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }

    public void setOrdenCola(
            int ordenCola) {

        this.ordenCola = ordenCola;
    }

    public void setPrioridad(
            int prioridad) {

        this.prioridad = prioridad;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setTamanio(
            int tamanio) {

        this.tamanio = tamanio;
    }

    public void setTopePila(
            int topePila) {

        this.topePila = topePila;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public java.time.LocalTime getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(java.time.LocalTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public void setTiempoEmpleado(
            long tiempoEmpleado) {

        this.tiempoEmpleado = tiempoEmpleado;
    }

    public void setSiguienteBCP(
            int siguienteBCP) {

        this.siguienteBCP = siguienteBCP;
    }

    public void setAH(int AH) {
        this.AH = AH;
    }

    public void setAL(String AL) {
        this.AL = AL;
    }

    public void setTextoDX(
            String textoDX) {

        this.textoDX = textoDX;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
    
    
}
