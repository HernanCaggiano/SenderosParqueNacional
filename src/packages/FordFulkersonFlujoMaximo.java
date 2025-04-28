package packages;

import java.util.*;

public class FordFulkersonFlujoMaximo {
	public int calcularFlujoMaximo(Map<String, Map<String, Integer>> grafo, String fuente, String sumidero) {
        Map<String, Map<String, Integer>> residual = new HashMap<>();
        for (String u : grafo.keySet()) {
            residual.put(u, new HashMap<>());
            for (String v : grafo.get(u).keySet()) {
                residual.get(u).put(v, grafo.get(u).get(v));
            }
        }

        int flujoMax = 0;
        List<String> camino;
        while ((camino = bfs(residual, fuente, sumidero)) != null) {
            int flujo = Integer.MAX_VALUE;
            for (int i = 0; i < camino.size() - 1; i++) {
                flujo = Math.min(flujo, residual.get(camino.get(i)).get(camino.get(i + 1)));
            }
            for (int i = 0; i < camino.size() - 1; i++) {
                String u = camino.get(i);
                String v = camino.get(i + 1);
                residual.get(u).put(v, residual.get(u).get(v) - flujo);
                residual.get(v).put(u, residual.get(v).getOrDefault(u, 0) + flujo);
            }
            flujoMax += flujo;
        }
        return flujoMax;
    }

    private List<String> bfs(Map<String, Map<String, Integer>> residual, String fuente, String destino) {
        Queue<String> cola = new LinkedList<>();
        Map<String, String> padre = new HashMap<>();
        cola.add(fuente);
        padre.put(fuente, null);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Map.Entry<String, Integer> vecino : residual.get(actual).entrySet()) {
                if (vecino.getValue() > 0 && !padre.containsKey(vecino.getKey())) {
                    padre.put(vecino.getKey(), actual);
                    cola.add(vecino.getKey());
                    if (vecino.getKey().equals(destino)) {
                        List<String> camino = new ArrayList<>();
                        for (String v = destino; v != null; v = padre.get(v)) {
                            camino.add(0, v);
                        }
                        return camino;
                    }
                }
            }
        }
        return null;
    }
}
