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

public class FormularioController {

	private static final String API_URL = "http://localhost:8000/formulario/save";

	public void enviarFormulario(FormularioBean formulario) {
		try {
			formatearFechas(formulario);

			// Realizar la solicitud POST y obtener la respuesta
			String respuesta = enviarSolicitud(formulario);

			// Imprimir la respuesta
			System.out.println("Respuesta del servidor:");
			System.out.println(respuesta);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}

	private void formatearFechas(FormularioBean formulario) throws ParseException {
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

		String fechaPensionFormateada = formateador.format(formulario.getFechaAfiliacionPension());
		String fechaSaludFormateada = formateador.format(formulario.getFechaAfiliacionSalud());

		formulario.setFechaAfiliacionPension(formateador.parse(fechaPensionFormateada));
		formulario.setFechaAfiliacionSalud(formateador.parse(fechaSaludFormateada));
	}

	private String enviarSolicitud(FormularioBean formulario) throws IOException {
		URL url = new URL(API_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonInputString = objectMapper.writeValueAsString(formulario);

			try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
				outputStream.writeBytes(jsonInputString);
				outputStream.flush();
			}

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					return response.toString();
				}
			} else {
				throw new IOException("Error en la solicitud: " + responseCode);
			}
		} finally {
			connection.disconnect();
		}
	}
}
