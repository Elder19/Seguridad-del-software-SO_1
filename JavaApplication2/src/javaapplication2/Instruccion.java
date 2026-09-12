package javaapplication2;

public class Instruccion {

    private final String operacion;
    private final String registro;
    private final Integer valor;
     /*crea los objetos intruccon para facilitar la ejecucion*/
    public Instruccion(String operacion, String registro,Integer valor) {
        this.operacion = operacion.toUpperCase();
        this.registro = registro.toUpperCase();
        this.valor = valor;    
    }
    /*---------------- crear el objeto ----------------*/

    public static Instruccion desdeTexto(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La instrucción está vacía"
            );
        }
        String[] partes = linea.trim().split("[,\\s]+");
        String operacion = partes[0].toUpperCase();
        if (partes.length < 2) {
            throw new IllegalArgumentException(
                    "Falta registro en: " + linea);
        }
        String registro = partes[1].toUpperCase();
        Integer valor = null;
        if (operacion.equals("MOV")) {
            if (partes.length < 3) {
                throw new IllegalArgumentException("MOV necesita un valor: " + linea);
            }
            valor =Integer.parseInt(partes[2]);
        }
        return new Instruccion(  operacion, registro, valor);
    }



    /*---------------- BINARIO ----------------*/

    public String getBinario() {
        String codigoOperacion;
        String codigoRegistro;
        switch (operacion) {
            case "LOAD":
                codigoOperacion = "0001";
                break;

            case "STORE":
                codigoOperacion = "0010";
                break;

            case "MOV":
                codigoOperacion = "0011";
                break;

            case "SUB":
                codigoOperacion = "0100";
                break;

            case "ADD":
                codigoOperacion = "0101";
                break;

            default:
                throw new IllegalStateException(
                        "Operación inválida"
                );
        }
        switch (registro) {

            case "AX":
                codigoRegistro = "0001";
                break;

            case "BX":
                codigoRegistro = "0010";
                break;

            case "CX":
                codigoRegistro = "0011";
                break;

            case "DX":
                codigoRegistro = "0100";
                break;

            default:
                throw new IllegalStateException(
                        "Registro inválido"
                );
        }
        String codigoValor = "00000000";

        if (operacion.equals("MOV")) {

            codigoValor =enteroABits(valor);
        }
        return codigoOperacion + codigoRegistro + codigoValor;
    }

    /*---------------- ENTERO A 8 BITS ----------------*/

    private static String enteroABits(int numero) {

        int valorAbsoluto = Math.abs(numero);

        String binarioValor =String.format("%7s", Integer.toBinaryString(valorAbsoluto )  ).replace(' ', '0');
        String signo;
        if (numero < 0) {
            signo = "1";
        } else {
            signo = "0";
        }

        return signo + binarioValor;
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
    /*---------------- ----------------*/
    public String toString() {

        if (valor != null) {
            return operacion+ " "+ registro + ", " + valor;
        }
        return operacion+ " "  + registro;
    }
}