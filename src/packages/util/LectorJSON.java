package packages.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import packages.model.Estacion;
import packages.model.Sendero;

public class LectorJSON {
	public static List<Estacion> leerEstacionesDesdeJSON(String ruta) {
		List<Estacion> estaciones = new ArrayList<>();
	    try {
	        InputStream inputStream = LectorJSON.class.getClassLoader().getResourceAsStream(ruta);
	        if (inputStream == null) {
	            throw new FileNotFoundException("Archivo no encontrado en el classpath: " + ruta);
	        }

	        String contenido = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	        JSONObject json = new JSONObject(contenido);
	        JSONArray array = json.getJSONArray("estaciones");

	        for (int i = 0; i < array.length(); i++) {
	            JSONObject obj = array.getJSONObject(i);
	            String nombre = obj.getString("nombre");
	            double latitud = obj.getDouble("latitud");
	            double longitud = obj.getDouble("longitud");
	            estaciones.add(new Estacion(nombre, latitud, longitud));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return estaciones;
    }

    public static List<Sendero> leerSenderosDesdeJSON(String ruta, List<Estacion> estaciones) {
    	List<Sendero> senderos = new ArrayList<>();
        try {
            InputStream inputStream = LectorJSON.class.getClassLoader().getResourceAsStream(ruta);
            if (inputStream == null) {
                throw new FileNotFoundException("Archivo no encontrado en el classpath: " + ruta);
            }

            String contenido = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(contenido);
            JSONArray array = json.getJSONArray("senderos");

            Map<String, Estacion> mapa = estaciones.stream()
                    .collect(Collectors.toMap(Estacion::getNombre, e -> e));

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Estacion origen = mapa.get(obj.getString("origen"));
                Estacion destino = mapa.get(obj.getString("destino"));
                int impacto = obj.getInt("impacto");
                int capacidad = obj.getInt("capacidad");

                if (origen != null && destino != null) {
                    senderos.add(new Sendero(origen, destino, impacto, capacidad));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return senderos;
    }
}
