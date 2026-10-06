package javaapplication2.memoria;

import java.util.ArrayList;
import java.util.List;

import javaapplication2.disco.IndicePrograma;
import javaapplication2.programa.Instruccion;
import javaapplication2.programa.Programa;
import javaapplication2.procesos.BCP;
import javaapplication2.procesos.Proceso;

public class Memory {

        private static final int TAMANIO_BCP = BCP.getTAMANIO_BCP();

        private static final int TAMANIO_TRABAJO = 4;

        private static final int POS_SIGUIENTE_BCP = 15;

        // private static final int POS_PILA = 19;
        private final Object[] memoria;
        private final boolean[] ocupadoSO;

        private final int espacioSO;

        private int siguienteIdTrabajo = 0;

        public Memory(int espacio, double espacioSo) {

                if (espacio < 128) {

                        throw new IllegalArgumentException(
                                        "El tamaño mínimo de memoria es 128");
                }

                if (espacioSo <= 0
                                || espacioSo >= 1) {

                        throw new IllegalArgumentException(
                                        "El porcentaje del SO debe estar entre 0 y 1");
                }

                memoria = new Object[espacio];

                espacioSO = (int) Math.ceil(
                                espacio * espacioSo);
                ocupadoSO = new boolean[espacioSO];
        }

        // recibe un proceso y lo carga en memoria, si no hay espacio suficiente
        // devuelve false
        public boolean cargarProceso(
                        Proceso proceso) {

                BCP bcp = obtenerBCP(proceso);

                if (proceso.getPrograma() == null) {

                        throw new IllegalArgumentException(
                                        "El proceso no tiene programa");
                }

                if (bcp.getBase() != -1
                                || buscarBCP(
                                                bcp.getPid()) != -1) {

                        throw new IllegalStateException(
                                        "El proceso ya tiene memoria asignada");
                }

                List<Instruccion> instrucciones = proceso
                                .getPrograma()
                                .getInstrucciones();

                if (instrucciones == null
                                || instrucciones.isEmpty()) {

                        throw new IllegalArgumentException(
                                        "El programa no tiene instrucciones");
                }

                for (Instruccion instruccion : instrucciones) {

                        if (instruccion == null) {

                                throw new IllegalArgumentException(
                                                "Las instrucciones no pueden ser null");
                        }
                }

                int posicionBCP = buscarEspacioSO(
                                TAMANIO_BCP);

                int tamanio = instrucciones.size();

                int base = buscarBloqueLibre(
                                tamanio);

                if (posicionBCP == -1
                                || base == -1) {

                        return false;
                }
                int ultimoBCP = buscarUltimoBCP();

                reservarEspacioSO(
                                posicionBCP,
                                TAMANIO_BCP);

                try {
                        for (int i = 0; i < tamanio; i++) {

                                memoria[base + i] = instrucciones.get(i);
                        }

                        bcp.setBase(
                                        base);

                        bcp.setTamanio(
                                        tamanio);

                        bcp.setPC(
                                        bcp.getPC()
                                                        + base);

                        bcp.setSiguienteBCP(-1);

                        guardarBCP(
                                        posicionBCP,
                                        bcp);

                        if (ultimoBCP != -1) {

                                memoria[ultimoBCP
                                                + POS_SIGUIENTE_BCP] = posicionBCP;
                        }

                        return true;

                } catch (Exception e) {

                        liberarEspacioSO(
                                        posicionBCP,
                                        TAMANIO_BCP);

                        for (int i = 0; i < tamanio; i++) {

                                memoria[base + i] = null;
                        }

                        bcp.setBase(-1);

                        bcp.setTamanio(0);

                        throw e;
                }
        }

        // guarda el BCP de un proceso en memoria, si no hay espacio suficiente devuelve
        // false

