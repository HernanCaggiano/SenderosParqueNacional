package packages;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GeneradorArchivoSenderos {
	 public static void main(String[] args) {
	        String rutaArchivo = "senderos.txt";

	        try (FileOutputStream fos = new FileOutputStream(rutaArchivo);
	             OutputStreamWriter osw = new OutputStreamWriter(fos);
	             BufferedWriter bw = new BufferedWriter(osw)) {

	            // Estaciones
	            bw.write("E EstacionA -34.6100 -58.3800\n");
	            bw.write("E EstacionB -34.6120 -58.3815\n");
	            bw.write("E EstacionC -34.6135 -58.3840\n");
	            bw.write("E EstacionD -34.6150 -58.3860\n");
	            bw.newLine();

	            // Senderos
	            bw.write("S EstacionA EstacionB 2\n");
	            bw.write("S EstacionB EstacionC 5\n");
	            bw.write("S EstacionC EstacionD 8\n");
	            bw.write("S EstacionA EstacionC 4\n");

	            bw.flush();
	            System.out.println("Archivo generado correctamente: " + rutaArchivo);

	        } catch (IOException e) {
	            System.out.println("Error al crear el archivo: " + e.getMessage());
	        }
	    }
}
