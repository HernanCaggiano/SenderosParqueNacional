// PrimAGM.java
package modelo;
import java.util.*;

public class PrimAGM {
    public static List<Sendero> computarAGM(Grafo g) {
        Collection<Estacion> estaciones = g.obtenerEstaciones();
        if (estaciones.isEmpty()) return new ArrayList<>();
        String inicio = estaciones.iterator().next().getId();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<Sendero> cola = new PriorityQueue<>();
        List<Sendero> resultado = new ArrayList<>();
        visitados.add(inicio);
        agregarAdyacentes(cola, g, inicio);
        procesarCola(cola, visitados, resultado, g);
        return resultado;
    }

    private static void agregarAdyacentes(PriorityQueue<Sendero> cola, Grafo g, String id) {
        cola.addAll(g.obtenerAdyacentes(id));
    }

    private static void procesarCola(PriorityQueue<Sendero> cola,
                                     Set<String> visitados,
                                     List<Sendero> resultado,
                                     Grafo g) {
        int total = g.obtenerEstaciones().size();
        while (!cola.isEmpty() && visitados.size() < total) {
            Sendero s = cola.poll();
            String u = s.getOrigen().getId();
            String v = s.getDestino().getId();
            String siguiente = visitados.contains(u) ? v : u;
            if (visitados.contains(siguiente)) continue;
            visitados.add(siguiente);
            resultado.add(s);
            agregarAdyacentes(cola, g, siguiente);
        }
    }
}
