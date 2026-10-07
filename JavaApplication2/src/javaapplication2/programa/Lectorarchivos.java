package javaapplication2.programa;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class Lectorarchivos {

        private final List<String> errores = new ArrayList<>();
        private int cantidadErrores = 0;

        /*---------------- LEER ARCHIVO ----------------*/

         public List<String> leerArchivo(File archivo) throws IOException {

         List<String> lineas = Files.readAllLines(archivo.toPath());

         boolean valido = validarGramatica(lineas);

         if (!valido) {

             String mensaje = "El archivo ASM contiene "
                     + cantidadErrores
                     + " error(es):\n";

             for (String error : errores) {
                 mensaje += error + "\n";
             }

             throw new IllegalArgumentException(mensaje);
         }

         // Eliminar líneas vacías antes de crear/guardar el programa
         List<String> instrucciones = new ArrayList<>();

         for (String linea : lineas) {

             if (linea != null && !linea.trim().isEmpty()) {
                 instrucciones.add(linea.trim());
             }
         }

         return instrucciones;
     }

        /*---------------- VALIDAR GRAMATICA ----------------*/

        public boolean validarGramatica(List<String> lineas) {

                errores.clear();
                cantidadErrores = 0;

                for (int i = 0; i < lineas.size(); i++) {

                        int numeroLinea = i + 1;
                        String linea = lineas.get(i).trim();

                        if (linea.isEmpty()) {
                                continue;
                        }

                        String operador = linea.split("\\s+")[0].toUpperCase();

                        switch (operador) {

                                case "MOV":
                                        validarMOV(linea, numeroLinea);
                                        break;

                                case "LOAD":
                                case "STORE":
                                case "ADD":
                                case "SUB":
                                        validarOperacionRegistro(
                                                        linea,
                                                        numeroLinea,
                                                        operador);
                                        break;

                                case "INC":
                                case "DEC":
                                        validarINCDEC(
                                                        linea,
                                                        numeroLinea,
                                                        operador);
                                        break;

                                case "SWAP":
                                case "CMP":
                                        validarDosRegistros(
                                                        linea,
                                                        numeroLinea,
                                                        operador);
                                        break;

                                case "JMP":
                                case "JE":
                                case "JNE":
                                        validarSalto(
                                                        linea,
                                                        numeroLinea,
                                                        operador);
                                        break;

                                case "PARAM":
                                        validarPARAM(
                                                        linea,
                                                        numeroLinea);
                                        break;

                                case "PUSH":
                                case "POP":
                                        validarOperacionRegistro(
                                                        linea,
                                                        numeroLinea,
                                                        operador);
                                        break;

                                case "INT":
                                        validarINT(
                                                        linea,
                                                        numeroLinea);
                                        break;

                                default:

                                        agregarError(
                                                        numeroLinea,
                                                        "Operación no válida: "
                                                                        + operador);

                                        break;
                        }
                }

                return cantidadErrores == 0;
        }

        /*---------------- MOV ----------------*/

       private void validarMOV(
                String linea,
                int numeroLinea) {

            String parametros = linea.substring(3).trim();

            String[] partes = parametros.split(",", -1);

            if (partes.length != 2) {

                agregarError(
                        numeroLinea,
                        "MOV debe tener el formato: MOV DESTINO, ORIGEN");

                return;
            }

            String destino = partes[0]
                    .trim()
                    .toUpperCase();

            String origen = partes[1]
                    .trim();

            // Validar destino
            if (!registroDestinoValido(destino)) {

                agregarError(
                        numeroLinea,
                        "Registro destino inválido: " + destino);

                return;
            }

            // Validar que exista origen
            if (origen.isEmpty()) {

                agregarError(
                        numeroLinea,
                        "MOV necesita un valor de origen.");

                return;
            }

         
             //Solamente DX puede recibir texto.
      
            if (origen.startsWith("\"")
                    && origen.endsWith("\"")) {

                if (!destino.equals("DX")) {

                    agregarError(
                            numeroLinea,
                            destino + " no admite cadenas de texto.");
                }

                return;
            }

            /*
             * AH
             * Solamente valores numéricos entre 0 y 255.
             */
            if (destino.equals("AH")) {

                try {

                    int valor = convertirNumero(origen);

                    if (valor < 0 || valor > 255) {

                        agregarError(
                                numeroLinea,
                                "AH solamente admite valores entre 0 y 255.");
                    }

                } catch (Exception e) {

                    agregarError(
                            numeroLinea,
                            "Valor numérico inválido para AH: " + origen);
                }

                return;
            }

            /*
             * AL
             * Solamente valores numéricos entre 0 y 255.
             */
            if (destino.equals("AL")) {

                try {

                    int valor = convertirNumero(origen);

                    if (valor < 0 || valor > 255) {

                        agregarError(
                                numeroLinea,
                                "AL solamente admite valores entre 0 y 255.");
                    }

                } catch (Exception e) {

                    agregarError(
                            numeroLinea,
                            "Valor numérico inválido para AL: " + origen);
                }

                return;
            }

            /*
             * MOV REGISTRO, REGISTRO
             */
            if (registroDestinoValido(origen.toUpperCase())) {
                return;
            }

            /*
             * MOV REGISTRO, NUMERO
             */
            try {

                convertirNumero(origen);

            } catch (Exception e) {

                agregarError(
                        numeroLinea,
                        "Valor de origen inválido: " + origen);
            }
        }

        /*---------------- OPERACION CON REGISTRO ----------------*/

        private void validarOperacionRegistro(
                        String linea,
                        int numeroLinea,
                        String operador) {

                String[] partes = linea.trim().split("\\s+");

                if (partes.length != 2) {

                        agregarError(
                                        numeroLinea,
                                        operador
                                                        + " debe tener el formato: "
                                                        + operador
                                                        + " REGISTRO");

                        return;
                }

                String registro = partes[1].trim().toUpperCase();

                if (!registroNumericoValido(registro)) {

                        agregarError(
                                        numeroLinea,
                                        "Registro inválido: "
                                                        + registro);
                }
        }

        /*---------------- INC / DEC ----------------*/

        private void validarINCDEC(
                        String linea,
                        int numeroLinea,
                        String operador) {

                String[] partes = linea.trim().split("\\s+");

                // INC o DEC sin registro trabaja sobre AC
                if (partes.length == 1) {
                        return;
                }

                if (partes.length != 2) {

                        agregarError(
                                        numeroLinea,
                                        operador
                                                        + " debe tener el formato: "
                                                        + operador
                                                        + " o "
                                                        + operador
                                                        + " REGISTRO");

                        return;
                }

                if (!registroNumericoValido(
                                partes[1].toUpperCase())) {

                        agregarError(
                                        numeroLinea,
                                        "Registro inválido: "
                                                        + partes[1]);
                }
        }

        /*---------------- DOS REGISTROS ----------------*/

        private void validarDosRegistros(
                        String linea,
                        int numeroLinea,
                        String operador) {

                String parametros = linea.substring(
                                operador.length()).trim();

                String[] registros = parametros.split(",");

                if (registros.length != 2) {

                        agregarError(
                                        numeroLinea,
                                        operador
                                                        + " necesita dos registros.");

                        return;
                }

                String registro1 = registros[0].trim().toUpperCase();

                String registro2 = registros[1].trim().toUpperCase();

                if (!registroNumericoValido(registro1)) {

                        agregarError(
                                        numeroLinea,
                                        "Registro inválido: "
                                                        + registro1);
                }

                if (!registroNumericoValido(registro2)) {

                        agregarError(
                                        numeroLinea,
                                        "Registro inválido: "
                                                        + registro2);
                }
        }

        /*---------------- SALTOS ----------------*/

        private void validarSalto(
                        String linea,
                        int numeroLinea,
                        String operador) {

                String valor = linea.substring(
                                operador.length()).trim();

                valor = valor
                                .replace("[", "")
                                .replace("]", "")
                                .trim();

                if (valor.isEmpty()) {

                        agregarError(
                                        numeroLinea,
                                        operador
                                                        + " necesita un desplazamiento.");

                        return;
                }

                try {

                        Integer.parseInt(valor);

                } catch (NumberFormatException e) {

                        agregarError(
                                        numeroLinea,
                                        "Desplazamiento inválido: "
                                                        + valor);
                }
        }

        /*---------------- PARAM ----------------*/

        private void validarPARAM(
                        String linea,
                        int numeroLinea) {

                String parametros = linea.substring(5).trim();

                if (parametros.isEmpty()) {

                        agregarError(
                                        numeroLinea,
                                        "PARAM necesita al menos un parámetro.");

                        return;
                }

                String[] valores = parametros.split(",");

                if (valores.length > 3) {

                        agregarError(
                                        numeroLinea,
                                        "PARAM permite máximo 3 parámetros.");

                        return;
                }

                for (String valor : valores) {

                        try {

                                int numero = convertirNumero(
                                                valor.trim());

                                if (numero < -127
                                                || numero > 127) {

                                        agregarError(
                                                        numeroLinea,
                                                        "El valor "
                                                                        + numero
                                                                        + " debe estar entre -127 y 127.");
                                }

                        } catch (Exception e) {

                                agregarError(
                                                numeroLinea,
                                                "Parámetro inválido: "
                                                                + valor.trim());
                        }
                }
        }

        /*---------------- INTERRUPCIONES ----------------*/

        private void validarINT(
                        String linea,
                        int numeroLinea) {

                String[] partes = linea.trim().split("\\s+");

                if (partes.length != 2) {

                        agregarError(
                                        numeroLinea,
                                        "INT debe tener el formato: INT XXH");

                        return;
                }

                String interrupcion = partes[1].toUpperCase();

                if (!interrupcion.equals("20H")
                                && !interrupcion.equals("10H")
                                && !interrupcion.equals("09H")
                                && !interrupcion.equals("21H")) {

                        agregarError(
                                        numeroLinea,
                                        "Interrupción no válida: "
                                                        + interrupcion);
                }
        }

        /*---------------- REGISTROS ----------------*/

        private boolean registroNumericoValido(
                        String registro) {

                return registro.equals("AX")
                                || registro.equals("BX")
                                || registro.equals("CX")
                                || registro.equals("DX")
                                || registro.equals("AC");
        }

        private boolean registroDestinoValido(
                        String registro) {

                return registroNumericoValido(registro)
                                || registro.equals("AH")
                                || registro.equals("AL");
        }

        /*---------------- CONVERTIR NUMERO ----------------*/

        private int convertirNumero(
                        String valor) {

                String numero = valor.trim().toUpperCase();

                if (numero.endsWith("H")) {

                        return Integer.parseInt(
                                        numero.substring(
                                                        0,
                                                        numero.length() - 1),
                                        16);
                }

                return Integer.parseInt(numero);
        }

        /*---------------- ERRORES ----------------*/

        private void agregarError(
                        int numeroLinea,
                        String mensaje) {

                cantidadErrores++;

                errores.add(
                                "Error en línea "
                                                + numeroLinea
                                                + ": "
                                                + mensaje);
        }

        public List<String> getErrores() {
                return new ArrayList<>(errores);
        }

        public int getCantidadErrores() {
                return cantidadErrores;
        }

        /*---------------- CONFIGURACION ----------------*/

        public String leerConfig(String nombre) {

                try {

                        java.io.InputStream archivo = getClass().getResourceAsStream(
                                        "/javaapplication2/config.xml");

                        if (archivo == null) {

                                throw new IllegalStateException(
                                                "No se encontró config.xml");
                        }

                        DocumentBuilderFactory factory = DocumentBuilderFactory
                                        .newInstance();

                        DocumentBuilder builder = factory.newDocumentBuilder();

                        Document documento = builder.parse(archivo);

                        if (documento
                                        .getElementsByTagName(nombre)
                                        .getLength() == 0) {

                                throw new IllegalArgumentException(
                                                "No existe la configuración: "
                                                                + nombre);
                        }

                        return documento
                                        .getElementsByTagName(nombre)
                                        .item(0)
                                        .getTextContent()
                                        .trim();

                } catch (Exception e) {

                        throw new RuntimeException(
                                        "Error leyendo configuración: "
                                                        + nombre,
                                        e);
                }
        }
}
