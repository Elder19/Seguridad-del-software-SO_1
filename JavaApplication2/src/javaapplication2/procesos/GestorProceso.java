package javaapplication2.procesos;

import javaapplication2.procesos.Proceso;
import javaapplication2.procesos.BCP;
import java.util.ArrayList;
import java.util.List;
import javaapplication2.programa.Programa;

public class GestorProceso {

    private final List<Proceso> procesos = new ArrayList<>();
    private Proceso ejecucion;
    private int siguientePid = 1;

    /*---------------- CREAR PROCESO ----------------*/

    public Proceso crearProceso(Programa programa) {

        int procesosActivos = 0;

        for (Proceso proceso : procesos) {

            if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.FINALIZADO) {

                procesosActivos++;
            }
        }

        if (procesosActivos >= 5) {

            throw new IllegalStateException(
                    "No se pueden tener más de 5 procesos activos.");
        }

        Proceso proceso = new Proceso(programa, siguientePid++);

        proceso.getBcp().setTiempoInicio(
                java.time.LocalTime.now());

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.NUEVO);

        proceso.getBcp().setOrdenCola(
                Ncola("NUEVO") + 1);

        procesos.add(proceso);

        return proceso;
    }

    public boolean tieneProcesosActivos(Programa programa) {

        if (programa == null) {
            return false;
        }

        for (Proceso proceso : procesos) {

            if (proceso.getPrograma() != null
                    && proceso.getPrograma()
                            .getNombre()
                            .equals(programa.getNombre())
                    && proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.FINALIZADO) {

                return true;
            }
        }

        return false;
    }

    // cuenta la cantidad de procesos en una cola específica
    public int Ncola(String estado) {

        int contador = 0;

        for (Proceso proceso : procesos) {

            if (proceso.getBcp()
                    .getEstadoProceso()
                    .toString()
                    .equals(estado)) {

                contador++;
            }
        }

        return contador;
    }

    // Ajusta el orden de los procesos en una cola específica al salir un proceso
    private void ajustarColaAlSalir(
            BCP.EstadoProceso estado,
            int ordenQueSale) {

        for (Proceso proceso : procesos) {

            if (proceso.getBcp().getEstadoProceso() == estado
                    && proceso.getBcp().getOrdenCola() > ordenQueSale) {

                proceso.getBcp().setOrdenCola(
                        proceso.getBcp().getOrdenCola() - 1);
            }
        }
    }

    /*---------------- NUEVO -> PREPARADO ----------------*/

    public void ponerEnReady(Proceso proceso) {

        if (proceso == null) {
            throw new IllegalArgumentException(
                    "El proceso no puede ser null.");
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.NUEVO) {

            throw new IllegalStateException(
                    "El proceso debe estar en estado NUEVO.");
        }

        if (proceso.getBcp().getBase() < 0
                || proceso.getBcp().getTamanio() <= 0) {

            throw new IllegalStateException(
                    "El proceso debe estar cargado en memoria.");
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();

        /*
         * Primero reorganizamos la cola de donde sale,
         * mientras todavía pertenece a NUEVO.
         */
        ajustarColaAlSalir(
                BCP.EstadoProceso.NUEVO,
                ordenAnterior);

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.PREPARADO);

        /*
         * Entra al final de PREPARADO.
         */
        proceso.getBcp().setOrdenCola(
                Ncola("PREPARADO"));
    }

    /*---------------- BUSCAR PROCESO ----------------*/

    public Proceso buscarProcesoPorPid(int pid) {

        for (Proceso proceso : procesos) {

            if (proceso.getBcp().getPid() == pid) {
                return proceso;
            }
        }

        return null;
    }

    /*---------------- PREPARADO -> EJECUCION ----------------*/

    public Proceso ponerEnEjecucion(int pid) {

        if (ejecucion != null) {

            throw new IllegalStateException(
                    "Ya existe un proceso en ejecución.");
        }

        Proceso proceso = buscarProcesoPorPid(pid);

        if (proceso == null) {
            return null;
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.PREPARADO) {

            throw new IllegalStateException(
                    "El proceso debe estar PREPARADO.");
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();

        /*
         * Sale de PREPARADO.
         */
        ajustarColaAlSalir(
                BCP.EstadoProceso.PREPARADO,
                ordenAnterior);

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.EJECUCION);

        /*
         * Solo tenemos un proceso en ejecución.
         */
        proceso.getBcp().setOrdenCola(1);

        ejecucion = proceso;

        return proceso;
    }

    /*---------------- EJECUCION -> EN ESPERA ----------------*/

    public void bloquearActual() {

        if (ejecucion == null) {
            return;
        }

        Proceso proceso = ejecucion;

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.EN_ESPERA);

        /*
         * Entra al final de EN_ESPERA.
         */
        proceso.getBcp().setOrdenCola(
                Ncola("EN_ESPERA"));

        ejecucion = null;
    }

    /*---------------- EN ESPERA -> PREPARADO ----------------*/

    public boolean desbloquearProceso(int pid) {

        Proceso proceso = buscarProcesoPorPid(pid);

        if (proceso == null) {
            return false;
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.EN_ESPERA) {

            return false;
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();

        ajustarColaAlSalir(
                BCP.EstadoProceso.EN_ESPERA,
                ordenAnterior);

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.PREPARADO);

        proceso.getBcp().setOrdenCola(
                Ncola("PREPARADO"));

        return true;
    }

    /*---------------- EJECUCION -> FINALIZADO ----------------*/

    public void terminarActual() {

        if (ejecucion == null) {
            return;
        }

        Proceso proceso = ejecucion;

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.FINALIZADO);

        proceso.getBcp().setOrdenCola(
                Ncola("FINALIZADO"));

        ejecucion = null;
    }

    /*----------------Nuevo-:Suspendido  ----------------*/
    public boolean suspenderProceso(int pid) {

        Proceso proceso = buscarProcesoPorPid(pid);

        if (proceso == null) {
            return false;
        }

        BCP.EstadoProceso estadoAnterior = proceso.getBcp().getEstadoProceso();

        int ordenAnterior = proceso.getBcp().getOrdenCola();

        ajustarColaAlSalir(
                estadoAnterior,
                ordenAnterior);

        if (ejecucion == proceso) {
            ejecucion = null;
        }

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.SUSPENDIDO);

        proceso.getBcp().setOrdenCola(
                Ncola("SUSPENDIDO"));

        return true;
    }

    /*---------------- REINICIAR ----------------*/

    public void reiniciarProcesos() {

        procesos.clear();

        ejecucion = null;

        siguientePid = 1;
    }

    public boolean reanudarProceso(int pid) {

        Proceso proceso = buscarProcesoPorPid(pid);

        if (proceso == null) {
            return false;
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.SUSPENDIDO) {

            return false;
        }

        int ordenAnterior = proceso.getBcp().getOrdenCola();

        ajustarColaAlSalir(
                BCP.EstadoProceso.SUSPENDIDO,
                ordenAnterior);

        proceso.getBcp().setEstadoProceso(
                BCP.EstadoProceso.PREPARADO);

        proceso.getBcp().setOrdenCola(
                Ncola("PREPARADO"));

        return true;
    }

    public Proceso obtenerSiguienteSuspendido() {

        Proceso seleccionado = null;

        for (Proceso proceso : procesos) {

            if (proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.SUSPENDIDO) {

                if (seleccionado == null
                        || proceso.getBcp().getOrdenCola() < seleccionado.getBcp().getOrdenCola()) {

                    seleccionado = proceso;
                }
            }
        }

        return seleccionado;
    }
    /*---------------- GETTERS ----------------*/

    public Proceso getEjecucion() {
        return ejecucion;
    }

    public List<Proceso> getProcesos() {

        return new ArrayList<>(procesos);
    }
}