        public boolean guardarBCPProceso(
                        Proceso proceso) {

                BCP bcp = obtenerBCP(proceso);

                if (proceso.getPrograma() == null) {
                        throw new IllegalArgumentException(
                                        "El proceso no tiene programa");
                }

                if (buscarBCP(bcp.getPid()) != -1) {
                        throw new IllegalStateException(
                                        "El BCP del proceso ya está guardado en RAM");
                }

                int posicionBCP = buscarEspacioSO(
                                TAMANIO_BCP);

                if (posicionBCP == -1) {
                        return false;
                }

                int ultimoBCP = buscarUltimoBCP();

                reservarEspacioSO(
                                posicionBCP,
                                TAMANIO_BCP);

                try {

                        bcp.setBase(-1);
                        bcp.setTamanio(0);
                        bcp.setPC(0);
                        bcp.setSiguienteBCP(-1);

                        guardarBCP(
                                        posicionBCP,
                                        bcp);

                        if (ultimoBCP != -1) {

                                memoria[ultimoBCP
                                                + POS_SIGUIENTE_BCP] = posicionBCP;
                        }

                        return true;

                } catch (Exception e) {

                        for (int i = 0; i < TAMANIO_BCP; i++) {

                                memoria[posicionBCP + i] = null;
                        }

                        liberarEspacioSO(
                                        posicionBCP,
                                        TAMANIO_BCP);

                        throw e;
                }
        }

        /*
         * ==================================================
         * CARGAR INSTRUCCIONES DE UN SUSPENDIDO
         * ==================================================
         */

        public boolean cargarInstruccionesSuspendido(
                        Proceso proceso,
                        List<Instruccion> instrucciones) {

                BCP bcp = obtenerBCP(proceso);

                if (bcp.getEstadoProceso() != BCP.EstadoProceso.SUSPENDIDO) {

                        return false;
                }

                int posicionBCP = buscarBCP(
                                bcp.getPid());

                if (posicionBCP == -1) {

                        throw new IllegalStateException(
                                        "El BCP del proceso suspendido no está en RAM.");
                }

                if (instrucciones == null
                                || instrucciones.isEmpty()) {

                        return false;
                }

                for (Instruccion instruccion : instrucciones) {

                        if (instruccion == null) {

                                throw new IllegalArgumentException(
                                                "Las instrucciones virtuales no pueden contener null.");
                        }
                }

                int tamanio = instrucciones.size();

                int base = buscarBloqueLibre(
                                tamanio);

                if (base == -1) {
                        return false;
                }

                int desplazamientoPC = 0;

                if (bcp.getBase() >= espacioSO
                                && bcp.getTamanio() > 0) {

                        desplazamientoPC = Math.max(
                                        0,
                                        bcp.getPC()
                                                        - bcp.getBase());

                } else if (bcp.getPC() > 0) {

                        desplazamientoPC = bcp.getPC();
                }

                if (desplazamientoPC >= tamanio) {
                        desplazamientoPC = 0;
                }

                try {

                        for (int i = 0; i < tamanio; i++) {

                                memoria[base + i] = instrucciones.get(i);
                        }

                        bcp.setBase(
                                        base);

                        bcp.setTamanio(
                                        tamanio);

                        bcp.setPC(
                                        base
                                                        + desplazamientoPC);

                        bcp.setSiguienteBCP(
                                        obtenerDireccionSiguienteBCP(
                                                        posicionBCP));

                        guardarBCP(
                                        posicionBCP,
                                        bcp);

                        return true;

                } catch (Exception e) {

                        for (int i = 0; i < tamanio; i++) {

                                memoria[base + i] = null;
                        }

                        bcp.setBase(-1);
                        bcp.setTamanio(0);
                        bcp.setPC(0);

                        guardarBCP(
                                        posicionBCP,
                                        bcp);

                        throw e;
                }
        }
        // guardar un trabajo en memoria, si no hay espacio suficiente devuelve false

