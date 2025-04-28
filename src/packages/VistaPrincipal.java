package packages;

import javax.swing.*;
import java.awt.*;
import org.openstreetmap.gui.jmapviewer.*;

@SuppressWarnings("serial")
public class VistaPrincipal extends JFrame {
	private JTextArea areaTexto;
    private JButton btnCargarArchivo;
    private JButton btnFlujoMaximo;
    private JButton btnFlujoMinimo;
    private JMapViewer mapa;

    public VistaPrincipal() {
        setTitle("Red de Senderos - Parque Nacional");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        btnCargarArchivo = new JButton("Cargar desde archivo");
        btnFlujoMaximo = new JButton("Flujo Máximo (Ford-Fulkerson)");
        btnFlujoMinimo = new JButton("Flujo Mínimo (Dijkstra)");
        panelBotones.add(btnCargarArchivo);
        panelBotones.add(btnFlujoMaximo);
        panelBotones.add(btnFlujoMinimo);
        getContentPane().add(panelBotones, BorderLayout.WEST);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(250, 0));
        getContentPane().add(scroll, BorderLayout.EAST);

        mapa = new JMapViewer();
        getContentPane().add(mapa, BorderLayout.CENTER);
    }

    public JButton getBtnCargarArchivo() { return btnCargarArchivo; }
    public JButton getBtnFlujoMaximo() { return btnFlujoMaximo; }
    public JButton getBtnFlujoMinimo() { return btnFlujoMinimo; }
    public JMapViewer getMapa() { return mapa; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void appendTexto(String texto) {
        areaTexto.append(texto + "\n");
    }

    public void limpiarTexto() {
        areaTexto.setText("");
    }

    public void agregarEstacionMapa(Estacion e) {
        mapa.addMapMarker(new MapMarkerDot(e.getNombre(), new Coordinate(e.getLatitud(), e.getLongitud())));
    }

}
