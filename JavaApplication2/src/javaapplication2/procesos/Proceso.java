
package javaapplication2.procesos;

import javaapplication2.programa.Programa;
import javaapplication2.procesos.BCP;

public class Proceso {

    private Programa Programa;
    private BCP bcp;

    public Proceso(Programa Programa, int pid) {
        this.Programa = Programa;
        this.bcp = new BCP(pid, BCP.EstadoProceso.NUEVO);

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