        public boolean guardarTrabajo(
                        IndicePrograma indice) {

                if (indice == null) {

                        throw new IllegalArgumentException(
                                        "Debe proporcionar un índice de programa.");
                }

                if (indice.getNombre() == null
                                || !indice
                                                .getNombre()
                                                .toLowerCase()
                                                .endsWith(".asm")) {

                        throw new IllegalArgumentException(
                                        "Solo los programas ASM pueden entrar en la cola de trabajos.");
                }

                int posicion = buscarEspacioSO(
                                TAMANIO_TRABAJO);

                if (posicion == -1) {

                        return false;
                }

                reservarEspacioSO(
                                posicion,
                                TAMANIO_TRABAJO);

                memoria[posicion] = siguienteIdTrabajo;

                memoria[posicion + 1] = indice.getNombre();

                memoria[posicion + 2] = indice.getDireccion();

                memoria[posicion + 3] = indice.getTamanio();

                siguienteIdTrabajo++;

                return true;
        }

        // liberar un trabajo de memoria, si no existe devuelve false
        public boolean liberarTrabajo(
                        int idTrabajo) {

                int posicion = buscarTrabajo(
                                idTrabajo);

                if (posicion == -1) {

                        return false;
                }

                for (int i = 0; i < TAMANIO_TRABAJO; i++) {

                        memoria[posicion + i] = null;
                }

                liberarEspacioSO(
                                posicion,
                                TAMANIO_TRABAJO);

                return true;
        }

        // busca un trabajo en memoria, devuelve la posición inicial del trabajo si se

        private int buscarTrabajo(
                        int idTrabajo) {

                for (int i = 0; i + TAMANIO_TRABAJO <= espacioSO; i++) {

                        if (esTrabajo(i)
                                        && ((Integer) memoria[i]) == idTrabajo) {

                                return i;
                        }
                }

                return -1;
        }

        private boolean esTrabajo(
                        int posicion) {

                if (posicion < 0
                                || posicion + TAMANIO_TRABAJO > espacioSO) {

                        return false;
                }

                if (!bloqueOcupadoSO(
                                posicion,
                                TAMANIO_TRABAJO)) {

                        return false;
                }

                if (estaDentroDeBCP(
                                posicion)) {

                        return false;
                }

                if (!(memoria[posicion] instanceof Integer)) {

                        return false;
                }

                if (!(memoria[posicion + 1] instanceof String)) {

                        return false;
                }

                if (!(memoria[posicion + 2] instanceof Integer)) {

                        return false;
                }

                if (!(memoria[posicion + 3] instanceof Integer)) {

                        return false;
                }

                String nombre = (String) memoria[posicion + 1];

                return nombre
                                .toLowerCase()
                                .endsWith(".asm");
        }

        private boolean estaDentroDeBCP(
                        int posicion) {

                for (int i = 0; i + TAMANIO_BCP <= espacioSO; i++) {

                        if (esBCP(i)
                                        && posicion >= i
                                        && posicion < i + TAMANIO_BCP) {

                                return true;
                        }
                }

                return false;
        }

        // obtiene una lista de trabajos en memoria, cada trabajo es un arreglo de 4
        // elementos:

        public List<Object[]> obtenerTrabajos() {

                List<Object[]> trabajos = new ArrayList<>();

                for (int i = 0; i + TAMANIO_TRABAJO <= espacioSO; i++) {

                        if (esTrabajo(i)) {

                                trabajos.add(
                                                new Object[] {
                                                                memoria[i],
                                                                memoria[i + 1],
                                                                memoria[i + 2],
                                                                memoria[i + 3]
                                                });

                                i += TAMANIO_TRABAJO - 1;
                        }
                }

                return trabajos;
        }

        // indica si hay trabajos en memoria, devuelve true si hay al menos un trabajo,
        // false si no hay ninguno

        public boolean hayTrabajos() {

                return !obtenerTrabajos()
                                .isEmpty();
        }

        // indica si hay BCP en memoria, devuelve true si hay al menos un BCP, false si
        // no hay ninguno
        private boolean esBCP(
                        int posicion) {

                if (posicion < 0
                                || posicion + TAMANIO_BCP > espacioSO) {

                        return false;
                }

                if (!bloqueOcupadoSO(
                                posicion,
                                TAMANIO_BCP)) {

                        return false;
                }

                return memoria[posicion] instanceof Integer
                                && memoria[posicion + 1] instanceof BCP.EstadoProceso;
        }

