package javaapplication2;

import java.util.List;

public class PlanificadorDeTrabajo {

    private final Memory memory;
    private final GestorProceso gestorProceso;
    private final Disco disco;

    public PlanificadorDeTrabajo(
            Memory memory,
            GestorProceso gestorProceso,
            Disco disco) {

        this.memory = memory;
        this.gestorProceso = gestorProceso;
        this.disco = disco;
    }


    // Busca el trabajo más antiguo según su ID
    private Object[] obtenerTrabajoMasAntiguo() {

        List<Object[]> trabajos =
                memory.obtenerTrabajos();

        if (trabajos.isEmpty()) {
            return null;
        }

        Object[] trabajoMasAntiguo =
                trabajos.get(0);

        for (Object[] trabajo : trabajos) {

            int idActual =
                    (Integer) trabajo[0];

            int idMasAntiguo =
                    (Integer) trabajoMasAntiguo[0];

            if (idActual < idMasAntiguo) {
                trabajoMasAntiguo = trabajo;
            }
        }

        return trabajoMasAntiguo;
    }


    // Admite el trabajo más antiguo
    public Proceso planificarSiguiente() {

        Object[] trabajo =
                obtenerTrabajoMasAntiguo();

        if (trabajo == null) {

            System.out.println(
                    "No hay trabajos pendientes."
            );

            return null;
        }


        // Datos del trabajo
        int idTrabajo =
                (Integer) trabajo[0];

        int direccion =
                (Integer) trabajo[2];

        // Recuperar el índice original del disco
        IndicePrograma indice =
                disco.obtenerIndicePorDireccion(
                        direccion
                );

        if (indice == null) {
            return null;
        }

        // Recuperar programa del disco
        Programa programa =
                disco.obtenerPrograma(indice);

        // Validar que pueda entrar en memoria
        if (programa == null|| !memory.puedeCargarPrograma(programa)) {

            return null;
        }


        // Crear proceso
        Proceso proceso =
                gestorProceso.crearProceso(programa);


        // Cargar BCP + instrucciones en RAM
        if (!memory.cargarProceso(proceso)) {
            return null;
        }
        // El trabajo ya fue admitido,
        // entonces sale de la lista de trabajos
        memory.liberarTrabajo(idTrabajo);


        // NUEVO -> PREPARADO
        gestorProceso.ponerEnReady(proceso);

        // Actualizar el BCP ya con estado PREPARADO
        memory.actualizarBCP(proceso);


        


        return proceso;
    }
}