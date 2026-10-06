package javaapplication2.cpu;

import javaapplication2.procesos.BCP;
import javaapplication2.disco.Disco;
import javaapplication2.programa.Instruccion;
import javaapplication2.memoria.Memory;
import javaapplication2.procesos.Proceso;

public class CPU {
        private boolean esperandoTeclado = false;

        private int PC;
        private int AC;
        private int AX;
        private int BX;
        private int CX;
        private int DX;

        private String IR;
        private int Segundero;
        private String textoDX;

        /*---------------- COMPARACIONES ----------------*/
        private boolean comparacionIgual;
        /*---------------- ENTRADA / SALIDA ----------------*/
        private Integer entradaTeclado;

        private String salidaMonitor;

        /*---------------- FINALIZACION ----------------*/

        private boolean programaFinalizado;

        public CPU() {

                this.PC = 0;
                this.AC = 0;

                this.AX = 0;
                this.BX = 0;
                this.CX = 0;
                this.DX = 0;
                this.IR = null;
                this.Segundero = 0;
                this.textoDX = null;
                this.comparacionIgual = false;
                this.entradaTeclado = null;
                this.salidaMonitor = "";
                this.programaFinalizado = false;
        }

        // Recupera el contexto de un proceso

        public void cargarContexto(
                        Memory memory,
                        int pid) {

                int[] contexto = memory.obtenerContexto(pid);

                this.PC = contexto[0];

                this.AC = contexto[1];

                this.AX = contexto[2];
                this.BX = contexto[3];
                this.CX = contexto[4];
                this.DX = contexto[5];

                /*
                 * AH y AL se reconstruyen automáticamente a partir de AX,
                 * por lo que no se cargan de forma independiente.
                 */
                this.textoDX = memory.obtenerTextoDX(pid);
                this.IR = null;
                this.Segundero = 0;

                this.programaFinalizado = false;
        }

        /* intrucciones */
        // recibe el disco la memoria y el proceso pregunta cual es la intruccion la
        // ejecuta y actualza bcp y demas
        public void ejecutarInstruccion(
                        Memory memory,
                        Disco disco,
                        Proceso proceso) {

                if (programaFinalizado) {
                        return;
                }

                if (proceso == null) {

                        throw new IllegalArgumentException(
                                        "No existe un proceso para ejecutar.");
                }

                if (proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA) {

                        return;
                }
                // para a aejecucion el estado para ejecutar
                if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.FINALIZADO) {

                        proceso.getBcp().setEstadoProceso(
                                        BCP.EstadoProceso.EJECUCION);
                }

                Instruccion instruccion = memory.leerInstruccion(
                                proceso,
                                PC);

                if (instruccion == null) {

                        throw new IllegalStateException(
                                        "No existe una instrucción en PC = "
                                                        + PC);
                }

                this.IR = instruccion.toString();

                String operador = instruccion
                                .getOperacion()
                                .toUpperCase();

                this.Segundero++;

                proceso.getBcp().setTiempoEmpleado(
                                proceso.getBcp().getTiempoEmpleado() + 1);