        // busca un bloque de memoria del SO que tenga el tamaño especificado, devuelve
        // la posición inicial del bloque si se encuentra, o -1 si no hay suficiente
        // espacio
        private int buscarEspacioSO(
                        int tamanio) {

                if (tamanio <= 0
                                || tamanio > espacioSO) {

                        return -1;
                }

                int consecutivas = 0;

                for (int i = 0; i < espacioSO; i++) {

                        if (!ocupadoSO[i]) {

                                consecutivas++;

                                if (consecutivas == tamanio) {

                                        return i
                                                        - tamanio
                                                        + 1;
                                }

                        } else {

                                consecutivas = 0;
                        }
                }

                return -1;
        }

        // reserva el espacio de memoria del SO para un bloque de tamaño especificado,
        // si el bloque está fuera de rango o ya ocupado, lanza una excepción

        private void reservarEspacioSO(
                        int inicio,
                        int tamanio) {

                if (inicio < 0
                                || inicio + tamanio > espacioSO) {

                        throw new IndexOutOfBoundsException(
                                        "Bloque del SO fuera de rango.");
                }

                for (int i = 0; i < tamanio; i++) {

                        if (ocupadoSO[inicio + i]) {

                                throw new IllegalStateException(
                                                "La posición "
                                                                + (inicio + i)
                                                                + " del SO ya se encuentra ocupada.");
                        }
                }

                for (int i = 0; i < tamanio; i++) {

                        ocupadoSO[inicio + i] = true;
                }
        }

        // liberar el espacio de memoria del SO para un bloque de tamaño especificado,
        // si el bloque está fuera de rango, no hace nada
        private void liberarEspacioSO(
                        int inicio,
                        int tamanio) {

                if (inicio < 0
                                || inicio + tamanio > espacioSO) {

                        return;
                }

                for (int i = 0; i < tamanio; i++) {

                        ocupadoSO[inicio + i] = false;
                }
        }

        // investiga si un bloque de memoria del SO está ocupado, devuelve true si todas
        // las posiciones
        private boolean bloqueOcupadoSO(
                        int inicio,
                        int tamanio) {

                if (inicio < 0
                                || inicio + tamanio > espacioSO) {

                        return false;
                }

                for (int i = 0; i < tamanio; i++) {

                        if (!ocupadoSO[inicio + i]) {

                                return false;
                        }
                }

                return true;
        }

        private void guardarBCP(
                        int posicion,
                        BCP bcp) {

                memoria[posicion] = bcp.getPid();

                memoria[posicion + 1] = bcp.getEstadoProceso();

                /*---------------- REGISTROS ----------------*/

                // 2
                memoria[posicion + 2] = bcp.getPC();

                // 3
                memoria[posicion + 3] = bcp.getAC();

                // 4
                memoria[posicion + 4] = bcp.getAX();

                // 5
                memoria[posicion + 5] = bcp.getBX();

                // 6
                memoria[posicion + 6] = bcp.getCX();

                // 7
                memoria[posicion + 7] = bcp.getDX();

                // 8
                memoria[posicion + 8] = bcp.getIR();

                /*---------------- MEMORIA ----------------*/

                // 9
                memoria[posicion + 9] = bcp.getBase();

                // 10
                memoria[posicion + 10] = bcp.getTamanio();

                /*---------------- INFORMACION CONTABLE ----------------*/

                // 11
                memoria[posicion + 11] = bcp.getCpu();

                // 12
                memoria[posicion + 12] = bcp.getTiempoInicio();

                // 13
                memoria[posicion + 13] = bcp.getTiempoEmpleado();

                /*---------------- ARCHIVOS ----------------*/

                // 14
                memoria[posicion + 14] = bcp.getArchivosAbiertos();

                /*---------------- ENLACE ----------------*/

                /*
                 * 15
                 *
                 * Se guarda la DIRECCIÓN REAL en RAM del siguiente BCP.
                 * Si no existe siguiente, se almacena null.
                 */
                memoria[posicion
                                + POS_SIGUIENTE_BCP] = bcp.getSiguienteBCP() == -1
                                                ? null
                                                : bcp.getSiguienteBCP();

                /*---------------- PLANIFICACION ----------------*/

                // 16
                memoria[posicion + 16] = bcp.getOrdenCola();

                // 17
                memoria[posicion + 17] = bcp.getPrioridad();

                /*---------------- PILA ----------------*/

                // 18
                memoria[posicion + 18] = bcp.getTopePila();

                Object[] pila = bcp.getPila();

                // 19
                memoria[posicion + 19] = pila[0];

                // 20
                memoria[posicion + 20] = pila[1];

                // 21
                memoria[posicion + 21] = pila[2];

                // 22
                memoria[posicion + 22] = pila[3];

                // 23
                memoria[posicion + 23] = pila[4];

                /*---------------- INTERRUPCIONES ----------------*/

                // 24
                memoria[posicion + 24] = bcp.getAH();

                // 25
                memoria[posicion + 25] = bcp.getAL();

                // 26
                memoria[posicion + 26] = bcp.getTextoDX();
        }

