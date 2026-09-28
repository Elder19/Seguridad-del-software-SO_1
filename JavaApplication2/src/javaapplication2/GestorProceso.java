package javaapplication2;

import java.util.ArrayDeque;
import java.util.Queue;

public class GestorProceso {

    // Procesos listos para utilizar la CPU
    private final Queue<Proceso> preparados = new ArrayDeque<>();

    // Procesos esperando algún evento o E/S
    private final Queue<Proceso> enEspera = new ArrayDeque<>();

    // Procesos suspendidos
    private final Queue<Proceso> suspendidos = new ArrayDeque<>();

    // Procesos que ya terminaron
    private final Queue<Proceso> finalizados = new ArrayDeque<>();

    // Solo puede existir un proceso ejecutándose
    private Proceso ejecucion;

    private int siguientePid = 1;


    /* Un proceso es un programa que ya tiene un espacio de memoria */
    public Proceso crearProceso(Programa programa) {

        Proceso proceso = new Proceso(programa, siguientePid++);

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.NUEVO
        );

        return proceso;
    }


    /* Limpia todo para hacer cambios de memoria */
    public boolean BorrarProcesos() {

        preparados.clear();
        suspendidos.clear();
        enEspera.clear();
        finalizados.clear();

        ejecucion = null;
        siguientePid = 1;

        return true;
    }


    /* Cambia el proceso de NUEVO a PREPARADO */
    public void ponerEnReady(Proceso proceso) {

        if (proceso.getBcp().getEstadoProceso()
                != BCP.EstadoProceso.NUEVO) {

            throw new IllegalStateException(
                    "El proceso debe estar en estado NUEVO"
            );
        }

        if (proceso.getBcp().getBase() < 0
                || proceso.getBcp().getTamanio() <= 0) {

            throw new IllegalStateException(
                    "El proceso debe estar cargado en memoria"
            );
        }

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.PREPARADO
        );

        preparados.offer(proceso);
    }


    /* Pasa el primero de PREPARADO a EJECUCION */
    public Proceso ejecutarSiguiente() {

        if (ejecucion == null && !preparados.isEmpty()) {

            ejecucion = preparados.poll();

            ejecucion.getBcp().setEstadoProceso(
                    BCP.EstadoProceso.EJECUCION
            );
        }

        return ejecucion;
    }


    /* Pasa el proceso actual de EJECUCION a EN_ESPERA */
    public void bloquearActual() {

        if (ejecucion != null) {

            ejecucion.getBcp().setEstadoProceso(
                    BCP.EstadoProceso.EN_ESPERA
            );

            enEspera.offer(ejecucion);

            ejecucion = null;
        }
    }


    /* Pasa un proceso de EN_ESPERA a PREPARADO por PID */
    public boolean desbloquearProceso(int pid) {

        Proceso encontrado = null;

        for (Proceso proceso : enEspera) {

            if (proceso.getBcp().getPid() == pid) {
                encontrado = proceso;
                break;
            }
        }

        if (encontrado == null) {
            return false;
        }

        enEspera.remove(encontrado);

        encontrado.getBcp().setEstadoProceso(
                BCP.EstadoProceso.PREPARADO
        );

        preparados.offer(encontrado);

        return true;
    }


    /* Pasa el proceso actual de EJECUCION a FINALIZADO */
    public void terminarActual() {

        if (ejecucion != null) {

            ejecucion.getBcp().setEstadoProceso(
                    BCP.EstadoProceso.FINALIZADO
            );

            finalizados.offer(ejecucion);

            ejecucion = null;
        }
    }


    /* ---------------- GETTERS ---------------- */

    public Proceso getEjecucion() {
        return ejecucion;
    }

    public Queue<Proceso> getPreparados() {
        return new ArrayDeque<>(preparados);
    }

    public Queue<Proceso> getEnEspera() {
        return new ArrayDeque<>(enEspera);
    }

    public Queue<Proceso> getSuspendidos() {
        return new ArrayDeque<>(suspendidos);
    }

    public Queue<Proceso> getFinalizados() {
        return new ArrayDeque<>(finalizados);
    }
}