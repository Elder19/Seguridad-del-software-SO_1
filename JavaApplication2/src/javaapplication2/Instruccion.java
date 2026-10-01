package javaapplication2;

public class Instruccion {

    private final String operacion;
    private final String registro;
    private final Integer valor;
    private final String RegistroDestino; 
     /*crea los objetos intruccon para facilitar la ejecucion*/
    public Instruccion(String operacion, String registro,Integer valor) {
        this.operacion = operacion.toUpperCase();
        this.registro = registro.toUpperCase();
        this.valor = valor;    
        this.RegistroDestino="";
       
    }
    public Instruccion(String operacion, String registro,String RegistroDestino) {
        this.operacion = operacion.toUpperCase();
        this.registro = registro.toUpperCase();
        this.RegistroDestino=RegistroDestino;
        this.valor = 0;
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