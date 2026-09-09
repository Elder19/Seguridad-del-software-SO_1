package javaapplication2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Lectorarchivos {

    // Guarda todos los errores encontrados
    private final List<String> errores = new ArrayList<>();

    // Cantidad total de errores
    private int cantidadErrores = 0;
    /*
     * Lee el archivo ASM y devuelve cada línea
     * como un elemento de una lista.
     */
    public List<String> leerArchivo(File archivo) throws IOException {

    // 1. Leer todas las líneas
    List<String> lineas = Files.readAllLines(archivo.toPath());

    // 2. Validar la gramática
    validarGramatica(lineas);

       if (cantidadErrores != 0) {

        System.out.println("Errores encontrados: " + cantidadErrores);

        for (String error : errores) {
            System.out.println(error);
        }

    } else {

        System.out.println("Gramática válida");

    }

    return lineas;
}



    /*
     * Valida todas las instrucciones del archivo.
     * Retorna:
     * true  -> todo el archivo es válido
     * false -> existe al menos un error
     */
    public boolean validarGramatica(List<String> lineas) {
        errores.clear();
        cantidadErrores = 0;
        for (int i = 0; i < lineas.size(); i++) {
            int numeroLinea = i + 1;
            String linea = lineas.get(i).trim();
            String[] partes = linea.split("[,\\s]+");
            String operador = partes[0].toUpperCase();
            switch (operador) {
                case "MOV":
                    validarMOV(partes, numeroLinea);
                    break;
                case "LOAD":
                case "STORE":
                case "ADD":
                case "SUB":
                    validarOperacionRegistro(
                            partes,
                            numeroLinea,
                            operador
                    );
                    break;
                default:

                    agregarError(
                            numeroLinea,
                            "Operación no válida: " + operador
                    );
                    break;
            }
        }
       return cantidadErrores == 0;
    }

    /*
     * Valida instrucciones del tipo:
     *
     * MOV AX, 5
     * MOV BX, -8
     */
    private void validarMOV(
            String[] partes,
            int numeroLinea) {
        if (partes.length != 3) {
            agregarError(
                    numeroLinea,
                    "MOV debe tener el formato: MOV REGISTRO, VALOR"
            );

            return;
        }
        String registro = partes[1].toUpperCase();
        if (!registroValido(registro)) {
            agregarError(
                    numeroLinea,
                    "Registro inválido: " + registro
            );
            return;
        }
        try {

            int valor = Integer.parseInt(partes[2]);
            if (valor < -127 || valor > 127) {
                agregarError(
                        numeroLinea,
                        "El valor debe estar entre -127 y 127."
                );
            }
        } catch (NumberFormatException e) {

            agregarError(
                    numeroLinea,
                    "El valor debe ser un número entero."
            );
        }
    }
    /*
     * Valida instrucciones
     */
    private void validarOperacionRegistro(
            String[] partes,
            int numeroLinea,
            String operador) {
        if (partes.length != 2) {

            agregarError(
                    numeroLinea,
                    operador
                    + " debe tener el formato: "
                    + operador
                    + " REGISTRO"
            );
            return;
        }
        String registro = partes[1].toUpperCase();
        if (!registroValido(registro)) {

            agregarError(
                    numeroLinea,
                    "Registro inválido: " + registro
            );
        }
    }


    /*
     * Comprueba los cuatro registros permitido
     */
    private boolean registroValido(String registro) {

        return registro.equals("AX")
                || registro.equals("BX")
                || registro.equals("CX")
                || registro.equals("DX");
    }


    /*
     * Agrega un error indicando exactamente
     * la línea donde ocurrió.
     */
    private void agregarError(
            int numeroLinea,
            String mensaje) {

        cantidadErrores++;

        errores.add(
                "Error en línea "
                + numeroLinea
                + ": "
                + mensaje
        );
    }

    /*
     * Devuelve todos los errores.
     */
    public List<String> getErrores() {

        return errores;
    }
    /*
     * Devuelve la cantidad de errores encontrados.
     */
    public int getCantidadErrores() {

        return cantidadErrores;
    }
}