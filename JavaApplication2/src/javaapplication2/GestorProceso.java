package javaapplication2;

import java.util.ArrayList;
import java.util.List;

public class GestorProceso {

    private final List<Proceso> procesos = new ArrayList<>();
    private Proceso ejecucion;
    private int siguientePid = 1;
    
    
    
    public Proceso crearProceso(Programa programa) {
        if (procesos.size()<5){
        Proceso proceso = new Proceso(programa, siguientePid++);
        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.NUEVO);
        proceso.getBcp().setOrdenCola(Ncola("NUEVO") + 1);
        procesos.add(proceso);
        return proceso;}
        else 
        {
           throw new IllegalStateException("No se pueden tener más de 5 procesos.");
        }
    }

    public int Ncola(String estado) {
        int contador = 0;
        for (int i = 0; i < procesos.size(); i++) {
            Proceso proceso = procesos.get(i);
            if (proceso.getBcp().getEstadoProceso().toString().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }
    

    private void ajustarColaAlSalir(BCP.EstadoProceso estado, int ordenQueSale) {
        for (Proceso proceso : procesos) {
            if (proceso.getBcp().getEstadoProceso() == estado && proceso.getBcp().getOrdenCola() > ordenQueSale) {
                proceso.getBcp().setOrdenCola(proceso.getBcp().getOrdenCola() - 1);
            }
        }
    }

    public void ponerEnReady(Proceso proceso) {
        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.NUEVO) {
            throw new IllegalStateException("El proceso debe estar en estado NUEVO");
        }

        if (proceso.getBcp().getBase() < 0 || proceso.getBcp().getTamanio() <= 0) {
            throw new IllegalStateException("El proceso debe estar cargado en memoria");
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();
        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.PREPARADO);
        ajustarColaAlSalir(BCP.EstadoProceso.NUEVO, ordenAnterior);
        proceso.getBcp().setOrdenCola(Ncola("PREPARADO"));
    }

    public Proceso buscarProcesoPorPid(int pid) {
        for (Proceso proceso : procesos) {
            if (proceso.getBcp().getPid() == pid) {
                return proceso;
            }
        }
        return null;
    }

    public Proceso ponerEnEjecucion(int pid) {
        Proceso proceso = buscarProcesoPorPid(pid);

        if (proceso == null) {
            return null;
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.PREPARADO) {
            throw new IllegalStateException("El proceso debe estar PREPARADO");
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();
        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.EJECUCION);
        ajustarColaAlSalir(BCP.EstadoProceso.PREPARADO, ordenAnterior);
        proceso.getBcp().setOrdenCola(Ncola("EJECUCION"));
        ejecucion = proceso;
        return proceso;
    }

    public void bloquearActual() {
        if (ejecucion == null) {
            return;
        }

        Proceso proceso = ejecucion;
        int ordenAnterior = proceso.getBcp().getOrdenCola();

        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.EN_ESPERA);
        ajustarColaAlSalir(BCP.EstadoProceso.EJECUCION, ordenAnterior);
        proceso.getBcp().setOrdenCola(Ncola("EN_ESPERA"));
        ejecucion = null;
    }

    public boolean desbloquearProceso(int pid) {
        Proceso proceso = buscarProcesoPorPid(pid);

        if (proceso == null) {
            return false;
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.EN_ESPERA) {
            return false;
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();

        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.PREPARADO);
        ajustarColaAlSalir(BCP.EstadoProceso.EN_ESPERA, ordenAnterior);
        proceso.getBcp().setOrdenCola(Ncola("PREPARADO"));

        return true;
    }

    public void terminarActual() {
        if (ejecucion == null) {
            return;
        }

        Proceso proceso = ejecucion;
        int ordenAnterior = proceso.getBcp().getOrdenCola();

        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.FINALIZADO);
        ajustarColaAlSalir(BCP.EstadoProceso.EJECUCION, ordenAnterior);
        proceso.getBcp().setOrdenCola(Ncola("FINALIZADO"));

        ejecucion = null;
    }

    public void reiniciarProcesos() {
        procesos.clear();
        ejecucion = null;
        siguientePid = 1;
    }

    public Proceso getEjecucion() {
        return ejecucion;
    }

    public List<Proceso> getProcesos() {
        return new ArrayList<>(procesos);
    }
}