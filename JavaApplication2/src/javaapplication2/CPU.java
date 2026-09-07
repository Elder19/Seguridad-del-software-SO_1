public class CPU {

    private int AC;
    private int PC;

    private int AX;
    private int BX;
    private int CX;
    private int DX;

    private String IR;

    public CPU() {

        AC = 0;
        PC = 0;

        AX = 0;
        BX = 0;
        CX = 0;
        DX = 0;

        IR = "";

    }

    public int getAC() {
        return AC;
    }

    public int getPC() {
        return PC;
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

    public void setAC(int AC) {
        this.AC = AC;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setAX(int AX) {
        this.AX = AX;
    }

    public void setBX(int BX) {
        this.BX = BX;
    }

    public void setCX(int CX) {
        this.CX = CX;
    }

    public void setDX(int DX) {
        this.DX = DX;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }
    

}