package packages.algorithms;

import java.util.*;

import packages.model.Estacion;
import packages.model.Grafo;
import packages.model.Sendero;

public class FordFulkerson {
	private Grafo grafo;
    private Map<String, List<Sendero>> adj;

    public FordFulkerson(Grafo grafo) {
        this.grafo = grafo;
        adj = new HashMap<>();
        for (Estacion e : grafo.getEstaciones()) {
            adj.put(e.getNombre(), new ArrayList<>());
        }
        for (Sendero s : grafo.getSenderos()) {
            adj.get(s.getOrigen().getNombre()).add(s);
        }
    }

    public int calcularFlujoMaximo(String origen, String destino) {
        Map<Sendero, Integer> capacidadRestante = new HashMap<>();
        for (Sendero s : grafo.getSenderos()) {
            capacidadRestante.put(s, s.getCapacidad());
        }

        int flujoMaximo = 0;

        while (true) {
            Map<String, Sendero> camino = new HashMap<>();
            Queue<String> cola = new LinkedList<>();
            Set<String> visitados = new HashSet<>();

            cola.add(origen);
            visitados.add(origen);

            while (!cola.isEmpty()) {
                String actual = cola.poll();

                for (Sendero s : adj.get(actual)) {
                    String vecino = s.getDestino().getNombre();
                    if (!visitados.contains(vecino) && capacidadRestante.get(s) > 0) {
                        camino.put(vecino, s);
                        visitados.add(vecino);
                        cola.add(vecino);
                    }
                }
            }

            if (!camino.containsKey(destino)) break;

            int flujo = Integer.MAX_VALUE;
            String nodo = destino;
            while (!nodo.equals(origen)) {
                Sendero s = camino.get(nodo);
                flujo = Math.min(flujo, capacidadRestante.get(s));
                nodo = s.getOrigen().getNombre();
            }

            nodo = destino;
            while (!nodo.equals(origen)) {
                Sendero s = camino.get(nodo);
                capacidadRestante.put(s, capacidadRestante.get(s) - flujo);
                nodo = s.getOrigen().getNombre();
            }

            flujoMaximo += flujo;
        }

        return flujoMaximo;
    }
}
