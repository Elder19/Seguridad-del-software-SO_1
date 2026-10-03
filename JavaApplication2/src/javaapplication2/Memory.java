package javaapplication2;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    private static final int TAMANIO_BCP = BCP.getTAMANIO_BCP();
    private static final int TAMANIO_TRABAJO = 4;
    private final Object[] memoria;
    private final int espacioSO;
    private int siguienteIdTrabajo = 0;
   
    // Crea la RAM y reserva un porcentaje para el sistema operativo.
    public Memory(int espacio, double espacioSo) {
        if (espacio < 128) {
            throw new IllegalArgumentException("El tamaño mínimo de memoria es 128");
        }
        if (espacioSo <= 0 || espacioSo >= 1) {
            throw new IllegalArgumentException("El porcentaje del SO debe estar entre 0 y 1");
        }
        memoria = new Object[espacio];
        espacioSO = (int) Math.ceil(espacio * espacioSo);
    }

    // Carga el BCP y las instrucciones de un proceso en RAM.
    public boolean cargarProceso(Proceso proceso) {
        BCP bcp = obtenerBCP(proceso);
        if (proceso.getPrograma() == null) {
            throw new IllegalArgumentException("El proceso no tiene programa");
        }
        if (bcp.getBase() != -1 || buscarBCP(bcp.getPid()) != -1) {
            throw new IllegalStateException("El proceso ya tiene memoria asignada");
        }
        List<Instruccion> instrucciones = proceso.getPrograma().getInstrucciones();
        if (instrucciones == null || instrucciones.isEmpty()) {
            throw new IllegalArgumentException("El programa no tiene instrucciones");
        }
        for (Instruccion instruccion : instrucciones) {
            if (instruccion == null) {
                throw new IllegalArgumentException("Las instrucciones no pueden ser null");
            }
        }
        int posicionBCP = buscarEspacioSO(TAMANIO_BCP);
        int tamanio = instrucciones.size();
        int base = buscarBloqueLibre(tamanio);
        if (posicionBCP == -1 || base == -1) {
            return false;
        }
        for (int i = 0; i < tamanio; i++) {
            memoria[base + i] = instrucciones.get(i);
        }
        bcp.setBase(base);
        bcp.setTamanio(tamanio);
        bcp.setPC(bcp.getPC() + base);
        guardarBCP(posicionBCP, bcp);
        return true;
    }

    // Guarda un trabajo pendiente dentro del espacio del sistema operativo cuando el usuario lo ejecuta
    
    public boolean guardarTrabajo(IndicePrograma indice) {

        if (indice == null) {
            throw new IllegalArgumentException(
                    "Debe proporcionar un índice de programa."
            );
        }

        int posicion =
                buscarEspacioSO(TAMANIO_TRABAJO);

        if (posicion == -1) {
            return false;
        }//descompone el objeto para que se ocupe la memoria como debe ser
        
        memoria[posicion] = indice;
        memoria[posicion + 1] = indice.getNombre();
        memoria[posicion + 2] = indice.getTamanio();
        memoria[posicion + 3] = siguienteIdTrabajo;

        siguienteIdTrabajo++;

        return true;
}

    // Libera de RAM un trabajo pendiente.
    public boolean liberarTrabajo(int idTrabajo) {

        int posicion =
                buscarTrabajo(idTrabajo);

        if (posicion == -1) {
            return false;
        }

        for (int i = 0;
                i < TAMANIO_TRABAJO;
                i++) {

            memoria[posicion + i] = null;
        }

        return true;
    }
    // Busca un trabajo por su identificador.
    private int buscarTrabajo(int idTrabajo) {

        for (int i = 0;
                i + TAMANIO_TRABAJO <= espacioSO;
                i++) {

            if (esTrabajo(i)
                    && ((Integer) memoria[i + 3])
                    == idTrabajo) {

                return i;
            }
        }

        return -1;
    
}

    private boolean esTrabajo(int posicion) {

    return posicion >= 0
            && posicion + TAMANIO_TRABAJO <= espacioSO && memoria[posicion]instanceof IndicePrograma
            && memoria[posicion + 1]instanceof String&& memoria[posicion + 2]instanceof Integer
            && memoria[posicion + 3] instanceof Integer;
}


    //trabajos pendientes
    public List<Object[]> obtenerTrabajos() {

        List<Object[]> trabajos =
                new ArrayList<>();

        for (int i = 0;
                i + TAMANIO_TRABAJO <= espacioSO;
                i++) {

            if (esTrabajo(i)) {

                trabajos.add(
                        new Object[]{
                            memoria[i],
                            memoria[i + 1],
                            memoria[i + 2],
                            memoria[i + 3]
                        }
                );
            }
        }

        return trabajos;
    }


