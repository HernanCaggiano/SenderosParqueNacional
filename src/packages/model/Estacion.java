package packages.model;

import java.util.ArrayList;
import java.util.List;

public class Estacion {
	private String nombre;
    private double latitud;
    private double longitud;
    private List<Sendero> senderos;

    public Estacion(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.senderos = new ArrayList<>();
    }

    public void agregarSendero(Sendero s) {
        senderos.add(s);
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public List<Sendero> getSenderos() {
        return senderos;
    }
}
