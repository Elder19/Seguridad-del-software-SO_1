package javaapplication2.disco;

import javaapplication2.programa.Instruccion;
import javaapplication2.programa.Programa;
import javaapplication2.procesos.Proceso;

public class Disco {
    private final int memoriaVirtual;
    private final int memoriaTotal;
    private final int totalIndices;

    private final Object[] disco;
    private final int inicioArchivos;
    private final int inicioVirtual;

    public Disco(int memoriaVirtual, int memoriaTotal) {

        if (memoriaTotal <= 256) {
            throw new IllegalArgumentException(
                    "El tamaño del disco debe ser mayor que 0.");
        }

        this.memoriaTotal = memoriaTotal;
        this.memoriaVirtual = memoriaVirtual;
        this.totalIndices = (int) Math.ceil(memoriaTotal * 0.10);

        this.disco = new Object[memoriaTotal];

        this.inicioArchivos = this.totalIndices;

        this.inicioVirtual = memoriaTotal - memoriaVirtual;
    }

    /*---------------- CARGAR PROGRAMA ----------------*/

    // guarda el programa en disco y crea un índice para él
    public void cargarPrograma(
            String nombre,
            java.util.List<String> instrucciones) {

        if (nombre == null || instrucciones == null) {
            throw new IllegalArgumentException(
                    "El nombre y las instrucciones no pueden ser null.");
        }

        int tamanio = instrucciones.size();

        int inicio = buscarBloque(tamanio);

        if (inicio == -1) {
            throw new IllegalStateException(
                    "No hay espacio suficiente para almacenar el programa.");
        }

        int posicionIndice = buscarIndiceLibre();

        if (posicionIndice == -1) {
            throw new IllegalStateException(
                    "No hay espacio disponible en el índice de archivos.");
        }

        // Guardar instrucciones directamente en disco
        for (int i = 0; i < tamanio; i++) {
            disco[inicio + i] = instrucciones.get(i);
        }

        // Crear índice
        disco[posicionIndice] = new IndicePrograma(
                nombre,
                inicio,
                tamanio);
    }

    // Recupera un programa del disco usando su índice
    public Programa obtenerPrograma(IndicePrograma indice) {

        java.util.List<String> instrucciones = new java.util.ArrayList<>();

        int inicio = indice.getDireccion();
        int tamanio = indice.getTamanio();

        for (int i = 0; i < tamanio; i++) {

            instrucciones.add(
                    disco[inicio + i].toString());
        }

        return new Programa(
                indice.getNombre(),
                instrucciones);
    }

    /*---------------- ÍNDICE ----------------*/
    // busca por el índice libre en el disco para almacenar un nuevo programa
    private int buscarIndiceLibre() {

        for (int i = 0; i < totalIndices; i++) {

            if (disco[i] == null) {
                return i;
            }
        }

        return -1;
    }

    /*---------------- ARCHIVOS ----------------*/

    // Busca un bloque de espacio libre en el disco para almacenar un programa
    private int buscarBloque(int tamanio) {

        int mejorInicio = -1;
        int mejorTamanio = Integer.MAX_VALUE;

        int inicioActual = -1;
        int tamanioActual = 0;

        // Recorre desde el inicio de los archivos hasta el inicio de la memoria virtual
        for (int i = inicioArchivos; i <= inicioVirtual; i++) {

            if (i < inicioVirtual && disco[i] == null) {

                if (tamanioActual == 0) {
                    inicioActual = i;
                }

                tamanioActual++;

            } else {

                if (tamanioActual >= tamanio
                        && tamanioActual < mejorTamanio) {

                    mejorInicio = inicioActual;
                    mejorTamanio = tamanioActual;
                }

                inicioActual = -1;
                tamanioActual = 0;
            }
        }

        return mejorInicio;
    }

    // Devuelve una lista de todos los programas ejecutables (.asm) en el disco

    public java.util.List<IndicePrograma> getIndicesProgramas() {

        java.util.List<IndicePrograma> indices = new java.util.ArrayList<>();

        for (int i = 0; i < totalIndices; i++) {

            if (disco[i] instanceof IndicePrograma) {

                IndicePrograma indice = (IndicePrograma) disco[i];

                if (indice.getNombre() != null
                        && indice.getNombre()
                                .toLowerCase()
                                .endsWith(".asm")) {

                    indices.add(indice);
                }
            }
        }

        return indices;
    }

