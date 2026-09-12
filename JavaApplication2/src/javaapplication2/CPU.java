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
    /*Metodo para guardar los cambios de contexto entre programas o procesos*/
    public void cargarContexto(Memory memory,int pid) {
        
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

    public void ejecutarInstruccion(Memory memory,Proceso proceso) {
        /*Lee la instruccion de la memoria*/
        Instruccion instruccion =memory.leerInstruccion( proceso,PC);
        this.IR =instruccion.getBinario();//instruccion actual en binario la guarda en en el IR
        String operador = instruccion.getOperacion(); // toma el tipo de operacion que se hace
        
        switch (operador) {

            case "MOV":
                ejecutarMOV(instruccion);
                break;

            case "LOAD":
                ejecutarLOAD(instruccion);
                break;

            case "STORE":
                ejecutarSTORE(instruccion);
                break;

            case "ADD":
                ejecutarADD(instruccion);
                break;

            case "SUB":
                ejecutarSUB(instruccion);
                break;

            default:
                throw new IllegalArgumentException(
                        "Operación no válida: "
                        + operador
                );
        }
        PC++;

       //Respalda la bcp del cpu en la del proceso
        guardarContexto(proceso.getBcp());
        memory.actualizarBCP(proceso);
    }

    /*---------------- MOV ----------------*/

    private void ejecutarMOV(Instruccion instruccion) 
    {String registro =instruccion.getRegistro();

        int valor =instruccion.getValor();
        asignarRegistro( registro,valor );
    }


    /*---------------- LOAD ----------------*/

    private void ejecutarLOAD(Instruccion instruccion) {

        String registro =instruccion.getRegistro();
        /* AC recibe el contenidodel registro.*/
        AC = obtenerRegistro(registro);
    }


    /*---------------- STORE ----------------*/

    private void ejecutarSTORE(
            Instruccion instruccion) {

        String registro =
                instruccion.getRegistro();

        /*El registro recibe el contenido del AC.*/
        asignarRegistro(registro, AC);
    }


    /*---------------- ADD ----------------*/

    private void ejecutarADD(Instruccion instruccion) {
        String registro = instruccion.getRegistro();
        AC =AC + obtenerRegistro( registro);
    }

    /*---------------- SUB ----------------*/

    private void ejecutarSUB(
        Instruccion instruccion) {
        String registro =instruccion.getRegistro();
        AC =AC - obtenerRegistro(registro);
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


    /*---------------- EJECUTAR TODO ----------------*/

    public void ejecutarTodo(
            Memory memory,
            Proceso proceso) {

        while (PC< proceso.getBcp().getTamanio()) {
            ejecutarInstruccion(memory,proceso);
            printCPU();
        }
        System.out.println("Proceso terminado.");
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
    
    public void reiniciar() {
    PC = 0;
    AC = 0;
    AX = 0;
    BX = 0;
    CX = 0;
    DX = 0;
    IR = null;
}


    /*---------------- IMPRIMIR CPU ----------------*/

    public void printCPU() {

        System.out.println(
                "----------- CPU -----------"
        );

        System.out.println(
                "PC: " + PC
        );

        System.out.println(
                "IR: " + IR
        );

        System.out.println(
                "AC: " + AC
        );

        System.out.println(
                "AX: " + AX
        );

        System.out.println(
                "BX: " + BX
        );

        System.out.println(
                "CX: " + CX
        );

        System.out.println(
                "DX: " + DX
        );

        System.out.println(
                "---------------------------"
        );
    }
}