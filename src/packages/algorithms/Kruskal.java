package packages.algorithms;

import java.util.*;

import packages.model.Estacion;
import packages.model.Grafo;
import packages.model.Sendero;

public class Kruskal {
	public static List<Sendero> obtenerAGM(Grafo grafo) {
        List<Sendero> resultado = new ArrayList<>();
        Map<String, String> padre = new HashMap<>();

        for (Estacion e : grafo.getEstaciones()) {
            padre.put(e.getNombre(), e.getNombre());
        }

        List<Sendero> senderosOrdenados = new ArrayList<>(grafo.getSenderos());
        senderosOrdenados.sort(Comparator.comparingInt(Sendero::getImpacto));

        for (Sendero s : senderosOrdenados) {
            String raiz1 = encontrar(padre, s.getOrigen().getNombre());
            String raiz2 = encontrar(padre, s.getDestino().getNombre());

            if (!raiz1.equals(raiz2)) {
                resultado.add(s);
                padre.put(raiz1, raiz2);
            }
        }

        return resultado;
    }

    private static String encontrar(Map<String, String> padre, String nodo) {
        if (!padre.get(nodo).equals(nodo)) {
            padre.put(nodo, encontrar(padre, padre.get(nodo)));
        }
        return padre.get(nodo);
    }
}