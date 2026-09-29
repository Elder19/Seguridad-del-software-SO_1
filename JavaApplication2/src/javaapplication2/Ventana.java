package javaapplication2;
import javax.swing.JOptionPane;

public class Ventana extends javax.swing.JFrame {

    private final SimuladorSO simulador = new SimuladorSO();
    private Programa programaActual;
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Ventana.class.getName());
    private Integer pidSeleccionado = null;
    public Ventana() {
        initComponents();
        configurarInterfaz();
        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();
        actualizarDisco();
        actualizarTablaProgramas();
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        combobox = new javax.swing.JComboBox<>();
        ejecutarpaso = new javax.swing.JToggleButton();
        Ejecutartodo = new javax.swing.JToggleButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablamemoria = new javax.swing.JTable();
        panelCPU = new javax.swing.JPanel();
        txtCpuPC = new javax.swing.JLabel();
        txtCpuAC = new javax.swing.JLabel();
        txtCpuAX = new javax.swing.JLabel();
        txtCpuBX = new javax.swing.JLabel();
        txtCpuCX = new javax.swing.JLabel();
        txtCpuDX = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tablarocesos = new javax.swing.JTable();
        apagaencender = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtInstrucciones = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        lblBcpPid = new javax.swing.JLabel();
        lblBcpEstado = new javax.swing.JLabel();
        lblBcpBase = new javax.swing.JLabel();
        lblBcpTamanio = new javax.swing.JLabel();
        lblBcpAC = new javax.swing.JLabel();
        lblBcpPC = new javax.swing.JLabel();
        lblBcpAX = new javax.swing.JLabel();
        lblBcpBX = new javax.swing.JLabel();
        lblBcpCX = new javax.swing.JLabel();
        lblBcpDX = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDisco = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaProgramas = new javax.swing.JTable();
        btnEjecutarPrograma = new javax.swing.JButton();
        btnEliminarPrograma = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("me cago en licha");
        setMinimumSize(new java.awt.Dimension(1050, 680));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "  Sistema Operativo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jPanel2.setAutoscrolls(true);

        combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opciones", "Cargar programa", "Cambiar memoria", "Cambiar Almacenamiento", "Reiniciar SO ", "Acerca del SO" }));
        combobox.addActionListener(this::comboboxActionPerformed);

        ejecutarpaso.setText("⏭️ Ejecutar paso");
        ejecutarpaso.addActionListener(this::ejecutarpasoActionPerformed);

        Ejecutartodo.setText(" ⏩ Ejecutar todo");
        Ejecutartodo.addActionListener(this::EjecutartodoActionPerformed);

        tablamemoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Posicion", "Zona", "Contenido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablamemoria);

        panelCPU.setBorder(javax.swing.BorderFactory.createTitledBorder(" CPU  "));

        txtCpuPC.setText("PC:");

        txtCpuAC.setText("AC:");

        txtCpuAX.setText("AX:");

        txtCpuBX.setText("BX:");

        txtCpuCX.setText("CX:");

        txtCpuDX.setText("DX:");

        javax.swing.GroupLayout panelCPULayout = new javax.swing.GroupLayout(panelCPU);
        panelCPU.setLayout(panelCPULayout);
        panelCPULayout.setHorizontalGroup(
            panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCPULayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCpuAX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCPULayout.createSequentialGroup()
                        .addComponent(txtCpuAC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelCPULayout.createSequentialGroup()
                        .addGroup(panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCpuPC, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCpuDX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addComponent(txtCpuCX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 93, Short.MAX_VALUE))
                    .addComponent(txtCpuBX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelCPULayout.setVerticalGroup(
            panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCPULayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCpuPC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpuAC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpuAX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpuBX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpuCX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpuDX)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tablarocesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Estado ", "PID", "Programa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tablarocesos.setFillsViewportHeight(true);
        Tablarocesos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablarocesosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tablarocesos);

        apagaencender.setText(" ⏻ Encender");
        apagaencender.addActionListener(this::apagaencenderActionPerformed);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("INSTRUCCIONES"));

        txtInstrucciones.setEditable(false);
        txtInstrucciones.setBackground(new java.awt.Color(102, 102, 102));
        txtInstrucciones.setColumns(20);
        txtInstrucciones.setRows(5);
        txtInstrucciones.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jScrollPane3.setViewportView(txtInstrucciones);
        txtInstrucciones.getAccessibleContext().setAccessibleName("Instrucciones");
        txtInstrucciones.getAccessibleContext().setAccessibleDescription("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("BCP del proceso seleccionado"));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblBcpPid.setText("PID: -");

        lblBcpEstado.setText("Estado: -");

        lblBcpBase.setText("Base: -");

        lblBcpTamanio.setText("Tamaño: -");

        lblBcpAC.setText("AC: -");

        lblBcpPC.setText("PC: -");

        lblBcpAX.setText("AX: -");

        lblBcpBX.setText("BX: -");

        lblBcpCX.setText("CX: -");

        lblBcpDX.setText("DX: -");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBcpPid, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBcpAX, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBcpPC, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblBcpAC, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblBcpBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblBcpTamanio, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblBcpCX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblBcpBX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblBcpDX, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(205, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblBcpEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(159, 159, 159))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBcpPid)
                    .addComponent(lblBcpPC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBcpEstado)
                    .addComponent(lblBcpAX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBcpBase)
                    .addComponent(lblBcpBX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBcpTamanio, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBcpCX))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBcpDX)
                    .addComponent(lblBcpAC))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaDisco.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Posición ", "Zona         ", "Contenido"
            }
        ));
        jScrollPane5.setViewportView(tablaDisco);

        TablaProgramas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Programa", "Tamaño"
            }
        ));
        jScrollPane2.setViewportView(TablaProgramas);

        btnEjecutarPrograma.setText("Ejecutar");
        btnEjecutarPrograma.addActionListener(this::btnEjecutarProgramaActionPerformed);

        btnEliminarPrograma.setText("Eliminar");
        btnEliminarPrograma.addActionListener(this::btnEliminarProgramaActionPerformed);

        jTextPane1.setText("Simulador SO");
        jScrollPane6.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(495, 495, 495)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnEjecutarPrograma)
                                .addGap(27, 27, 27)
                                .addComponent(btnEliminarPrograma)))
                        .addGap(144, 144, 144)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(367, 367, 367)
                                .addComponent(apagaencender))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(panelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Ejecutartodo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ejecutarpaso))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(113, 113, 113)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(751, 751, 751)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apagaencender)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(214, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEjecutarPrograma)
                            .addComponent(btnEliminarPrograma))
                        .addGap(395, 395, 395)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Ejecutartodo)
                            .addComponent(ejecutarpaso, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(604, 604, 604))))
        );

        apagaencender.getAccessibleContext().setAccessibleName("Encendido/apagado");
        apagaencender.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void apagaencenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagaencenderActionPerformed

        if (apagaencender.isSelected()) {

            simulador.encender();

            apagaencender.setText("Apagar");

            mostrarInformacion(
                "Sistema operativo encendido",
                "El sistema operativo se inició correctamente."
            );

        } else {

            boolean hayProcesos =
            simulador.getGestorProceso().getEjecucion() != null
        || !simulador.getGestorProceso().getPreparados().isEmpty()
        || !simulador.getGestorProceso().getEnEspera().isEmpty()
        || !simulador.getGestorProceso().getSuspendidos().isEmpty();

            if (hayProcesos) {

                boolean continuar =
                confirmar(
                    "Confirmar apagado",
                    "Hay procesos cargados actualmente.\n\n"
                    + "¿Desea apagar el sistema operativo?"
                );

                if (!continuar) {

                    // Como el JToggleButton cambió de estado
                    // por el clic, lo volvemos a dejar seleccionado.
                    apagaencender.setSelected(true);

                    return;
                }
            }

            simulador.apagar();

            apagaencender.setText("Encender");

            mostrarInformacion(
                "Sistema operativo apagado",
                "El sistema operativo se apagó correctamente."
            );
        }
    }//GEN-LAST:event_apagaencenderActionPerformed

    private void TablarocesosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablarocesosMouseClicked
        int fila = Tablarocesos.getSelectedRow();

        if (fila == -1) {
            return;
        }

        pidSeleccionado = Integer.valueOf(
            Tablarocesos
            .getValueAt(fila, 1)
            .toString()
        );

        actualizarBCPSeleccionado();
    }//GEN-LAST:event_TablarocesosMouseClicked

    private void EjecutartodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EjecutartodoActionPerformed
        Proceso actual =
    simulador.getGestorProceso().getEjecucion();
        if (actual == null) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No hay ningún proceso en ejecución.\n"
                + "Cargue un programa antes de usar «Ejecutar todo».",
                "Sin proceso en ejecución",
                javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int pid = actual.getBcp().getPid();

        int respuesta =
        javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Se ejecutarán todas las instrucciones restantes "
            + "del proceso PID " + pid + ".\n"
            + "¿Desea continuar?",
            "Confirmar ejecución completa",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (respuesta !=
            javax.swing.JOptionPane.YES_OPTION) {

            return;
        }

        try {

            simulador.ejecutarProcesoCompleto();

            actual.getBcp().setEstadoProceso(BCP.EstadoProceso.FINALIZADO);
            simulador.getMemory()
            .actualizarBCP(actual);

            simulador.getGestorProceso()
            .terminarActual();

            actualizarCPU();
            actualizarMemoria();
            actualizarProcesos();
            actualizarBCPSeleccionado();

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "El proceso PID "
                + pid
                + " finalizó correctamente.",
                "Ejecución completada",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No se pudo completar la ejecución.\n\n"
                + "Detalle: "
                + e.getMessage(),
                "Error de ejecución",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );

        }
    }//GEN-LAST:event_EjecutartodoActionPerformed

    private void ejecutarpasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarpasoActionPerformed
        Proceso actual =
        simulador
        .getGestorProceso().getEjecucion();

        if (actual == null) {

            mostrarAdvertencia(
                "Sin proceso en ejecución",
                "No hay ningún proceso en ejecución.\n"
                + "Cargue un programa antes de "
                + "ejecutar instrucciones."
            );

            return;
        }

        try {

            boolean quedan =
            simulador
            .ejecutarSiguienteInstruccion();

            actualizarCPU();
            actualizarMemoria();
            actualizarProcesos();
            actualizarBCPSeleccionado();

            CPU cpu = simulador.getCpu();

            if (!quedan) {

                mostrarInformacion(
                    "Proceso finalizado",
                    "El proceso PID "
                    + actual.getBcp().getPid()
                    + " completó todas sus instrucciones."
                );
            }

        } catch (Exception e) {

            mostrarError(
                "Error de ejecución",
                "No se pudo ejecutar la siguiente instrucción.\n\n"
                + "Detalle: "
                + e.getMessage()
            );
        }
    }//GEN-LAST:event_ejecutarpasoActionPerformed

    private void comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxActionPerformed
        String opcion =
        combobox.getSelectedItem().toString();

        switch (opcion) {

            case "Cargar programa":
            seleccionarPrograma();
            break;

            case "Cambiar memoria":
            cambiarMemoria();
        
            break;

            case "Cambiar Almacenamiento":
            cambiarAlmacenamiento();
            reiniciarSO();
             case "Acerca del SO":
            mostrarAcercaDelDispositivo();
            break;

            case "Reiniciar SO":
            reiniciarSO();
            break;
            
          
          

            default:
            break;
        }

        combobox.setSelectedIndex(0);
    }//GEN-LAST:event_comboboxActionPerformed

    private void btnEjecutarProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarProgramaActionPerformed
        int fila = TablaProgramas.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Seleccione un programa del índice"
        );
        return;
    }

    try {

        // Obtener el índice correspondiente a la fila seleccionada
        IndicePrograma indice =
                simulador.getDisco()
                        .getIndicesProgramas()
                        .get(fila);

        // Reconstruir el Programa leyendo sus instrucciones del disco
        programaActual =
                simulador.getDisco()
                        .obtenerPrograma(indice);

        // Crear el proceso y cargarlo en RAM
        crearProceso();

    } catch (Exception e) {

        mostrarError(
                "No se pudo ejecutar el programa",
                e.getMessage()
        );
    }


    }//GEN-LAST:event_btnEjecutarProgramaActionPerformed

    private void btnEliminarProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProgramaActionPerformed
       int fila = TablaProgramas.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Seleccione un programa del índice"
        );
        return;
    }
    }//GEN-LAST:event_btnEliminarProgramaActionPerformed
  
    
    private void seleccionarPrograma() {

    if (!simulador.isEncendido()) {

        mostrarAdvertencia(
                "Sistema operativo apagado",
                "Primero debe encender el sistema operativo "
                + "para cargar un programa."
        );

        return;
    }

    javax.swing.JFileChooser selector =
            new javax.swing.JFileChooser();

    selector.setDialogTitle(
            "Seleccionar programa ASM"
    );

    selector.setFileFilter(
            new javax.swing.filechooser.FileNameExtensionFilter(
                    "Archivos ASM (*.asm)",
                    "asm"
            )
    );

    selector.setAcceptAllFileFilterUsed(false);

    int resultado =
            selector.showOpenDialog(this);

    if (resultado
            != javax.swing.JFileChooser.APPROVE_OPTION) {

        

        return;
    }

    try {

        java.io.File archivo =
                selector.getSelectedFile();

        // Lee el ASM, crea Programa y lo guarda en disco.
        simulador.cargarPrograma(archivo);
        // Actualiza la tabla del disco.
        actualizarDisco();
        actualizarTablaProgramas();
        
    } catch (Exception e) {
        mostrarError(
                "No se pudo cargar el programa",
                "El archivo ASM contiene errores "
                + "o no pudo ser almacenado.\n\n"
                + "Detalle: "
                + e.getMessage()
        );
    }
}
    private void crearProceso() {

    if (!simulador.isEncendido()) {

        mostrarAdvertencia(
                "Sistema operativo apagado",
                "Primero debe encender el sistema operativo."
        );

        return;
    }

    if (programaActual == null) {

        mostrarAdvertencia(
                "Programa no seleccionado",
                "Debe seleccionar un programa del disco "
                + "antes de crear el proceso."
        );

        return;
    }

    try {

        Proceso proceso =
                simulador.prepararPrograma(
                        programaActual
                );

        simulador.despacharSiguiente();

        mostrarInstrucciones();

        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();


        mostrarInformacion(
                "Proceso creado",
                "Se creó correctamente el proceso para «"
                + programaActual.getNombre()
                + "».\n\n"
                + "PID asignado: "
                + proceso.getBcp().getPid()
        );

    } catch (Exception e) {

     
        mostrarError(
                "No se pudo crear el proceso",
                e.getMessage()
        );
    }
}
    private void mostrarInstrucciones() {

    txtInstrucciones.setText("");

    if (programaActual == null) {
        return;
    }

    for (int i = 0;
            i < programaActual
                    .getInstrucciones()
                    .size();
            i++) {

        txtInstrucciones.append(
                i
                + "   "
                + programaActual
                        .getInstrucciones()
                        .get(i)
                + "\n"
        );
    }
}
    
    
    private void cambiarMemoria() {
         if (!simulador.isEncendido()) {

        mostrarAdvertencia(
                "Sistema operativo apagado",
                "Primero debe encender el sistema operativo "
                + "para cambiar el tamaño de la memoria."
        );

        return;
    }

    String entrada =
            javax.swing.JOptionPane.showInputDialog(
                    this,
                    "Ingrese el nuevo tamaño de memoria.\n"
                    + "El mínimo permitido es 128 posiciones:",
                    "Cambiar memoria",
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

    if (entrada == null) {

        return;
    }

    entrada = entrada.trim();

    if (entrada.isEmpty()) {

        mostrarAdvertencia(
                "Valor requerido",
                "Debe ingresar un tamaño de memoria."
        );

        return;
    }

    int nuevoTamanio;

    try {

        nuevoTamanio =
                Integer.parseInt(entrada);

    } catch (NumberFormatException e) {

        mostrarError(
                "Tamaño de memoria inválido",
                "El tamaño de memoria debe ser "
                + "un número entero."
        );

        return;
    }

    if (nuevoTamanio < 128) {

        mostrarAdvertencia(
                "Tamaño de memoria inválido",
                "La memoria debe tener como mínimo "
                + "128 posiciones."
        );

        return;
    }

    boolean continuar =
            confirmar(
                    "Confirmar cambio de memoria",
                    "Cambiar el tamaño de la memoria eliminará "
                    + "todos los procesos cargados actualmente.\n\n"
                    + "Nuevo tamaño: "
                    + nuevoTamanio
                    + " posiciones.\n\n"
                    + "¿Desea continuar?"
            );

    if (!continuar) {

        

        return;
    }

    try {

        simulador.cambiarMemoria(
                nuevoTamanio
        );

        programaActual = null;
        pidSeleccionado = null;

        txtInstrucciones.setText("");

        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();

        limpiarBCP();

       

        mostrarInformacion(
                "Memoria actualizada",
                "El tamaño de la memoria se cambió "
                + "correctamente a "
                + nuevoTamanio
                + " posiciones."
        );

    } catch (Exception e) {

        

        mostrarError(
                "No se pudo cambiar la memoria",
                "Ocurrió un error al cambiar "
                + "el tamaño de la memoria.\n\n"
                + "Detalle: "
                + e.getMessage()
        );
    }

    }
    
    
    private void cambiarAlmacenamiento() {
         if (!simulador.isEncendido()) {

        mostrarAdvertencia(
                "Sistema operativo apagado",
                "Primero debe encender el sistema operativo "
                + "para cambiar el tamaño de la memoria."
        );

        return;
    }

    String entrada =
            javax.swing.JOptionPane.showInputDialog(
                    this,
                    "Ingrese el nuevo tamaño de memoria.\n"
                    + "El mínimo permitido es 512 posiciones:",
                    "Cambiar memoria",
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

    if (entrada == null) {

        return;
    }

    entrada = entrada.trim();

    if (entrada.isEmpty()) {

        mostrarAdvertencia(
                "Valor requerido",
                "Debe ingresar un tamaño de almacenamiento."
        );

        return;
    }

    int nuevoTamanio;

    try {

        nuevoTamanio =
                Integer.parseInt(entrada);

    } catch (NumberFormatException e) {

        mostrarError(
                "Tamaño de almacenamiento inválido",
                "El tamaño de almacenamiento debe ser "
                + "un número entero."
        );

        return;
    }

    if (nuevoTamanio < 128) {

        mostrarAdvertencia(
                "Tamaño de almacenamiento inválido",
                "La memoria debe tener como mínimo "
                + "512 posiciones."
        );

        return;
    }

    boolean continuar =
            confirmar(
                    "Confirmar cambio de memoria",
                    "Cambiar el tamaño de la memoria eliminará "
                    + "todos los procesos cargados actualmente.\n\n"
                    + "Nuevo tamaño: "
                    + nuevoTamanio
                    + " posiciones.\n\n"
                    + "¿Desea continuar?"
            );

    if (!continuar) {

        

        return;
    }

    try {

        simulador.cambiarAlmacenamiento(
                nuevoTamanio
        );

        programaActual = null;
        pidSeleccionado = null;

        txtInstrucciones.setText("");

        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();
        limpiarBCP();

       

        mostrarInformacion(
                "Memoria actualizada",
                "El tamaño de almacenamiento se cambió "
                + "correctamente a "
                + nuevoTamanio
                + " posiciones."
        );

    } catch (Exception e) {

        

        mostrarError(
                "No se pudo cambiar la almacenamiento",
                "Ocurrió un error al cambiar "
                + "el tamaño de la memoria.\n\n"
                + "Detalle: "
                + e.getMessage()
        );
    }

    }

  private void reiniciarSO() {

     if (!simulador.isEncendido()) {

        mostrarAdvertencia(
                "Sistema operativo apagado",
                "El sistema operativo ya se encuentra apagado."
        );

        return;
    }

    boolean continuar =
            confirmar(
                    "Confirmar reinicio",
                    "Reiniciar el sistema eliminará todos "
                    + "los procesos cargados y restablecerá "
                    + "la CPU y la memoria.\n\n"
                    + "¿Desea continuar?"
            );

    if (!continuar) {

       

        return;
    }

    try {

        simulador.reiniciarSistema();

        programaActual = null;
        pidSeleccionado = null;

        txtInstrucciones.setText("");

        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();

        limpiarBCP();

      

        mostrarInformacion(
                "Sistema reiniciado",
                "El sistema operativo se reinició correctamente."
        );

    } catch (Exception e) {

       

        mostrarError(
                "No se pudo reiniciar el sistema",
                "Ocurrió un error durante el reinicio.\n\n"
                + "Detalle: "
                + e.getMessage()
        );
    }
}
  private void limpiarBCP() {

    lblBcpPid.setText("PID: -");
    lblBcpEstado.setText("Estado: -");
    lblBcpPC.setText("PC: -");
    lblBcpAC.setText("AC: -");

    lblBcpAX.setText("AX: -");
    lblBcpBX.setText("BX: -");
    lblBcpCX.setText("CX: -");
    lblBcpDX.setText("DX: -");

    lblBcpBase.setText("Base: -");
    lblBcpTamanio.setText("Tamaño: -");
}
    
  
    /**
     * Estilo visual aplicado después de initComponents().
     * Los componentes continúan siendo administrados por Ventana.form,
     * por lo que la ventana sigue siendo editable desde Design de NetBeans.
     */
    private void configurarInterfaz() {
        final java.awt.Color fondo = new java.awt.Color(15, 23, 42);
        final java.awt.Color tarjeta = new java.awt.Color(30, 41, 59);
        final java.awt.Color superficie = new java.awt.Color(51, 65, 85);
        final java.awt.Color borde = new java.awt.Color(71, 85, 105);
        final java.awt.Color texto = new java.awt.Color(226, 232, 240);
        final java.awt.Color textoSec = new java.awt.Color(148, 163, 184);
        final java.awt.Color azul = new java.awt.Color(37, 99, 235);
        final java.awt.Color azulHover = new java.awt.Color(29, 78, 216);
        final java.awt.Color rojo = new java.awt.Color(220, 38, 38);
        final java.awt.Color verde = new java.awt.Color(22, 163, 74);

        setTitle("");
        setMinimumSize(new java.awt.Dimension(1050, 680));
        setSize(new java.awt.Dimension(1360, 820));
        setLocationRelativeTo(null);
        getContentPane().setBackground(fondo);

        jPanel2.setBackground(fondo);
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 14, 14));

  
        estilizarPanel(panelCPU, "CPU", tarjeta, borde, texto);
        estilizarPanel(jPanel1, "BCP DEL PROCESO SELECCIONADO", tarjeta, borde, texto);

        estilizarScroll(jScrollPane4, "MEMORIA RAM", tarjeta, borde, texto);
        estilizarScroll(jScrollPane1, "PROCESOS", tarjeta, borde, texto);
        estilizarScroll(jScrollPane5, "DISCO", tarjeta, borde, texto);
        estilizarScroll(jScrollPane2, "PROGRAMAS EN DISCO", tarjeta, borde, texto);
        estilizarScroll(jScrollPane3, "INSTRUCCIONES", tarjeta, borde, texto);

        javax.swing.JTable[] tablas = {
            tablamemoria, Tablarocesos, tablaDisco, TablaProgramas
        };
        for (javax.swing.JTable tabla : tablas) {
            estilizarTabla(tabla, superficie, tarjeta, borde, texto, textoSec);
        }

        txtInstrucciones.setBackground(new java.awt.Color(17, 24, 39));
        txtInstrucciones.setForeground(new java.awt.Color(203, 213, 225));
        txtInstrucciones.setCaretColor(texto);
        txtInstrucciones.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));
        txtInstrucciones.setMargin(new java.awt.Insets(10, 10, 10, 10));

        java.awt.Component[] etiquetasCPU = {
            txtCpuPC, txtCpuAC, txtCpuAX, txtCpuBX, txtCpuCX, txtCpuDX
        };
        for (java.awt.Component componente : etiquetasCPU) {
            componente.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
            componente.setForeground(texto);
        }

        javax.swing.JLabel[] etiquetasBCP = {
            lblBcpPid, lblBcpEstado, lblBcpBase, lblBcpTamanio,
            lblBcpAC, lblBcpPC, lblBcpAX, lblBcpBX, lblBcpCX, lblBcpDX
        };
        for (javax.swing.JLabel etiqueta : etiquetasBCP) {
            etiqueta.setForeground(texto);
            etiqueta.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13));
        }

        estilizarBoton(apagaencender, verde, texto);
        estilizarBoton(Ejecutartodo, azul, texto);
        estilizarBoton(ejecutarpaso, azulHover, texto);
        estilizarBoton(btnEjecutarPrograma, azul, texto);
        estilizarBoton(btnEliminarPrograma, rojo, texto);

        combobox.setBackground(superficie);
        combobox.setForeground(texto);
        combobox.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13));
        combobox.setFocusable(false);

        // Las tablas aprovechan el ancho disponible cuando la ventana cambia de tamaño.
        tablamemoria.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        Tablarocesos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        tablaDisco.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        TablaProgramas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);

        // Proporciones iniciales de columnas; siguen siendo redimensionables.
        if (tablamemoria.getColumnModel().getColumnCount() >= 3) {
            tablamemoria.getColumnModel().getColumn(0).setPreferredWidth(65);
            tablamemoria.getColumnModel().getColumn(1).setPreferredWidth(85);
            tablamemoria.getColumnModel().getColumn(2).setPreferredWidth(230);
        }
        if (Tablarocesos.getColumnModel().getColumnCount() >= 3) {
            Tablarocesos.getColumnModel().getColumn(0).setPreferredWidth(105);
            Tablarocesos.getColumnModel().getColumn(1).setPreferredWidth(55);
            Tablarocesos.getColumnModel().getColumn(2).setPreferredWidth(190);
        }

        // Maximizada se ve mejor, pero continúa siendo completamente redimensionable.
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    private void estilizarPanel(javax.swing.JPanel panel, String titulo,
            java.awt.Color fondo, java.awt.Color borde, java.awt.Color texto) {
        panel.setBackground(fondo);
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(borde),
                "  " + titulo + "  ",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12),
                texto));
    }

    private void estilizarScroll(javax.swing.JScrollPane scroll, String titulo,
            java.awt.Color fondo, java.awt.Color borde, java.awt.Color texto) {
        scroll.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(borde),
                "  " + titulo + "  ",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12),
                texto));
        scroll.getViewport().setBackground(fondo);
        scroll.setBackground(fondo);
    }

    private void estilizarTabla(javax.swing.JTable tabla,
            java.awt.Color fondo, java.awt.Color seleccion,
            java.awt.Color grid, java.awt.Color texto, java.awt.Color textoSec) {
        tabla.setBackground(fondo);
        tabla.setForeground(texto);
        tabla.setSelectionBackground(new java.awt.Color(30, 64, 175));
        tabla.setSelectionForeground(java.awt.Color.WHITE);
        tabla.setGridColor(grid);
        tabla.setRowHeight(26);
        tabla.setShowVerticalLines(false);
        tabla.setFillsViewportHeight(true);
        tabla.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
        tabla.getTableHeader().setBackground(new java.awt.Color(15, 23, 42));
        tabla.getTableHeader().setForeground(textoSec);
        tabla.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
        tabla.getTableHeader().setReorderingAllowed(false);
    }

    private void estilizarBoton(javax.swing.AbstractButton boton,
            java.awt.Color fondo, java.awt.Color texto) {
        boton.setBackground(fondo);
        boton.setForeground(texto);
        boton.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(fondo.darker()),
                javax.swing.BorderFactory.createEmptyBorder(7, 12, 7, 12)));
    }

    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(() -> new Ventana().setVisible(true));
    }
    
   private void actualizarCPU() {

    CPU cpu = simulador.getCpu();

    txtCpuPC.setText("PC: " + cpu.getPC());
    /*
    txtCpuIR.setText(
            "IR: " + (cpu.getIR() == null ? "-" : cpu.getIR())
    );*/

    txtCpuAC.setText("AC: " + cpu.getAC());
    txtCpuAX.setText("AX: " + cpu.getAX());
    txtCpuBX.setText("BX: " + cpu.getBX());
    txtCpuCX.setText("CX: " + cpu.getCX());
    txtCpuDX.setText("DX: " + cpu.getDX());
}
 private void actualizarMemoria() {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
                    tablamemoria.getModel();

    modelo.setRowCount(0);

    Memory memory = simulador.getMemory();
    Object[] memoria = memory.getMemoriaSnapshot();

    for (int i = 0; i < memoria.length; i++) {


        String zona =
                i < memory.getEspacioSO()
                ? "SO"
                : "Usuario";

        modelo.addRow(new Object[]{
            i,
            zona,
            memoria[i]
        });
    }
}  
 
 
 
 private void actualizarProcesos() {
     javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
                    Tablarocesos.getModel();

    modelo.setRowCount(0);

    GestorProceso gestor =
            simulador.getGestorProceso();

    // PREPARADOS
    for (Proceso proceso : gestor.getPreparados()) {
        agregarProcesoTabla(
                modelo,
                "PREPARADO",
                proceso
        );
    }

    // EJECUCIÓN
    Proceso ejecucion =
            gestor.getEjecucion();

    if (ejecucion != null) {
        agregarProcesoTabla(
                modelo,
                "EJECUCION",
                ejecucion
        );
    }

    // EN ESPERA
    for (Proceso proceso : gestor.getEnEspera()) {
        agregarProcesoTabla(
                modelo,
                "EN_ESPERA",
                proceso
        );
    }

    // SUSPENDIDOS
    for (Proceso proceso : gestor.getSuspendidos()) {
        agregarProcesoTabla(
                modelo,
                "SUSPENDIDO",
                proceso
        );
    }

    // FINALIZADOS
    for (Proceso proceso : gestor.getFinalizados()) {
        agregarProcesoTabla(
                modelo,
                "FINALIZADO",
                proceso
        );
    }
  
}
 
 private void agregarProcesoTabla(
        javax.swing.table.DefaultTableModel modelo,
        String estado,
        Proceso proceso) {

    modelo.addRow(new Object[]{
        estado,
        proceso.getBcp().getPid(),
        proceso.getPrograma().getNombre()
    });
}
 
 private void actualizarBCPSeleccionado() {

    if (pidSeleccionado == null) {
        return;
    }

    Proceso proceso =
            buscarProceso(pidSeleccionado);

    if (proceso == null) {
        return;
    }

    BCP bcp = proceso.getBcp();

    lblBcpPid.setText(
            "PID: " + bcp.getPid()
    );

    lblBcpEstado.setText(
            "Estado: "
            + bcp.getEstadoProceso()
    );

    lblBcpPC.setText(
            "PC: " + bcp.getPC()
    );

    lblBcpAC.setText(
            "AC: " + bcp.getAC()
    );

    lblBcpAX.setText(
            "AX: " + bcp.getAX()
    );

    lblBcpBX.setText(
            "BX: " + bcp.getBX()
    );

    lblBcpCX.setText(
            "CX: " + bcp.getCX()
    );

    lblBcpDX.setText(
            "DX: " + bcp.getDX()
    );

    lblBcpBase.setText(
            "Base: " + bcp.getBase()
    );

    lblBcpTamanio.setText(
            "Tamaño: " + bcp.getTamanio()
    );
}
 private Proceso buscarProceso(int pid) {


    GestorProceso gestor =
            simulador.getGestorProceso();

    Proceso ejecucion =
            gestor.getEjecucion();

    if (ejecucion != null
            && ejecucion.getBcp().getPid() == pid) {
        return ejecucion;
    }

    for (Proceso proceso : gestor.getPreparados()) {
        if (proceso.getBcp().getPid() == pid) {
            return proceso;
        }
    }

    for (Proceso proceso : gestor.getEnEspera()) {
        if (proceso.getBcp().getPid() == pid) {
            return proceso;
        }
    }

    for (Proceso proceso : gestor.getSuspendidos()) {
        if (proceso.getBcp().getPid() == pid) {
            return proceso;
        }
    }

    for (Proceso proceso : gestor.getFinalizados()) {
        if (proceso.getBcp().getPid() == pid) {
            return proceso;
        }
    }

    return null;
}
   
    

 
 private void mostrarInformacion(String titulo, String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            javax.swing.JOptionPane.INFORMATION_MESSAGE
    );
}

