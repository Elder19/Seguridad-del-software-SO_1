package javaapplication2;

import java.util.ArrayDeque;
import java.util.Queue;

public class GestorProceso {

    private final Queue<Proceso> ready = new ArrayDeque<>();
    private final Queue<Proceso> blocked = new ArrayDeque<>();
    private final Queue<Proceso> terminated = new ArrayDeque<>();

    private Proceso running;
    private int siguientePid = 1;

  public Proceso crearProceso(Programa programa) {

    Proceso proceso = new Proceso(programa, siguientePid++);

    proceso.getBcp().setEstadoProceso("NEW");

    return proceso;
}

  
public boolean BorrarProcesos(){
    ready.clear();
    blocked.clear();
    terminated.clear();
    running = null; 
    siguientePid= 1; 
    return true; 
}
public void ponerEnReady(Proceso proceso) {

    if (!"NEW".equals(proceso.getBcp().getEstadoProceso())) {
        throw new IllegalStateException(
                "El proceso debe estar en estado NEW"
        );
    }

    if (proceso.getBcp().getBase() < 0
            || proceso.getBcp().getTamanio() <= 0) {

        throw new IllegalStateException(
                "El proceso debe estar cargado en memoria"
        );
    }

    proceso.getBcp().setEstadoProceso("Ready");
    ready.offer(proceso);
}

    // Pasa el primero de Ready a Running (FCFS).
    public Proceso ejecutarSiguiente() {
        if (running == null && !ready.isEmpty()) {
            running = ready.poll();
            running.getBcp().setEstadoProceso("Running");
        }

        return running;
    }

    // Pasa el proceso actual a Blocked.
    public void bloquearActual() {
        if (running != null) {
            running.getBcp().setEstadoProceso("Blocked");
            blocked.offer(running);
            running = null;
        }
    }

    // Pasa un proceso de Blocked a Ready por su PID.
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
        encontrado.getBcp().setEstadoProceso("Ready");
        ready.offer(encontrado);

        return true;
    }

    // Pasa el proceso actual a Terminated.
    public void terminarActual() {
        if (running != null) {
            running.getBcp().setEstadoProceso("Terminated");
            terminated.offer(running);
            running = null;
        }
    }

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

    public void imprimirEstados() {
        imprimirCola("READY", ready);

        System.out.println("RUNNING:");
        if (running == null) {
            System.out.println("  CPU libre");
        } else {
            imprimirProceso(running);
        }

        imprimirCola("BLOCKED", blocked);
        imprimirCola("TERMINATED", terminated);
    }

    private void imprimirCola(String nombre, Queue<Proceso> cola) {
        System.out.println(nombre + ":");

        if (cola.isEmpty()) {
            System.out.println("  Sin procesos");
        }

        for (Proceso proceso : cola) {
            imprimirProceso(proceso);
        }
    }

    private void imprimirProceso(Proceso proceso) {
        System.out.println(
                "  PID: " + proceso.getBcp().getPid()
                + " | Programa: " + proceso.getPrograma().getNombre()
                + " | PC: " + proceso.getBcp().getPC()
                + " | AC: " + proceso.getBcp().getAC()
                + " | ax: " + proceso.getBcp().getAX()
                + " | bx: " + proceso.getBcp().getBX()
                + " | cx: " + proceso.getBcp().getCX()
                + " | dx: " + proceso.getBcp().getDX()
        );
    }
}