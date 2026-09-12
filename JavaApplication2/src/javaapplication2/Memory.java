package javaapplication2;

import java.util.List;

public class Memory {

    private static final int TAMANIO_BCP = 10;// tamaño para calcular los bloques del bcp

    private final Object[] memoria;
    private final int espacioSO;

    public Memory() {

        memoria = new Object[128];
        espacioSO = (int) Math.ceil(128 * 0.20);
    }

    public Memory(int espacio) {
        if (espacio < 128) {
            throw new IllegalArgumentException(
                    "El tamaño mínimo de memoria es 128"
            );
        }
        memoria = new Object[espacio];
        espacioSO = (int) Math.ceil(espacio * 0.20);
    }

    /*
     * Carga los atributos del BCP en el SO y las instrucciones
     * en la zona de usuario.
     */
    public boolean cargarProceso(Proceso proceso) {
        BCP bcp = obtenerBCP(proceso);
        if (proceso.getPrograma() == null) {
            throw new IllegalArgumentException(
                    "El proceso no tiene programa"
            );
        }
        if (bcp.getBase() != -1 || buscarBCP(bcp.getPid()) != -1) {
            throw new IllegalStateException(
                    "El proceso ya tiene memoria asignada"
            );
        }
        List<Instruccion> instrucciones = proceso.getPrograma().getInstrucciones();

        if (instrucciones == null || instrucciones.isEmpty()) {
            throw new IllegalArgumentException(
                    "El programa no tiene instrucciones"
            );
        }
        for (Instruccion instruccion : instrucciones) {
            if (instruccion == null) {
                throw new IllegalArgumentException(
                        "Las instrucciones no pueden ser null"
                );
            }
        }
        int posicionBCP = buscarEspacioBCP();
        int tamanio = instrucciones.size();
        int base = buscarBloqueLibre(tamanio);

        // No modifica nada si alguna de las zonas no tiene espacio.
        if (posicionBCP == -1 || base == -1) {
            return false;
        }
        for (int i = 0; i < tamanio; i++) {
            memoria[base + i] = instrucciones.get(i);
        }
        bcp.setBase(base);
        bcp.setTamanio(tamanio);
        bcp.setPC(0);
        guardarBCP(posicionBCP, bcp);

        return true;
    }

    public boolean limpiarMemoria() {
        for (int i = 0; i < memoria.length; i++) {
            memoria[i] = null;
        }
        return true;
    }

    // Cada atributo ocupa una posición del arreglo.
    private void guardarBCP(int posicion, BCP bcp) {
        memoria[posicion] = bcp.getPid();
        memoria[posicion + 1] = bcp.getEstadoProceso();
        memoria[posicion + 2] = bcp.getPC();
        memoria[posicion + 3] = bcp.getAC();
        memoria[posicion + 4] = bcp.getBase();
        memoria[posicion + 5] = bcp.getTamanio();
        memoria[posicion + 6] = bcp.getAX();
        memoria[posicion + 7] = bcp.getBX();
        memoria[posicion + 8] = bcp.getCX();
        memoria[posicion + 9] = bcp.getDX();
    }

    /*
     * Copia el estado y los registros actuales del BCP a memoria.
     */
    public void actualizarBCP(Proceso proceso) {
        BCP bcp = obtenerBCP(proceso);
        int posicion = validarAsignacion(bcp);
        guardarBCP(posicion, bcp);
    }

    // Libera tanto las instrucciones como las celdas del BCP.
    public void liberarProceso(Proceso proceso) {
        BCP bcp = obtenerBCP(proceso);
        int posicionBCP = validarAsignacion(bcp);
        for (int i = 0; i < bcp.getTamanio(); i++) {
            memoria[bcp.getBase() + i] = null;
        }
        for (int i = 0; i < TAMANIO_BCP; i++) {
            memoria[posicionBCP + i] = null;
        }
        bcp.setBase(-1);
        bcp.setTamanio(0);
    }

    /*
     * La zona SO se organiza en bloques de 10 posiciones.
     * El PID ocupa siempre la primera posición de cada bloque.
     */
    private int buscarEspacioBCP() {

        for (int i = 0;
                i + TAMANIO_BCP <= espacioSO;
                i += TAMANIO_BCP) {

            if (memoria[i] == null) {
                return i;
            }
        }

        return -1;
    }

