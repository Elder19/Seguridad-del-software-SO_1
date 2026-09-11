/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication2;

/**
 *
 * @author elder la clase administra un unico proceso actual 
 */
public class Proceso {
    
    private Programa Programa; 
    private BCP bcp; 
    
    public Proceso(Programa Programa, int pid){
        this.Programa= Programa; 
        this.bcp = new BCP(pid, "Ready");
    }
 
    public Programa getPrograma() {
        return Programa;
    }

    public BCP getBcp() {
        return bcp;
    }

    public void setPrograma(Programa Programa) {
        this.Programa = Programa;
    }

    public void setBcp(BCP bcp) {
        this.bcp = bcp;
    }
    
    
}
