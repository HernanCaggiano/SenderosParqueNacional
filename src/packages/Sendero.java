package packages;

public class Sendero implements Comparable<Sendero> {
	private Estacion origen;
    private Estacion destino;
    private int impactoAmbiental;

    public Sendero(Estacion origen, Estacion destino, int impacto) {
        this.origen = origen;
        this.destino = destino;
        this.impactoAmbiental = impacto;
    }

    public Estacion getOrigen() { return origen; }
    public Estacion getDestino() { return destino; }
    public int getImpactoAmbiental() { return impactoAmbiental; }

    @Override
    public int compareTo(Sendero o) {
        return Integer.compare(this.impactoAmbiental, o.impactoAmbiental);
    }

    @Override
    public String toString() {
        return origen + " <--> " + destino + " (Impacto: " + impactoAmbiental + ")";
    }
}
