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
    

    public BCP(int Pid, String EstadoProceso, int PC, int AC) {
        this.Pid = Pid;
        this.EstadoProceso = EstadoProceso;
        this.PC = PC;
        this.AC = AC;
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
    
    
    
    
}