    /*Compara los bloques en el Mso para buscar los bcp*/
    private int buscarBCP(int pid) {

        for (int i = 0;
                i + TAMANIO_BCP <= espacioSO;
                i += TAMANIO_BCP) {

            if (memoria[i] != null
                    && ((Integer) memoria[i]).intValue() == pid) {
                return i;
            }
        }

        return -1;
    }

    // Busca instrucciones consecutivas libres en la zona de usuario.
    private int buscarBloqueLibre(int tamanio) {
        int consecutivas = 0;
        for (int i = espacioSO; i < memoria.length; i++) {
            if (memoria[i] == null) {
                consecutivas++;
                if (consecutivas == tamanio) {
                    return i - tamanio + 1;
                }
            } else {
                consecutivas = 0;
            }
        }
        return -1;
    }

    /*recibe un objeto tipo proceso para buscar su bcp*/
    private BCP obtenerBCP(Proceso proceso) {
        if (proceso == null || proceso.getBcp() == null) {
            throw new IllegalArgumentException(
                    "Debe proporcionar un proceso con BCP"
            );
        }
        return proceso.getBcp();
    }

    /*
     * Comprueba que la base y el tamaño coincidan
     * con la asignación registrada en memoria.
     */
    private int validarAsignacion(BCP bcp) {
        int posicion = buscarBCP(bcp.getPid());
        if (posicion == -1) {
            throw new IllegalStateException(
                    "El proceso no está cargado en esta memoria"
            );
        }
        int baseGuardada = (Integer) memoria[posicion + 4];
        int tamanioGuardado = (Integer) memoria[posicion + 5];
        if (bcp.getBase() != baseGuardada
                || bcp.getTamanio() != tamanioGuardado) {

            throw new IllegalStateException(
                    "La base o el tamaño del BCP fueron modificados"
            );
        }

        return posicion;
    }

    public int getEspacio() {
        return memoria.length;
    }

    public int getEspacioSO() {
        return espacioSO;
    }

    /*
     * Devuelve una copia de la memoria para que la interfaz pueda
     * representarla sin modificar el arreglo interno.
     */
    public Object[] getMemoriaSnapshot() {
        return memoria.clone();
    }

    public Instruccion leerInstruccion(
            Proceso proceso,
            int pc) {

        BCP bcp = obtenerBCP(proceso);
        validarAsignacion(bcp);

        if (pc < 0 || pc >= bcp.getTamanio()) {
            throw new IndexOutOfBoundsException(
                    "El PC está fuera del programa"
            );
        }
        return (Instruccion) memoria[bcp.getBase() + pc];
    }

    /*busca el BCP de un proceso por su PID y devuelve los registros necesarios para reconstruir el contexto de la CPU.*/
    public int[] obtenerContexto(int pid) {

        int posicion = buscarBCP(pid);

        if (posicion == -1) {
            throw new IllegalArgumentException(
                    "No existe un BCP con PID " + pid
            );
        }

        return new int[]{
            (Integer) memoria[posicion + 2], // PC
            (Integer) memoria[posicion + 3], // AC
            (Integer) memoria[posicion + 6], // AX
            (Integer) memoria[posicion + 7], // BX
            (Integer) memoria[posicion + 8], // CX
            (Integer) memoria[posicion + 9] // DX
        };
    }

    public void imprimirMemoria() {

        String[] atributos = {
            "PID", "Estado", "PC", "AC", "Base", "Tamaño", "AX", "BX", "CX", "DX"
        };

        for (int i = 0; i < memoria.length; i++) {

            String zona = i < espacioSO ? "SO" : "Usuario";
            String contenido;

            if (memoria[i] == null) {
                contenido = "Libre";

            } else if (i < espacioSO) {
                contenido = atributos[i % TAMANIO_BCP]
                        + ": " + String.valueOf(memoria[i]);

            } else {
                contenido = String.valueOf(memoria[i]);
            }

            System.out.println(
                    "[" + i + "] " + zona + " | " + contenido
            );
        }
    }
}
