package javaapplication2.procesos;

import javaapplication2.memoria.Memory;
import javaapplication2.procesos.GestorProceso;
import javaapplication2.procesos.Proceso;
import javaapplication2.cpu.CPU;

public class Despachador {

    private final GestorProceso gestorProceso;
    private final CPU cpu;

    public Despachador(
            GestorProceso gestorProceso,
            CPU cpu) {

        this.gestorProceso = gestorProceso;
        this.cpu = cpu;
    }

    public Proceso despacharSiguiente(
            int pid,
            Memory memory) {

        Proceso proceso = gestorProceso.ponerEnEjecucion(pid);

        if (proceso == null) {
            return null;
        }

        proceso.getBcp().setCpu(1);

        // Actualizar BCP en RAM
        memory.actualizarBCP(proceso);

        // Cargar contexto en CPU
        cpu.cargarContexto(
                memory,
                pid);

        return proceso;
    }

}
