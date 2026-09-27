package javaapplication2;

import java.util.ArrayDeque;
import java.util.Queue;

public class GestorProceso {
    /*colas de tipos de proceso*/
    private final Queue<Proceso> ready = new ArrayDeque<>();
    private final Queue<Proceso> blocked = new ArrayDeque<>();
    private final Queue<Proceso> terminated = new ArrayDeque<>();

    private Proceso running;
    private int siguientePid = 1;
  /*Un proceso es un programa que ya tiene un espacio de memoria*/
  public Proceso crearProceso(Programa programa) {
    Proceso proceso = new Proceso(programa, siguientePid++);
    proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.NUEVO);
    return proceso;
}

  /*limpia todo para hacer cambios de memoria*/
    public boolean BorrarProcesos(){
        ready.clear();
        blocked.clear();
        terminated.clear();
        running = null; 
        siguientePid= 1; 
        return true; 
    }
    
    /*cambia el estado de algun proceso de nuevo a listo para ejecutar*/
    public void ponerEnReady(Proceso proceso) {

        if (!"NEW".equals(proceso.getBcp().getEstadoProceso())) {
            throw new IllegalStateException(
                    "El proceso debe estar en estado NEW"
            );
        }
        if (proceso.getBcp().getBase() < 0|| proceso.getBcp().getTamanio() <= 0) {
            throw new IllegalStateException(
                    "El proceso debe estar cargado en memoria"
            );
        }

        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.PREPARADO);
        ready.offer(proceso);
    }
// Pasa el primero de Preparado a Ejecución.
public Proceso ejecutarSiguiente() {

    if (running == null && !ready.isEmpty()) {

        running = ready.poll();

        running.getBcp().setEstadoProceso(
                BCP.EstadoProceso.EJECUCION
        );
    }

    return running;
}


// Pasa el proceso actual a En Espera.
public void bloquearActual() {

    if (running != null) {

        running.getBcp().setEstadoProceso(
                BCP.EstadoProceso.EN_ESPERA
        );

        blocked.offer(running);

        running = null;
    }
}


// Pasa un proceso de En Espera a Preparado por PID.
public boolean desbloquearProceso(int pid) {

    Proceso encontrado = null;

    for (Proceso proceso : blocked) {

        if (proceso.getBcp().getPid() == pid) {
            encontrado = proceso;
            break;
        }
    }

    if (encontrado == null) {
        return false;
    }

    blocked.remove(encontrado);

    encontrado.getBcp().setEstadoProceso(
            BCP.EstadoProceso.PREPARADO
    );

    ready.offer(encontrado);

    return true;
}

    // Pasa el proceso actual a Terminated.
    public void terminarActual() {
        if (running != null) {
            running.getBcp().setEstadoProceso(BCP.EstadoProceso.FINALIZADO);
            terminated.offer(running);
            running = null;
        }
    }
/*---------------------------------------GETTERS--------------------------------------------------------------*/
    public Proceso getRunning() {
        return running;
    }

    public Queue<Proceso> getReady() {
        return new ArrayDeque<>(ready);
    }

    public Queue<Proceso> getBlocked() {
        return new ArrayDeque<>(blocked);
    }

    public Queue<Proceso> getTerminated() {
        return new ArrayDeque<>(terminated);
    }

    
    
    
    
   
}