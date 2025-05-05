package packages.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import packages.model.Estacion;
import packages.model.Grafo;
import packages.model.Sendero;

public class FloydWarshall {
	private int[][] dist;
    private String[][] next;
    private List<String> nombres;
    private Map<String, Estacion> estacionesMap;
    private Grafo grafo;

    public FloydWarshall(Grafo grafo) {
        this.grafo = grafo;
        int n = grafo.getEstaciones().size();
        nombres = new ArrayList<>();
        estacionesMap = new HashMap<>();
        Map<String, Integer> index = new HashMap<>();

        for (Estacion e : grafo.getEstaciones()) {
            index.put(e.getNombre(), nombres.size());
            nombres.add(e.getNombre());
            estacionesMap.put(e.getNombre(), e);
        }

        dist = new int[n][n];
        next = new String[n][n];

        for (int i = 0; i < n; i++) Arrays.fill(dist[i], Integer.MAX_VALUE / 2);

        for (Sendero s : grafo.getSenderos()) {
            int i = index.get(s.getOrigen().getNombre());
            int j = index.get(s.getDestino().getNombre());
            dist[i][j] = s.getImpacto();
            next[i][j] = nombres.get(j);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public String imprimirTodosLosCaminosMinimos() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nombres.size(); i++) {
            for (int j = 0; j < nombres.size(); j++) {
                if (i != j) {
                    sb.append("De ").append(nombres.get(i)).append(" a ").append(nombres.get(j))
                      .append(": impacto mínimo = ").append(dist[i][j]).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public List<Sendero> recuperarCaminoMinimo(String origen, String destino) {
        List<Sendero> camino = new ArrayList<>();
        Map<String, Integer> index = new HashMap<>();
        for (int i = 0; i < nombres.size(); i++) {
            index.put(nombres.get(i), i);
        }

        if (!index.containsKey(origen) || !index.containsKey(destino)) return camino;

        int i = index.get(origen);
        int j = index.get(destino);

        if (next[i][j] == null) return camino;

        while (!nombres.get(i).equals(nombres.get(j))) {
            int k = index.get(next[i][j]);
            Estacion origenEst = estacionesMap.get(nombres.get(i));
            Estacion destinoEst = estacionesMap.get(nombres.get(k));

            Sendero senderoReal = buscarSendero(origenEst, destinoEst);
            if (senderoReal != null) camino.add(senderoReal);

            i = k;
        }

        return camino;
    }

    private Sendero buscarSendero(Estacion origen, Estacion destino) {
        for (Sendero s : grafo.getSenderos()) {
            if (s.getOrigen().equals(origen) && s.getDestino().equals(destino)) {
                return s;
            }
        }
        return null;
    }
}
