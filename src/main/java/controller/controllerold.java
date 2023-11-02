package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.FormularioBean;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class controllerold {

	public void EnviarFormulario(FormularioBean formulario) {

		try {
			SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

			String fechaPensionFormateada = formateador.format(formulario.getFechaAfiliacionPension());
			String fechaSaludFormateada = formateador.format(formulario.getFechaAfiliacionSalud());

			formulario.setFechaAfiliacionPension(formateador.parse(fechaPensionFormateada));
			formulario.setFechaAfiliacionSalud(formateador.parse(fechaSaludFormateada));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			// URL de la API o página web a la que quieres hacer la solicitud GET
			URL url = new URL("http://localhost:8000/formulario/save");

			// Abrir conexión HTTP
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setDoOutput(true);

			ObjectMapper objectMapper = new ObjectMapper();

			String jsonInputString = objectMapper.writeValueAsString(formulario);

			// Escribir el JSON en el cuerpo de la solicitud
			try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
				outputStream.writeBytes(jsonInputString);
				outputStream.flush();
			}

			// Obtener respuesta
			int responseCode = connection.getResponseCode();

			// Leer la respuesta del servidor
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// Imprimir la respuesta
			System.out.println("Respuesta del servidor:");
			System.out.println(response.toString());

			// Cerrar la conexión
			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}