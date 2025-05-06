package packages.model;

public class Sendero {
	private Estacion origen;
    private Estacion destino;
    private int impacto;
    private int capacidad;

    public Sendero(Estacion origen, Estacion destino, int impacto, int capacidad) {
        this.origen = origen;
        this.destino = destino;
        this.impacto = impacto;
        this.capacidad = capacidad;
    }

    public Estacion getOrigen() {
        return origen;
    }

    public Estacion getDestino() {
        return destino;
    }

    public int getImpacto() {
        return impacto;
    }

    public int getCapacidad() {
        return capacidad;
    }
    
    public double getDistanciaGeografica() {
        double lat1 = origen.getLatitud();
        double lon1 = origen.getLongitud();
        double lat2 = destino.getLatitud();
        double lon2 = destino.getLongitud();
        double radio = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radio * c;
    }

    @Override
    public String toString() {
        return origen.getNombre() + " -> " + destino.getNombre() + " (impacto: " + impacto + ")";
    }
}
