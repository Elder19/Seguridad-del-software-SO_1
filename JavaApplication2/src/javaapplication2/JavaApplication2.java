package javaapplication2;

public class JavaApplication2 {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new Ventana().setVisible(true);
        });
    }
}