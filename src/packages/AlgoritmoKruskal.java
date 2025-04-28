package packages;

import java.util.*;

public class AlgoritmoKruskal {
	private List<Sendero> resultado;
    private int impactoTotal;

    public void calcularAGM(Grafo grafo) {
        resultado = new ArrayList<>();
        impactoTotal = 0;

        Map<Estacion, Estacion> padre = new HashMap<>();
        for (Estacion e : grafo.getEstaciones()) {
            padre.put(e, e);
        }

        List<Sendero> ordenados = new ArrayList<>(grafo.getSenderos());
        Collections.sort(ordenados);

        for (Sendero s : ordenados) {
            Estacion raiz1 = encontrar(padre, s.getOrigen());
            Estacion raiz2 = encontrar(padre, s.getDestino());

            if (!raiz1.equals(raiz2)) {
                resultado.add(s);
                impactoTotal += s.getImpactoAmbiental();
                padre.put(raiz1, raiz2);
            }
        }
    }

    private Estacion encontrar(Map<Estacion, Estacion> padre, Estacion e) {
        if (padre.get(e).equals(e)) return e;
        Estacion raiz = encontrar(padre, padre.get(e));
        padre.put(e, raiz);
        return raiz;
    }

    public List<Sendero> getResultado() {
        return resultado;
    }

    public int getImpactoTotal() {
        return impactoTotal;
    }
}
