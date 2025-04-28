package packages;

import java.util.*;

public class UtilidadesFlujo {
	public static Map<String, Map<String, Integer>> construirMapaFlujo(Grafo grafo) {
        Map<String, Map<String, Integer>> mapa = new HashMap<>();

        for (Estacion estacion : grafo.getEstaciones()) {
            mapa.put(estacion.getNombre(), new HashMap<>());
        }

        for (Sendero s : grafo.getSenderos()) {
            String origen = s.getOrigen().getNombre();
            String destino = s.getDestino().getNombre();
            int capacidad = s.getImpactoAmbiental();

            mapa.get(origen).put(destino, capacidad);
            mapa.get(destino).put(origen, capacidad);
        }

        return mapa;
    }
}
