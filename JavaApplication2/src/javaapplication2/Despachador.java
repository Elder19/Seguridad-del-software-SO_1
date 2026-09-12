package javaapplication2;

public class Despachador {

    private final GestorProceso gestorProceso;
    private final CPU cpu;

    public Despachador(
            GestorProceso gestorProceso,
            CPU cpu) {

        this.gestorProceso = gestorProceso;
        this.cpu = cpu;
    }


 /*Toma el siguiente proceso de la cola READY y carga su contexto en la CPU para ejecutarlo.  */
public Proceso despacharSiguiente(Memory memory) {

        Proceso proceso =gestorProceso.ejecutarSiguiente();

        if (proceso == null) {
            System.out.println( "No hay procesos en READY.");

            return null;
        }
        int pid =proceso.getBcp().getPid();
        memory.actualizarBCP(proceso);

        // Cargar registros del BCP en la CPU.
        cpu.cargarContexto(memory,pid);

        System.out.println(
                "Dispatcher: PID "
                + pid
                + " enviado a la CPU."
        );

        return proceso;
    }
}