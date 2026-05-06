package mc.modpack.builder.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class PetitionMaker {
    public static String BASE_URL = "https://api.curseforge.com/";

    public void makePetition(String url, String key) {
        //Creating the client that will make the petition
        HttpClient client = HttpClient.newHttpClient();

        //Defining the petition
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + url))
                .header("Content-Type", "application/json")
                .header("x-api-key", key)
                .GET()
                .build();
    }
}
