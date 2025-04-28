package packages;

import java.util.*;

public class Grafo {
	private Map<String, Estacion> estaciones;
    private List<Sendero> senderos;

    public Grafo() {
        estaciones = new HashMap<>();
        senderos = new ArrayList<>();
    }

    public void agregarEstacion(String nombre, double lat, double lon) {
        estaciones.putIfAbsent(nombre, new Estacion(nombre, lat, lon));
    }

    public void agregarSendero(String origen, String destino, int impacto) {
        Estacion a = estaciones.get(origen);
        Estacion b = estaciones.get(destino);
        if (a != null && b != null) {
            senderos.add(new Sendero(a, b, impacto));
        }
    }

    public Estacion getEstacion(String nombre) {
        return estaciones.get(nombre);
    }

    public Collection<Estacion> getEstaciones() {
        return estaciones.values();
    }

    public List<Sendero> getSenderos() {
        return senderos;
    }
}
