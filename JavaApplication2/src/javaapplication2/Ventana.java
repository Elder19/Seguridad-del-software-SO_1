package javaapplication2;

public class Ventana extends javax.swing.JFrame {

    private final SimuladorSO simulador = new SimuladorSO();
    private Programa programaActual;
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Ventana.class.getName());
    private Integer pidSeleccionado = null;
    public Ventana() {
        initComponents();
        actualizarCPU();
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
        txtCpuIR = new javax.swing.JLabel();
        txtCpuAC = new javax.swing.JLabel();
        txtCpuAX = new javax.swing.JLabel();
        txtCpuBX = new javax.swing.JLabel();
        txtCpuCX = new javax.swing.JLabel();
        txtCpuDX = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tablarocesos = new javax.swing.JTable();
        apagaencender = new javax.swing.JToggleButton();
        label1 = new java.awt.Label();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextArea();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "  Sistemas Operarativos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jPanel2.setAutoscrolls(true);

        combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opciones", "Cargar programa", "Cambiar memoria", "Reiniciar SO" }));
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

        panelCPU.setBorder(javax.swing.BorderFactory.createTitledBorder(" 💻 CPU  "));

        txtCpuPC.setText("PC:");

        txtCpuIR.setText("IR: ");

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
                            .addComponent(txtCpuIR, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCpuDX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                                .addComponent(txtCpuCX, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addComponent(txtCpuBX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelCPULayout.setVerticalGroup(
            panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCPULayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCpuPC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpuIR)
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
                .addContainerGap(22, Short.MAX_VALUE))
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
        Tablarocesos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablarocesosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tablarocesos);

        apagaencender.setText(" ⏻ Encender");
        apagaencender.addActionListener(this::apagaencenderActionPerformed);

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label1.setText("SIMULADOR SO");

        txtConsola.setEditable(false);
        txtConsola.setColumns(20);
        txtConsola.setRows(5);
        txtConsola.setBorder(javax.swing.BorderFactory.createTitledBorder(" 📟 CONSOLA"));
        jScrollPane2.setViewportView(txtConsola);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("📋 INSTRUCCIONES"));

        txtInstrucciones.setEditable(false);
        txtInstrucciones.setBackground(new java.awt.Color(204, 204, 204));
        txtInstrucciones.setColumns(20);
        txtInstrucciones.setRows(5);
        txtInstrucciones.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jScrollPane3.setViewportView(txtInstrucciones);
        txtInstrucciones.getAccessibleContext().setAccessibleName("Instrucciones");
        txtInstrucciones.getAccessibleContext().setAccessibleDescription("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("📄 BCP del proceso seleccionado"));
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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblBcpBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBcpTamanio, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBcpCX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBcpBX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBcpAC, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(lblBcpDX, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBcpPid, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(lblBcpPC, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblBcpEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBcpAX, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBcpDX)
                    .addComponent(lblBcpAC))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(panelCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Ejecutartodo)
                                            .addComponent(ejecutarpaso)))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(116, 116, 116)
                        .addComponent(apagaencender)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(apagaencender, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ejecutarpaso, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Ejecutartodo))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        apagaencender.getAccessibleContext().setAccessibleName("Encendido/apagado");
        apagaencender.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.getAccessibleContext().setAccessibleParent(jScrollPane4);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void apagaencenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagaencenderActionPerformed

        if (apagaencender.isSelected()) {

            simulador.encender();

            apagaencender.setText("Apagar");

            consola("Sistema operativo encendido.");

            mostrarInformacion(
                "Sistema operativo encendido",
                "El sistema operativo se inició correctamente."
            );

        } else {

            boolean hayProcesos =
            simulador.getGestorProceso().getRunning() != null
            || !simulador.getGestorProceso().getReady().isEmpty()
            || !simulador.getGestorProceso().getBlocked().isEmpty();

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

            consola("Sistema operativo apagado.");

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
        simulador.getGestorProceso().getRunning();

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
            + "del proceso PID " + pid + ".\n\n"
            + "¿Desea continuar?",
            "Confirmar ejecución completa",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (respuesta !=
            javax.swing.JOptionPane.YES_OPTION) {

            consola(
                "Ejecución completa del PID "
                + pid
                + " cancelada."
            );

            return;
        }

        try {

            simulador.ejecutarProcesoCompleto();

            actual.getBcp()
            .setEstadoProceso("Terminated");

            simulador.getMemory()
            .actualizarBCP(actual);

            simulador.getGestorProceso()
            .terminarActual();

            actualizarCPU();
            actualizarMemoria();
            actualizarProcesos();
            actualizarBCPSeleccionado();

            consola(
                "PID "
                + pid
                + " finalizó correctamente."
            );

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

            consola(
                "ERROR al ejecutar PID "
                + pid
                + ": "
                + e.getMessage()
            );
        }
    }//GEN-LAST:event_EjecutartodoActionPerformed

    private void ejecutarpasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarpasoActionPerformed
        Proceso actual =
        simulador
        .getGestorProceso()
        .getRunning();

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

            consola(
                "PID "
                + actual.getBcp().getPid()
                + " | Instrucción ejecutada"
                + " | PC = " + cpu.getPC()
                + " | AC = " + cpu.getAC()
            );

            if (!quedan) {

                consola(
                    "PID "
                    + actual.getBcp().getPid()
                    + " completó todas sus instrucciones."
                );

                mostrarInformacion(
                    "Proceso finalizado",
                    "El proceso PID "
                    + actual.getBcp().getPid()
                    + " completó todas sus instrucciones."
                );
            }

        } catch (Exception e) {

            consola(
                "ERROR al ejecutar instrucción: "
                + e.getMessage()
            );

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

            case "Reiniciar SO":
            reiniciarSO();
            break;

            default:
            break;
        }

        combobox.setSelectedIndex(0);
    }//GEN-LAST:event_comboboxActionPerformed
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

    if (resultado !=
            javax.swing.JFileChooser.APPROVE_OPTION) {

        consola(
                "Selección de programa cancelada."
        );

        return;
    }

    try {

        java.io.File archivo =
                selector.getSelectedFile();

        programaActual =
                simulador.cargarPrograma(archivo);

        Proceso proceso =
                simulador.prepararPrograma(
                        programaActual
                );

        simulador.despacharSiguiente();

        mostrarInstrucciones();

        actualizarCPU();
        actualizarMemoria();
        actualizarProcesos();

        consola(
                "Programa "
                + archivo.getName()
                + " cargado. PID "
                + proceso.getBcp().getPid()
                + " enviado a CPU."
        );

        mostrarInformacion(
                "Programa cargado",
                "El programa «"
                + archivo.getName()
                + "» se cargó correctamente.\n\n"
                + "PID asignado: "
                + proceso.getBcp().getPid()
        );

    } catch (Exception e) {

        consola(
                "ERROR al cargar programa: "
                + e.getMessage()
        );

        mostrarError(
                "No se pudo cargar el programa",
                "El archivo ASM contiene errores "
                + "o no pudo ser procesado.\n\n"
                + "Detalle: "
                + e.getMessage()
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
    private void consola(String mensaje) {

    txtConsola.append(
            "> " + mensaje + "\n"
    );

    txtConsola.setCaretPosition(
            txtConsola
                    .getDocument()
                    .getLength()
    );
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

        consola(
                "Cambio de memoria cancelado."
        );

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

        consola(
                "Cambio de memoria cancelado."
        );

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

        consola(
                "Memoria cambiada a "
                + nuevoTamanio
                + " posiciones."
        );

        mostrarInformacion(
                "Memoria actualizada",
                "El tamaño de la memoria se cambió "
                + "correctamente a "
                + nuevoTamanio
                + " posiciones."
        );

    } catch (Exception e) {

        consola(
                "ERROR al cambiar memoria: "
                + e.getMessage()
        );

        mostrarError(
                "No se pudo cambiar la memoria",
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

        consola(
                "Reinicio del sistema cancelado."
        );

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

        consola(
                "Sistema operativo reiniciado."
        );

        mostrarInformacion(
                "Sistema reiniciado",
                "El sistema operativo se reinició correctamente."
        );

    } catch (Exception e) {

        consola(
                "ERROR al reiniciar: "
                + e.getMessage()
        );

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
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Ventana().setVisible(true));
    }
    
   private void actualizarCPU() {

    CPU cpu = simulador.getCpu();

    txtCpuPC.setText("PC: " + cpu.getPC());

    txtCpuIR.setText(
            "IR: " + (cpu.getIR() == null ? "-" : cpu.getIR())
    );

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

        if (memoria[i] == null) {
            continue;
        }

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

    for (Proceso proceso : gestor.getReady()) {
        agregarProcesoTabla(
                modelo,
                "READY",
                proceso
        );
    }

    Proceso running =
            gestor.getRunning();

    if (running != null) {

        agregarProcesoTabla(
                modelo,
                "RUNNING",
                running
        );
    }

    for (Proceso proceso : gestor.getBlocked()) {

        agregarProcesoTabla(
                modelo,
                "BLOCKED",
                proceso
        );
    }

    for (Proceso proceso : gestor.getTerminated()) {

        agregarProcesoTabla(
                modelo,
                "TERMINATED",
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

    Proceso running =
            gestor.getRunning();

    if (running != null
            && running.getBcp().getPid()
            == pid) {

        return running;
    }

    for (Proceso proceso : gestor.getReady()) {

        if (proceso.getBcp().getPid() == pid) {
            return proceso;
        }
    }

    for (Proceso proceso : gestor.getBlocked()) {

        if (proceso.getBcp().getPid() == pid) {
            return proceso;
        }
    }

    for (Proceso proceso : gestor.getTerminated()) {

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



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Ejecutartodo;
    private javax.swing.JTable Tablarocesos;
    private javax.swing.JToggleButton apagaencender;
    private javax.swing.JComboBox<String> combobox;
    private javax.swing.JToggleButton ejecutarpaso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private java.awt.Label label1;
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
    private javax.swing.JTable tablamemoria;
    private javax.swing.JTextArea txtConsola;
    private javax.swing.JLabel txtCpuAC;
    private javax.swing.JLabel txtCpuAX;
    private javax.swing.JLabel txtCpuBX;
    private javax.swing.JLabel txtCpuCX;
    private javax.swing.JLabel txtCpuDX;
    private javax.swing.JLabel txtCpuIR;
    private javax.swing.JLabel txtCpuPC;
    private javax.swing.JTextArea txtInstrucciones;
    // End of variables declaration//GEN-END:variables



}


