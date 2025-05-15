package modelo;
public class Sendero implements Comparable<Sendero> {
    private final Estacion origen, destino;
    private final int impacto;
    public Sendero(Estacion origen, Estacion destino, int impacto) {
        this.origen = origen; this.destino = destino; this.impacto = impacto;
    }
    public Estacion getOrigen() { return origen; }
    public Estacion getDestino() { return destino; }
    public int getImpacto() { return impacto; }
    @Override public int compareTo(Sendero o) { return Integer.compare(this.impacto, o.impacto); }
    @Override public String toString() {
        return origen.getId() + "-" + destino.getId() + " (impacto=" + impacto + ")";
    }
}