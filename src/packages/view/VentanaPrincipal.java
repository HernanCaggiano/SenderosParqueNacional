package packages.view;

import packages.algorithms.*;
import packages.util.*;
import packages.model.*;

import org.openstreetmap.gui.jmapviewer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	private JMapViewer mapa;
    private Grafo grafo;

    public VentanaPrincipal() {
        setTitle("Red de Senderos - Parque Nacional");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel del mapa
        mapa = new JMapViewer();
        add(mapa, BorderLayout.CENTER);
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnCargar = new JButton("Cargar Datos");
        JButton btnAGM = new JButton("Árbol Generador Mínimo");
        JButton btnDijkstra = new JButton("Camino Mínimo");
        JButton btnFloyd = new JButton("Floyd-Warshall");
        JButton btnFord = new JButton("Flujo Máximo");

        panelBotones.add(btnCargar);
        panelBotones.add(btnAGM);
        panelBotones.add(btnDijkstra);
        panelBotones.add(btnFloyd);
        panelBotones.add(btnFord);
        add(panelBotones, BorderLayout.NORTH);

        // Acciones de los botones
        btnCargar.addActionListener(this::accionCargar);
        btnAGM.addActionListener(this::accionAGM);
        btnDijkstra.addActionListener(this::accionDijkstra);
        btnFloyd.addActionListener(this::accionFloyd);
        btnFord.addActionListener(this::accionFord);
    }

    private void accionCargar(ActionEvent e) {
        grafo = new Grafo();
        List<Estacion> estaciones = LectorJSON.leerEstacionesDesdeJSON("resources/datos.json");
        List<Sendero> senderos = LectorJSON.leerSenderosDesdeJSON("resources/datos.json", estaciones);

        for (Estacion est : estaciones) grafo.agregarEstacion(est);
        for (Sendero s : senderos) grafo.agregarSendero(s);

        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();

        for (Estacion est : estaciones) {
            MapMarkerDot marker = new MapMarkerDot(est.getLatitud(), est.getLongitud());
            mapa.addMapMarker(marker);
        }

        for (Sendero s : grafo.getSenderos()) {
            MapPolygonImpl polygon = new MapPolygonImpl(
                new Coordinate(s.getOrigen().getLatitud(), s.getOrigen().getLongitud()),
                new Coordinate((s.getOrigen().getLatitud() + s.getDestino().getLatitud()) / 2,
                               (s.getOrigen().getLongitud() + s.getDestino().getLongitud()) / 2),
                new Coordinate(s.getDestino().getLatitud(), s.getDestino().getLongitud())
            );
            mapa.addMapPolygon(polygon);
        }

        JOptionPane.showMessageDialog(this, "Datos cargados correctamente.");
    }

    private void accionAGM(ActionEvent e) {
        if (grafo == null) return;
        mapa.removeAllMapPolygons();

        List<Sendero> agm = Kruskal.obtenerAGM(grafo);
        pintarSenderos(agm, Color.GREEN);
    }

    private void accionDijkstra(ActionEvent e) {
        if (grafo == null) return;

        String origen = JOptionPane.showInputDialog("Nombre estación origen:");
        String destino = JOptionPane.showInputDialog("Nombre estación destino:");
        Dijkstra dijkstra = new Dijkstra(grafo);
        List<Sendero> camino = dijkstra.caminoMinimo(origen, destino);
        pintarSenderos(camino, Color.BLUE);
    }

    private void accionFloyd(ActionEvent e) {
        if (grafo == null) return;

        String origen = JOptionPane.showInputDialog("Nombre estación origen:");
        String destino = JOptionPane.showInputDialog("Nombre estación destino:");

        FloydWarshall fw = new FloydWarshall(grafo);
        List<Sendero> camino = fw.recuperarCaminoMinimo(origen, destino);

        if (camino.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay camino entre " + origen + " y " + destino);
            return;
        }

        pintarSenderos(camino, Color.MAGENTA);
        int impactoTotal = camino.stream().mapToInt(Sendero::getImpacto).sum();
        JOptionPane.showMessageDialog(this, "Impacto mínimo entre " + origen + " y " + destino + ": " + impactoTotal);
    }

    private void accionFord(ActionEvent e) {
        if (grafo == null) return;

        String origen = JOptionPane.showInputDialog("Nombre estación origen:");
        String destino = JOptionPane.showInputDialog("Nombre estación destino:");

        FordFulkerson ff = new FordFulkerson(grafo);
        int flujo = ff.calcularFlujoMaximo(origen, destino);
        JOptionPane.showMessageDialog(this, "Flujo máximo entre " + origen + " y " + destino + ": " + flujo);
    }

    private void pintarSenderos(List<Sendero> senderos, Color color) {
        for (Sendero s : senderos) {
            MapPolygonImpl polygon = new MapPolygonImpl(
                new Coordinate(s.getOrigen().getLatitud(), s.getOrigen().getLongitud()),
                new Coordinate((s.getOrigen().getLatitud() + s.getDestino().getLatitud()) / 2,
                               (s.getOrigen().getLongitud() + s.getDestino().getLongitud()) / 2),
                new Coordinate(s.getDestino().getLatitud(), s.getDestino().getLongitud())
            );
            polygon.setColor(color);
            mapa.addMapPolygon(polygon);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}