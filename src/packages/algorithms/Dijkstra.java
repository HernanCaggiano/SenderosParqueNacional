package packages.algorithms;

import java.util.*;

import packages.model.Estacion;
import packages.model.Grafo;
import packages.model.Sendero;

public class Dijkstra {
	private Grafo grafo;

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    public List<Sendero> caminoMinimo(String nombreOrigen, String nombreDestino) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, Sendero> anterior = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        PriorityQueue<String> cola = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (Estacion e : grafo.getEstaciones()) {
            dist.put(e.getNombre(), Integer.MAX_VALUE);
        }
        dist.put(nombreOrigen, 0);
        cola.add(nombreOrigen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (visitados.contains(actual)) continue;
            visitados.add(actual);

            Estacion estActual = grafo.getEstacion(actual);
            for (Sendero s : estActual.getSenderos()) {
                String vecino = s.getDestino().getNombre();
                int nuevoCosto = dist.get(actual) + s.getImpacto();

                if (nuevoCosto < dist.get(vecino)) {
                    dist.put(vecino, nuevoCosto);
                    anterior.put(vecino, s);
                    cola.add(vecino);
                }
            }
        }

        LinkedList<Sendero> camino = new LinkedList<>();
        String actual = nombreDestino;
        while (anterior.containsKey(actual)) {
            Sendero s = anterior.get(actual);
            camino.addFirst(s);
            actual = s.getOrigen().getNombre();
        }

        return camino;
    }
}
