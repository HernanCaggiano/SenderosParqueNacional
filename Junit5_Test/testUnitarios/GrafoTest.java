package testUnitarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.Estacion;
import modelo.Grafo;
import modelo.Sendero;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


class GrafoTest {
    private Grafo grafo;
    private Estacion e1, e2, e3;

    @BeforeEach
    void setUp() {
        grafo = new Grafo();
        e1 = new Estacion("E1", "1", 0, 0);
        e2 = new Estacion("E2", "2", 1, 1);
        e3 = new Estacion("E3", "3", 2, 2);
        grafo.agregarEstacion(e1);
        grafo.agregarEstacion(e2);
        grafo.agregarEstacion(e3);
        grafo.agregarSendero(new Sendero(e1, e2, 3));
        grafo.agregarSendero(new Sendero(e2, e3, 4));
    }

    @Test
    void testObtenerEstaciones() {
        Set<Estacion> esperado = Set.of(e1, e2, e3);
        assertEquals(esperado, new HashSet<>(grafo.obtenerEstaciones()));
    }

    @Test
    void testObtenerSenderos() {
        assertEquals(2, grafo.obtenerSenderos().size());
    }

    @Test
    void testObtenerAdyacentes() {
        List<Sendero> ady = grafo.obtenerAdyacentes("E2");
        assertEquals(2, ady.size());
    }

    @Test
    void testObtenerAdyacentesSinConexiones() {
        grafo.agregarEstacion(new Estacion("E4", "4", 3, 3));
        List<Sendero> ady = grafo.obtenerAdyacentes("E4");
        assertTrue(ady.isEmpty());
    }

    @Test
    void testLimpiar() {
       
        assertFalse(grafo.obtenerEstaciones().isEmpty());
        grafo.limpiar();
        assertTrue(grafo.obtenerSenderos().isEmpty());
    }
}