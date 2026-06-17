/*
 *  Copyright 2026 Mario Aldeondo (@maldeondo)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package mc.modpack.builder.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class that handles the making of HTTP petitions to Curseforge's API servers
 */
public class PetitionMaker {
    /**
     * Common start of all the urls that will be used
     */
    public static String BASE_URL = "https://api.curseforge.com/";


    /**
    * Makes an HTTP petition to the specified API endpoint, and parses and returns the server's response
    *
    * @param url Endpoint of the API to call
    * @param key API key, for optional use when accessing key-locked endpoints
    *
    * @return A {@link PetitionResult PetitionResult} object containing all the information from the petition,
    *         like the HTTP response code and headers, as well as the response's body
    */
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

    /**
    * Handles the download of a specifica version of a mod (the .jar file)
    *
    * @param url The direct url from which to download the mod
    * @param route The path to the directory where the mod will be downloaded
    *
    * @return true if the download process went fine, false otherwise
 */
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
