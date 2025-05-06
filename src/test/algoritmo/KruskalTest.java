package test.algoritmo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import packages.algorithms.Kruskal;
import packages.model.*;

public class KruskalTest {
	private Grafo grafo;

    @Before
    public void setUp() {
        grafo = new Grafo();

        Estacion a = new Estacion("A", 0.0, 0.0);
        Estacion b = new Estacion("B", 0.0, 0.0);
        Estacion c = new Estacion("C", 0.0, 0.0);
        Estacion d = new Estacion("D", 0.0, 0.0);

        grafo.agregarEstacion(a);
        grafo.agregarEstacion(b);
        grafo.agregarEstacion(c);
        grafo.agregarEstacion(d);

        grafo.agregarSendero(new Sendero(a, b, 1, 10));
        grafo.agregarSendero(new Sendero(b, c, 4, 10));
        grafo.agregarSendero(new Sendero(c, d, 3, 10));
        grafo.agregarSendero(new Sendero(a, d, 2, 10));
        grafo.agregarSendero(new Sendero(b, d, 5, 10));
    }

    @Test
    public void testObtenerAGM() {
        List<Sendero> agm = Kruskal.obtenerAGM(grafo);

        // Debería tener exactamente (n - 1) senderos en el árbol
        assertEquals(3, agm.size());

        int impactoTotal = agm.stream().mapToInt(Sendero::getImpacto).sum();
        assertEquals(6, impactoTotal); // 1 + 2 + 3 = 6
    }
}
