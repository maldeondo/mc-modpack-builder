package mc.modpack.builder.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PetitionMaker {
    public static String BASE_URL = "https://api.curseforge.com/";

    public static PetitionResult makePetition(String url, String key) throws IOException, InterruptedException {
        //Creating the client that will make the petition
        HttpClient client = HttpClient.newHttpClient();

        //Defining the petition
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + url))
                .header("Content-Type", "application/json")
                .header("x-api-key", key)
                .GET()
                .build();

        //Making the petition
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Building the result
        return new PetitionResult(response.statusCode(), response.body(), response.headers());
    }

    public static boolean downloadMod(String url, String route) {
        //Define the path for the file
        Path target = Paths.get(route);

        //Define the client
        HttpClient client = HttpClient.newHttpClient();

        //Define the request
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        //Try and get the file
        try {
            HttpResponse<Path> response = client.send(req, HttpResponse.BodyHandlers.ofFile(target));
            System.out.println(response.statusCode());
            return response.statusCode() == 200;
        }
        catch (IOException | InterruptedException e) {
            return false;
        }
    }
}
