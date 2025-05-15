// KruskalAGM.java
package modelo;

import java.util.*;

public class KruskalAGM {
    private static class DSU {
        private final Map<String,String> padre = new HashMap<>();
        void crearConjunto(String id) { padre.put(id, id); }
        String buscar(String id) {
            String p = padre.get(id);
            if (!p.equals(id)) {
                p = buscar(p);
                padre.put(id, p);
            }
            return p;
        }
        void unificar(String a, String b) {
            padre.put(buscar(a), buscar(b));
        }
    }

    public static List<Sendero> computarAGM(Grafo g) {
        DSU dsu = new DSU();
        iniciarConjuntos(dsu, g.obtenerEstaciones());
        List<Sendero> lista = convertirALista(g.obtenerSenderos());
        Collections.sort(lista);
        return construirAGM(dsu, lista);
    }

    private static void iniciarConjuntos(DSU dsu, Collection<Estacion> estaciones) {
        for (Estacion e : estaciones) dsu.crearConjunto(e.getId());
    }

    private static List<Sendero> convertirALista(Collection<Sendero> senderos) {
        return new ArrayList<>(senderos);
    }

    private static List<Sendero> construirAGM(DSU dsu, List<Sendero> lista) {
        List<Sendero> resultado = new ArrayList<>();
        for (Sendero s : lista) {
            String u = s.getOrigen().getId();
            String v = s.getDestino().getId();
            if (!dsu.buscar(u).equals(dsu.buscar(v))) {
                dsu.unificar(u, v);
                resultado.add(s);
            }
        }
        return resultado;
    }
}
