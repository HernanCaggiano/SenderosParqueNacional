package packages;

import java.util.*;

public class DijkstraFlujoMinimo {
	public Map<String, Integer> calcularCostoMinimo(Map<String, Map<String, Integer>> grafo, String origen) {
        Map<String, Integer> distancias = new HashMap<>();
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.costo));

        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);
        cola.add(new Nodo(origen, 0));

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            if (actual.costo > distancias.get(actual.nombre)) continue;

            for (Map.Entry<String, Integer> vecino : grafo.get(actual.nombre).entrySet()) {
                int nuevoCosto = actual.costo + vecino.getValue();
                if (nuevoCosto < distancias.get(vecino.getKey())) {
                    distancias.put(vecino.getKey(), nuevoCosto);
                    cola.add(new Nodo(vecino.getKey(), nuevoCosto));
                }
            }
        }

        return distancias;
    }

    private static class Nodo {
        String nombre;
        int costo;

        Nodo(String nombre, int costo) {
            this.nombre = nombre;
            this.costo = costo;
        }
    }
}
