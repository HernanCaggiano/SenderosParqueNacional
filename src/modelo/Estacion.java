package modelo;
public class Estacion {
    private final String id, nombre;
    private final double latitud, longitud;
    public Estacion(String id, String nombre, double latitud, double longitud) {
        this.id = id; this.nombre = nombre;
        this.latitud = latitud; this.longitud = longitud;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    @Override public String toString() { return nombre + " (" + id + ")"; }
}