package test.algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import packages.algorithms.FloydWarshall;
import packages.model.*;

public class FloydWarshallTest {
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
    public void testRecuperarCaminoMinimo() {
        FloydWarshall floyd = new FloydWarshall(grafo);
        List<Sendero> camino = floyd.recuperarCaminoMinimo("A", "D");

        // Se espera: A → B → C → D (impacto total = 3 + 1 + 2 = 6)
        assertEquals(3, camino.size());
        assertEquals("A", camino.get(0).getOrigen().getNombre());
        assertEquals("B", camino.get(0).getDestino().getNombre());

        assertEquals("B", camino.get(1).getOrigen().getNombre());
        assertEquals("C", camino.get(1).getDestino().getNombre());

        assertEquals("C", camino.get(2).getOrigen().getNombre());
        assertEquals("D", camino.get(2).getDestino().getNombre());
    }

    @Test
    public void testImprimirTodosLosCaminosMinimos() {
        FloydWarshall floyd = new FloydWarshall(grafo);
        String resultado = floyd.imprimirTodosLosCaminosMinimos();

        // Comprobar que incluye un camino conocido
        assertTrue(resultado.contains("De A a D: impacto mínimo = 6"));
        assertTrue(resultado.contains("De A a C: impacto mínimo = 4")); // A → B → C
    }
}