                try {
                        if (Segundero >= instruccion.ObtenerPeso(operador)) {

                                boolean avanzarPC = true;

                                switch (operador) {

                                        /*---------------- MOV ----------------*/

                                        case "MOV":

                                                ejecutarMOV(
                                                                instruccion);

                                                break;

                                        /*---------------- LOAD ----------------*/

                                        case "LOAD":

                                                ejecutarLOAD(
                                                                instruccion);

                                                break;

                                        /*---------------- STORE ----------------*/

                                        case "STORE":

                                                ejecutarSTORE(
                                                                instruccion);

                                                break;

                                        /*---------------- ADD ----------------*/

                                        case "ADD":

                                                ejecutarADD(
                                                                instruccion);

                                                break;

                                        /*---------------- SUB ----------------*/

                                        case "SUB":

                                                ejecutarSUB(
                                                                instruccion);

                                                break;

                                        /*---------------- INC ----------------*/

                                        case "INC":

                                                ejecutarINC(
                                                                instruccion);

                                                break;

                                        /*---------------- DEC ----------------*/

                                        case "DEC":

                                                ejecutarDEC(
                                                                instruccion);

                                                break;

                                        /*---------------- SWAP ----------------*/

                                        case "SWAP":

                                                ejecutarSWAP(
                                                                instruccion);

                                                break;

                                        case "INT 20H":

                                                ejecutarINT20H(
                                                                proceso);

                                                avanzarPC = false;

                                                break;

                                        case "INT 10H":

                                                ejecutarINT10H(
                                                                proceso);

                                                break;

                                        case "INT 09H":

                                                boolean completada = ejecutarINT09H(proceso);

                                                if (!completada) {

                                                        avanzarPC = false;
                                                }

                                                break;

                                        case "INT 21H":

                                                ejecutarINT21H(
                                                                disco,
                                                                proceso);

                                                break;

                                        /*---------------- JMP ----------------*/

                                        case "JMP":

                                                ejecutarJMP(
                                                                instruccion);

                                                avanzarPC = false;

                                                break;

                                        /*---------------- CMP ----------------*/

                                        case "CMP":

                                                ejecutarCMP(
                                                                instruccion);

                                                break;

                                        /*---------------- JE ----------------*/

                                        case "JE":

                                                avanzarPC = ejecutarJE(
                                                                instruccion);

                                                break;

                                        /*---------------- JNE ----------------*/

                                        case "JNE":

                                                avanzarPC = ejecutarJNE(
                                                                instruccion);

                                                break;

                                        /*---------------- PARAM ----------------*/

                                        case "PARAM":

                                                ejecutarPARAM(
                                                                instruccion,
                                                                proceso);

                                                break;

                                        /*---------------- PUSH ----------------*/

                                        case "PUSH":

                                                ejecutarPUSH(
                                                                instruccion,
                                                                proceso);

                                                break;

                                        /*---------------- POP ----------------*/

                                        case "POP":

                                                ejecutarPOP(
                                                                instruccion,
                                                                proceso);

                                                break;

                                        default:

                                                throw new IllegalArgumentException(
                                                                "Operación no válida: "
                                                                                + operador);
                                }

                                // el pc avanza solo si se permite
                                if (avanzarPC
                                                && !programaFinalizado) {

                                        PC++;
                                }

                                Segundero = 0;
                        }

                } catch (Exception e) {

                        // ERROR REAL DE EJECUCIÓN.
                        programaFinalizado = true;
                        proceso.getBcp().setEstadoProceso(BCP.EstadoProceso.FINALIZADO);
                        Segundero = 0;

                        guardarContexto(
                                        proceso.getBcp());

                        memory.actualizarBCP(
                                        proceso);

                        throw e;
                }

                guardarContexto(
                                proceso.getBcp());