//trbajos en admisioon? 
    public boolean hayTrabajos() {
        return !obtenerTrabajos().isEmpty();
    }

    // Comprueba si una posición corresponde al inicio de un BCP.
   private boolean esBCP(int posicion) {
            return posicion >= 0
                    && posicion + TAMANIO_BCP <= espacioSO
                    && memoria[posicion] instanceof Integer
                    && memoria[posicion + 1] instanceof BCP.EstadoProceso;
        }

        // Busca un bloque consecutivo libre dentro del espacio reservado al SO.
        private int buscarEspacioSO(int tamanio) {
            int consecutivas = 0;
            for (int i = 0; i < espacioSO; i++) {
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

    // Guarda todos los datos del BCP dentro del espacio del SO.
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
        memoria[posicion + 10] = bcp.getOrdenCola();
    }

    // Actualiza en RAM los valores actuales del BCP.
    public void actualizarBCP(Proceso proceso) {
        BCP bcp = obtenerBCP(proceso);
        int posicion = validarAsignacion(bcp);
        guardarBCP(posicion, bcp);
    }

    // Libera las instrucciones y el BCP de un proceso.
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

    // Busca el BCP correspondiente a un PID dentro del espacio del SO.
    private int buscarBCP(int pid) {
        for (int i = 0; i + TAMANIO_BCP <= espacioSO; i++) {
            if (esBCP(i) && ((Integer) memoria[i]) == pid) {
                return i;
            }
        }
        return -1;
    }

    // Busca un bloque consecutivo para instrucciones en la zona de usuario.
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

    // Obtiene el BCP asociado a un proceso y valida que exista.
    private BCP obtenerBCP(Proceso proceso) {
        if (proceso == null || proceso.getBcp() == null) {
            throw new IllegalArgumentException("Debe proporcionar un proceso con BCP");
        }
        return proceso.getBcp();
    }

    // Comprueba que la base y tamaño del BCP coincidan con lo guardado en RAM.
    private int validarAsignacion(BCP bcp) {
        int posicion = buscarBCP(bcp.getPid());
        if (posicion == -1) {
            throw new IllegalStateException("El proceso no está cargado en esta memoria");
        }
        int baseGuardada = (Integer) memoria[posicion + 4];
        int tamanioGuardado = (Integer) memoria[posicion + 5];
        if (bcp.getBase() != baseGuardada || bcp.getTamanio() != tamanioGuardado) {
            throw new IllegalStateException("La base o el tamaño del BCP fueron modificados");
        }
        return posicion;
    }

    // Lee una instrucción utilizando la dirección actual del PC.
    public Instruccion leerInstruccion(Proceso proceso, int pc) {
        BCP bcp = obtenerBCP(proceso);
        validarAsignacion(bcp);
        int inicio = bcp.getBase();
        int fin = bcp.getBase() + bcp.getTamanio();
        if (pc < inicio || pc >= fin) {
            throw new IndexOutOfBoundsException("El PC está fuera del programa");
        }
        return (Instruccion) memoria[pc];
    }

    // Obtiene los registros necesarios para cargar el contexto de un proceso en CPU.
    public int[] obtenerContexto(int pid) {
        int posicion = buscarBCP(pid);
        if (posicion == -1) {
            throw new IllegalArgumentException("No existe un BCP con PID " + pid);
        }
        return new int[]{
            (Integer) memoria[posicion + 2],
            (Integer) memoria[posicion + 3],
            (Integer) memoria[posicion + 6],
            (Integer) memoria[posicion + 7],
            (Integer) memoria[posicion + 8],
            (Integer) memoria[posicion + 9]
        };
    }



    // Borra completamente el contenido de la RAM.
    public boolean limpiarMemoria() {
        for (int i = 0; i < memoria.length; i++) {
            memoria[i] = null;
        }
        return true;
    }

    // Devuelve el tamaño total de la RAM.
    public int getEspacio() {
        return memoria.length;
    }

    // Devuelve cuánto espacio de RAM está reservado para el SO.
    public int getEspacioSO() {
        return espacioSO;
    }

    // Devuelve una copia del contenido actual de la RAM.
    public Object[] getMemoriaSnapshot() {
        return memoria.clone();
    }
    
    //se usa en planificador de tareas para admintir el siguiente 
    public boolean puedeCargarPrograma(Programa programa) {

    if (programa == null
            || programa.getInstrucciones() == null
            || programa.getInstrucciones().isEmpty()) {
        return false;
    }

    // Espacio para el BCP dentro de la zona del SO
    int espacioBCP = buscarEspacioSO(TAMANIO_BCP);

    // Espacio para las instrucciones en memoria de usuario
    int tamanioPrograma = programa.getInstrucciones().size();
    int espacioUsuario = buscarBloqueLibre(tamanioPrograma);

    return espacioBCP != -1 && espacioUsuario != -1;
}
}