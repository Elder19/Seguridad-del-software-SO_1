package javaapplication2;

public class Disco {
    private static final int TAMANIO_BCP = BCP.getTAMANIO_BCP();// tamaño para calcular los bloques del bcp
    private final int memoriaVirtual;
    private final int memoriaTotal;
    private final int totalIndices;

    private final Object[] disco;
    private final int inicioArchivos;
    private final int inicioVirtual;

    public Disco(int memoriaVirtual, int memoriaTotal) {

        if (memoriaTotal <= 256) {
            throw new IllegalArgumentException(
                    "El tamaño del disco debe ser mayor que 0."
            );
        }
        
        this.memoriaTotal = memoriaTotal;
        this.memoriaVirtual = memoriaVirtual;
       this.totalIndices = (int) Math.ceil(memoriaTotal * 0.10);
    
       
        this.disco = new Object[memoriaTotal];

        this.inicioArchivos = this.totalIndices;

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
    

    public boolean limpiarMemoria() {
       for (int i = 0; i < disco.length; i++) {
           disco[i] = null;
       }
           return true; 
   }

    /* Calcula cuánto espacio necesita el proceso en memoria virtual */
   private int tamanioProcesoVirtual(Proceso proceso) {
       return TAMANIO_BCP + proceso.getPrograma().getInstrucciones().size();
   }


   /* Revisa si el proceso completo cabe en memoria virtual */
   public boolean cabeVirtual(Proceso proceso) {

       if (proceso == null || proceso.getPrograma() == null) {
           return false;
       }

       int tamanio = tamanioProcesoVirtual(proceso);

       return buscarBloqueVirtual(tamanio) != -1;
   }


   /* Busca un bloque consecutivo libre en memoria virtual */
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


   /* Guarda BCP + instrucciones del proceso en memoria virtual */
   public boolean cargarEnVirtual(Proceso proceso) {

       if (proceso == null || proceso.getPrograma() == null) {
           throw new IllegalArgumentException("Proceso inválido");
       }

       if (buscarProcesoVirtual(proceso.getBcp().getPid()) != -1) {
           throw new IllegalStateException("El proceso ya está en memoria virtual");
       }

       int tamanio = tamanioProcesoVirtual(proceso);
       int inicio = buscarBloqueVirtual(tamanio);

       if (inicio == -1) {
           return false;
       }

       BCP bcp = proceso.getBcp();

       disco[inicio] = bcp.getPid();
       disco[inicio + 1] = BCP.EstadoProceso.SUSPENDIDO;
       disco[inicio + 2] = bcp.getPC();
       disco[inicio + 3] = bcp.getAC();
       disco[inicio + 4] = bcp.getBase();
       disco[inicio + 5] = bcp.getTamanio();
       disco[inicio + 6] = bcp.getAX();
       disco[inicio + 7] = bcp.getBX();
       disco[inicio + 8] = bcp.getCX();
       disco[inicio + 9] = bcp.getDX();

       int posicion = inicio + TAMANIO_BCP;

       for (Instruccion instruccion : proceso.getPrograma().getInstrucciones()) {
           disco[posicion++] = instruccion;
       }

       return true;
   }


   /* Busca un proceso por PID dentro de memoria virtual */
   private int buscarProcesoVirtual(int pid) {

       for (int i = inicioVirtual; i < memoriaTotal; i++) {

           if (disco[i] instanceof Integer
                   && ((Integer) disco[i]).intValue() == pid) {
               return i;
           }
       }

       return -1;
   }


   /* Libera el espacio ocupado por un proceso en memoria virtual */
   public boolean liberarDeVirtual(Proceso proceso) {

       if (proceso == null || proceso.getPrograma() == null) {
           return false;
       }

       int inicio = buscarProcesoVirtual(proceso.getBcp().getPid());

       if (inicio == -1) {
           return false;
       }

       int tamanio = tamanioProcesoVirtual(proceso);

       for (int i = 0; i < tamanio; i++) {
           disco[inicio + i] = null;
       }

       return true;
   }
   public IndicePrograma obtenerIndicePorDireccion(int direccion) {

    for (int i = 0; i < totalIndices; i++) {

        if (disco[i] instanceof IndicePrograma) {

            IndicePrograma indice =
                    (IndicePrograma) disco[i];

            if (indice.getDireccion() == direccion) {
                return indice;
            }
        }
    }

    return null;
}


   /* Indica si un proceso está actualmente en memoria virtual */
   public boolean estaEnVirtual(int pid) {
       return buscarProcesoVirtual(pid) != -1;
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