        /*
         * ==================================================
         * ACTUALIZAR BCP
         * ==================================================
         */

        public void actualizarBCP(
                        Proceso proceso) {

                BCP bcp = obtenerBCP(
                                proceso);

                int posicion = validarAsignacion(
                                bcp);

                bcp.setSiguienteBCP(
                                obtenerDireccionSiguienteBCP(
                                                posicion));

                guardarBCP(
                                posicion,
                                bcp);
        }

        // libera el proceso de memoria, si no existe devuelve false
        public void liberarProceso(
                        Proceso proceso) {

                BCP bcp = obtenerBCP(
                                proceso);

                int posicionBCP = validarAsignacion(
                                bcp);

                int base = bcp.getBase();

                int tamanio = bcp.getTamanio();

                int posicionAnterior = buscarBCPAnterior(
                                posicionBCP);

                int posicionSiguiente = obtenerDireccionSiguienteBCP(
                                posicionBCP);

                if (posicionAnterior != -1) {

                        memoria[posicionAnterior
                                        + POS_SIGUIENTE_BCP] = posicionSiguiente == -1
                                                        ? null
                                                        : posicionSiguiente;
                }

                bcp.setSiguienteBCP(-1);

                for (int i = 0; i < tamanio; i++) {

                        memoria[base + i] = null;
                }

                for (int i = 0; i < TAMANIO_BCP; i++) {

                        memoria[posicionBCP + i] = null;
                }

                liberarEspacioSO(
                                posicionBCP,
                                TAMANIO_BCP);

                bcp.setBase(-1);

                bcp.setTamanio(0);
        }

        // busca un BCP en memoria, devuelve la posición inicial del BCP si se
        // encuentra, o -1 si no existe
        private int buscarBCP(
                        int pid) {

                for (int i = 0; i + TAMANIO_BCP <= espacioSO; i++) {

                        if (esBCP(i)
                                        && ((Integer) memoria[i]) == pid) {

                                return i;
                        }
                }

                return -1;
        }

        // busca el último BCP en memoria, devuelve la posición inicial del BCP si se
        // encuentra, o -1 si no existe
        private int buscarUltimoBCP() {

                for (int i = 0; i + TAMANIO_BCP <= espacioSO; i++) {

                        if (esBCP(i)
                                        && obtenerDireccionSiguienteBCP(i) == -1) {

                                return i;
                        }
                }

                return -1;
        }

        // busca el BCP anterior al BCP en la posición especificada, devuelve la
        // posición inicial del BCP anterior si se encuentra, o -1 si no existe
        private int buscarBCPAnterior(
                        int posicionBCP) {

                for (int i = 0; i + TAMANIO_BCP <= espacioSO; i++) {

                        if (esBCP(i)
                                        && obtenerDireccionSiguienteBCP(i) == posicionBCP) {

                                return i;
                        }
                }

                return -1;
        }

