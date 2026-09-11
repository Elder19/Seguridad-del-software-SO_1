package javaapplication2;

public class CPU {

    private int PC;
    private int AC;

    private int AX;
    private int BX;
    private int CX;
    private int DX;

    private String IR;


    public CPU() {

        this.PC = 0;
        this.AC = 0;

        this.AX = 0;
        this.BX = 0;
        this.CX = 0;
        this.DX = 0;

        this.IR = null;
    }


    /*---------------- CARGAR CONTEXTO ----------------*/

    public void cargarContexto(Memory memory, int pid) {

        int[] contexto = memory.obtenerContexto(pid);

        this.PC = contexto[0];
        this.AC = contexto[1];
        this.AX = contexto[2];
        this.BX = contexto[3];
        this.CX = contexto[4];
        this.DX = contexto[5];

        this.IR = null;
    }


    /*---------------- EJECUTAR INSTRUCCIÓN ----------------*/

    public void ejecutarInstruccion(
            Memory memory,
            Proceso proceso) {

        
        String instruccion =
                memory.leerInstruccion(proceso, PC);

       
        this.IR = instruccion;

        
        String[] partes =
                instruccion.trim().split("[,\\s]+");

        String operador =
                partes[0].toUpperCase();

       
        switch (operador) {

            case "MOV":
                ejecutarMOV(partes);
                break;

            case "LOAD":
                ejecutarLOAD(partes);
                break;

            case "STORE":
                ejecutarSTORE(partes);
                break;

            case "ADD":
                ejecutarADD(partes);
                break;

            case "SUB":
                ejecutarSUB(partes);
                break;

            default:
                throw new IllegalArgumentException(
                        "Operación no válida: "
                        + operador
                );
        }

       
        PC++;

        // Guardar registros actuales en el BCP
        guardarContexto(proceso.getBcp());

       
        memory.actualizarBCP(proceso);
    }


    /*---------------- MOV ----------------*/

    private void ejecutarMOV(String[] partes) {

   
        String registro =
                partes[1].toUpperCase();

        int valor =
                Integer.parseInt(partes[2]);

        asignarRegistro(registro, valor);
    }


    /*---------------- LOAD ----------------*/

    private void ejecutarLOAD(String[] partes) {

       
        String registro =
                partes[1].toUpperCase();

        // AC recibe el valor del registro
        AC = obtenerRegistro(registro);
    }


    /*---------------- STORE ----------------*/

    private void ejecutarSTORE(String[] partes) {


        String registro =
                partes[1].toUpperCase();

        // El registro recibe el valor del AC
        asignarRegistro(registro, AC);
    }


    /*---------------- ADD ----------------*/

    private void ejecutarADD(String[] partes) {


        String registro =
                partes[1].toUpperCase();

        AC = AC + obtenerRegistro(registro);
    }


    /*---------------- SUB ----------------*/

    private void ejecutarSUB(String[] partes) {

        String registro =
                partes[1].toUpperCase();

        AC = AC - obtenerRegistro(registro);
    }


    /*---------------- OBTENER REGISTRO ----------------*/

    private int obtenerRegistro(String registro) {

        switch (registro) {

            case "AX":
                return AX;

            case "BX":
                return BX;

            case "CX":
                return CX;

            case "DX":
                return DX;

            default:
                throw new IllegalArgumentException(
                        "Registro no válido: "
                        + registro
                );
        }
    }


    /*---------------- ASIGNAR REGISTRO ----------------*/

    private void asignarRegistro(
            String registro,
            int valor) {

        switch (registro) {

            case "AX":
                AX = valor;
                break;

            case "BX":
                BX = valor;
                break;

            case "CX":
                CX = valor;
                break;

            case "DX":
                DX = valor;
                break;

            default:
                throw new IllegalArgumentException(
                        "Registro no válido: "
                        + registro
                );
        }
    }


    /*---------------- GUARDAR CONTEXTO ----------------*/

    private void guardarContexto(BCP bcp) {

        bcp.setPC(PC);
        bcp.setAC(AC);

        bcp.setAX(AX);
        bcp.setBX(BX);
        bcp.setCX(CX);
        bcp.setDX(DX);
    }


    /*---------------- GETTERS ----------------*/

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
    public void ejecutarTodo(
        Memory memory,
        Proceso proceso) {

    while (PC < proceso.getBcp().getTamanio()) {

        ejecutarInstruccion(
                memory,
                proceso
        );

        printCPU();
    }

    System.out.println(
            "Proceso terminado."
    );
}
  
 public void printCPU() {

    System.out.println("----------- CPU -----------");
    System.out.println("PC: " + PC);
    System.out.println("IR: " + IR);
    System.out.println("AC: " + AC);
    System.out.println("AX: " + AX);
    System.out.println("BX: " + BX);
    System.out.println("CX: " + CX);
    System.out.println("DX: " + DX);
    System.out.println("---------------------------");
}
}