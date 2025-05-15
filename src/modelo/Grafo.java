package modelo;

import java.util.*;

public class Grafo {
	private final Map<String, Estacion> estaciones = new HashMap<>();
	private final List<Sendero> senderos = new ArrayList<>();
	private final Map<String, List<Sendero>> ady = new HashMap<>();

	public void agregarEstacion(Estacion e) {
		estaciones.put(e.getId(), e);
		ady.putIfAbsent(e.getId(), new ArrayList<>());
	}

	public void agregarSendero(Sendero s) {
		senderos.add(s);
		ady.get(s.getOrigen().getId()).add(s);
		ady.get(s.getDestino().getId()).add(s);
	}

	public Estacion obtenerEstacion(String id) {
		return estaciones.get(id);
	}

	public Collection<Estacion> obtenerEstaciones() {
		return estaciones.values();
	}

	public List<Sendero> obtenerSenderos() {
		return senderos;
	}

	public List<Sendero> obtenerAdyacentes(String id) {
		return ady.getOrDefault(id, Collections.emptyList());
	}

	public void limpiar() {
		estaciones.clear();
		senderos.clear();
		ady.clear();
	}
}