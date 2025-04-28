package packages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class ControladorRedSenderos {
	private VistaPrincipal vista;
    private Grafo grafo;

    public ControladorRedSenderos(VistaPrincipal vista, Grafo grafo) {
        this.vista = vista;
        this.grafo = grafo;
        agregarEventos();
    }

    private void agregarEventos() {
        // Botón de Flujo Mínimo
        vista.getBtnFlujoMinimo().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String origen = JOptionPane.showInputDialog(vista, "Nombre de estación origen:");
                Map<String, Map<String, Integer>> grafoMap = UtilidadesFlujo.construirMapaFlujo(grafo);
                DijkstraFlujoMinimo dijkstra = new DijkstraFlujoMinimo();
                Map<String, Integer> costos = dijkstra.calcularCostoMinimo(grafoMap, origen);
                vista.limpiarTexto();
                vista.appendTexto("Costos mínimos desde " + origen + ":");
                for (String destino : costos.keySet()) {
                    vista.appendTexto(" - " + destino + ": " + costos.get(destino));
                }
            }
        });

        // Botón de Flujo Máximo
        vista.getBtnFlujoMaximo().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fuente = JOptionPane.showInputDialog(vista, "Nombre de estación fuente:");
                String sumidero = JOptionPane.showInputDialog(vista, "Nombre de estación destino:");
                Map<String, Map<String, Integer>> grafoMap = UtilidadesFlujo.construirMapaFlujo(grafo);
                FordFulkersonFlujoMaximo ford = new FordFulkersonFlujoMaximo();
                int maxFlujo = ford.calcularFlujoMaximo(grafoMap, fuente, sumidero);
                vista.limpiarTexto();
                vista.appendTexto("Flujo máximo de " + fuente + " a " + sumidero + ": " + maxFlujo);
            }
        });

        // Botón de Cargar Archivo
        vista.getBtnCargarArchivo().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int resultado = fileChooser.showOpenDialog(vista);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    try {
                        // Cargamos datos desde el archivo
                        CargadorDesdeArchivo.cargar(archivo.getAbsolutePath(), grafo);
                        vista.limpiarTexto();
                        vista.appendTexto("Datos cargados correctamente desde: " + archivo.getName());
                        
                        // Actualizamos el mapa
                        vista.getMapa().removeAllMapMarkers();
                        for (Estacion est : grafo.getEstaciones()) {
                            vista.agregarEstacionMapa(est);
                        }
                        
                        // Marcamos los senderos agregando ambos extremos
                        for (Sendero s : grafo.getSenderos()) {
                            Coordinate c1 = new Coordinate(s.getOrigen().getLatitud(), s.getOrigen().getLongitud());
                            Coordinate c2 = new Coordinate(s.getDestino().getLatitud(), s.getDestino().getLongitud());
                            vista.getMapa().addMapMarker(new MapMarkerDot(s.getOrigen().getNombre(), c1));
                            vista.getMapa().addMapMarker(new MapMarkerDot(s.getDestino().getNombre(), c2));
                        }
                        
                    } catch (Exception ex) {
                        vista.mostrarMensaje("Error al cargar el archivo: " + ex.getMessage());
                    }
                }
            }
        });
    }
}
