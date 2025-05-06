package test.algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import packages.algorithms.Dijkstra;
import packages.model.*;

public class DijkstraTest {
	private Grafo grafo;
    private Estacion a, b, c, d;

    @Before
    public void setUp() {
        grafo = new Grafo();

        // Crear estaciones
        a = new Estacion("A", 0.0, 0.0);
        b = new Estacion("B", 0.0, 0.0);
        c = new Estacion("C", 0.0, 0.0);
        d = new Estacion("D", 0.0, 0.0);

        // Agregarlas al grafo
        grafo.agregarEstacion(a);
        grafo.agregarEstacion(b);
        grafo.agregarEstacion(c);
        grafo.agregarEstacion(d);

        // Crear senderos (impacto ambiental como peso)
        grafo.agregarSendero(new Sendero(a, b, 3, 1));   // A → B (3)
        grafo.agregarSendero(new Sendero(b, c, 1, 1));   // B → C (1)
        grafo.agregarSendero(new Sendero(a, c, 10, 1));  // A → C (10)
        grafo.agregarSendero(new Sendero(c, d, 2, 1));   // C → D (2)
    }

    @Test
    public void testCaminoMinimo() {
        Dijkstra dijkstra = new Dijkstra(grafo);
        List<Sendero> camino = dijkstra.caminoMinimo("A", "D");

        // Camino esperado: A → B → C → D con impacto total 3 + 1 + 2 = 6
        assertEquals(3, camino.size());

        assertEquals("A", camino.get(0).getOrigen().getNombre());
        assertEquals("B", camino.get(0).getDestino().getNombre());

        assertEquals("B", camino.get(1).getOrigen().getNombre());
        assertEquals("C", camino.get(1).getDestino().getNombre());

        assertEquals("C", camino.get(2).getOrigen().getNombre());
        assertEquals("D", camino.get(2).getDestino().getNombre());
    }

    @Test
    public void testCaminoMinimoDirecto() {
        Dijkstra dijkstra = new Dijkstra(grafo);
        List<Sendero> camino = dijkstra.caminoMinimo("A", "C");

        // Camino esperado: A → B → C (3 + 1 = 4), no A → C directo (10)
        assertEquals(2, camino.size());
        assertEquals("A", camino.get(0).getOrigen().getNombre());
        assertEquals("B", camino.get(0).getDestino().getNombre());
        assertEquals("C", camino.get(1).getDestino().getNombre());
    }

    @Test
    public void testCaminoInexistente() {
        Estacion e = new Estacion("E", 0.0, 0.0); // Estación aislada
        grafo.agregarEstacion(e);

        Dijkstra dijkstra = new Dijkstra(grafo);
        List<Sendero> camino = dijkstra.caminoMinimo("A", "E");

        // No hay camino desde A hasta E
        assertTrue(camino.isEmpty());
    }
}