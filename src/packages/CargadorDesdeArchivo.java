package packages;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CargadorDesdeArchivo {
	public static void cargar(String rutaArchivo, Grafo grafo) throws FileNotFoundException {
        File archivo = new File(rutaArchivo);
        Scanner scanner = new Scanner(archivo);

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine().trim();
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue; // Saltar comentarios o líneas vacías
            }
            String[] partes = linea.split(" ");
            if (partes[0].equals("E") && partes.length == 4) {
                String nombre = partes[1];
                double latitud = Double.parseDouble(partes[2]);
                double longitud = Double.parseDouble(partes[3]);
                grafo.agregarEstacion(nombre, latitud, longitud);
            } else if (partes[0].equals("S") && partes.length == 4) {
                String origen = partes[1];
                String destino = partes[2];
                int impacto = Integer.parseInt(partes[3]);
                grafo.agregarSendero(origen, destino, impacto);
            }
        }
        scanner.close();
    }

}
