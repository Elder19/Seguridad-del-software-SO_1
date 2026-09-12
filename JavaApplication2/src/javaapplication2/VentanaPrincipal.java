package javaapplication2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame {

    private final SimuladorSO simulador;

    private Programa programaSeleccionado;
    private File archivoSeleccionado;

    private final JLabel lblEstadoSO = new JLabel("APAGADO");
    private final JLabel lblArchivo = new JLabel("Ningún archivo seleccionado");
    private final JLabel lblMemoria = new JLabel();

    private final JTextField txtPC = crearCampoRegistro();
    private final JTextField txtIR = crearCampoRegistro();
    private final JTextField txtAC = crearCampoRegistro();
    private final JTextField txtAX = crearCampoRegistro();
    private final JTextField txtBX = crearCampoRegistro();
    private final JTextField txtCX = crearCampoRegistro();
    private final JTextField txtDX = crearCampoRegistro();

    private final JTextArea areaPrograma = new JTextArea();
    private final JTextArea areaConsola = new JTextArea();

    private final DefaultTableModel modeloProcesos = new DefaultTableModel(
            new Object[]{"Estado", "PID", "Programa", "PC", "AC", "Base", "Tamaño"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final DefaultTableModel modeloMemoria = new DefaultTableModel(
            new Object[]{"Posición", "Zona", "Contenido"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private final JTable tablaProcesos = new JTable(modeloProcesos);
    private final JTable tablaMemoria = new JTable(modeloMemoria);

    private final JButton btnEncender = new JButton("Encender");
    private final JButton btnApagar = new JButton("Apagar");
    private final JButton btnSeleccionar = new JButton("Seleccionar .asm");
    private final JButton btnCargar = new JButton("Cargar programa");
    private final JButton btnDespachar = new JButton("Despachar");
    private final JButton btnPaso = new JButton("Ejecutar paso");
    private final JButton btnCompleto = new JButton("Ejecutar completo");
    private final JButton btnReiniciar = new JButton("Reiniciar");

    private final JSpinner spinnerMemoria = new JSpinner(
            new SpinnerNumberModel(128, 128, 65536, 1)
    );
    private final JButton btnCambiarMemoria = new JButton("Cambiar memoria");

    public VentanaPrincipal() {
        simulador = new SimuladorSO();
        configurarVentana();
        construirInterfaz();
        registrarEventos();
        actualizarTodo();
    }

    private void configurarVentana() {
        setTitle("Simulador de Sistema Operativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 760));
        setSize(1350, 850);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(8, 8));

        JPanel superior = new JPanel(new BorderLayout());
        superior.setBorder(BorderFactory.createEmptyBorder(10, 12, 6, 12));

        JLabel titulo = new JLabel("SIMULADOR DE SISTEMA OPERATIVO");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel estado = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        estado.add(new JLabel("Estado:"));
        lblEstadoSO.setFont(new Font("SansSerif", Font.BOLD, 14));
        estado.add(lblEstadoSO);
        estado.add(new JLabel("   Memoria:"));
        estado.add(lblMemoria);

        superior.add(titulo, BorderLayout.WEST);
        superior.add(estado, BorderLayout.EAST);
        add(superior, BorderLayout.NORTH);

        JPanel panelControl = crearPanelControl();
        JPanel panelCPU = crearPanelCPU();
        JPanel panelProcesos = crearPanelProcesos();
        JPanel panelMemoria = crearPanelMemoria();
        JPanel panelConsola = crearPanelConsola();

        JPanel izquierda = new JPanel(new BorderLayout(8, 8));
        izquierda.add(panelControl, BorderLayout.NORTH);
        izquierda.add(panelCPU, BorderLayout.CENTER);

        JSplitPane splitSuperior = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                izquierda,
                panelProcesos
        );
        splitSuperior.setResizeWeight(0.42);

        JSplitPane splitCentral = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                splitSuperior,
                panelMemoria
        );
        splitCentral.setResizeWeight(0.48);

        JSplitPane splitPrincipal = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                splitCentral,
                panelConsola
        );
        splitPrincipal.setResizeWeight(0.82);

        splitSuperior.setBorder(BorderFactory.createEmptyBorder());
        splitCentral.setBorder(BorderFactory.createEmptyBorder());
        splitPrincipal.setBorder(BorderFactory.createEmptyBorder());

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        centro.add(splitPrincipal, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearPanelControl() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Control y programa"));

        JPanel botonesSO = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonesSO.add(btnEncender);
        botonesSO.add(btnApagar);
        botonesSO.add(btnReiniciar);

        JPanel archivo = new JPanel(new BorderLayout(6, 6));
        archivo.add(lblArchivo, BorderLayout.CENTER);

        JPanel botonesArchivo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonesArchivo.add(btnSeleccionar);
        botonesArchivo.add(btnCargar);
        botonesArchivo.add(btnDespachar);
        botonesArchivo.add(btnPaso);
        botonesArchivo.add(btnCompleto);

        JPanel memoria = new JPanel(new FlowLayout(FlowLayout.LEFT));
        memoria.add(new JLabel("Tamaño de memoria:"));
        memoria.add(spinnerMemoria);
        memoria.add(btnCambiarMemoria);

        areaPrograma.setEditable(false);
        areaPrograma.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaPrograma.setRows(8);

        JPanel arriba = new JPanel(new GridLayout(4, 1));
        arriba.add(botonesSO);
        arriba.add(archivo);
        arriba.add(botonesArchivo);
        arriba.add(memoria);

        panel.add(arriba, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaPrograma), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelCPU() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("CPU"));

        JPanel registros = new JPanel(new GridLayout(7, 2, 8, 8));
        registros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        agregarRegistro(registros, "PC", txtPC);
        agregarRegistro(registros, "IR", txtIR);
        agregarRegistro(registros, "AC", txtAC);
        agregarRegistro(registros, "AX", txtAX);
        agregarRegistro(registros, "BX", txtBX);
        agregarRegistro(registros, "CX", txtCX);
        agregarRegistro(registros, "DX", txtDX);

        panel.add(registros, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearPanelProcesos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Procesos"));
        tablaProcesos.setFillsViewportHeight(true);
        panel.add(new JScrollPane(tablaProcesos), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMemoria() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Memoria principal"));
        tablaMemoria.setFillsViewportHeight(true);
        panel.add(new JScrollPane(tablaMemoria), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelConsola() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Consola"));
        areaConsola.setEditable(false);
        areaConsola.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        panel.add(new JScrollPane(areaConsola), BorderLayout.CENTER);
        return panel;
    }

    private void registrarEventos() {
        btnEncender.addActionListener(e -> ejecutarAccion(() -> {
            simulador.encender();
            escribirConsola("Sistema operativo encendido.");
        }));

        btnApagar.addActionListener(e -> ejecutarAccion(() -> {
            simulador.apagar();
            escribirConsola("Sistema operativo apagado.");
        }));

        btnSeleccionar.addActionListener(e -> seleccionarArchivo());
        btnCargar.addActionListener(e -> cargarPrograma());
        btnDespachar.addActionListener(e -> despacharProceso());
        btnPaso.addActionListener(e -> ejecutarPaso());
        btnCompleto.addActionListener(e -> ejecutarCompleto());

        btnReiniciar.addActionListener(e -> ejecutarAccion(() -> {
            simulador.reiniciarSistema();
            programaSeleccionado = null;
            archivoSeleccionado = null;
            lblArchivo.setText("Ningún archivo seleccionado");
            areaPrograma.setText("");
            escribirConsola("Sistema reiniciado.");
        }));

        btnCambiarMemoria.addActionListener(e -> cambiarMemoria());
    }

    private void seleccionarArchivo() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar programa ASM");
        selector.setFileFilter(new FileNameExtensionFilter("Archivos ASM (*.asm)", "asm"));
        selector.setAcceptAllFileFilterUsed(false);

        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            archivoSeleccionado = selector.getSelectedFile();
            lblArchivo.setText(archivoSeleccionado.getAbsolutePath());
            escribirConsola("Archivo seleccionado: " + archivoSeleccionado.getName());
        }
    }

    private void cargarPrograma() {
        ejecutarAccion(() -> {
            if (archivoSeleccionado == null) {
                throw new IllegalStateException("Primero debe seleccionar un archivo .asm.");
            }

            programaSeleccionado = simulador.cargarPrograma(archivoSeleccionado);
            mostrarPrograma(programaSeleccionado);

            Proceso proceso = simulador.prepararPrograma(programaSeleccionado);
            escribirConsola(
                    "Programa cargado. PID " + proceso.getBcp().getPid()
                    + " enviado a READY."
            );
        });
    }

    private void despacharProceso() {
        ejecutarAccion(() -> {
            Proceso proceso = simulador.despacharSiguiente();
            if (proceso == null) {
                escribirConsola("No hay procesos en READY.");
            } else {
                escribirConsola(
                        "Dispatcher: PID " + proceso.getBcp().getPid()
                        + " enviado a la CPU."
                );
            }
        });
    }

    private void ejecutarPaso() {
        ejecutarAccion(() -> {
            Proceso actual = simulador.getProcesoActual();
            if (actual == null) {
                throw new IllegalStateException("Debe despachar un proceso antes de ejecutar.");
            }

            int pcAntes = simulador.getCpu().getPC();
            if (pcAntes >= actual.getBcp().getTamanio()) {
                finalizarProcesoActual();
                return;
            }

            Instruccion instruccion = simulador.getMemory().leerInstruccion(actual, pcAntes);
            boolean quedan = simulador.ejecutarSiguienteInstruccion();

            escribirConsola(
                    "PID " + actual.getBcp().getPid()
                    + " | Ejecutada: " + instruccion
            );

            if (!quedan) {
                finalizarProcesoActual();
            }
        });
    }

    private void ejecutarCompleto() {
        ejecutarAccion(() -> {
            Proceso actual = simulador.getProcesoActual();
            if (actual == null) {
                throw new IllegalStateException("Debe despachar un proceso antes de ejecutar.");
            }

            simulador.ejecutarProcesoCompleto();
            escribirConsola("PID " + actual.getBcp().getPid() + " terminó su ejecución.");
            finalizarProcesoActual();
        });
    }

    private void finalizarProcesoActual() {
        Proceso actual = simulador.getGestorProceso().getRunning();
        if (actual != null) {
            actual.getBcp().setEstadoProceso("Terminated");
            simulador.getMemory().actualizarBCP(actual);
            simulador.getGestorProceso().terminarActual();
            escribirConsola("PID " + actual.getBcp().getPid() + " movido a TERMINATED.");
        }
    }

    private void cambiarMemoria() {
        ejecutarAccion(() -> {
            int nuevoTamanio = (Integer) spinnerMemoria.getValue();

            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "Cambiar la memoria eliminará todos los procesos actuales. ¿Continuar?",
                    "Cambiar memoria",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            simulador.cambiarMemoria(nuevoTamanio);
            programaSeleccionado = null;
            archivoSeleccionado = null;
            lblArchivo.setText("Ningún archivo seleccionado");
            areaPrograma.setText("");
            escribirConsola("Memoria cambiada a " + nuevoTamanio + " posiciones.");
        });
    }

    private void ejecutarAccion(Accion accion) {
        try {
            accion.ejecutar();
        } catch (Exception ex) {
            escribirConsola("ERROR: " + ex.getMessage());
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            actualizarTodo();
        }
    }

    private void actualizarTodo() {
        actualizarEstado();
        actualizarCPU();
        actualizarProcesos();
        actualizarMemoria();
        actualizarBotones();
    }

    private void actualizarEstado() {
        boolean encendido = simulador.isEncendido();
        lblEstadoSO.setText(encendido ? "ENCENDIDO" : "APAGADO");
        lblEstadoSO.setForeground(encendido ? new Color(0, 140, 0) : Color.RED.darker());
        lblMemoria.setText(
                simulador.getMemory().getEspacio()
                + " posiciones (SO: "
                + simulador.getMemory().getEspacioSO()
                + ")"
        );
    }

    private void actualizarCPU() {
        CPU cpu = simulador.getCpu();
        txtPC.setText(String.valueOf(cpu.getPC()));
        txtIR.setText(cpu.getIR() == null ? "-" : cpu.getIR());
        txtAC.setText(String.valueOf(cpu.getAC()));
        txtAX.setText(String.valueOf(cpu.getAX()));
        txtBX.setText(String.valueOf(cpu.getBX()));
        txtCX.setText(String.valueOf(cpu.getCX()));
        txtDX.setText(String.valueOf(cpu.getDX()));
    }

    private void actualizarProcesos() {
        modeloProcesos.setRowCount(0);
        GestorProceso gestor = simulador.getGestorProceso();

        agregarCola("READY", gestor.getReady());

        Proceso running = gestor.getRunning();
        if (running != null) {
            agregarProceso("RUNNING", running);
        }

        agregarCola("BLOCKED", gestor.getBlocked());
        agregarCola("TERMINATED", gestor.getTerminated());
    }

    private void agregarCola(String estado, Queue<Proceso> cola) {
        for (Proceso proceso : cola) {
            agregarProceso(estado, proceso);
        }
    }

    private void agregarProceso(String estado, Proceso proceso) {
        BCP bcp = proceso.getBcp();
        modeloProcesos.addRow(new Object[]{
            estado,
            bcp.getPid(),
            proceso.getPrograma().getNombre(),
            bcp.getPC(),
            bcp.getAC(),
            bcp.getBase(),
            bcp.getTamanio()
        });
    }

    private void actualizarMemoria() {
        modeloMemoria.setRowCount(0);

        Memory memory = simulador.getMemory();
        Object[] snapshot = memory.getMemoriaSnapshot();
        int espacioSO = memory.getEspacioSO();

        String[] atributosBCP = {
            "PID", "Estado", "PC", "AC", "Base", "Tamaño", "AX", "BX", "CX", "DX"
        };

        for (int i = 0; i < snapshot.length; i++) {
            String zona = i < espacioSO ? "SO" : "Usuario";
            String contenido;

            if (snapshot[i] == null) {
                contenido = "Libre";
            } else if (i < espacioSO) {
                contenido = atributosBCP[i % 10] + ": " + snapshot[i];
            } else {
                contenido = snapshot[i].toString();
            }

            modeloMemoria.addRow(new Object[]{i, zona, contenido});
        }
    }

    private void actualizarBotones() {
        boolean encendido = simulador.isEncendido();
        boolean hayArchivo = archivoSeleccionado != null;
        boolean hayRunning = simulador.getGestorProceso().getRunning() != null;
        boolean hayReady = !simulador.getGestorProceso().getReady().isEmpty();

        btnEncender.setEnabled(!encendido);
        btnApagar.setEnabled(encendido);
        btnSeleccionar.setEnabled(encendido);
        btnCargar.setEnabled(encendido && hayArchivo);
        btnDespachar.setEnabled(encendido && hayReady && !hayRunning);
        btnPaso.setEnabled(encendido && hayRunning);
        btnCompleto.setEnabled(encendido && hayRunning);
        btnReiniciar.setEnabled(encendido);
        btnCambiarMemoria.setEnabled(encendido);
    }

    private void mostrarPrograma(Programa programa) {
        StringBuilder texto = new StringBuilder();
        texto.append("Programa: ").append(programa.getNombre()).append("\n");
        texto.append("Instrucciones: ").append(programa.getTamanio()).append("\n\n");

        for (int i = 0; i < programa.getInstrucciones().size(); i++) {
            texto.append(String.format("%03d  %s%n", i, programa.getInstrucciones().get(i)));
        }

        areaPrograma.setText(texto.toString());
        areaPrograma.setCaretPosition(0);
    }

    private void escribirConsola(String mensaje) {
        areaConsola.append("> " + mensaje + System.lineSeparator());
        areaConsola.setCaretPosition(areaConsola.getDocument().getLength());
    }

    private void agregarRegistro(JPanel panel, String nombre, JTextField campo) {
        JLabel etiqueta = new JLabel(nombre + ":");
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(etiqueta);
        panel.add(campo);
    }

    private static JTextField crearCampoRegistro() {
        JTextField campo = new JTextField();
        campo.setEditable(false);
        campo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
        return campo;
    }

    @FunctionalInterface
    private interface Accion {
        void ejecutar() throws Exception;
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
