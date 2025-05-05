package packages.model;

import java.util.*;

public class Grafo {
	private Map<String, Estacion> estaciones;
    private List<Sendero> senderos;

    public Grafo(List<Estacion> estacionesList) {
        estaciones = new HashMap<>();
        for (Estacion e : estacionesList) {
            estaciones.put(e.getNombre(), e);
        }
        senderos = new ArrayList<>();
    }
    
    public Grafo() {
    	this.estaciones = new HashMap<>();
        this.senderos = new ArrayList<>();
    }

    public void construirDesdeEstaciones(List<Estacion> estacionesList) {
        for (Estacion e : estacionesList) {
            for (Sendero s : e.getSenderos()) {
                senderos.add(s);
            }
        }
    }
    
    public void agregarEstacion(Estacion estacion) {
    	estaciones.put(estacion.getNombre(), estacion);
    }
    
    public void agregarSendero(Sendero sendero) {
        senderos.add(sendero);
        sendero.getOrigen().agregarSendero(sendero);
    }

    public List<Sendero> getSenderos() {
        return senderos;
    }

    public Estacion getEstacion(String nombre) {
        return estaciones.get(nombre);
    }

    public Collection<Estacion> getEstaciones() {
        return estaciones.values();
    }

    public Map<String, Estacion> getMapaEstaciones() {
        return estaciones;
    }
}
