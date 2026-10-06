package javaapplication2.procesos;

import java.util.List;

import javaapplication2.disco.Disco;
import javaapplication2.disco.IndicePrograma;
import javaapplication2.memoria.Memory;
import javaapplication2.programa.Programa;

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

        private Object[] obtenerTrabajoMasAntiguo() {

                List<Object[]> trabajos = memory.obtenerTrabajos();

                if (trabajos.isEmpty()) {
                        return null;
                }

                Object[] trabajoMasAntiguo = trabajos.get(0);

                for (Object[] trabajo : trabajos) {

                        int idActual = (Integer) trabajo[0];

                        int idMasAntiguo = (Integer) trabajoMasAntiguo[0];

                        if (idActual < idMasAntiguo) {
                                trabajoMasAntiguo = trabajo;
                        }
                }

                return trabajoMasAntiguo;
        }

        public Proceso planificarSiguiente() {

                Object[] trabajo = obtenerTrabajoMasAntiguo();

                if (trabajo == null) {
                        return null;
                }

                int idTrabajo = (Integer) trabajo[0];

                int direccion = (Integer) trabajo[2];

                IndicePrograma indice = disco.obtenerIndicePorDireccion(
                                direccion);

                if (indice == null) {
                        return null;
                }

                Programa programa = disco.obtenerPrograma(
                                indice);

                if (programa == null) {
                        return null;
                }

                if (!memory.puedeGuardarBCP()) {
                        return null;
                }

                // se verifica si el programa cabe en RAM antes de crear el proceso
                if (memory.puedeCargarInstrucciones(programa)) {

                        Proceso proceso = gestorProceso.crearProceso(
                                        programa);

                        if (!memory.cargarProceso(proceso)) {

                                throw new IllegalStateException(
                                                "No se pudo cargar el proceso en RAM.");
                        }

                        /*
                         * NUEVO -> PREPARADO
                         */
                        gestorProceso.ponerEnReady(
                                        proceso);

                        memory.actualizarBCP(
                                        proceso);

                        /*
                         * Ya fue admitido.
                         * Sale de la cola de trabajos.
                         */
                        memory.liberarTrabajo(
                                        idTrabajo);

                        return proceso;
                }

                // si el programa no cabe en RAM, se verifica si cabe en memoria virtual
                if (!disco.cabeVirtual(programa)) {

                        return null;
                }

                Proceso proceso = gestorProceso.crearProceso(
                                programa);

                // si el programa cabe en memoria virtual, se guarda el BCP en RAM y las
                // instrucciones en memoria virtual
                if (!memory.guardarBCPProceso(
                                proceso)) {

                        throw new IllegalStateException(
                                        "No se pudo guardar el BCP en RAM.");
                }
                // si no se pudieron cargar las instrucciones en memoria virtual, se lanza una
                // excepción
                if (!disco.cargarEnVirtual(
                                proceso)) {

                        throw new IllegalStateException(
                                        "No se pudieron cargar las instrucciones en memoria virtual.");
                }

                /*
                 * NUEVO -> SUSPENDIDO
                 */
                if (!gestorProceso.suspenderProceso(
                                proceso.getBcp().getPid())) {

                        throw new IllegalStateException(
                                        "No se pudo suspender el proceso.");
                }

                memory.actualizarBCP(
                                proceso);

                memory.liberarTrabajo(
                                idTrabajo);

                return proceso;
        }
}
