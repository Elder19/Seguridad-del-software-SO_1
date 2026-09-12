package javaapplication2;

public class JavaApplication2 {

    public static void main(String[] args) {

        SimuladorSO simulador =
                new SimuladorSO();

        simulador.encender();

        System.out.println(
                "Sistema listo para iniciar interfaz."
        );
    }
}