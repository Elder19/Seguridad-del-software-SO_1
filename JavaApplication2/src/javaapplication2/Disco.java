package javaapplication2;

public class Disco {

    private final int memoriaVirtual;
    private final int memoriaTotal;
    private final int totalIndices;

    private final Object[] disco;

    
    private final int inicioArchivos;
    private final int inicioVirtual;

    public Disco(int memoriaVirtual, int memoriaTotal,int totalIndices) {

        if (memoriaTotal <= 256) {
            throw new IllegalArgumentException(
                    "El tamaño del disco debe ser mayor que 0."
            );
        }

        if (totalIndices < 0 || memoriaVirtual < 0) {
            throw new IllegalArgumentException(
                    "Los tamaños no pueden ser negativos."
            );
        }

        if (totalIndices + memoriaVirtual >= memoriaTotal) {
            throw new IllegalArgumentException(
                    "La distribución del disco no es válida."
            );
        }
        this.memoriaTotal = memoriaTotal;
        this.memoriaVirtual = memoriaVirtual;
        this.totalIndices = totalIndices;

        this.disco = new Object[memoriaTotal];

        this.inicioArchivos = totalIndices;

        this.inicioVirtual = memoriaTotal - memoriaVirtual;
    }


        /*---------------- CARGAR PROGRAMA ----------------*/
    public void cargarPrograma(
            String nombre,
            java.util.List<String> instrucciones) {

        if (nombre == null || instrucciones == null) {
            throw new IllegalArgumentException(
                    "El nombre y las instrucciones no pueden ser null."
            );
        }

        int tamanio = instrucciones.size();

        int inicio = buscarBloque(tamanio);

        if (inicio == -1) {
            throw new IllegalStateException(
                    "No hay espacio suficiente para almacenar el programa."
            );
        }

        int posicionIndice = buscarIndiceLibre();

        if (posicionIndice == -1) {
            throw new IllegalStateException(
                    "No hay espacio disponible en el índice de archivos."
            );
        }

        // Guardar instrucciones directamente en disco
        for (int i = 0; i < tamanio; i++) {
            disco[inicio + i] = instrucciones.get(i);
        }

        // Crear índice
        disco[posicionIndice] =
                new IndicePrograma(
                        nombre,
                        inicio,
                        tamanio
                );
    }
    public Programa obtenerPrograma(IndicePrograma indice) {

    java.util.List<String> instrucciones =
            new java.util.ArrayList<>();

    int inicio = indice.getDireccion();
    int tamanio = indice.getTamanio();

    for (int i = 0; i < tamanio; i++) {

        instrucciones.add(
                disco[inicio + i].toString()
        );
    }

    return new Programa(
            indice.getNombre(),
            instrucciones
    );
}

    /*---------------- ÍNDICE ----------------*/

    private int buscarIndiceLibre() {

        for (int i = 0; i < totalIndices; i++) {

            if (disco[i] == null) {
                return i;
            }
        }

        return -1;
    }


    /*---------------- ARCHIVOS ----------------*/

    /*
     * Busca mediante BEST FIT el bloque libre más pequeño
     * en el que pueda entrar el programa.
     */
    private int buscarBloque(int tamanio) {

        int mejorInicio = -1;
        int mejorTamanio = Integer.MAX_VALUE;

        int inicioActual = -1;
        int tamanioActual = 0;

        /*
         * Empieza después del índice termina antes de memoria virtual.
         */
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
    
    public java.util.List<IndicePrograma> getIndicesProgramas() {

    java.util.List<IndicePrograma> indices =
            new java.util.ArrayList<>();

    for (int i = 0; i < totalIndices; i++) {

        if (disco[i] instanceof IndicePrograma) {

            indices.add(
                    (IndicePrograma) disco[i]
            );
        }
    }

    return indices;
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
}