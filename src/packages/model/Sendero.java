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

    @Override
    public String toString() {
        return origen.getNombre() + " -> " + destino.getNombre() + " (impacto: " + impacto + ")";
    }
}
