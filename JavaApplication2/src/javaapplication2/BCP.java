package javaapplication2;

import java.util.ArrayList;
import java.util.List;

public class BCP {

    private final int Pid;
    private static final int TAMANIO_BCP = 10;

      public enum EstadoProceso {
        NUEVO,
        PREPARADO,
        EJECUCION,
        SUSPENDIDO,
        EN_ESPERA,
        FINALIZADO
    }
    private EstadoProceso estadoProceso;

    // Registros
    private int PC;
    private int AC;
    private int AX; 
    private int BX; 
    private int CX;
    private int DX;
    private String IR;
    private int ordenCola = 0;
    

    // Memoria
    private int base = -1;
    private int tamanio = 0;

    // Pila
    private final Object[] pila;
    private int topePila;

    // Información contable
    private int cpu;
    private long tiempoInicio;
    private long tiempoEmpleado;

    // Archivos abiertos
    private final List<String> archivosAbiertos;

    // Enlace al siguiente BCP
    private BCP siguienteBCP;

    // Prioridad
    private int prioridad;


     public BCP(int Pid, EstadoProceso estadoProceso) {

        this.Pid = Pid;
        this.estadoProceso = estadoProceso;

        this.PC = 0;
        this.AC = 0;
        this.AX = 0;
        this.BX = 0;
        this.CX = 0;
        this.DX = 0;
        this.IR = null;

        this.pila = new Object[5];
        this.topePila = 0;

        this.cpu = -1;
        this.tiempoInicio = 0;
        this.tiempoEmpleado = 0;
        this.archivosAbiertos = new ArrayList<>();
        this.siguienteBCP = null;
        this.prioridad = 0;
    }


    /*---------------- PILA ----------------*/

    public void push(Object valor) {

        if (topePila >= pila.length) {
            throw new IllegalStateException(
                    "Error de desbordamiento de pila."
            );
        }

        pila[topePila] = valor;
        topePila++;
    }


    public Object pop() {

        if (topePila == 0) {
            throw new IllegalStateException(
                    "La pila está vacía."
            );
        }

        topePila--;

        Object valor = pila[topePila];
        pila[topePila] = null;

        return valor;
    }


    /*---------------- ARCHIVOS ----------------*/

    public void agregarArchivoAbierto(String archivo) {
        archivosAbiertos.add(archivo);
    }

    public void cerrarArchivo(String archivo) {
        archivosAbiertos.remove(archivo);
    }


    /*---------------- GETTERS ----------------*/

    public int getPid() {
        return Pid;
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

    public void setEstadoProceso(EstadoProceso estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public void setTopePila(int topePila) {
        this.topePila = topePila;
    }

    public EstadoProceso getEstadoProceso() {
        return estadoProceso;
    }

    public int getTopePila() {
        return topePila;
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

    public int getBase() {
        return base;
    }

    public int getTamanio() {
        return tamanio;
    }

    public Object[] getPila() {
        return pila.clone();
    }

    public int getCpu() {
        return cpu;
    }

    public long getTiempoInicio() {
        return tiempoInicio;
    }

    public long getTiempoEmpleado() {
        return tiempoEmpleado;
    }

    public List<String> getArchivosAbiertos() {
        return new ArrayList<>(archivosAbiertos);
    }

    public BCP getSiguienteBCP() {
        return siguienteBCP;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public static int getTAMANIO_BCP() {
        return TAMANIO_BCP;
    }
    


    /*---------------- SETTERS ----------------*/

 
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

    public void setBase(int base) {
        this.base = base;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public void setTiempoInicio(long tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public void setTiempoEmpleado(long tiempoEmpleado) {
        this.tiempoEmpleado = tiempoEmpleado;
    }

    public void setSiguienteBCP(BCP siguienteBCP) {
        this.siguienteBCP = siguienteBCP;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
    public int getOrdenCola() {
    return ordenCola;
}

public void setOrdenCola(int ordenCola) {
    this.ordenCola = ordenCola;
}
}