        // obtiene la siguiente dirección de BCP a partir de la posición del BCP actual,
        // devuelve -1 si no hay siguiente BCP
        private int obtenerDireccionSiguienteBCP(
                        int posicionBCP) {

                if (!esBCP(posicionBCP)) {

                        return -1;
                }

                Object enlace = memoria[posicionBCP
                                + POS_SIGUIENTE_BCP];

                if (enlace == null) {

                        return -1;
                }

                if (!(enlace instanceof Integer)) {

                        throw new IllegalStateException(
                                        "El enlace al siguiente BCP no contiene una dirección válida.");
                }

                return (Integer) enlace;
        }

        // busca un bloque de memoria libre que tenga el tamaño especificado, devuelve
        // la posición inicial del bloque si se encuentra, o -1 si no hay suficiente
        // espacio
        private int buscarBloqueLibre(
                        int tamanio) {

                int consecutivas = 0;

                for (int i = espacioSO; i < memoria.length; i++) {

                        if (memoria[i] == null) {

                                consecutivas++;

                                if (consecutivas == tamanio) {

                                        return i
                                                        - tamanio
                                                        + 1;
                                }

                        } else {

                                consecutivas = 0;
                        }
                }

                return -1;
        }

        // obtiene el BCP de un proceso, lanza una excepción si el proceso es null o no
        // tiene BCP
        private BCP obtenerBCP(
                        Proceso proceso) {

                if (proceso == null
                                || proceso.getBcp() == null) {

                        throw new IllegalArgumentException(
                                        "Debe proporcionar un proceso con BCP");
                }

                return proceso.getBcp();
        }

        // validar si la asignacion es valida ya se

        private int validarAsignacion(
                        BCP bcp) {

                int posicion = buscarBCP(
                                bcp.getPid());

                if (posicion == -1) {

                        throw new IllegalStateException(
                                        "El proceso no está cargado en esta memoria");
                }

                int baseGuardada = (Integer) memoria[posicion + 9];

                int tamanioGuardado = (Integer) memoria[posicion + 10];

                if (bcp.getBase() != baseGuardada
                                || bcp.getTamanio() != tamanioGuardado) {

                        throw new IllegalStateException(
                                        "La base o el tamaño del BCP fueron modificados");
                }

                return posicion;
        }

        // lee la instrucción de un proceso en memoria, lanza una excepción si el
        // proceso no está cargado o si el PC está fuera del programa

        public Instruccion leerInstruccion(
                        Proceso proceso,
                        int pc) {

                BCP bcp = obtenerBCP(
                                proceso);

                validarAsignacion(
                                bcp);

                int inicio = bcp.getBase();

                int fin = bcp.getBase()
                                + bcp.getTamanio();

                if (pc < inicio
                                || pc >= fin) {

                        throw new IndexOutOfBoundsException(
                                        "El PC está fuera del programa");
                }

                return (Instruccion) memoria[pc];
        }

        // optiene el contexto de un proceso en memoria, lanza una excepción si el
        // proceso no está cargado

        public int[] obtenerContexto(
                        int pid) {

                int posicion = buscarBCP(
                                pid);

                if (posicion == -1) {

                        throw new IllegalArgumentException(
                                        "No existe un BCP con PID "
                                                        + pid);
                }

                return new int[] {

                                (Integer) memoria[posicion + 2], // PC

                                (Integer) memoria[posicion + 3], // AC

                                (Integer) memoria[posicion + 4], // AX

                                (Integer) memoria[posicion + 5], // BX

                                (Integer) memoria[posicion + 6], // CX

                                (Integer) memoria[posicion + 7] // DX
                };
        }

        public boolean limpiarMemoria() {

                for (int i = 0; i < memoria.length; i++) {

                        memoria[i] = null;
                }

                /*
                 * Limpiar también el mapa de ocupación
                 * de la zona del SO.
                 */
                for (int i = 0; i < ocupadoSO.length; i++) {

                        ocupadoSO[i] = false;
                }

                siguienteIdTrabajo = 0;

                return true;
        }

