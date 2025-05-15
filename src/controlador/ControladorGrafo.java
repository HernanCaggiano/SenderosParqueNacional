package controlador;

import modelo.*;
import vista.VistaMapa;
import javax.swing.*;
import com.google.gson.*;
import java.io.*;
import java.awt.Color;

public class ControladorGrafo {
    private final Grafo grafo = new Grafo();
    private final VistaMapa vista;

    public ControladorGrafo(VistaMapa v) {
        this.vista = v;
    }

    public void cargarGrafoDesdeJSON() {
 
        grafo.limpiar();
        vista.mostrarGrafoCompleto(new Grafo());

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Seleccionar JSON del grafo");
        if (fc.showOpenDialog(vista) != JFileChooser.APPROVE_OPTION)
            return;
        File f = fc.getSelectedFile();

        try (FileReader reader = new FileReader(f)) {
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(reader).getAsJsonObject();

            // 3) Vuelve a poblar el grafo con los nuevos datos
            for (JsonElement je : root.getAsJsonArray("estaciones")) {
                JsonObject o = je.getAsJsonObject();
                grafo.agregarEstacion(new Estacion(
                    o.get("id").getAsString(),
                    o.get("nombre").getAsString(),
                    o.get("latitud").getAsDouble(),
                    o.get("longitud").getAsDouble()
                ));
            }
            for (JsonElement je : root.getAsJsonArray("senderos")) {
                JsonObject o = je.getAsJsonObject();
                Estacion u = grafo.obtenerEstacion(o.get("idOrigen").getAsString());
                Estacion vtx = grafo.obtenerEstacion(o.get("idDestino").getAsString());
                grafo.agregarSendero(new Sendero(u, vtx, o.get("impacto").getAsInt()));
            }

            // 4) Dibuja el nuevo grafo
            vista.mostrarGrafoCompleto(grafo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error leyendo JSON: " + ex.getMessage());
        }
    }

    public void mostrarPrim() {
        vista.mostrarGrafoCompleto(grafo);
        java.util.List<Sendero> agm = PrimAGM.computarAGM(grafo);
        vista.resaltarAGM(agm, Color.BLACK);
        mostrarDetalle(agm, "Prim");
    }

    public void mostrarKruskal() {
        vista.mostrarGrafoCompleto(grafo);
        java.util.List<Sendero> agm = KruskalAGM.computarAGM(grafo);
        vista.resaltarAGM(agm, Color.BLUE);
        mostrarDetalle(agm, "Kruskal");
    }

    private void mostrarDetalle(java.util.List<Sendero> agm, String n) {
    	vista.setTitle(n);
        int tot = agm.stream().mapToInt(Sendero::getImpacto).sum();
        StringBuilder sb = new StringBuilder(n + " AGM:\n");
        agm.forEach(s -> sb.append(s).append("\n"));
        sb.append("Total impacto: ").append(tot);
        JOptionPane.showMessageDialog(vista, sb.toString(),n, JOptionPane.INFORMATION_MESSAGE);
    }

    public void ejecutar() {
        vista.setControlador(this);
        vista.setVisible(true);
    }
}
			