private void mostrarAdvertencia(String titulo, String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            javax.swing.JOptionPane.WARNING_MESSAGE
    );
}

private void mostrarError(String titulo, String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje,
            titulo,
            javax.swing.JOptionPane.ERROR_MESSAGE
    );
}

private boolean confirmar(String titulo, String mensaje) {

    int respuesta =
            javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    mensaje,
                    titulo,
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

    return respuesta
            == javax.swing.JOptionPane.YES_OPTION;
}
private void actualizarDisco() {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
                    tablaDisco.getModel();

    modelo.setRowCount(0);

    Disco discoSO = simulador.getDisco();

    Object[] disco =
            discoSO.getDiscoSnapshot();

    for (int i = 0; i < disco.length; i++) {

        String zona;

        if (i < discoSO.getTotalIndices()) {
            zona = "Índice";

        } else if (i < discoSO.getInicioVirtual()) {
            zona = "Archivos";

        } else {
            zona = "Virtual";
        }

        String contenido;

        if (disco[i] == null) {

            contenido = "Libre";

        } else if (disco[i] instanceof IndicePrograma) {

            IndicePrograma indice =
                    (IndicePrograma) disco[i];

            contenido =
                    indice.getNombre()
                    + " | Dir: "
                    + indice.getDireccion()
                    + " | Tamaño: "
                    + indice.getTamanio();

        } else {

            contenido = disco[i].toString();
        }

        modelo.addRow(new Object[]{
            i,
            zona,
            contenido
        });
    }
}
private void actualizarTablaProgramas() {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
                    TablaProgramas.getModel();

    modelo.setRowCount(0);

    for (IndicePrograma indice :
            simulador.getDisco().getIndicesProgramas()) {

        modelo.addRow(new Object[]{
            indice.getNombre(),
            indice.getTamanio()
        });
    }
}private void mostrarAcercaDelDispositivo() {

    try {

        Memory memoria = simulador.getMemory();
        Disco disco = simulador.getDisco();

        int memoriaTotal =
                memoria.getMemoriaSnapshot().length;

        int memoriaSO =
                memoria.getEspacioSO();

        int memoriaUsuario =
                memoriaTotal - memoriaSO;

        String mensaje =
            "CONFIGURACIÓN DEL DISPOSITIVO\n\n"

            + "MEMORIA RAM\n"
            + "Memoria total: " + memoriaTotal + "\n"
            + "Reservada para SO: " + memoriaSO + "\n"
            + "Disponible para usuario: " + memoriaUsuario + "\n\n"

            + "ALMACENAMIENTO\n"
            + "Almacenamiento total: "
            + disco.getMemoriaTotal() + "\n"

            + "Memoria virtual: "
            + disco.getMemoriaVirtual() + "\n"

            + "Índices reservados: "
            + disco.getTotalIndices();

        javax.swing.JTextArea area =
                new javax.swing.JTextArea(mensaje);

        area.setEditable(false);
        area.setOpaque(false);

        area.setFont(
            new java.awt.Font(
                "Monospaced",
                java.awt.Font.PLAIN,
                13
            )
        );

        javax.swing.JOptionPane.showMessageDialog(
            this,
            area,
            "Acerca del dispositivo",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {

        mostrarError(
            "No se pudo mostrar la configuración",
            e.getMessage()
        );
    }
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Ejecutartodo;
    private javax.swing.JTable TablaProgramas;
    private javax.swing.JTable Tablarocesos;
    private javax.swing.JToggleButton apagaencender;
    private javax.swing.JButton btnEjecutarPrograma;
    private javax.swing.JButton btnEliminarPrograma;
    private javax.swing.JComboBox<String> combobox;
    private javax.swing.JToggleButton ejecutarpaso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblBcpAC;
    private javax.swing.JLabel lblBcpAX;
    private javax.swing.JLabel lblBcpBX;
    private javax.swing.JLabel lblBcpBase;
    private javax.swing.JLabel lblBcpCX;
    private javax.swing.JLabel lblBcpDX;
    private javax.swing.JLabel lblBcpEstado;
    private javax.swing.JLabel lblBcpPC;
    private javax.swing.JLabel lblBcpPid;
    private javax.swing.JLabel lblBcpTamanio;
    private javax.swing.JPanel panelCPU;
    private javax.swing.JTable tablaDisco;
    private javax.swing.JTable tablamemoria;
    private javax.swing.JLabel txtCpuAC;
    private javax.swing.JLabel txtCpuAX;
    private javax.swing.JLabel txtCpuBX;
    private javax.swing.JLabel txtCpuCX;
    private javax.swing.JLabel txtCpuDX;
    private javax.swing.JLabel txtCpuPC;
    private javax.swing.JTextArea txtInstrucciones;
    // End of variables declaration//GEN-END:variables



}


