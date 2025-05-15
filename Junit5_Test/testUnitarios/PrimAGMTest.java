package testUnitarios;

import org.junit.jupiter.api.Test;
import modelo.Estacion;
import modelo.Grafo;
import modelo.PrimAGM;
import modelo.Sendero;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class PrimAGMTest {

    @Test
    void testMSTVacioPrim() {
        Grafo g = new Grafo();
        List<Sendero> resultado = PrimAGM.computarAGM(g);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testMSTConUnArista() {
        Grafo g = new Grafo();
        Estacion a = new Estacion("A", "A", 0, 0);
        Estacion b = new Estacion("B", "B", 1, 1);
        g.agregarEstacion(a);
        g.agregarEstacion(b);
        g.agregarSendero(new Sendero(a, b, 7));
        List<Sendero> mst = PrimAGM.computarAGM(g);
        assertEquals(1, mst.size());
        assertEquals(7, mst.get(0).getImpacto());
    }

    @Test
    void testImpactoTotalPrim() {
        Grafo g = new Grafo();
        Estacion a = new Estacion("A","A",0,0);
        Estacion b = new Estacion("B","B",0,0);
        Estacion c = new Estacion("C","C",0,0);
        Estacion d = new Estacion("D","D",0,0);
        g.agregarEstacion(a);
        g.agregarEstacion(b);
        g.agregarEstacion(c);
        g.agregarEstacion(d);
        g.agregarSendero(new Sendero(a,b,1));
        g.agregarSendero(new Sendero(b,c,1));
        g.agregarSendero(new Sendero(c,d,1));
        g.agregarSendero(new Sendero(a,d,10));
        List<Sendero> mst = PrimAGM.computarAGM(g);
        int suma = mst.stream().mapToInt(Sendero::getImpacto).sum();
        assertEquals(3, mst.size());
        assertEquals(3, suma);
    }

    @Test
    void testCoberturaDeNodosPrim() {
        Grafo g = new Grafo();
        Estacion a = new Estacion("A","A",0,0);
        Estacion b = new Estacion("B","B",0,0);
        Estacion c = new Estacion("C","C",0,0);
        g.agregarEstacion(a);
        g.agregarEstacion(b);
        g.agregarEstacion(c);
        g.agregarSendero(new Sendero(a,b,2));
        g.agregarSendero(new Sendero(b,c,3));
        List<Sendero> mst = PrimAGM.computarAGM(g);
        Set<String> nodos = new HashSet<>();
        for (Sendero s : mst) {
            nodos.add(s.getOrigen().getId());
            nodos.add(s.getDestino().getId());
        }
        assertEquals(Set.of("A","B","C"), nodos);
    }
}