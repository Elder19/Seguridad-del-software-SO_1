package javaapplication2;

import java.util.List;

public class planificacorDeProcesos {

    private String tipoAlgoritmo;
    public planificacorDeProcesos(String tipoAlgoritmo) {
        this.tipoAlgoritmo = tipoAlgoritmo;
    }


    public int seleccionarSiguiente(Memory memory) {

        List<Integer> preparados =
                memory.obtenerPidsPreparados();

        if (preparados.isEmpty()) {
            return -1;
        }

        return validarSiguiente(
                preparados,
                tipoAlgoritmo,
                memory
        );
    }


    private int validarSiguiente(
        List<Integer> preparados,
        String tipoAlgoritmo,
        Memory memory) {

    int siguiente = -1;

    switch (tipoAlgoritmo) {

        case "FIFO":
            siguiente = validarFIFO(
                    preparados,
                    memory
            );
            break;

        default:
            return -1;
    }

    return siguiente;
}


    private int validarFIFO(
            List<Integer> preparados,
            Memory memory) {

        int pidElegido = -1;
        int menorOrden = Integer.MAX_VALUE;

        for (Integer pid : preparados) {

            int[] datos =
                    memory.obtenerDatosBCP(pid);

            if (datos == null) {
                continue;
            }
            
            int ordenCola = datos[4];

            if (ordenCola < menorOrden) {

                menorOrden = ordenCola;
                pidElegido = pid;
            }
        }

        return pidElegido;
    }
}