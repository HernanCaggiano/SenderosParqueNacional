package test.algoritmo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import packages.algorithms.FordFulkerson;
import packages.model.*;

public class FordFulkersonTest {
	private Grafo grafo;

    @Before
    public void setUp() {
        grafo = new Grafo();

        // Crear estaciones
        Estacion a = new Estacion("A", 0.0, 0.0);
        Estacion b = new Estacion("B", 0.0, 0.0);
        Estacion c = new Estacion("C", 0.0, 0.0);
        Estacion d = new Estacion("D", 0.0, 0.0);

        // Agregar estaciones al grafo
        grafo.agregarEstacion(a);
        grafo.agregarEstacion(b);
        grafo.agregarEstacion(c);
        grafo.agregarEstacion(d);

        // Agregar senderos con capacidad
        grafo.agregarSendero(new Sendero(a, b, 1, 10));
        grafo.agregarSendero(new Sendero(a, c, 1, 5));
        grafo.agregarSendero(new Sendero(b, c, 1, 15));
        grafo.agregarSendero(new Sendero(b, d, 1, 10));
        grafo.agregarSendero(new Sendero(c, d, 1, 10));
    }

    @Test
    public void testFlujoMaximo() {
        FordFulkerson fordFulkerson = new FordFulkerson(grafo);
        int flujoMaximo = fordFulkerson.calcularFlujoMaximo("A", "D");

        assertEquals(15, flujoMaximo);
    }
}