/*
 * Interfaz sencilla y editable desde NetBeans Design.
 * Toda la estructura visual se encuentra en Ventana.form.
 */
package javaapplication2;

import javaapplication2.SimuladorSO;
import javaapplication2.programa.Programa;
import javaapplication2.programa.Instruccion;
import javaapplication2.disco.IndicePrograma;
import javaapplication2.disco.Disco;
import javaapplication2.memoria.Memory;
import javaapplication2.procesos.PlanificadorDeTrabajo;
import javaapplication2.procesos.Proceso;
import javaapplication2.procesos.BCP;
import javaapplication2.cpu.CPU;

public class Ventana extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(Ventana.class.getName());
    private final SimuladorSO simulador = new SimuladorSO();
    private Programa programaActual;
    private Integer pidSeleccionado;
    // sirve para saber si el proceso seleccionado es el que está en ejecución
    private boolean modoAutomatico = false;
    private boolean automaticoEsperandoEntrada = false;

    // Indica si el worker automático está activo.
    private boolean workerAutomaticoActivo = false;

    // Consola independiente para cada proceso.
    private final java.util.Map<Integer, StringBuilder> consolasProcesos = new java.util.HashMap<>();

    // Texto que el usuario todavía no ha enviado con Enter en cada proceso.
    private final java.util.Map<Integer, String> entradasPendientes = new java.util.HashMap<>();

    // Evita que una actualización visual se confunda con escritura del usuario.
    private boolean actualizandoMonitor = false;
    private int inicioEntradaMonitor = 0;

    // Evita modificar el simulador desde dos hilos al mismo tiempo.
    private final Object bloqueoSimulador = new Object();

    // Se activa cuando una INT 21H cambió el contenido del disco.
    private volatile boolean discoPendienteDeActualizar = false;

    public Ventana() {
        initComponents();
        configurarMonitorConsola();
        aplicarTema();
        configurarInterfaz();
        setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        refrescarVista();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                comboOpciones = new javax.swing.JComboBox();
                btnEncender = new javax.swing.JToggleButton();
                panelIzquierdo = new javax.swing.JPanel();
                panelCPU = new javax.swing.JPanel();
                lblPC = new javax.swing.JLabel();
                lblAC = new javax.swing.JLabel();
                lblAX = new javax.swing.JLabel();
                lblBX = new javax.swing.JLabel();
                lblCX = new javax.swing.JLabel();
                lblDX = new javax.swing.JLabel();
                lblIR = new javax.swing.JLabel();
                lblSegundero = new javax.swing.JLabel();
                scrollRAM = new javax.swing.JScrollPane();
                tablaRAM = new javax.swing.JTable();
                scrollDisco = new javax.swing.JScrollPane();
                tablaDisco = new javax.swing.JTable();
                panelCentro = new javax.swing.JPanel();
                panelProgramas = new javax.swing.JPanel();
                scrollProgramas = new javax.swing.JScrollPane();
                tablaProgramas = new javax.swing.JTable();
                btnEliminar = new javax.swing.JButton();
                btnEjecutarPrograma = new javax.swing.JButton();
                scrollTrabajos = new javax.swing.JScrollPane();
                tablaTrabajos = new javax.swing.JTable();
                scrollInstrucciones = new javax.swing.JScrollPane();
                txtInstrucciones = new javax.swing.JTextArea();
                btnEjecutarTodo = new javax.swing.JButton();
                btnEjecutarPaso = new javax.swing.JButton();
                panelDerecho = new javax.swing.JPanel();
                scrollProcesos = new javax.swing.JScrollPane();
                tablaProcesos = new javax.swing.JTable();
                panelBCPEstadisticas = new javax.swing.JPanel();
                scrollBCP = new javax.swing.JScrollPane();
                txtBCP = new javax.swing.JTextArea();
                scrollEstadisticas = new javax.swing.JScrollPane();
                txtEstadisticas = new javax.swing.JTextArea();
                scrollMonitor = new javax.swing.JScrollPane();
                txtMonitor = new javax.swing.JTextArea();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Simulador de Sistema Operativo");
                setMinimumSize(new java.awt.Dimension(1000, 650));

                comboOpciones.setModel(new javax.swing.DefaultComboBoxModel(
                                new String[] { "Opciones", "Cargar programa", "Cambiar memoria",
                                                "Cambiar almacenamiento", "Reiniciar SO", "Acerca del SO" }));
                comboOpciones.addActionListener(this::comboOpcionesActionPerformed);

                btnEncender.setText("Encender");
                btnEncender.addActionListener(this::btnEncenderActionPerformed);

                panelCPU.setBorder(javax.swing.BorderFactory.createTitledBorder("CPU"));

                lblPC.setText("PC: 0");

                lblAC.setText("AC: 0");

                lblAX.setText("AX: 0");

                lblBX.setText("BX: 0");

                lblCX.setText("CX: 0");

                lblDX.setText("DX: 0");

                lblIR.setText("IR: -");

                lblSegundero.setText("Segundero: 0 / 0");

                javax.swing.GroupLayout panelCPULayout = new javax.swing.GroupLayout(panelCPU);
                panelCPU.setLayout(panelCPULayout);
                panelCPULayout.setHorizontalGroup(
                                panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelCPULayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(panelCPULayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(lblPC)
                                                                                .addComponent(lblAC)
                                                                                .addComponent(lblAX)
                                                                                .addComponent(lblBX)
                                                                                .addComponent(lblCX)
                                                                                .addComponent(lblDX)
                                                                                .addComponent(lblIR)
                                                                                .addComponent(lblSegundero))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                panelCPULayout.setVerticalGroup(
                                panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelCPULayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(lblPC)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblAC)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblAX)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblBX)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblCX)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblDX)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblIR)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lblSegundero)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                scrollRAM.setBorder(javax.swing.BorderFactory.createTitledBorder("MEMORIA RAM"));

                tablaRAM.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Posición", "Zona", "Contenido"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                scrollRAM.setViewportView(tablaRAM);

                scrollDisco.setBorder(javax.swing.BorderFactory.createTitledBorder("DISCO"));

                tablaDisco.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Posición", "Zona", "Contenido"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                scrollDisco.setViewportView(tablaDisco);

                javax.swing.GroupLayout panelIzquierdoLayout = new javax.swing.GroupLayout(panelIzquierdo);
                panelIzquierdo.setLayout(panelIzquierdoLayout);
                panelIzquierdoLayout.setHorizontalGroup(
                                panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                                                .addComponent(panelCPU,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollRAM,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                320, Short.MAX_VALUE))
                                                .addComponent(scrollDisco, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                                Short.MAX_VALUE));
                panelIzquierdoLayout.setVerticalGroup(
                                panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                                                .addGroup(panelIzquierdoLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(panelCPU,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(scrollRAM,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                280, Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollDisco,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                280, Short.MAX_VALUE)));

                panelProgramas.setBorder(javax.swing.BorderFactory.createTitledBorder("PROGRAMAS EN DISCO"));

                tablaProgramas.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Programa", "Tamaño"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                scrollProgramas.setViewportView(tablaProgramas);

                btnEliminar.setText("Eliminar");
                btnEliminar.addActionListener(this::btnEliminarActionPerformed);

                btnEjecutarPrograma.setText("Ejecutar");
                btnEjecutarPrograma.addActionListener(this::btnEjecutarProgramaActionPerformed);

                javax.swing.GroupLayout panelProgramasLayout = new javax.swing.GroupLayout(panelProgramas);
                panelProgramas.setLayout(panelProgramasLayout);
                panelProgramasLayout.setHorizontalGroup(
                                panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(scrollProgramas, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                250, Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                panelProgramasLayout.createSequentialGroup()
                                                                                .addContainerGap(
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(btnEliminar)
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(btnEjecutarPrograma)
                                                                                .addContainerGap()));
                panelProgramasLayout.setVerticalGroup(
                                panelProgramasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelProgramasLayout.createSequentialGroup()
                                                                .addComponent(scrollProgramas,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                150, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(panelProgramasLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnEliminar)
                                                                                .addComponent(btnEjecutarPrograma))
                                                                .addContainerGap()));

                scrollTrabajos.setBorder(javax.swing.BorderFactory.createTitledBorder("COLA DE TRABAJOS"));

                tablaTrabajos.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "ID", "Programa", "Tamaño"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                scrollTrabajos.setViewportView(tablaTrabajos);

                scrollInstrucciones.setBorder(javax.swing.BorderFactory.createTitledBorder("INSTRUCCIONES"));

                txtInstrucciones.setEditable(false);
                txtInstrucciones.setColumns(20);
                txtInstrucciones.setRows(5);
                scrollInstrucciones.setViewportView(txtInstrucciones);

                btnEjecutarTodo.setText("Ejecutar todo");
                btnEjecutarTodo.addActionListener(this::btnEjecutarTodoActionPerformed);

                btnEjecutarPaso.setText("Ejecutar paso");
                btnEjecutarPaso.addActionListener(this::btnEjecutarPasoActionPerformed);

                javax.swing.GroupLayout panelCentroLayout = new javax.swing.GroupLayout(panelCentro);
                panelCentro.setLayout(panelCentroLayout);
                panelCentroLayout.setHorizontalGroup(
                                panelCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(panelProgramas, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(scrollTrabajos, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                                Short.MAX_VALUE)
                                                .addComponent(scrollInstrucciones,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                                Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCentroLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(btnEjecutarTodo)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnEjecutarPaso)));
                panelCentroLayout.setVerticalGroup(
                                panelCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelCentroLayout.createSequentialGroup()
                                                                .addComponent(panelProgramas,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollTrabajos,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                150, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollInstrucciones,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                190, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(panelCentroLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnEjecutarTodo)
                                                                                .addComponent(btnEjecutarPaso))));

                scrollProcesos.setBorder(javax.swing.BorderFactory.createTitledBorder("PROCESOS"));

                tablaProcesos.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {
                                                "Estado", "PID", "Programa"
                                }) {
                        boolean[] canEdit = new boolean[] {
                                        false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                tablaProcesos.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tablaProcesosMouseClicked(evt);
                        }
                });
                scrollProcesos.setViewportView(tablaProcesos);

                scrollBCP.setBorder(javax.swing.BorderFactory.createTitledBorder("BCP DEL PROCESO SELECCIONADO"));

                txtBCP.setEditable(false);
                txtBCP.setColumns(20);
                txtBCP.setRows(5);
                txtBCP.setText("Sin proceso seleccionado.");
                scrollBCP.setViewportView(txtBCP);

                scrollEstadisticas.setBorder(javax.swing.BorderFactory.createTitledBorder("ESTADÍSTICAS DEL PROCESO"));

                txtEstadisticas.setEditable(false);
                txtEstadisticas.setColumns(20);
                txtEstadisticas.setRows(5);
                txtEstadisticas.setText("Sin proceso seleccionado.");
                scrollEstadisticas.setViewportView(txtEstadisticas);

                javax.swing.GroupLayout panelBCPEstadisticasLayout = new javax.swing.GroupLayout(panelBCPEstadisticas);
                panelBCPEstadisticas.setLayout(panelBCPEstadisticasLayout);
                panelBCPEstadisticasLayout.setHorizontalGroup(
                                panelBCPEstadisticasLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelBCPEstadisticasLayout.createSequentialGroup()
                                                                .addComponent(scrollBCP,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                220, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollEstadisticas,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                220, Short.MAX_VALUE)));
                panelBCPEstadisticasLayout.setVerticalGroup(
                                panelBCPEstadisticasLayout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(scrollBCP, javax.swing.GroupLayout.DEFAULT_SIZE, 180,
                                                                Short.MAX_VALUE)
                                                .addComponent(scrollEstadisticas, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                180, Short.MAX_VALUE));

                scrollMonitor.setBorder(javax.swing.BorderFactory.createTitledBorder("MONITOR"));

                txtMonitor.setEditable(false);
                txtMonitor.setColumns(20);
                txtMonitor.setRows(5);
                scrollMonitor.setViewportView(txtMonitor);

                javax.swing.GroupLayout panelDerechoLayout = new javax.swing.GroupLayout(panelDerecho);
                panelDerecho.setLayout(panelDerechoLayout);
                panelDerechoLayout.setHorizontalGroup(
                                panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(scrollProcesos, javax.swing.GroupLayout.DEFAULT_SIZE, 430,
                                                                Short.MAX_VALUE)
                                                .addComponent(panelBCPEstadisticas,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(scrollMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                                Short.MAX_VALUE));
                panelDerechoLayout.setVerticalGroup(
                                panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelDerechoLayout.createSequentialGroup()
                                                                .addComponent(scrollProcesos,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                220, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(panelBCPEstadisticas,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(scrollMonitor,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                180, Short.MAX_VALUE)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(comboOpciones,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(btnEncender))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(panelIzquierdo,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(panelCentro,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(panelDerecho,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(comboOpciones,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnEncender))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(panelIzquierdo,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(panelCentro,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(panelDerecho,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));

                pack();
        }// </editor-fold>//GEN-END:initComponents

    // Cambia únicamente colores y fuentes; no modifica la estructura creada por
    // Design.
    private void aplicarTema() {

        java.awt.Color fondo = new java.awt.Color(15, 23, 42);
        java.awt.Color panel = new java.awt.Color(30, 41, 59);
        java.awt.Color tabla = new java.awt.Color(51, 65, 85);
        java.awt.Color texto = new java.awt.Color(226, 232, 240);
        java.awt.Color borde = new java.awt.Color(71, 85, 105);

        getContentPane().setBackground(fondo);

        javax.swing.JPanel[] paneles = {
            panelIzquierdo,
            panelCentro,
            panelDerecho,
            panelProgramas,
            panelBCPEstadisticas,
            panelCPU
        };

        for (javax.swing.JPanel panelActual : paneles) {
            panelActual.setBackground(fondo);
        }

        javax.swing.JScrollPane[] scrolls = {
            scrollRAM,
            scrollDisco,
            scrollProgramas,
            scrollTrabajos,
            scrollInstrucciones,
            scrollProcesos,
            scrollBCP,
            scrollEstadisticas,
            scrollMonitor
        };

        for (javax.swing.JScrollPane scroll : scrolls) {
            scroll.setBackground(panel);
            scroll.getViewport().setBackground(panel);

            if (scroll.getBorder() instanceof javax.swing.border.TitledBorder) {

                javax.swing.border.TitledBorder titulo = (javax.swing.border.TitledBorder) scroll
                        .getBorder();

                titulo.setTitleColor(texto);
            }
        }

        if (panelCPU.getBorder() instanceof javax.swing.border.TitledBorder) {

            ((javax.swing.border.TitledBorder) panelCPU.getBorder())
                    .setTitleColor(texto);
        }

        if (panelProgramas.getBorder() instanceof javax.swing.border.TitledBorder) {

            ((javax.swing.border.TitledBorder) panelProgramas.getBorder())
                    .setTitleColor(texto);
        }

        javax.swing.JTable[] tablas = {
            tablaRAM,
            tablaDisco,
            tablaProgramas,
            tablaTrabajos,
            tablaProcesos
        };

        for (javax.swing.JTable tablaActual : tablas) {
            tablaActual.setBackground(tabla);
            tablaActual.setForeground(texto);
            tablaActual.setGridColor(borde);
            tablaActual.setSelectionBackground(
                    new java.awt.Color(30, 64, 175));
            tablaActual.setSelectionForeground(java.awt.Color.WHITE);
            tablaActual.setFillsViewportHeight(true);
            tablaActual.getTableHeader().setBackground(fondo);
            tablaActual.getTableHeader().setForeground(texto);
        }

        javax.swing.JTextArea[] areas = {
            txtInstrucciones,
            txtBCP,
            txtEstadisticas,
            txtMonitor
        };

        for (javax.swing.JTextArea area : areas) {
            area.setBackground(new java.awt.Color(17, 24, 39));
            area.setForeground(texto);
            area.setCaretColor(texto);
        }

        javax.swing.JLabel[] etiquetasCPU = {
            lblPC,
            lblAC,
            lblAX,
            lblBX,
            lblCX,
            lblDX,
            lblIR,
            lblSegundero
        };

        for (javax.swing.JLabel etiqueta : etiquetasCPU) {
            etiqueta.setForeground(texto);
        }

        comboOpciones.setBackground(tabla);
        comboOpciones.setForeground(texto);

        btnEncender.setBackground(new java.awt.Color(22, 163, 74));
        btnEncender.setForeground(java.awt.Color.WHITE);

        btnEjecutarPrograma.setBackground(new java.awt.Color(37, 99, 235));
        btnEjecutarPrograma.setForeground(java.awt.Color.WHITE);

        btnEjecutarTodo.setBackground(new java.awt.Color(37, 99, 235));
        btnEjecutarTodo.setForeground(java.awt.Color.WHITE);

        btnEjecutarPaso.setBackground(new java.awt.Color(37, 99, 235));
        btnEjecutarPaso.setForeground(java.awt.Color.WHITE);

        btnEliminar.setBackground(new java.awt.Color(220, 38, 38));
        btnEliminar.setForeground(java.awt.Color.WHITE);
    }

    /*
         * ==================================================
         * CONFIGURAR INTERFAZ
         * ==================================================
     */
    private void configurarInterfaz() {

        setMinimumSize(new java.awt.Dimension(1100, 700));

        java.awt.Font fuenteCodigo = new java.awt.Font(
                java.awt.Font.MONOSPACED,
                java.awt.Font.PLAIN,
                12);

        txtInstrucciones.setFont(fuenteCodigo);
        txtBCP.setFont(fuenteCodigo);
        txtEstadisticas.setFont(fuenteCodigo);
        txtMonitor.setFont(fuenteCodigo);

        txtBCP.setLineWrap(false);
        txtEstadisticas.setLineWrap(false);

        tablaProgramas.setModel(
                crearModeloSoloLectura(
                        "Programa",
                        "Tamaño",
                        "Dirección"));

        tablaTrabajos.setModel(
                crearModeloSoloLectura(
                        "ID",
                        "Programa",
                        "Tamaño",
                        "Estado"));

        tablaProcesos.setModel(
                crearModeloSoloLectura(
                        "Estado",
                        "PID",
                        "Programa",
                        "Orden"));

        configurarTabla(tablaRAM);
        configurarTabla(tablaDisco);
        configurarTabla(tablaProgramas);
        configurarTabla(tablaTrabajos);
        configurarTabla(tablaProcesos);

        tablaRAM.getColumnModel().getColumn(0).setPreferredWidth(65);
        tablaRAM.getColumnModel().getColumn(1).setPreferredWidth(135);
        tablaRAM.getColumnModel().getColumn(2).setPreferredWidth(360);

        tablaDisco.getColumnModel().getColumn(0).setPreferredWidth(65);
        tablaDisco.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaDisco.getColumnModel().getColumn(2).setPreferredWidth(380);

        tablaProgramas.getColumnModel().getColumn(0).setPreferredWidth(220);
        tablaProgramas.getColumnModel().getColumn(1).setPreferredWidth(70);
        tablaProgramas.getColumnModel().getColumn(2).setPreferredWidth(80);

        tablaTrabajos.getColumnModel().getColumn(0).setPreferredWidth(45);
        tablaTrabajos.getColumnModel().getColumn(1).setPreferredWidth(190);
        tablaTrabajos.getColumnModel().getColumn(2).setPreferredWidth(65);
        tablaTrabajos.getColumnModel().getColumn(3).setPreferredWidth(110);

        tablaProcesos.getColumnModel().getColumn(0).setPreferredWidth(105);
        tablaProcesos.getColumnModel().getColumn(1).setPreferredWidth(45);
        tablaProcesos.getColumnModel().getColumn(2).setPreferredWidth(190);
        tablaProcesos.getColumnModel().getColumn(3).setPreferredWidth(55);

        tablaProgramas
                .getSelectionModel()
                .addListSelectionListener(evento -> {

                    if (!evento.getValueIsAdjusting()) {
                        seleccionarProgramaDesdeTabla();
                    }
                });

        tablaProcesos
                .getSelectionModel()
                .addListSelectionListener(evento -> {

                    if (!evento.getValueIsAdjusting()) {
                        seleccionarProcesoDesdeTabla();
                    }
                });

        btnEjecutarTodo.setToolTipText(
                "Ejecuta automáticamente el flujo de CPU.");

        btnEjecutarPaso.setToolTipText(
                "Ejecuta exactamente un segundo de CPU.");

        txtMonitor.setToolTipText(
                "Cuando un proceso espere INT 09H, escriba un valor de 0 a 255 y presione Enter.");
    }

    private javax.swing.table.DefaultTableModel crearModeloSoloLectura(
            String... columnas) {

        return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                columnas) {

            public boolean isCellEditable(
                    int fila,
                    int columna) {

                return false;
            }
        };
    }

    private void configurarTabla(
            javax.swing.JTable tabla) {

        tabla.setRowHeight(22);
        tabla.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoResizeMode(
                javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
    }

    private void seleccionarProgramaDesdeTabla() {

        int fila = tablaProgramas.getSelectedRow();

        if (fila < 0) {
            return;
        }

        String nombre = String.valueOf(
                tablaProgramas.getValueAt(fila, 0));

        for (IndicePrograma indice : simulador.getDisco().getIndicesProgramas()) {

            if (indice.getNombre().equals(nombre)) {
                programaActual = simulador
                        .getDisco()
                        .obtenerPrograma(indice);
                actualizarInstrucciones();
                return;
            }
        }
    }

    private void seleccionarProcesoDesdeTabla() {

        int fila = tablaProcesos.getSelectedRow();

        if (fila < 0) {
            return;
        }

        pidSeleccionado = Integer.valueOf(
                String.valueOf(
                        tablaProcesos.getValueAt(fila, 1)));

        actualizarBCP();
        actualizarEstadisticas();
        actualizarInstrucciones();
        actualizarMonitor();
    }

    private void sincronizarTabla(
            javax.swing.table.DefaultTableModel modelo,
            java.util.List<Object[]> filas) {

        while (modelo.getRowCount() > filas.size()) {
            modelo.removeRow(modelo.getRowCount() - 1);
        }

        for (int fila = 0; fila < filas.size(); fila++) {

            Object[] datos = filas.get(fila);

            if (fila >= modelo.getRowCount()) {
                modelo.addRow(datos);
                continue;
            }

            for (int columna = 0; columna < datos.length; columna++) {

                Object anterior = modelo.getValueAt(
                        fila,
                        columna);

                Object nuevo = datos[columna];

                if (!java.util.Objects.equals(
                        anterior,
                        nuevo)) {

                    modelo.setValueAt(
                            nuevo,
                            fila,
                            columna);
                }
            }
        }
    }

    // Enciende o apaga el simulador.
    private void btnEncenderActionPerformed(java.awt.event.ActionEvent evt) {
        if (btnEncender.isSelected()) {
            simulador.encender();
            btnEncender.setText("Apagar");
            mostrarInformacion("Sistema operativo", "Sistema operativo encendido.");
            return;
        }
        boolean activos = false;
        for (Proceso p : simulador.getGestorProceso().getProcesos()) {
            if (p.getBcp().getEstadoProceso() != BCP.EstadoProceso.FINALIZADO) {
                activos = true;
                break;
            }
        }
        if (activos && !confirmar("Confirmar apagado", "Hay procesos cargados. ¿Desea apagar el sistema?")) {
            btnEncender.setSelected(true);
            return;
        }
        simulador.apagar();
        btnEncender.setText("Encender");
        mostrarInformacion("Sistema operativo", "Sistema operativo apagado.");
    }

    // Ejecuta la opción seleccionada en el menú superior.
    private void comboOpcionesActionPerformed(java.awt.event.ActionEvent evt) {
        String opcion = String.valueOf(comboOpciones.getSelectedItem());
        if ("Cargar programa".equals(opcion)) {
            seleccionarProgramas();
        } else if ("Cambiar memoria".equals(opcion)) {
            cambiarMemoria();
        } else if ("Cambiar almacenamiento".equals(opcion)) {
            cambiarAlmacenamiento();
        } else if ("Reiniciar SO".equals(opcion)) {
            reiniciarSO();
        } else if ("Acerca del SO".equals(opcion)) {
            mostrarAcercaDelDispositivo();
        }
        if (comboOpciones.getSelectedIndex() != 0) {
            comboOpciones.setSelectedIndex(0);
        }
    }

    // Permite seleccionar uno o varios archivos ASM y guardarlos en disco.
    private void seleccionarProgramas() {

        if (!simulador.isEncendido()) {
            mostrarAdvertencia(
                    "Sistema apagado",
                    "Primero debe encender el sistema operativo.");
            return;
        }

        javax.swing.JFileChooser selector = new javax.swing.JFileChooser();

        selector.setDialogTitle(
                "Seleccionar programas ASM");

        selector.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Archivos ASM (*.asm)",
                        "asm"));

        selector.setAcceptAllFileFilterUsed(false);
        selector.setMultiSelectionEnabled(true);

        if (selector.showOpenDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }

        java.io.File[] archivos = selector.getSelectedFiles();

        if (archivos == null
                || archivos.length == 0) {

            archivos = new java.io.File[]{
                selector.getSelectedFile()
            };
        }

        int cargados = 0;
        StringBuilder errores = new StringBuilder();

        for (java.io.File archivo : archivos) {

            try {

                synchronized (bloqueoSimulador) {
                    simulador.cargarPrograma(archivo);
                }

                cargados++;

            } catch (Exception e) {

                if (errores.length() > 0) {
                    errores.append("\n");
                }

                errores.append(archivo.getName())
                        .append(": ")
                        .append(e.getMessage());
            }
        }

        actualizarDisco();
        actualizarProgramas();

        if (errores.length() > 0) {

            mostrarError(
                    "Carga de programas",
                    "Cargados: "
                    + cargados
                    + "\n"
                    + errores);
        }
    }

    // Coloca el programa seleccionado en la lista de trabajos y trata de admitirlo
    // en memoria.
    private void btnEjecutarProgramaActionPerformed(
            java.awt.event.ActionEvent evt) {

        if (!simulador.isEncendido()) {

            mostrarAdvertencia(
                    "Sistema apagado",
                    "Primero debe encender el sistema operativo.");

            return;
        }

        int fila = tablaProgramas.getSelectedRow();

        if (fila == -1) {

            mostrarAdvertencia(
                    "Programa",
                    "Seleccione un programa.");

            return;
        }

        String nombreSeleccionado = String.valueOf(
                tablaProgramas.getValueAt(
                        fila,
                        0));

        try {

            synchronized (bloqueoSimulador) {

                IndicePrograma indice = null;

                for (IndicePrograma actual : simulador
                        .getDisco()
                        .getIndicesProgramas()) {

                    if (actual.getNombre()
                            .equals(nombreSeleccionado)) {

                        indice = actual;
                        break;
                    }
                }

                if (indice == null) {

                    throw new IllegalStateException(
                            "El programa seleccionado ya no existe en disco.");
                }

                programaActual = simulador
                        .getDisco()
                        .obtenerPrograma(indice);

                if (!simulador
                        .getMemory()
                        .guardarTrabajo(indice)) {

                    mostrarAdvertencia(
                            "Lista de trabajos",
                            "No hay espacio para registrar otro trabajo.");

                    return;
                }

                intentarAdmitirTrabajosPendientes();

                if (simulador
                        .getGestorProceso()
                        .getEjecucion() == null) {

                    simulador.despacharSiguiente();
                }
            }

            /*
                         * El modelo de programas no se reconstruye aquí, por lo que
                         * la selección queda exactamente donde el usuario la dejó.
             */
            refrescarProcesosYMemoria();

        } catch (Exception e) {

            mostrarError(
                    "Ejecutar programa",
                    e.getMessage());
        }
    }

    private void btnEliminarActionPerformed(
            java.awt.event.ActionEvent evt) {

        if (!simulador.isEncendido()) {

            mostrarAdvertencia(
                    "Sistema apagado",
                    "Primero debe encender el sistema operativo.");

            return;
        }

        int fila = tablaProgramas.getSelectedRow();

        if (fila == -1) {

            mostrarAdvertencia(
                    "Programa",
                    "Seleccione un programa.");

            return;
        }

        String nombreSeleccionado = String.valueOf(
                tablaProgramas.getValueAt(
                        fila,
                        0));

        try {

            IndicePrograma indice = null;

            for (IndicePrograma actual : simulador
                    .getDisco()
                    .getIndicesProgramas()) {

                if (actual.getNombre()
                        .equals(nombreSeleccionado)) {

                    indice = actual;
                    break;
                }
            }

            if (indice == null) {

                throw new IllegalStateException(
                        "El programa seleccionado ya no existe en disco.");
            }

            if (!confirmar(
                    "Eliminar programa",
                    "¿Desea eliminar \""
                    + indice.getNombre()
                    + "\"?")) {
                return;
            }

            synchronized (bloqueoSimulador) {
                simulador.EliminarArchivo(indice);
            }

            programaActual = null;
            tablaProgramas.clearSelection();

            actualizarDisco();
            actualizarProgramas();

            mostrarInformacion(
                    "Eliminar programa",
                    "Programa eliminado correctamente.");

        } catch (Exception e) {

            mostrarAdvertencia(
                    "Eliminar programa",
                    e.getMessage());
        }
    }

    // Ejecuta un segundo de CPU y actualiza la interfaz.
    private void btnEjecutarPasoActionPerformed(
            java.awt.event.ActionEvent evt) {

        modoAutomatico = false;
        automaticoEsperandoEntrada = false;

        try {

            Proceso actual;
            Instruccion instruccion;
            int segunderoAntes;
            int peso;
            boolean quedan;

            synchronized (bloqueoSimulador) {

                actual = simulador
                        .getGestorProceso()
                        .getEjecucion();

                if (actual == null) {

                    simulador.despacharSiguiente();

                    actual = simulador
                            .getGestorProceso()
                            .getEjecucion();
                }

                if (actual == null) {

                    mostrarAdvertencia(
                            "Ejecución",
                            "No hay procesos preparados para ejecutar.");

                    return;
                }

                instruccion = simulador
                        .getMemory()
                        .leerInstruccion(
                                actual,
                                simulador.getCpu().getPC());

                segunderoAntes = simulador
                        .getCpu()
                        .getSegundero();

                peso = instruccion.ObtenerPeso(
                        instruccion.getOperacion());

                quedan = simulador.ejecutarSiguienteInstruccion();

                if (!quedan
                        && actual.getBcp().getEstadoProceso() != BCP.EstadoProceso.EN_ESPERA) {

                    intentarAdmitirTrabajosPendientes();

                    if (simulador
                            .getGestorProceso()
                            .getEjecucion() == null) {

                        simulador.despacharSiguiente();
                    }
                }
            }

            registrarSalidaConsola(
                    actual,
                    instruccion,
                    segunderoAntes,
                    peso);

            if (!quedan
                    || actual.getBcp().getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA) {

                refrescarProcesosYMemoria();

            } else {

                refrescarEjecucion();
            }

        } catch (Exception e) {

            refrescarEjecucion();

            mostrarError(
                    "Ejecución",
                    e.getMessage());
        }
    }

    // Ejecuta automáticamente un segundo por vez para que se vea el avance del
    // segundero.
    private void btnEjecutarTodoActionPerformed(java.awt.event.ActionEvent evt) {

        synchronized (bloqueoSimulador) {

            if (simulador
                    .getGestorProceso()
                    .getEjecucion() == null) {

                simulador.despacharSiguiente();
            }
        }

        if (simulador
                .getGestorProceso()
                .getEjecucion() == null) {

            mostrarAdvertencia(
                    "Ejecución",
                    "No hay procesos preparados para ejecutar.");

            return;
        }

        modoAutomatico = true;
        automaticoEsperandoEntrada = false;

        iniciarEjecucionAutomatica();
    }

    private void iniciarEjecucionAutomatica() {

        if (workerAutomaticoActivo) {
            return;
        }

        workerAutomaticoActivo = true;

        btnEjecutarTodo.setEnabled(false);
        btnEjecutarPaso.setEnabled(false);

        new javax.swing.SwingWorker<Void, Void>() {

            protected Void doInBackground() throws Exception {

                boolean quedan = true;

                while (quedan) {

                    Proceso actual;
                    Instruccion instruccion;
                    int segunderoAntes;
                    int peso;
                    boolean quedoEnEspera;

                    synchronized (bloqueoSimulador) {

                        actual = simulador
                                .getGestorProceso()
                                .getEjecucion();

                        if (actual == null) {

                            simulador.despacharSiguiente();

                            actual = simulador
                                    .getGestorProceso()
                                    .getEjecucion();
                        }

                        if (actual == null) {
                            automaticoEsperandoEntrada = true;
                            break;
                        }

                        instruccion = simulador
                                .getMemory()
                                .leerInstruccion(
                                        actual,
                                        simulador.getCpu().getPC());

                        segunderoAntes = simulador
                                .getCpu()
                                .getSegundero();

                        peso = instruccion.ObtenerPeso(
                                instruccion.getOperacion());

                        quedan = simulador.ejecutarSiguienteInstruccion();

                        quedoEnEspera = actual.getBcp()
                                .getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA;
                    }

                    registrarSalidaConsola(
                            actual,
                            instruccion,
                            segunderoAntes,
                            peso);

                    javax.swing.SwingUtilities.invokeLater(
                            () -> refrescarEjecucion());

                    if (quedoEnEspera) {

                        Proceso procesoAhora;

                        synchronized (bloqueoSimulador) {

                            procesoAhora = simulador
                                    .getGestorProceso()
                                    .getEjecucion();
                        }

                        if (procesoAhora != null) {

                            Thread.sleep(1000);
                            continue;
                        }

                        automaticoEsperandoEntrada = true;
                        break;
                    }

                    Thread.sleep(1000);
                }

                return null;
            }

            protected void done() {

                try {

                    get();

                    if (!automaticoEsperandoEntrada) {

                        synchronized (bloqueoSimulador) {

                            intentarAdmitirTrabajosPendientes();

                            if (simulador
                                    .getGestorProceso()
                                    .getEjecucion() == null) {

                                simulador.despacharSiguiente();
                            }
                        }

                        modoAutomatico = false;
                    }

                    refrescarProcesosYMemoria();

                } catch (Exception e) {

                    modoAutomatico = false;
                    automaticoEsperandoEntrada = false;

                    refrescarProcesosYMemoria();

                    mostrarError(
                            "Ejecución",
                            e.getMessage());

                } finally {

                    workerAutomaticoActivo = false;

                    /*
                                         * Nunca dejamos bloqueados los controles solo porque
                                         * algún proceso esté EN_ESPERA. Si aparece otro READY,
                                         * el usuario puede iniciar paso o automático.
                     */
                    btnEjecutarTodo.setEnabled(true);
                    btnEjecutarPaso.setEnabled(true);
                }
            }

        }.execute();
    }

    // Selecciona un proceso para mostrar su BCP, estadísticas, instrucciones y
    // monitor.
    private void tablaProcesosMouseClicked(java.awt.event.MouseEvent evt) {
        seleccionarProcesoDesdeTabla();
    }

    /*
         * El panel derecho y el bloque INSTRUCCIONES deben representar
         * el mismo proceso.
         *
         * Si el usuario seleccionó un PID, ese PID tiene prioridad visual.
         * Si no existe selección, se muestra el proceso actualmente RUNNING.
     */
    private Proceso obtenerProcesoParaMostrar() {

        if (pidSeleccionado != null) {

            Proceso seleccionado = simulador
                    .getGestorProceso()
                    .buscarProcesoPorPid(
                            pidSeleccionado);

            if (seleccionado != null) {
                return seleccionado;
            }
        }

        return simulador
                .getGestorProceso()
                .getEjecucion();
    }

    // Refresca todos los componentes visibles del simulador.
    // Se usa solamente cuando cambió una parte grande del sistema.
    private void refrescarVista() {

        actualizarCPU();
        actualizarMemoria();
        actualizarDisco();
        actualizarProgramas();
        actualizarTrabajos();
        actualizarProcesos();
        actualizarBCP();
        actualizarEstadisticas();
        actualizarInstrucciones();
        actualizarMonitor();
    }

    // Refresco de cada segundo de CPU.
    // Las tablas se actualizan en el mismo modelo y NO pierden la selección.
    private void refrescarEjecucion() {

        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();
        actualizarBCP();
        actualizarEstadisticas();
        actualizarInstrucciones();
        actualizarMonitor();

        if (discoPendienteDeActualizar) {
            discoPendienteDeActualizar = false;
            actualizarDisco();
            actualizarProgramas();
        }
    }

    // Refresco cuando cambia admisión, estado, memoria o cola de trabajos.
    private void refrescarProcesosYMemoria() {

        actualizarMemoria();
        actualizarTrabajos();
        actualizarProcesos();
        actualizarCPU();
        actualizarBCP();
        actualizarEstadisticas();
        actualizarInstrucciones();
        actualizarMonitor();

        if (discoPendienteDeActualizar) {
            discoPendienteDeActualizar = false;
            actualizarDisco();
            actualizarProgramas();
        }
    }

    // Muestra los registros de CPU, IR y avance de la instrucción actual.
    private void actualizarCPU() {

        CPU cpu = simulador.getCpu();

        lblPC.setText("PC: " + cpu.getPC());
        lblAC.setText("AC: " + cpu.getAC());
        lblAX.setText("AX: " + cpu.getAX());
        lblBX.setText("BX: " + cpu.getBX());
        lblCX.setText("CX: " + cpu.getCX());
        lblDX.setText("DX: " + cpu.getDX());
        lblIR.setText(
                "IR: "
                + (cpu.getIR() == null
                ? "-"
                : cpu.getIR()));

        int peso = 0;

        Proceso proceso = simulador
                .getGestorProceso()
                .getEjecucion();

        if (proceso != null) {

            try {

                int limite = proceso.getBcp().getBase()
                        + proceso.getBcp().getTamanio();

                if (cpu.getPC() >= proceso.getBcp().getBase()
                        && cpu.getPC() < limite) {

                    Instruccion instruccion = simulador
                            .getMemory()
                            .leerInstruccion(
                                    proceso,
                                    cpu.getPC());

                    peso = instruccion.ObtenerPeso(
                            instruccion.getOperacion());
                }

            } catch (Exception e) {
                peso = 0;
            }
        }

        lblSegundero.setText(
                "Segundero: "
                + cpu.getSegundero()
                + " / "
                + peso);

        if (panelCPU.getBorder() instanceof javax.swing.border.TitledBorder) {

            javax.swing.border.TitledBorder borde = (javax.swing.border.TitledBorder) panelCPU.getBorder();

            borde.setTitle(
                    proceso == null
                            ? "CPU - LIBRE"
                            : "CPU - PID " + proceso.getBcp().getPid());

            panelCPU.repaint();
        }
    }

    // Muestra RAM sin reconstruir toda la JTable en cada segundo.
    private void actualizarMemoria() {

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaRAM.getModel();

        Memory memory = simulador.getMemory();
        Object[] memoria = memory.getMemoriaSnapshot();

        java.util.List<Proceso> procesos = simulador
                .getGestorProceso()
                .getProcesos();

        java.util.List<Object[]> trabajos = memory.obtenerTrabajos();

        java.util.List<Object[]> filas = new java.util.ArrayList<>();

        for (int posicion = 0; posicion < memoria.length; posicion++) {

            filas.add(
                    new Object[]{
                        posicion,
                        obtenerZonaRAM(
                                posicion,
                                memory,
                                procesos,
                                trabajos),
                        obtenerContenidoRAM(
                                posicion,
                                memoria[posicion],
                                memory,
                                procesos,
                                trabajos)
                    });
        }

        sincronizarTabla(
                modelo,
                filas);
    }

    private String obtenerZonaRAM(
            int posicion,
            Memory memory,
            java.util.List<Proceso> procesos,
            java.util.List<Object[]> trabajos) {

        if (posicion >= memory.getEspacioSO()) {

            for (Proceso proceso : procesos) {

                BCP bcp = proceso.getBcp();

                if (bcp.getBase() >= 0
                        && bcp.getTamanio() > 0
                        && posicion >= bcp.getBase()
                        && posicion < bcp.getBase() + bcp.getTamanio()) {

                    return "Usuario - PID " + bcp.getPid();
                }
            }

            return "Usuario";
        }

        for (Proceso proceso : procesos) {

            int inicioBCP = memory.obtenerPosicionBCP(
                    proceso.getBcp().getPid());

            if (inicioBCP >= 0
                    && posicion >= inicioBCP
                    && posicion < inicioBCP + memory.getTamanioBCP()) {

                return "SO - BCP PID "
                        + proceso.getBcp().getPid();
            }
        }

        for (Object[] trabajo : trabajos) {

            int id = (Integer) trabajo[0];
            int inicioTrabajo = memory.obtenerPosicionTrabajo(id);

            if (inicioTrabajo >= 0
                    && posicion >= inicioTrabajo
                    && posicion < inicioTrabajo + memory.getTamanioTrabajo()) {

                return "SO - Trabajo " + id;
            }
        }

        return memory.estaOcupadoSO(posicion)
                ? "SO - Reservado"
                : "SO - Libre";
    }

    private String obtenerContenidoRAM(
            int posicion,
            Object valor,
            Memory memory,
            java.util.List<Proceso> procesos,
            java.util.List<Object[]> trabajos) {

        for (Proceso proceso : procesos) {

            int inicioBCP = memory.obtenerPosicionBCP(
                    proceso.getBcp().getPid());

            if (inicioBCP >= 0
                    && posicion >= inicioBCP
                    && posicion < inicioBCP + memory.getTamanioBCP()) {

                int desplazamiento = posicion - inicioBCP;

                String nombreCampo = nombreCampoBCP(
                        desplazamiento);

                String contenido;

                if (desplazamiento == 15) {
                    contenido = valor == null
                            ? "Sin siguiente BCP"
                            : "Dirección RAM " + valor;
                } else {
                    contenido = valor == null
                            ? "null"
                            : String.valueOf(valor);
                }

                return nombreCampo
                        + ": "
                        + contenido;
            }
        }

        for (Object[] trabajo : trabajos) {

            int id = (Integer) trabajo[0];
            int inicioTrabajo = memory.obtenerPosicionTrabajo(id);

            if (inicioTrabajo >= 0
                    && posicion >= inicioTrabajo
                    && posicion < inicioTrabajo + memory.getTamanioTrabajo()) {

                int desplazamiento = posicion - inicioTrabajo;

                String[] camposTrabajo = {
                    "ID",
                    "Programa",
                    "Dirección disco",
                    "Tamaño"
                };

                return camposTrabajo[desplazamiento]
                        + ": "
                        + (valor == null
                                ? "null"
                                : valor);
            }
        }

        return valor == null
                ? ""
                : String.valueOf(valor);
    }

    private String nombreCampoBCP(
            int desplazamiento) {

        String[] campos = {
            "PID",
            "Estado",
            "PC",
            "AC",
            "AX",
            "BX",
            "CX",
            "DX",
            "IR",
            "Base",
            "Tamaño",
            "CPU",
            "Tiempo inicio",
            "Tiempo empleado",
            "Archivos abiertos",
            "Siguiente BCP",
            "Orden cola",
            "Prioridad",
            "Tope pila",
            "Pila[0]",
            "Pila[1]",
            "Pila[2]",
            "Pila[3]",
            "Pila[4]",
            "AH",
            "AL",
            "Texto DX"
        };

        if (desplazamiento < 0
                || desplazamiento >= campos.length) {

            return "Campo";
        }

        return campos[desplazamiento];
    }

    // Carga en la tabla el contenido completo del disco.
    private void actualizarDisco() {

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaDisco
                .getModel();

        Disco disco = simulador.getDisco();
        Object[] datos = disco.getDiscoSnapshot();

        java.util.List<Object[]> filas = new java.util.ArrayList<>();

        for (int i = 0; i < datos.length; i++) {

            Object dato = datos[i];
            String zona;
            String contenido;

            if (i < disco.getTotalIndices()) {

                zona = "Índice";

                if (dato instanceof IndicePrograma) {

                    IndicePrograma indice = (IndicePrograma) dato;

                    boolean esPrograma = indice.getNombre() != null
                            && indice.getNombre()
                                    .toLowerCase()
                                    .endsWith(".asm");

                    zona = esPrograma
                            ? "Índice programa"
                            : "Índice archivo";

                    contenido = "Nombre: " + indice.getNombre()
                            + " | Dirección: " + indice.getDireccion()
                            + " | Tamaño: " + indice.getTamanio();

                } else {

                    contenido = dato == null
                            ? "Libre"
                            : String.valueOf(dato);
                }

            } else if (i < disco.getInicioVirtual()) {

                zona = "Archivos";
                contenido = dato == null
                        ? "Libre"
                        : String.valueOf(dato);

            } else {

                zona = "Virtual";
                contenido = dato == null
                        ? "Libre"
                        : String.valueOf(dato);
            }

            filas.add(
                    new Object[]{
                        i,
                        zona,
                        contenido
                    });
        }

        sincronizarTabla(
                modelo,
                filas);
    }

    // Muestra programas ASM del disco y conserva la selección por nombre.
    private void actualizarProgramas() {

        String programaSeleccionado = null;
        int filaAnterior = tablaProgramas.getSelectedRow();

        if (filaAnterior >= 0
                && filaAnterior < tablaProgramas.getRowCount()) {

            programaSeleccionado = String.valueOf(
                    tablaProgramas.getValueAt(
                            filaAnterior,
                            0));
        }

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaProgramas
                .getModel();

        java.util.List<Object[]> filas = new java.util.ArrayList<>();

        for (IndicePrograma indice : simulador.getDisco().getIndicesProgramas()) {

            filas.add(
                    new Object[]{
                        indice.getNombre(),
                        indice.getTamanio(),
                        indice.getDireccion()
                    });
        }

        sincronizarTabla(
                modelo,
                filas);

        if (programaSeleccionado != null) {

            for (int fila = 0; fila < tablaProgramas.getRowCount(); fila++) {

                if (programaSeleccionado.equals(
                        String.valueOf(
                                tablaProgramas.getValueAt(
                                        fila,
                                        0)))) {

                    if (tablaProgramas.getSelectedRow() != fila) {

                        tablaProgramas.setRowSelectionInterval(
                                fila,
                                fila);
                    }

                    break;
                }
            }
        }
    }

    // Muestra la cola de trabajos y el estado de admisión.
    private void actualizarTrabajos() {

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaTrabajos
                .getModel();

        java.util.List<Object[]> filas = new java.util.ArrayList<>();

        for (Object[] trabajo : simulador.getMemory().obtenerTrabajos()) {

            String nombre = String.valueOf(trabajo[1]);

            if (!nombre.toLowerCase().endsWith(".asm")) {
                continue;
            }

            filas.add(
                    new Object[]{
                        trabajo[0],
                        trabajo[1],
                        trabajo[3],
                        "ESPERANDO RAM"
                    });
        }

        sincronizarTabla(
                modelo,
                filas);
    }

    // Muestra todos los procesos y conserva visualmente el PID seleccionado.
    private void actualizarProcesos() {

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaProcesos
                .getModel();

        java.util.List<Object[]> filas = new java.util.ArrayList<>();

        int filaSeleccionada = -1;
        int fila = 0;

        for (Proceso proceso : simulador
                .getGestorProceso()
                .getProcesos()) {

            filas.add(
                    new Object[]{
                        proceso.getBcp().getEstadoProceso(),
                        proceso.getBcp().getPid(),
                        proceso.getPrograma().getNombre(),
                        proceso.getBcp().getOrdenCola()
                    });

            if (pidSeleccionado != null
                    && proceso.getBcp().getPid() == pidSeleccionado) {

                filaSeleccionada = fila;
            }

            fila++;
        }

        sincronizarTabla(
                modelo,
                filas);

        if (filaSeleccionada >= 0
                && tablaProcesos.getSelectedRow() != filaSeleccionada) {

            tablaProcesos.setRowSelectionInterval(
                    filaSeleccionada,
                    filaSeleccionada);
        }
    }

    // Muestra el BCP completo del proceso seleccionado o del proceso en CPU.
    private void actualizarBCP() {

        Proceso proceso = obtenerProcesoParaMostrar();

        if (proceso == null) {
            txtBCP.setText("Sin proceso seleccionado.");

            if (scrollBCP.getBorder() instanceof javax.swing.border.TitledBorder) {

                ((javax.swing.border.TitledBorder) scrollBCP.getBorder())
                        .setTitle("BCP DEL PROCESO SELECCIONADO");

                scrollBCP.repaint();
            }

            return;
        }

        BCP bcp = proceso.getBcp();
        Memory memory = simulador.getMemory();

        int posicionBCP = memory.obtenerPosicionBCP(
                bcp.getPid());

        int siguienteBCP = -1;

        if (posicionBCP >= 0) {

            Object[] memoria = memory.getMemoriaSnapshot();

            Object enlace = memoria[posicionBCP + 15];

            if (enlace instanceof Integer) {
                siguienteBCP = (Integer) enlace;
            }
        }

        String archivos = bcp.getArchivosAbiertos().isEmpty()
                ? "[]"
                : bcp.getArchivosAbiertos().toString();

        String pila = java.util.Arrays.toString(
                bcp.getPila());

        StringBuilder texto = new StringBuilder();

        texto.append("Posición BCP RAM -> ")
                .append(
                        posicionBCP < 0
                                ? "Liberado"
                                : posicionBCP)
                .append("\n")
                .append("PID -> ").append(bcp.getPid()).append("\n")
                .append("Estado -> ").append(bcp.getEstadoProceso()).append("\n")
                .append("PC -> ").append(bcp.getPC()).append("\n")
                .append("AC -> ").append(bcp.getAC()).append("\n")
                .append("AX -> ").append(bcp.getAX()).append("\n")
                .append("BX -> ").append(bcp.getBX()).append("\n")
                .append("CX -> ").append(bcp.getCX()).append("\n")
                .append("DX -> ").append(bcp.getDX()).append("\n")
                .append("IR -> ")
                .append(bcp.getIR() == null ? "-" : bcp.getIR())
                .append("\n")
                .append("Base -> ").append(bcp.getBase()).append("\n")
                .append("Tamaño -> ").append(bcp.getTamanio()).append("\n")
                .append("CPU -> ").append(bcp.getCpu()).append("\n")
                .append("Tiempo inicio -> ")
                .append(formatearHora(bcp.getTiempoInicio()))
                .append("\n")
                .append("Tiempo empleado -> ")
                .append(bcp.getTiempoEmpleado())
                .append(" s\n")
                .append("Archivos abiertos -> ")
                .append(archivos)
                .append("\n")
                .append("Siguiente BCP -> ")
                .append(
                        siguienteBCP < 0
                                ? "-"
                                : siguienteBCP)
                .append("\n")
                .append("Orden cola -> ")
                .append(bcp.getOrdenCola())
                .append("\n")
                .append("Prioridad -> ")
                .append(bcp.getPrioridad())
                .append("\n")
                .append("Tope pila -> ")
                .append(bcp.getTopePila())
                .append("\n")
                .append("Pila -> ")
                .append(pila)
                .append("\n")
                .append("AH -> ")
                .append(bcp.getAH())
                .append("\n")
                .append("AL -> ")
                .append(bcp.getAL() == null ? "-" : bcp.getAL())
                .append("\n")
                .append("Texto DX -> ")
                .append(
                        bcp.getTextoDX() == null
                        ? "-"
                        : bcp.getTextoDX());

        txtBCP.setText(
                texto.toString());

        txtBCP.setCaretPosition(0);

        if (scrollBCP.getBorder() instanceof javax.swing.border.TitledBorder) {

            javax.swing.border.TitledBorder borde = (javax.swing.border.TitledBorder) scrollBCP.getBorder();

            borde.setTitle(
                    "BCP DEL PROCESO - PID "
                    + bcp.getPid());

            scrollBCP.repaint();
        }
    }

    // Muestra la información contable requerida para el proceso seleccionado.
    private void actualizarEstadisticas() {

        Proceso proceso = obtenerProcesoParaMostrar();

        if (proceso == null) {
            txtEstadisticas.setText("Sin proceso seleccionado.");

            if (scrollEstadisticas.getBorder() instanceof javax.swing.border.TitledBorder) {

                ((javax.swing.border.TitledBorder) scrollEstadisticas.getBorder())
                        .setTitle("ESTADÍSTICAS DEL PROCESO");

                scrollEstadisticas.repaint();
            }

            return;
        }

        BCP bcp = proceso.getBcp();

        java.time.LocalTime inicio = bcp.getTiempoInicio();

        java.time.LocalTime finalCalculado = inicio == null
                ? null
                : inicio.plusSeconds(
                        bcp.getTiempoEmpleado());

        String horaFinal = bcp.getEstadoProceso() == BCP.EstadoProceso.FINALIZADO
                ? formatearHora(finalCalculado)
                : "En curso";

        String rangoMemoria = bcp.getBase() < 0
                || bcp.getTamanio() <= 0
                ? "Liberado"
                : bcp.getBase()
                + " - "
                + (bcp.getBase()
                + bcp.getTamanio()
                - 1);

        txtEstadisticas.setText(
                "Proceso: "
                + proceso.getPrograma().getNombre()
                + "\nPID: "
                + bcp.getPid()
                + "\nEstado: "
                + bcp.getEstadoProceso()
                + "\nInicio: "
                + formatearHora(inicio)
                + "\nFinal: "
                + horaFinal
                + "\nDuración: "
                + bcp.getTiempoEmpleado()
                + " s"
                + "\nCPU: "
                + bcp.getCpu()
                + "\nRango RAM: "
                + rangoMemoria
                + "\nPrioridad: "
                + bcp.getPrioridad());

        txtEstadisticas.setCaretPosition(0);

        if (scrollEstadisticas.getBorder() instanceof javax.swing.border.TitledBorder) {

            javax.swing.border.TitledBorder borde = (javax.swing.border.TitledBorder) scrollEstadisticas
                    .getBorder();

            borde.setTitle(
                    "ESTADÍSTICAS - PID "
                    + bcp.getPid());

            scrollEstadisticas.repaint();
        }
    }

    private String formatearHora(
            java.time.LocalTime hora) {

        if (hora == null) {
            return "-";
        }

        return hora.format(
                java.time.format.DateTimeFormatter
                        .ofPattern("h:mm a"));
    }

    // Muestra las instrucciones del MISMO proceso seleccionado en la tabla de
    // procesos.
    private void actualizarInstrucciones() {

        txtInstrucciones.setText("");

        Proceso proceso = obtenerProcesoParaMostrar();

        /*
                 * Si todavía no existe proceso, se permite mostrar el programa
                 * seleccionado desde "PROGRAMAS EN DISCO".
         */
        if (proceso == null && programaActual == null) {
            return;
        }

        Programa programa = proceso != null
                ? proceso.getPrograma()
                : programaActual;

        int indiceActual = -1;

        if (proceso != null
                && proceso.getBcp().getBase() >= 0
                && proceso.getBcp().getTamanio() > 0) {

            /*
                         * Si este es el RUNNING, la referencia exacta es el PC de CPU.
                         * Para PREPARADO o EN_ESPERA usamos el PC guardado en su BCP.
             */
            Proceso ejecutando = simulador
                    .getGestorProceso()
                    .getEjecucion();

            int pcMostrar = proceso == ejecutando
                    ? simulador.getCpu().getPC()
                    : proceso.getBcp().getPC();

            indiceActual = pcMostrar
                    - proceso.getBcp().getBase();
        }

        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < programa.getInstrucciones().size(); i++) {

            texto.append(
                    i == indiceActual
                            ? "▶ "
                            : "  ");

            texto.append(i)
                    .append("   ")
                    .append(
                            programa
                                    .getInstrucciones()
                                    .get(i));

            if (i < programa.getInstrucciones().size() - 1) {
                texto.append("\n");
            }
        }

        txtInstrucciones.setText(
                texto.toString());

        /*
                 * Mantener visible la instrucción actual del proceso mostrado.
         */
        if (indiceActual >= 0) {

            int posicion = 0;

            for (int i = 0; i < indiceActual
                    && i < programa.getInstrucciones().size(); i++) {

                posicion += ("  " + i + "   "
                        + programa.getInstrucciones().get(i)
                        + "\n").length();
            }

            final int caret = Math.min(
                    posicion,
                    txtInstrucciones
                            .getDocument()
                            .getLength());

            javax.swing.SwingUtilities.invokeLater(
                    () -> txtInstrucciones.setCaretPosition(caret));
        }
    }

    // Admite trabajos en orden mientras exista espacio disponible en memoria.
    private int intentarAdmitirTrabajosPendientes() {
        int admitidos = 0;
        PlanificadorDeTrabajo planificador = new PlanificadorDeTrabajo(simulador.getMemory(),
                simulador.getGestorProceso(), simulador.getDisco());
        while (simulador.getMemory().hayTrabajos()) {
            Proceso proceso = planificador.planificarSiguiente();
            if (proceso == null) {
                break;
            }
            admitidos++;
        }
        return admitidos;
    }

    // Cambia el tamaño de RAM y limpia los procesos actuales.
    private void cambiarMemoria() {

        if (!simulador.isEncendido()) {
            mostrarAdvertencia(
                    "Sistema apagado",
                    "Primero debe encender el sistema operativo.");
            return;
        }

        String valor = javax.swing.JOptionPane.showInputDialog(
                this,
                "Nuevo tamaño de RAM (mínimo 128):");

        if (valor == null) {
            return;
        }

        try {

            int tamanio = Integer.parseInt(
                    valor.trim());

            if (tamanio < 128) {
                mostrarAdvertencia(
                        "Memoria",
                        "El mínimo es 128.");
                return;
            }

            if (!confirmar(
                    "Cambiar memoria",
                    "Se eliminarán los procesos actuales. ¿Continuar?")) {
                return;
            }

            synchronized (bloqueoSimulador) {
                simulador.cambiarMemoria(tamanio);
            }

            limpiarEstadoVisualProcesos();
            refrescarVista();

        } catch (Exception e) {

            mostrarError(
                    "Memoria",
                    e.getMessage());
        }
    }

    // Cambia el tamaño del almacenamiento y limpia la ejecución actual.
    private void cambiarAlmacenamiento() {

        if (!simulador.isEncendido()) {
            mostrarAdvertencia(
                    "Sistema apagado",
                    "Primero debe encender el sistema operativo.");
            return;
        }

        String valor = javax.swing.JOptionPane.showInputDialog(
                this,
                "Nuevo tamaño de almacenamiento (mínimo 512):");

        if (valor == null) {
            return;
        }

        try {

            int tamanio = Integer.parseInt(
                    valor.trim());

            if (tamanio < 512) {
                mostrarAdvertencia(
                        "Almacenamiento",
                        "El mínimo es 512.");
                return;
            }

            if (!confirmar(
                    "Cambiar almacenamiento",
                    "Se eliminarán los datos y procesos actuales. ¿Continuar?")) {
                return;
            }

            synchronized (bloqueoSimulador) {
                simulador.cambiarAlmacenamiento(tamanio);
            }

            limpiarEstadoVisualProcesos();
            refrescarVista();

        } catch (Exception e) {

            mostrarError(
                    "Almacenamiento",
                    e.getMessage());
        }
    }

    // Reinicia CPU, RAM y procesos del simulador.
    private void reiniciarSO() {

        if (!simulador.isEncendido()) {
            mostrarAdvertencia(
                    "Sistema apagado",
                    "El sistema operativo está apagado.");
            return;
        }

        if (!confirmar(
                "Reiniciar",
                "¿Desea reiniciar el sistema operativo?")) {
            return;
        }

        synchronized (bloqueoSimulador) {
            simulador.reiniciarSistema();
        }

        limpiarEstadoVisualProcesos();
        refrescarVista();
    }

    private void limpiarEstadoVisualProcesos() {

        programaActual = null;
        pidSeleccionado = null;
        modoAutomatico = false;
        automaticoEsperandoEntrada = false;

        consolasProcesos.clear();
        entradasPendientes.clear();

        inicioEntradaMonitor = 0;

        actualizandoMonitor = true;

        try {
            txtMonitor.setText("");
        } finally {
            actualizandoMonitor = false;
        }
    }

    /*---------------- CONSOLA POR PROCESO ----------------*/

 /*
         * El monitor conserva por separado:
         *
         * 1. La salida ya confirmada del proceso.
         * 2. Lo que el usuario está escribiendo antes de presionar Enter.
         *
         * Por eso una actualización de CPU/RAM nunca borra un valor parcialmente
         * escrito en una INT 09H.
     */
    private void configurarMonitorConsola() {

        txtMonitor.setEditable(false);
        txtMonitor.setFocusable(true);
        txtMonitor.setRequestFocusEnabled(true);

        txtMonitor.setLineWrap(true);
        txtMonitor.setWrapStyleWord(true);

        txtMonitor.addKeyListener(
                new java.awt.event.KeyAdapter() {

            public void keyPressed(
                    java.awt.event.KeyEvent evt) {

                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {

                    evt.consume();

                    ingresarValorDesdeMonitor();
                }
            }
        });
    }

    private void guardarEntradaPendiente() {

        if (actualizandoMonitor) {
            return;
        }

        Proceso proceso = obtenerProcesoSeleccionado();

        if (proceso == null
                || proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.EN_ESPERA) {

            return;
        }

        String texto = txtMonitor.getText();

        int inicio = Math.min(
                Math.max(
                        inicioEntradaMonitor,
                        0),
                texto.length());

        entradasPendientes.put(
                proceso.getBcp().getPid(),
                texto.substring(inicio));
    }

    // Devuelve el proceso que el usuario tiene seleccionado en la tabla.
    private Proceso obtenerProcesoSeleccionado() {

        if (pidSeleccionado == null) {
            return simulador
                    .getGestorProceso()
                    .getEjecucion();
        }

        return simulador
                .getGestorProceso()
                .buscarProcesoPorPid(
                        pidSeleccionado);
    }

    private Proceso obtenerProcesoEsperandoTeclado() {

        for (Proceso proceso : simulador.getGestorProceso().getProcesos()) {

            if (proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA
                    && "INT 09H".equalsIgnoreCase(
                            proceso.getBcp().getIR())) {

                return proceso;
            }
        }

        return null;
    }

    // Muestra la consola del proceso seleccionado.
    // Si está esperando INT 09H, permite escribir sin borrar la entrada.
    private void actualizarMonitor() {

        Proceso proceso = obtenerProcesoSeleccionado();

        if (proceso == null) {

            txtMonitor.setEditable(false);
            txtMonitor.setText("");
            inicioEntradaMonitor = 0;

            return;
        }

        int pid = proceso.getBcp().getPid();

        /*---------------------------------------
                  Título del monitor
                ---------------------------------------*/
        if (scrollMonitor.getBorder() instanceof javax.swing.border.TitledBorder) {

            javax.swing.border.TitledBorder borde = (javax.swing.border.TitledBorder) scrollMonitor
                    .getBorder();

            borde.setTitle(
                    "MONITOR - PID " + pid);

            scrollMonitor.repaint();
        }

        StringBuilder consola = consolasProcesos.computeIfAbsent(
                pid,
                clave -> new StringBuilder());

        /*
                 * Solamente se habilita teclado cuando
                 * realmente está detenido en INT 09H.
         */
        boolean esperandoEntrada = proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA
                && "INT 09H".equalsIgnoreCase(
                        proceso.getBcp().getIR());

        /*
                 * ==================================================
                 * NO ESTÁ ESPERANDO TECLADO
                 * ==================================================
         */
        if (!esperandoEntrada) {

            txtMonitor.setEditable(false);

            String texto = consola.toString();

            if (!txtMonitor.getText().equals(texto)) {

                actualizandoMonitor = true;

                try {

                    txtMonitor.setText(texto);

                } finally {

                    actualizandoMonitor = false;
                }
            }

            inicioEntradaMonitor = txtMonitor.getDocument().getLength();

            return;
        }

        /*
                 * ==================================================
                 * ESPERANDO INT 09H
                 * ==================================================
         */
        txtMonitor.setEditable(true);
        txtMonitor.setFocusable(true);

        /*
                 * Construir solamente la parte FIJA de la consola.
         */
        String textoBase = consola.toString();

        if (!textoBase.isEmpty()
                && !textoBase.endsWith("\n")) {

            textoBase += "\n";
        }

        textoBase += "> ";

        /*
                 * A partir de aquí puede escribir el usuario.
         */
        inicioEntradaMonitor = textoBase.length();

        /*
                 * MUY IMPORTANTE:
                 *
                 * Si el usuario YA está escribiendo,
                 * NO tocamos el contenido del JTextArea.
                 *
                 * Esto evita:
                 *
                 * > 5
                 *
                 * convertirse otra vez en:
                 *
                 * >
         */
        if (txtMonitor.isFocusOwner()
                && txtMonitor.getText()
                        .startsWith(textoBase)) {

            return;
        }

        /*
                 * Si venimos de otro proceso o de otro refresco,
                 * recuperamos lo que hubiera escrito.
         */
        String entradaGuardada = entradasPendientes.getOrDefault(
                pid,
                "");

        actualizandoMonitor = true;

        try {

            txtMonitor.setText(
                    textoBase
                    + entradaGuardada);

            txtMonitor.setCaretPosition(
                    txtMonitor
                            .getDocument()
                            .getLength());

        } finally {

            actualizandoMonitor = false;
        }

        /*
                 * Cuando aparece INT 09H,
                 * mandamos automáticamente el teclado al monitor.
         */
        javax.swing.SwingUtilities.invokeLater(
                () -> {

                    txtMonitor.requestFocusInWindow();

                    txtMonitor.setCaretPosition(
                            txtMonitor
                                    .getDocument()
                                    .getLength());
                });
    }
    // Guarda texto confirmado en la consola del proceso indicado.

    private synchronized void escribirEnConsola(
            Proceso proceso,
            String texto) {

        if (proceso == null
                || texto == null
                || texto.isEmpty()) {

            return;
        }

        int pid = proceso.getBcp().getPid();

        StringBuilder consola = consolasProcesos.computeIfAbsent(
                pid,
                clave -> new StringBuilder());

        if (consola.length() > 0
                && consola.charAt(consola.length() - 1) != '\n') {

            consola.append('\n');
        }

        consola.append(texto);

        Proceso visible = obtenerProcesoSeleccionado();

        if (visible != null
                && visible.getBcp().getPid() == pid) {

            javax.swing.SwingUtilities.invokeLater(
                    this::actualizarMonitor);
        }
    }

    // Registra salidas de E/S sin refrescar componentes que no cambiaron.
    private void registrarSalidaConsola(
            Proceso proceso,
            Instruccion instruccion,
            int segunderoAntes,
            int peso) {

        if (proceso == null
                || instruccion == null
                || peso <= 0) {

            return;
        }

        boolean termino = segunderoAntes + 1 >= peso;

        if (!termino) {
            return;
        }

        String operacion = instruccion
                .getOperacion()
                .toUpperCase();

        if (operacion.equals("INT 10H")) {

            String salida = simulador
                    .getCpu()
                    .getSalidaMonitor();

            if (salida != null
                    && !salida.isEmpty()) {

                escribirEnConsola(
                        proceso,
                        salida);
            }
        }

        if (operacion.equals("INT 21H")) {

            discoPendienteDeActualizar = true;

            if (simulador.getCpu().getAH() == 0x4D) {

                escribirEnConsola(
                        proceso,
                        String.valueOf(
                                simulador
                                        .getCpu()
                                        .getAL()));
            }
        }

        if (operacion.equals("INT 09H")
                && proceso.getBcp().getEstadoProceso() == BCP.EstadoProceso.EN_ESPERA) {

            escribirEnConsola(
                    proceso,
                    "Esperando entrada de teclado (0-255)");
        }
    }

    // Completa la INT 09H con el valor escrito después del prompt.
    private void ingresarValorDesdeMonitor() {

        guardarEntradaPendiente();

        Proceso proceso = obtenerProcesoSeleccionado();

        if (proceso == null) {

            mostrarAdvertencia(
                    "Consola",
                    "Seleccione un proceso.");

            return;
        }

        if (proceso.getBcp().getEstadoProceso() != BCP.EstadoProceso.EN_ESPERA) {

            mostrarAdvertencia(
                    "Consola",
                    "El proceso seleccionado no está esperando una entrada.");

            actualizarMonitor();
            return;
        }

        int pid = proceso.getBcp().getPid();

        String entrada = entradasPendientes
                .getOrDefault(
                        pid,
                        "")
                .trim();

        if (entrada.isEmpty()) {

            mostrarAdvertencia(
                    "Consola",
                    "Ingrese un número entre 0 y 255.");

            txtMonitor.requestFocusInWindow();
            return;
        }

        try {

            int valor = Integer.parseInt(
                    entrada);

            if (valor < 0
                    || valor > 255) {

                throw new IllegalArgumentException(
                        "El valor debe estar entre 0 y 255.");

            }
            actualizarMonitor();

            long segundosEspera;

            synchronized (bloqueoSimulador) {

                segundosEspera = simulador.completarEntradaTeclado(
                        pid,
                        valor);

            }

            /*
                         * Solo se borra el borrador cuando la entrada se aceptó.
                         * Si hay un error, el usuario conserva exactamente lo escrito.
             */
            entradasPendientes.remove(
                    pid);

            escribirEnConsola(
                    proceso,
                    "> " + valor);

            escribirEnConsola(
                    proceso,
                    "Entrada recibida. Tiempo de espera: "
                    + segundosEspera
                    + " s");

            refrescarProcesosYMemoria();

            if (modoAutomatico
                    && automaticoEsperandoEntrada) {

                automaticoEsperandoEntrada = false;

                if (!workerAutomaticoActivo) {
                    iniciarEjecucionAutomatica();
                }
            }

        } catch (NumberFormatException e) {

            mostrarError(
                    "Consola",
                    "Debe ingresar un número entero entre 0 y 255.");

            actualizarMonitor();
            txtMonitor.requestFocusInWindow();

        } catch (Exception e) {

            mostrarError(
                    "Consola",
                    e.getMessage());

            actualizarMonitor();
            txtMonitor.requestFocusInWindow();
        }
    }

    // Muestra la configuración principal del dispositivo.
    private void mostrarAcercaDelDispositivo() {
        Memory m = simulador.getMemory();
        Disco d = simulador.getDisco();
        mostrarInformacion("Acerca del dispositivo",
                "RAM total: " + m.getMemoriaSnapshot().length + "\nRAM SO: " + m.getEspacioSO()
                + "\nAlmacenamiento: " + d.getMemoriaTotal() + "\nMemoria virtual: "
                + d.getMemoriaVirtual() + "\nÍndices: " + d.getTotalIndices());
    }

    // Muestra un mensaje informativo.
    private void mostrarInformacion(String titulo, String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, titulo,
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Muestra una advertencia.
    private void mostrarAdvertencia(String titulo, String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, titulo,
                javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    // Muestra un error.
    private void mostrarError(String titulo, String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(this, mensaje, titulo, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    // Solicita una confirmación de sí o no.
    private boolean confirmar(String titulo, String mensaje) {
        return javax.swing.JOptionPane.showConfirmDialog(this, mensaje, titulo,
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE) == javax.swing.JOptionPane.YES_OPTION;
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Ventana().setVisible(true));
    }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnEjecutarPaso;
        private javax.swing.JButton btnEjecutarPrograma;
        private javax.swing.JButton btnEjecutarTodo;
        private javax.swing.JButton btnEliminar;
        private javax.swing.JToggleButton btnEncender;
        private javax.swing.JComboBox comboOpciones;
        private javax.swing.JLabel lblAC;
        private javax.swing.JLabel lblAX;
        private javax.swing.JLabel lblBX;
        private javax.swing.JLabel lblCX;
        private javax.swing.JLabel lblDX;
        private javax.swing.JLabel lblIR;
        private javax.swing.JLabel lblPC;
        private javax.swing.JLabel lblSegundero;
        private javax.swing.JPanel panelBCPEstadisticas;
        private javax.swing.JPanel panelCPU;
        private javax.swing.JPanel panelCentro;
        private javax.swing.JPanel panelDerecho;
        private javax.swing.JPanel panelIzquierdo;
        private javax.swing.JPanel panelProgramas;
        private javax.swing.JScrollPane scrollBCP;
        private javax.swing.JScrollPane scrollDisco;
        private javax.swing.JScrollPane scrollEstadisticas;
        private javax.swing.JScrollPane scrollInstrucciones;
        private javax.swing.JScrollPane scrollMonitor;
        private javax.swing.JScrollPane scrollProcesos;
        private javax.swing.JScrollPane scrollProgramas;
        private javax.swing.JScrollPane scrollRAM;
        private javax.swing.JScrollPane scrollTrabajos;
        private javax.swing.JTable tablaDisco;
        private javax.swing.JTable tablaProcesos;
        private javax.swing.JTable tablaProgramas;
        private javax.swing.JTable tablaRAM;
        private javax.swing.JTable tablaTrabajos;
        private javax.swing.JTextArea txtBCP;
        private javax.swing.JTextArea txtEstadisticas;
        private javax.swing.JTextArea txtInstrucciones;
        private javax.swing.JTextArea txtMonitor;
        // End of variables declaration//GEN-END:variables
}