        /*
         * ==================================================
         * GETTERS
         * ==================================================
         */

        public int getEspacio() {

                return memoria.length;
        }

        public int getEspacioSO() {

                return espacioSO;
        }

        public Object[] getMemoriaSnapshot() {

                return memoria.clone();
        }

        public boolean puedeGuardarBCP() {

                return buscarEspacioSO(
                                TAMANIO_BCP) != -1;
        }

        // valida si el programa cabe en memoria, devuelve true si hay suficiente
        // espacio para cargar el programa, false si no hay suficiente espacio
        public boolean puedeCargarInstrucciones(
                        Programa programa) {

                if (programa == null
                                || programa.getInstrucciones() == null
                                || programa.getInstrucciones().isEmpty()) {

                        return false;
                }

                int tamanioPrograma = programa
                                .getInstrucciones()
                                .size();

                return buscarBloqueLibre(
                                tamanioPrograma) != -1;
        }

        public boolean puedeCargarPrograma(
                        Programa programa) {

                return puedeGuardarBCP()
                                && puedeCargarInstrucciones(
                                                programa);
        }

        public List<Integer> obtenerPidsPreparados() {

                List<Integer> preparados = new ArrayList<>();

                for (int i = 0; i + TAMANIO_BCP <= espacioSO; i++) {

                        if (esBCP(i)
                                        && memoria[i + 1] == BCP.EstadoProceso.PREPARADO) {

                                preparados.add(
                                                (Integer) memoria[i]);

                                i += TAMANIO_BCP - 1;
                        }
                }

                return preparados;
        }

        public int[] obtenerDatosBCP(
                        int pid) {

                int posicion = buscarBCP(
                                pid);

                if (posicion == -1) {

                        return null;
                }

                return new int[] {

                                (Integer) memoria[posicion], // PID

                                (Integer) memoria[posicion + 2], // PC

                                (Integer) memoria[posicion + 9], // Base

                                (Integer) memoria[posicion + 10], // Tamaño

                                (Integer) memoria[posicion + 16] // Orden cola
                };
        }

        /*
         * ==================================================
         * POSICIONES INTERNAS
         * ==================================================
         */

        public int obtenerPosicionBCP(
                        int pid) {

                return buscarBCP(
                                pid);
        }

        public int obtenerPosicionTrabajo(
                        int idTrabajo) {

                return buscarTrabajo(
                                idTrabajo);
        }

        public int getTamanioBCP() {

                return TAMANIO_BCP;
        }

        public int getTamanioTrabajo() {

                return TAMANIO_TRABAJO;
        }

        public int obtenerAH(
                        int pid) {

                int posicion = buscarBCP(
                                pid);

                if (posicion == -1) {

                        throw new IllegalArgumentException(
                                        "No existe un BCP con PID "
                                                        + pid);
                }

                Object valor = memoria[posicion + 24];

                if (valor == null) {
                        return 0;
                }

                return (Integer) valor;
        }

        public String obtenerAL(
                        int pid) {

                int posicion = buscarBCP(
                                pid);

                if (posicion == -1) {

                        throw new IllegalArgumentException(
                                        "No existe un BCP con PID "
                                                        + pid);
                }

                return (String) memoria[posicion + 25];
        }

        public String obtenerTextoDX(
                        int pid) {

                int posicion = buscarBCP(
                                pid);

                if (posicion == -1) {

                        throw new IllegalArgumentException(
                                        "No existe un BCP con PID "
                                                        + pid);
                }

                return (String) memoria[posicion + 26];
        }

        /*
         * ==================================================
         * CONSULTA OCUPACION SO
         * ==================================================
         */

        public boolean estaOcupadoSO(
                        int posicion) {

                if (posicion < 0
                                || posicion >= espacioSO) {

                        return false;
                }

                return ocupadoSO[posicion];
        }
}
