package test.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import packages.model.Estacion;
import packages.model.Sendero;
import packages.util.LectorJSON;

public class LectorJSONTest {
	@Test
    public void testLeerEstacionesDesdeJSON() {
        List<Estacion> estaciones = LectorJSON.leerEstacionesDesdeJSON("resources/datos_test.json");
        assertEquals(2, estaciones.size());

        Estacion a = estaciones.get(0);
        assertEquals("A", a.getNombre());
        assertEquals(1.1, a.getLatitud(), 0.001);
        assertEquals(2.2, a.getLongitud(), 0.001);

        Estacion b = estaciones.get(1);
        assertEquals("B", b.getNombre());
        assertEquals(3.3, b.getLatitud(), 0.001);
        assertEquals(4.4, b.getLongitud(), 0.001);
    }

    @Test
    public void testLeerSenderosDesdeJSON() {
        List<Estacion> estaciones = LectorJSON.leerEstacionesDesdeJSON("resources/datos_test.json");
        List<Sendero> senderos = LectorJSON.leerSenderosDesdeJSON("resources/datos_test.json", estaciones);

        assertEquals(1, senderos.size());

        Sendero s = senderos.get(0);
        assertEquals("A", s.getOrigen().getNombre());
        assertEquals("B", s.getDestino().getNombre());
        assertEquals(5, s.getImpacto());
        assertEquals(10, s.getCapacidad());
    }
}