package vista;

import modelo.Grafo;
import modelo.Estacion;
import modelo.Sendero;
import controlador.ControladorGrafo;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Arrays;

public class VistaMapa extends JFrame {

	private static final long serialVersionUID = 7765531963363704843L;
	private final JMapViewer mapa = new JMapViewer();
	private final JButton btnPrim = new JButton("Prim");
	private final JButton btnKruskal = new JButton("Kruskal");
	private final JMenuItem menuCargar = new JMenuItem("Cargar JSON");
	private double minLat, maxLat, minLon, maxLon;

	public VistaMapa() {
		super("Mapa");
		mapa.setLayout(null);
		inicializarComponentes();
		setSize(800, 600);
		agregarLeyenda();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void setControlador(ControladorGrafo c) {
		menuCargar.addActionListener(e -> c.cargarGrafoDesdeJSON());
		btnPrim.addActionListener(e -> c.mostrarPrim());
		btnKruskal.addActionListener(e -> c.mostrarKruskal());
	}

	public void mostrarGrafoCompleto(Grafo g) {
		limpiarMapa();
		inicializarLimites();
		calcularLimites(g);
		pintarMarcadores(g);
		pintarSenderos(g);
		ajustarVista();
	}

	public void resaltarAGM(List<Sendero> aristas, Color color) {
		for (Sendero s : aristas) {
			dibujarSendero(s, color, 3f);
		}
		mapa.repaint();
	}

	private void inicializarComponentes() {
		JMenu menu = new JMenu("Archivo");
		menu.add(menuCargar);
		JMenuBar barra = new JMenuBar();
		barra.add(menu);
		setJMenuBar(barra);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBotones.add(btnPrim);
		panelBotones.add(btnKruskal);

		setLayout(new BorderLayout());
		add(panelBotones, BorderLayout.NORTH);
		add(mapa, BorderLayout.CENTER);
	}

	private void agregarLeyenda() {
		JPanel leyenda = new JPanel();
		leyenda.setLayout(new BoxLayout(leyenda, BoxLayout.Y_AXIS));
		leyenda.setBackground(new Color(255, 255, 255, 200));
		leyenda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		leyenda.setSize(180, 120);

		leyenda.add(crearEntradaLeyenda(Color.RED, "Impacto alto (≥8)"));
		leyenda.add(crearEntradaLeyenda(Color.YELLOW, "Impacto medio (4–7)"));
		leyenda.add(crearEntradaLeyenda(Color.GREEN, "Impacto bajo (<4)"));

		leyenda.add(crearEntradaLeyenda(Color.BLUE, "Algoritmo Kruskal"));
		leyenda.add(crearEntradaLeyenda(Color.BLACK, "Algoritmo Prim"));

		mapa.add(leyenda);
		posicionarLeyenda(leyenda);

		mapa.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				posicionarLeyenda(leyenda);
			}
		});
	}

	private void posicionarLeyenda(JPanel leyenda) {
		int x = mapa.getWidth() - leyenda.getWidth() - 10;
		int y = 10;
		leyenda.setLocation(x, y);
	}

	private JPanel crearEntradaLeyenda(Color color, String texto) {
		JPanel renglon = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
		renglon.setOpaque(false);

		JPanel cuadrito = new JPanel();
		cuadrito.setBackground(color);
		cuadrito.setPreferredSize(new Dimension(12, 12));

		JLabel etiqueta = new JLabel(texto);
		etiqueta.setFont(etiqueta.getFont().deriveFont(12f));

		renglon.add(cuadrito);
		renglon.add(etiqueta);
		return renglon;
	}

	private void limpiarMapa() {
		mapa.removeAllMapMarkers();
		mapa.removeAllMapPolygons();
	}

	private void inicializarLimites() {
		minLat = Double.MAX_VALUE;
		maxLat = -Double.MAX_VALUE;
		minLon = Double.MAX_VALUE;
		maxLon = -Double.MAX_VALUE;
	}

	private void calcularLimites(Grafo g) {
		for (Estacion e : g.obtenerEstaciones()) {
			double lat = e.getLatitud(), lon = e.getLongitud();
			minLat = Math.min(minLat, lat);
			maxLat = Math.max(maxLat, lat);
			minLon = Math.min(minLon, lon);
			maxLon = Math.max(maxLon, lon);
		}
	}

	private void pintarMarcadores(Grafo g) {
		for (Estacion e : g.obtenerEstaciones()) {
			MapMarkerDot marcador = new MapMarkerDot(new Coordinate(e.getLatitud(), e.getLongitud()));
			marcador.setName(e.getNombre());
			mapa.addMapMarker(marcador);
		}
	}

	private void pintarSenderos(Grafo g) {
		for (Sendero s : g.obtenerSenderos()) {
			dibujarSendero(s, colorPorImpacto(s.getImpacto()), 2f);
		}
	}

	private void dibujarSendero(Sendero s, Color color, float grosor) {
		Coordinate c1 = new Coordinate(s.getOrigen().getLatitud(), s.getOrigen().getLongitud());
		Coordinate c2 = new Coordinate(s.getDestino().getLatitud(), s.getDestino().getLongitud());
		MapPolygonImpl pol = new MapPolygonImpl(Arrays.asList(c1, c2, c1));
		pol.setStroke(new BasicStroke(grosor));
		pol.setColor(color);
		mapa.addMapPolygon(pol);
	}

	private Color colorPorImpacto(int impacto) {
		if (impacto >= 8)
			return Color.RED;
		if (impacto >= 4)
			return Color.YELLOW;
		return Color.GREEN;
	}

	private void ajustarVista() {
		if (!mapa.getMapPolygonList().isEmpty()) {
			mapa.setDisplayToFitMapPolygons();
		} else {
			double centroLat = (minLat + maxLat) / 2;
			double centroLon = (minLon + maxLon) / 2;
			mapa.setDisplayPosition(new Coordinate(centroLat, centroLon), 10);
		}
	}
}