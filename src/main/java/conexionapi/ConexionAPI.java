package conexionapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {

    public String conexionAPI(String url) {
        HttpClient client = HttpClient.newBuilder()
                // NORMAL para seguir redirecciones 301 y 302
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "\nError: Código de estado: " + response.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();  // Log de la excepción para facilitar el rastreo
            return "\n¡Error en la solicitud a la API! " + e.getMessage();
        }
    }
}
