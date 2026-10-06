package javaapplication2.programa;

public class Instruccion {

    private final String operacion;
    private final String registro;
    private final Integer valor;
    private final String RegistroDestino;
    private final String textoOriginal;

    public Instruccion(
            String operacion,
            String registro,
            Integer valor,
            String textoOriginal) {

        this.operacion = operacion.toUpperCase();
        this.registro = registro == null
                ? ""
                : registro.toUpperCase();

        this.valor = valor;
        this.RegistroDestino = "";
        this.textoOriginal = textoOriginal;
    }

    public Instruccion(
            String operacion,
            String registro,
            String RegistroDestino,
            String textoOriginal) {

        this.operacion = operacion.toUpperCase();
        this.registro = registro == null
                ? ""
                : registro.toUpperCase();

        this.RegistroDestino = RegistroDestino == null
                ? ""
                : RegistroDestino;

        this.valor = null;
        this.textoOriginal = textoOriginal;
    }

    // cargar una instrucción desde un texto, por ejemplo: "MOV AX, 5" o "INT 20H"

    public static Instruccion desdeTexto(String linea) {

        if (linea == null
                || linea.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "La instrucción está vacía");
        }

        String texto = linea.trim();

        String[] partes = texto.split("\\s+");

        String operador = partes[0].toUpperCase();

        if (operador.equals("INT")) {

            if (partes.length != 2) {
                throw new IllegalArgumentException(
                        "INT necesita el número de interrupción.");
            }

            String operacionCompleta = "INT "
                    + partes[1].toUpperCase();

            return new Instruccion(
                    operacionCompleta,
                    "",
                    (Integer) null,
                    texto);
        }

        if ((operador.equals("INC")
                || operador.equals("DEC"))
                && partes.length == 1) {

            return new Instruccion(
                    operador,
                    "",
                    (Integer) null,
                    texto);
        }

        /*---------------- MOV ----------------*/

        if (operador.equals("MOV")) {

            String parametros = texto.substring(3).trim();

            int coma = parametros.indexOf(",");

            if (coma == -1) {
                throw new IllegalArgumentException(
                        "MOV necesita dos parámetros.");
            }

            String destino = parametros.substring(
                    0,
                    coma).trim();

            String origen = parametros.substring(
                    coma + 1).trim();

            /*
             * Si el origen es número decimal
             */
            try {

                int numero = Integer.parseInt(origen);

                return new Instruccion(
                        operador,
                        destino,
                        numero,
                        texto);

            } catch (NumberFormatException e) {

                /*
                 * Puede ser:
                 * MOV AX, DX
                 * MOV DX, "archivo.txt"
                 * MOV AL, "texto"
                 * MOV AH, 3CH
                 */

                return new Instruccion(
                        operador,
                        destino,
                        origen,
                        texto);
            }
        }

        /*---------------- OPERACIONES NORMALES ----------------*/

        String registro = "";

        if (partes.length >= 2) {

            registro = partes[1]
                    .replace(",", "")
                    .trim();
        }

        return new Instruccion(
                operador,
                registro,
                (Integer) null,
                texto);
    }

    /*---------------- GETTERS ----------------*/

    public String getOperacion() {
        return operacion;
    }

    public String getRegistro() {
        return registro;
    }

    public Integer getValor() {
        return valor;
    }

    public String getRegistroDestino() {
        return RegistroDestino;
    }

    /*---------------- TO STRING ----------------*/

    public String toString() {
        return textoOriginal;
    }

    /*---------------- PESOS ----------------*/

    public int ObtenerPeso(String operador) {

        switch (operador.toUpperCase()) {

            case "MOV":
                return 1;

            case "LOAD":
                return 2;

            case "STORE":
                return 2;

            case "ADD":
                return 3;

            case "SUB":
                return 3;

            case "INC":
                return 1;

            case "DEC":
                return 1;

            case "SWAP":
                return 1;

            case "INT 20H":
                return 2;

            case "INT 10H":
                return 2;

            case "INT 09H":
                return 3;

            case "INT 21H":
                return 5;

            case "JMP":
                return 2;

            case "CMP":
                return 2;

            case "JE":
                return 2;

            case "JNE":
                return 2;

            case "PARAM":
                return 3;

            case "PUSH":
                return 1;

            case "POP":
                return 1;

            default:
                return 0;
        }
    }
}
