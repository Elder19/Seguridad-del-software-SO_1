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

    //busca el trabajo con mayor tiempo de espera
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
                    (Integer) trabajo[3];

            int idMasAntiguo =
                    (Integer) trabajoMasAntiguo[3];

            if (idActual < idMasAntiguo) {
                trabajoMasAntiguo = trabajo;
            }
        }

        return trabajoMasAntiguo;
    }


    //admite el mas antiguo lo reconstruye 
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
        IndicePrograma indice =
                (IndicePrograma) trabajo[0];
   /*
        String nombrePrograma =
                (String) trabajo[1];

        int tamanioPrograma =
                (Integer) trabajo[2];*/

        int idTrabajo =
                (Integer) trabajo[3];


        // Recuperar el programa del disco
        Programa programa = disco.obtenerPrograma(indice);
//valida si se puede cargar en las memorias
        if (programa == null||!memory.puedeCargarPrograma(programa)) {

            return null;
        }


        //  se convierte en proceso
        Proceso proceso =gestorProceso.crearProceso(programa);
        // Cargar BCP + instrucciones
        if (!memory.cargarProceso(proceso)) {
            return null;
        }


        // NUEVO -> PREPARADO
        gestorProceso.ponerEnReady(proceso);
        memory.actualizarBCP(proceso);
        memory.liberarTrabajo(idTrabajo);


        return proceso;
    }
}