                memory.actualizarBCP(
                                proceso);
        }

        private void ejecutarMOV(
                        Instruccion instruccion) {

                String texto = instruccion
                                .toString()
                                .trim();

                String parametros = texto
                                .substring(3)
                                .trim();

                int coma = parametros.indexOf(",");

                if (coma == -1) {

                        throw new IllegalArgumentException(
                                        "MOV requiere dos parámetros.");
                }

                String destino = parametros
                                .substring(
                                                0,
                                                coma)
                                .trim()
                                .toUpperCase();

                String origen = parametros
                                .substring(
                                                coma + 1)
                                .trim();

                /*
                 * MOV con cadena.
                 *
                 * Ejemplo:
                 *
                 * MOV DX, "archivo.txt"
                 */
                if (origen.startsWith("\"")
                                && origen.endsWith("\"")) {

                        String valorTexto = origen.substring(
                                        1,
                                        origen.length() - 1);
                        if (destino.equals("DX")) {

                                textoDX = valorTexto;

                                return;
                        }

                        throw new IllegalArgumentException(
                                        "El registro "
                                                        + destino
                                                        + " no admite cadenas. Solo DX puede recibir texto.");

                }

                /*
                 * AH permite números hexadecimales.
                 *
                 * Ejemplo:
                 *
                 * MOV AH, 3CH
                 */
                if (destino.equals("AH")) {

                        setAH(
                                        convertirNumero(origen));

                        return;
                }

                if (destino.equals("AL")) {

                        setAL(
                                        convertirNumero(
                                                        origen));

                        return;
                }

                /*
                 * MOV registro, registro
                 */
                if (esRegistro(origen)) {

                        asignarRegistro(
                                        destino,
                                        obtenerRegistro(
                                                        origen));

                        return;
                }

                /*
                 * MOV registro, inmediato
                 */
                asignarRegistro(
                                destino,
                                convertirNumero(
                                                origen));
        }

        private void ejecutarLOAD(
                        Instruccion instruccion) {

                String registro = instruccion
                                .getRegistro()
                                .toUpperCase();

                AC = obtenerRegistro(
                                registro);
        }

        private void ejecutarSTORE(
                        Instruccion instruccion) {

                String registro = instruccion
                                .getRegistro()
                                .toUpperCase();

                asignarRegistro(
                                registro,
                                AC);
        }

        private void ejecutarADD(
                        Instruccion instruccion) {

                String registro = instruccion
                                .getRegistro()
                                .toUpperCase();

                AC = AC
                                + obtenerRegistro(
                                                registro);
        }

        private void ejecutarSUB(
                        Instruccion instruccion) {

                String registro = instruccion
                                .getRegistro()
                                .toUpperCase();

                AC = AC
                                - obtenerRegistro(
                                                registro);
        }

        private void ejecutarINC(
                        Instruccion instruccion) {

                String[] partes = instruccion
                                .toString()
                                .trim()
                                .split("\\s+");

                if (partes.length == 1) {

                        AC++;

                        return;
                }

                String registro = partes[1]
                                .trim()
                                .toUpperCase();

                asignarRegistro(
                                registro,
                                obtenerRegistro(
                                                registro) + 1);
        }

        private void ejecutarDEC(
                        Instruccion instruccion) {

                String[] partes = instruccion
                                .toString()
                                .trim()
                                .split("\\s+");

                if (partes.length == 1) {

                        AC--;

                        return;
                }

                String registro = partes[1]
                                .trim()
                                .toUpperCase();

                asignarRegistro(
                                registro,
                                obtenerRegistro(
                                                registro) - 1);
        }

        private void ejecutarSWAP(
                        Instruccion instruccion) {

                String parametros = obtenerParametros(
                                instruccion.toString(),
                                "SWAP");

                String[] registros = parametros.split(",");

                if (registros.length != 2) {

                        throw new IllegalArgumentException(
                                        "SWAP requiere dos registros.");
                }

                String registro1 = registros[0]
                                .trim()
                                .toUpperCase();

                String registro2 = registros[1]
                                .trim()
                                .toUpperCase();

                int temporal = obtenerRegistro(
                                registro1);

                asignarRegistro(
                                registro1,
                                obtenerRegistro(
                                                registro2));

                asignarRegistro(
                                registro2,
                                temporal);
        }

        private void ejecutarCMP(
                        Instruccion instruccion) {

                String parametros = obtenerParametros(
                                instruccion.toString(),
                                "CMP");

                String[] registros = parametros.split(",");

                if (registros.length != 2) {

                        throw new IllegalArgumentException(
                                        "CMP requiere dos registros.");
                }

                String registro1 = registros[0]
                                .trim()
                                .toUpperCase();

                String registro2 = registros[1]
                                .trim()
                                .toUpperCase();

                comparacionIgual = obtenerRegistro(registro1) == obtenerRegistro(registro2);
        }

        private void ejecutarJMP(
                        Instruccion instruccion) {

                int desplazamiento = obtenerDesplazamiento(
                                instruccion.toString(),
                                "JMP");

                PC = PC
                                + desplazamiento;
        }

        private boolean ejecutarJE(
                        Instruccion instruccion) {

                if (comparacionIgual) {

                        int desplazamiento = obtenerDesplazamiento(
                                        instruccion.toString(),
                                        "JE");

                        PC = PC
                                        + desplazamiento;

                        return false;
                }

                return true;
        }

        private boolean ejecutarJNE(
                        Instruccion instruccion) {

                if (!comparacionIgual) {

                        int desplazamiento = obtenerDesplazamiento(
                                        instruccion.toString(),
                                        "JNE");

                        PC = PC
                                        + desplazamiento;

                        return false;
                }

                return true;
        }

        private void ejecutarPARAM(
                        Instruccion instruccion,
                        Proceso proceso) {

                String parametros = obtenerParametros(
                                instruccion.toString(),
                                "PARAM");

                String[] valores = parametros.split(",");

                if (valores.length > 3) {

                        throw new IllegalArgumentException(
                                        "PARAM permite máximo 3 parámetros.");
                }

                for (String valor : valores) {

                        proceso
                                        .getBcp()
                                        .push(
                                                        convertirNumero(
                                                                        valor.trim()));
                }
        }

        private void ejecutarPUSH(
                        Instruccion instruccion,
                        Proceso proceso) {

                String registro = obtenerParametros(
                                instruccion.toString(),
                                "PUSH")
                                .trim()
                                .toUpperCase();

                proceso
                                .getBcp()
                                .push(
                                                obtenerRegistro(
                                                                registro));
        }

        private void ejecutarPOP(
                        Instruccion instruccion,
                        Proceso proceso) {

                String registro = obtenerParametros(
                                instruccion.toString(),
                                "POP")
                                .trim()
                                .toUpperCase();

                Object valor = proceso
                                .getBcp()
                                .pop();

                if (!(valor instanceof Integer)) {

                        throw new IllegalStateException(
                                        "El valor extraído de la pila no es numérico.");
                }

                asignarRegistro(
                                registro,
                                (Integer) valor);
        }

        private void ejecutarINT20H(
                        Proceso proceso) {

                programaFinalizado = true;

                proceso
                                .getBcp()
                                .setEstadoProceso(
                                                BCP.EstadoProceso.FINALIZADO);
        }

        private void ejecutarINT10H(
                        Proceso proceso) {

                salidaMonitor = String.valueOf(
                                DX);

                proceso
                                .getBcp()
                                .setEstadoProceso(
                                                BCP.EstadoProceso.PREPARADO);
        }

        private boolean ejecutarINT09H(Proceso proceso) {

                if (entradaTeclado == null) {

                        esperandoTeclado = true;

                        proceso.getBcp().setEstadoProceso(
                                        BCP.EstadoProceso.EN_ESPERA);

                        return false;
                }

                DX = entradaTeclado;

                entradaTeclado = null;

                esperandoTeclado = false;

                return true;
        }

        private void ejecutarINT21H(
                        Disco disco,
                        Proceso proceso) {

                if (disco == null) {

                        throw new IllegalStateException(
                                        "INT 21H requiere acceso al disco.");
                }

                if (textoDX == null
                                || textoDX.isBlank()) {

                        throw new IllegalStateException(
                                        "DX no contiene el nombre de un archivo.");
                }

                switch (getAH()) {

                        /*----------------------------------
                         CREAR ARCHIVO
                        AH = 3CH
                          ----------------------------------*/

                        case 0x3C:

                                validarArchivoNoAbierto(
                                                proceso,
                                                textoDX);
                                disco.crearArchivo(
                                                textoDX);

                                break;

                        /*----------------------------------
                         ABRIR ARCHIVO
                        AH = 3DH
                          ----------------------------------*/

                        case 0x3D:

                                if (archivoEstaAbierto(
                                                proceso,
                                                textoDX)) {

                                        throw new IllegalStateException(
                                                        "El archivo "
                                                                        + textoDX
                                                                        + " ya se encuentra abierto.");
                                }

                                disco.abrirArchivo(
                                                textoDX);

                                proceso
                                                .getBcp()
                                                .agregarArchivoAbierto(
                                                                textoDX);

                                break;

                        /*----------------------------------
                          LEER ARCHIVO
                         AH = 4DH
                          ----------------------------------*/

                        case 0x4D:

                                validarArchivoAbierto(
                                                proceso,
                                                textoDX);

                                String contenido = disco.leerArchivo(
                                                textoDX).trim();

                                if (contenido.isEmpty()) {
                                        throw new IllegalStateException(
                                                        "El archivo no contiene un valor numérico.");
                                }

                                setAL(
                                                convertirNumero(
                                                                contenido));

                                break;

                        /*----------------------------------
                        ESCRIBIR ARCHIVO
                         AH = 40H
                          ----------------------------------*/

                        case 0x40:

                                validarArchivoAbierto(
                                                proceso,
                                                textoDX);

                                disco.escribirArchivo(
                                                textoDX,
                                                String.valueOf(getAL()));

                                break;

                        /*----------------------------------
                        ELIMINAR ARCHIVO
                         AH = 41H
                          ----------------------------------*/

                        case 0x41:

                                if (archivoEstaAbierto(
                                                proceso,
                                                textoDX)) {

                                        throw new IllegalStateException(
                                                        "No se puede eliminar el archivo "
                                                                        + textoDX
                                                                        + " porque se encuentra abierto.");
                                }

                                disco.eliminarArchivo(
                                                textoDX);

                                break;

                        default:

                                throw new IllegalArgumentException(
                                                "Servicio INT 21H no válido: "
                                                                + Integer
                                                                                .toHexString(getAH())
                                                                                .toUpperCase()
                                                                + "H");
                }

        }

        // validar si un archivo esta abierto

        private void validarArchivoAbierto(
                        Proceso proceso,
                        String archivo) {

                if (!archivoEstaAbierto(
                                proceso,
                                archivo)) {

                        throw new IllegalStateException(
                                        "El archivo "
                                                        + archivo
                                                        + " no se encuentra abierto.");
                }
        }

        // validar archivo no abierto
        private void validarArchivoNoAbierto(
                        Proceso proceso,
                        String archivo) {

                if (archivoEstaAbierto(
                                proceso,
                                archivo)) {

                        throw new IllegalStateException(
                                        "El archivo "
                                                        + archivo
                                                        + " ya se encuentra abierto.");
                }
        }

        private boolean archivoEstaAbierto(
                        Proceso proceso,
                        String archivo) {

                return proceso
                                .getBcp()
                                .getArchivosAbiertos()
                                .contains(
                                                archivo);
        }

        private int obtenerRegistro(
                        String registro) {

                switch (registro.toUpperCase()) {

                        case "AX":

                                return AX;

                        case "AH":

                                return getAH();

                        case "AL":

                                return getAL();

                        case "BX":

                                return BX;

                        case "CX":

                                return CX;

                        case "DX":

                                return DX;

                        case "AC":

                                return AC;

                        default:

                                throw new IllegalArgumentException(
                                                "Registro no válido: "
                                                                + registro);
                }
        }

        private void asignarRegistro(
                        String registro,
                        int valor) {

                switch (registro.toUpperCase()) {

                        case "AX":

                                AX = valor;

                                break;

                        case "AH":

                                setAH(valor);

                                break;

                        case "AL":

                                setAL(valor);

                                break;

                        case "BX":

                                BX = valor;

                                break;

                        case "CX":

                                CX = valor;

                                break;

                        case "DX":

                                DX = valor;

                                /*
                                 * Si DX recibe un número,
                                 * deja de representar una cadena.
                                 */
                                textoDX = null;

                                break;

                        case "AC":

                                AC = valor;

                                break;

                        default:

                                throw new IllegalArgumentException(
                                                "Registro no válido: "
                                                                + registro);
                }
        }

        // recibe un valor para ver si un archivo

        private boolean esRegistro(
                        String valor) {

                String registro = valor
                                .trim()
                                .toUpperCase();

                return registro.equals("AX")
                                || registro.equals("AH")
                                || registro.equals("AL")
                                || registro.equals("BX")
                                || registro.equals("CX")
                                || registro.equals("DX")
                                || registro.equals("AC");
        }

        // trasnfoma el valor para guardarlo en ax
        private int convertirNumero(
                        String valor) {

                String numero = valor
                                .trim()
                                .toUpperCase();

                try {

                        /*
                         * Hexadecimal.
                         *
                         * Ejemplo:
                         *
                         * 3CH
                         * 21H
                         */
                        if (numero.endsWith("H")) {

                                return Integer.parseInt(
                                                numero.substring(
                                                                0,
                                                                numero.length() - 1),
                                                16);
                        }

                        /*
                         * Decimal.
                         */
                        return Integer.parseInt(
                                        numero);

                } catch (NumberFormatException e) {

                        throw new IllegalArgumentException(
                                        "Valor numérico inválido: "
                                                        + valor);
                }
        }

        //
        private String obtenerParametros(
                        String instruccion,
                        String operador) {

                if (instruccion.length() <= operador.length()) {

                        return "";
                }

                return instruccion
                                .substring(
                                                operador.length())
                                .trim();
        }

        // obterner desplazamiento
        private int obtenerDesplazamiento(
                        String instruccion,
                        String operador) {

                String valor = obtenerParametros(
                                instruccion,
                                operador)
                                .replace(
                                                "[",
                                                "")
                                .replace(
                                                "]",
                                                "")
                                .trim();

                try {

                        return Integer.parseInt(
                                        valor);

                } catch (NumberFormatException e) {

                        throw new IllegalArgumentException(
                                        "Desplazamiento inválido: "
                                                        + valor);
                }
        }

        /*
         * ==================================================
         * GUARDAR CONTEXTO
         * ==================================================
         */

        private void guardarContexto(
                        BCP bcp) {

                bcp.setPC(
                                PC);

                bcp.setAC(
                                AC);

                bcp.setAX(
                                AX);

                bcp.setBX(
                                BX);

                bcp.setCX(
                                CX);

                bcp.setDX(
                                DX);

                bcp.setIR(
                                IR);

                // se guardan para que puedan visualizarse
                bcp.setAH(
                                getAH());

                bcp.setAL(
                                String.valueOf(getAL()));

                bcp.setTextoDX(
                                textoDX);
        }

        // reiniciar registros
        public void reiniciar() {

                PC = 0;

                AC = 0;

                AX = 0;

                BX = 0;

                CX = 0;

                DX = 0;

                IR = null;

                Segundero = 0;

                textoDX = null;

                comparacionIgual = false;

                entradaTeclado = null;

                salidaMonitor = "";

                programaFinalizado = false;
        }

        public int getPC() {

                return PC;
        }

        public int getAC() {

                return AC;
        }

        public int getAX() {

                return AX;
        }

        public int getBX() {

                return BX;
        }

        public int getCX() {

                return CX;
        }

        public int getDX() {

                return DX;
        }

        public String getIR() {

                return IR;
        }

        public int getSegundero() {

                return Segundero;
        }

        public int getAH() {

                return (AX >> 8) & 0xFF;
        }

        public int getAL() {

                return AX & 0xFF;
        }

        public String getTextoDX() {

                return textoDX;
        }

        public String getSalidaMonitor() {

                return salidaMonitor;
        }

        public boolean isProgramaFinalizado() {

                return programaFinalizado;
        }

        public void setEntradaTeclado(
                        int entradaTeclado) {

                this.entradaTeclado = entradaTeclado;
        }

        public void setEntradaTeclado(
                        int entradaTeclado,
                        Proceso proceso) {

                this.entradaTeclado = entradaTeclado;

                if (proceso != null
                                && proceso
                                                .getBcp()
                                                .getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA) {

                        proceso
                                        .getBcp()
                                        .setEstadoProceso(
                                                        BCP.EstadoProceso.PREPARADO);
                }
        }

        public void setAH(
                        int valor) {

                validarByte(valor, "AH");

                AX = ((valor & 0xFF) << 8)
                                | (AX & 0x00FF);
        }

        public void setAL(
                        int valor) {

                validarByte(valor, "AL");

                AX = (AX & 0xFF00)
                                | (valor & 0xFF);
        }

        private void validarByte(
                        int valor,
                        String registro) {

                if (valor < 0 || valor > 255) {

                        throw new IllegalArgumentException(
                                        registro
                                                        + " solamente admite valores entre 0 y 255.");
                }
        }

        public void setTextoDX(
                        String textoDX) {

                this.textoDX = textoDX;
        }
}
