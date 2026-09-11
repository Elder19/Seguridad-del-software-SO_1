/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication2;

/**
 *
 * @author elder
 */
public class BCP {
    private final int Pid;
    private String EstadoProceso; 
    private int PC;
    private int AC;
     private int AX;
    private int BX;
     private int CX;
       private int DX;
     
 
    private int base = -1;
    private int tamanio = 0;
    
  

    public BCP(int Pid, String EstadoProceso) {
        this.Pid = Pid;
        this.EstadoProceso = EstadoProceso;
        this.PC = 0;
        this.AC = 0;
        this.AX = 0;
        this.BX = 0;
        this.CX = 0;
        this.DX = 0;
    }
    
    public void ActualizarBCP(String EstadoProceso, int PC, int AC){
        this.EstadoProceso= EstadoProceso; 
        this.AC=AC; 
        this.PC = PC; 
    }

    public int getPid() {
        return Pid;
    }

    public String getEstadoProceso() {
        return EstadoProceso;
    }

    public int getPC() {
        return PC;
    }

    public int getAC() {
        return AC;
    }

    public void setEstadoProceso(String EstadoProceso) {
        this.EstadoProceso = EstadoProceso;
    }

    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }
    public int getBase() {
    return base;
}

public void setBase(int base) {
    this.base = base;
}

public int getTamanio() {
    return tamanio;
}

public void setTamanio(int tamanio) {
    this.tamanio = tamanio;
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
    
}