    // ---------------- LIMPIAR MEMORIA ----------------
    public boolean limpiarMemoria() {
        for (int i = 0; i < disco.length; i++) {
            disco[i] = null;
        }
        return true;
    }

    // ---------------- MEMORIA VIRTUAL ----------------

    // retorna el tamaño que ocupará un proceso en memoria virtual
    private int tamanioProcesoVirtual(Proceso proceso) {

        return 1
                + proceso.getPrograma()
                        .getInstrucciones()
                        .size();
    }

    // Indica si un programa cabe en memoria virtual.
    // las instrucciones del programa se almacenan en memoria virtual, mientras que
    // el BCP se mantiene en RAM.
    public boolean cabeVirtual(Programa programa) {

        if (programa == null
                || programa.getInstrucciones() == null
                || programa.getInstrucciones().isEmpty()) {

            return false;
        }

        int tamanio = 1
                + programa.getInstrucciones().size();

        return buscarBloqueVirtual(tamanio) != -1;
    }

    /*
     * Sobrecarga útil cuando ya existe el proceso.
     */
    public boolean cabeVirtual(Proceso proceso) {

        if (proceso == null) {
            return false;
        }

        return cabeVirtual(
                proceso.getPrograma());
    }

    // Busca un bloque de espacio libre en memoria virtual para almacenar un proceso
    private int buscarBloqueVirtual(int tamanio) {

        int consecutivas = 0;

        for (int i = inicioVirtual; i < memoriaTotal; i++) {

            if (disco[i] == null) {

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

    // Carga un proceso en memoria virtual, incluyendo su PID y sus instrucciones
    public boolean cargarEnVirtual(Proceso proceso) {

        if (proceso == null
                || proceso.getPrograma() == null) {

            throw new IllegalArgumentException(
                    "Proceso inválido.");
        }

        int pid = proceso.getBcp().getPid();

        if (buscarProcesoVirtual(pid) != -1) {

            throw new IllegalStateException(
                    "El proceso ya está en memoria virtual.");
        }

        int tamanio = tamanioProcesoVirtual(proceso);

        int inicio = buscarBloqueVirtual(tamanio);

        if (inicio == -1) {
            return false;
        }
        disco[inicio] = pid;

        int posicion = inicio + 1;

        for (Instruccion instruccion : proceso.getPrograma()
                .getInstrucciones()) {

            disco[posicion] = instruccion;

            posicion++;
        }

        return true;
    }

    // Busca un proceso en memoria virtual por su PID y devuelve la posición de
    // inicio de sus instrucciones
    private int buscarProcesoVirtual(int pid) {

        for (int i = inicioVirtual; i < memoriaTotal; i++) {

            if (disco[i] instanceof Integer
                    && ((Integer) disco[i]) == pid) {

                return i;
            }
        }

        return -1;
    }

    // Obtiene las instrucciones de un proceso desde memoria virtual
    public java.util.List<Instruccion> obtenerInstruccionesVirtuales(
            Proceso proceso) {

        java.util.List<Instruccion> instrucciones = new java.util.ArrayList<>();

        if (proceso == null
                || proceso.getPrograma() == null) {

            return instrucciones;
        }

        int inicio = buscarProcesoVirtual(
                proceso.getBcp().getPid());

        if (inicio == -1) {
            return instrucciones;
        }

        int cantidad = proceso.getPrograma()
                .getInstrucciones()
                .size();

        // Recupera las instrucciones del proceso desde memoria virtual
        for (int i = 0; i < cantidad; i++) {

            Object valor = disco[inicio + 1 + i];

            if (valor instanceof Instruccion) {

                instrucciones.add(
                        (Instruccion) valor);
            }
        }

        return instrucciones;
    }
    // Libera el espacio ocupado por un proceso en memoria virtual

    public boolean liberarDeVirtual(
            Proceso proceso) {

        if (proceso == null
                || proceso.getPrograma() == null) {

            return false;
        }

        int inicio = buscarProcesoVirtual(
                proceso.getBcp().getPid());

        if (inicio == -1) {
            return false;
        }

        int tamanio = tamanioProcesoVirtual(proceso);

        for (int i = 0; i < tamanio; i++) {

            disco[inicio + i] = null;
        }

        return true;
    }

    // Verifica si un proceso está actualmente en memoria virtual
    public boolean estaEnVirtual(int pid) {

        return buscarProcesoVirtual(pid) != -1;
    }

    public IndicePrograma obtenerIndicePorDireccion(int direccion) {

        for (int i = 0; i < totalIndices; i++) {

            if (disco[i] instanceof IndicePrograma) {

                IndicePrograma indice = (IndicePrograma) disco[i];

                if (indice.getDireccion() == direccion) {
                    return indice;
                }
            }
        }

        return null;
    }

    /*---------------- GETTERS ----------------*/

    public int getMemoriaVirtual() {
        return memoriaVirtual;
    }

    public int getMemoriaTotal() {
        return memoriaTotal;
    }

    public int getTotalIndices() {
        return totalIndices;
    }

    public int getInicioArchivos() {
        return inicioArchivos;
    }

    public int getInicioVirtual() {
        return inicioVirtual;
    }

    public Object[] getDiscoSnapshot() {
        return disco.clone();
    }

    /*---------------- CREAR ARCHIVO ----------------*/

    public void crearArchivo(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacío.");
        }

        if (buscarIndicePorNombre(nombre) != null) {
            throw new IllegalStateException("El archivo ya existe.");
        }

        int posicionIndice = buscarIndiceLibre();

        if (posicionIndice == -1) {
            throw new IllegalStateException("No hay espacio disponible en el índice.");
        }

        disco[posicionIndice] = new IndicePrograma(nombre, -1, 0);
    }

    /*---------------- ABRIR ARCHIVO ----------------*/

    public void abrirArchivo(String nombre) {

        if (buscarIndicePorNombre(nombre) == null) {
            throw new IllegalStateException("El archivo no existe.");
        }
    }

    /*---------------- LEER ARCHIVO ----------------*/

    public String leerArchivo(String nombre) {

        IndicePrograma indice = buscarIndicePorNombre(nombre);

        if (indice == null) {
            throw new IllegalStateException("El archivo no existe.");
        }

        if (indice.getTamanio() == 0) {
            return "";
        }

        String contenido = "";

        for (int i = 0; i < indice.getTamanio(); i++) {
            contenido += disco[indice.getDireccion() + i].toString();
        }

        return contenido;
    }

    /*---------------- ESCRIBIR ARCHIVO ----------------*/

    public void escribirArchivo(String nombre, String contenido) {

        IndicePrograma indice = buscarIndicePorNombre(nombre);

        if (indice == null) {
            throw new IllegalStateException("El archivo no existe.");
        }

        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null.");
        }

        int posicionIndice = buscarPosicionIndice(nombre);

        if (indice.getDireccion() != -1) {

            for (int i = 0; i < indice.getTamanio(); i++) {
                disco[indice.getDireccion() + i] = null;
            }
        }

        if (contenido.isEmpty()) {
            disco[posicionIndice] = new IndicePrograma(nombre, -1, 0);
            return;
        }

        int inicio = buscarBloque(contenido.length());

        if (inicio == -1) {
            throw new IllegalStateException("No hay espacio suficiente en disco.");
        }

        for (int i = 0; i < contenido.length(); i++) {
            disco[inicio + i] = String.valueOf(contenido.charAt(i));
        }

        disco[posicionIndice] = new IndicePrograma(nombre, inicio, contenido.length());
    }

    /*---------------- ELIMINAR ARCHIVO ----------------*/

    public void eliminarArchivo(String nombre) {

        int posicionIndice = buscarPosicionIndice(nombre);

        if (posicionIndice == -1) {
            throw new IllegalStateException("El archivo no existe.");
        }

        IndicePrograma indice = (IndicePrograma) disco[posicionIndice];

        if (indice.getDireccion() != -1) {

            for (int i = 0; i < indice.getTamanio(); i++) {
                disco[indice.getDireccion() + i] = null;
            }
        }

        disco[posicionIndice] = null;
    }

    /*---------------- BUSCAR ARCHIVO ----------------*/

    private IndicePrograma buscarIndicePorNombre(String nombre) {

        for (int i = 0; i < totalIndices; i++) {

            if (disco[i] instanceof IndicePrograma) {

                IndicePrograma indice = (IndicePrograma) disco[i];

                if (indice.getNombre().equalsIgnoreCase(nombre)) {
                    return indice;
                }
            }
        }

        return null;
    }

    /*---------------- BUSCAR POSICION DEL INDICE ----------------*/

    private int buscarPosicionIndice(String nombre) {

        for (int i = 0; i < totalIndices; i++) {

            if (disco[i] instanceof IndicePrograma) {

                IndicePrograma indice = (IndicePrograma) disco[i];

                if (indice.getNombre().equalsIgnoreCase(nombre)) {
                    return i;
                }
            }
        }

        return -1;
    }

}
