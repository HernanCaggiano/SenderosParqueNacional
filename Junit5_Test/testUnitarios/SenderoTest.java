package testUnitarios;

import org.junit.jupiter.api.Test;
import modelo.Estacion;
import modelo.Sendero;
import static org.junit.jupiter.api.Assertions.*;

class SenderoTest {

    @Test
    void testCompareTo_MenorImpacto() {
        Estacion origen = new Estacion("O", "O", 0, 0);
        Estacion destino = new Estacion("D", "D", 0, 0);
        Sendero s1 = new Sendero(origen, destino, 2);
        Sendero s2 = new Sendero(origen, destino, 5);
        assertTrue(s1.compareTo(s2) < 0);
    }

    @Test
    void testCompareTo_MayorImpacto() {
        Estacion origen = new Estacion("O", "O", 0, 0);
        Estacion destino = new Estacion("D", "D", 0, 0);
        Sendero s1 = new Sendero(origen, destino, 7);
        Sendero s2 = new Sendero(origen, destino, 3);
        assertTrue(s1.compareTo(s2) > 0);
    }

    @Test
    void testCompareTo_IgualImpacto() {
        Estacion origen = new Estacion("O", "O", 0, 0);
        Estacion destino = new Estacion("D", "D", 0, 0);
        Sendero s1 = new Sendero(origen, destino, 4);
        Sendero s2 = new Sendero(origen, destino, 4);
        assertEquals(0, s1.compareTo(s2));
    }

    @Test
    void testGetOrigen() {
        Estacion o = new Estacion("X", "X", 0, 0);
        Estacion d = new Estacion("Y", "Y", 0, 0);
        Sendero s = new Sendero(o, d, 1);
        assertEquals(o, s.getOrigen());
    }

    @Test
    void testGetDestino() {
        Estacion o = new Estacion("X", "X", 0, 0);
        Estacion d = new Estacion("Y", "Y", 0, 0);
        Sendero s = new Sendero(o, d, 1);
        assertEquals(d, s.getDestino());
    }

    @Test
    void testGetImpacto() {
        Estacion o = new Estacion("X", "X", 0, 0);
        Estacion d = new Estacion("Y", "Y", 0, 0);
        Sendero s = new Sendero(o, d, 9);
        assertEquals(9, s.getImpacto());
    }
}
