package mc.modpack.builder.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpHeaders;

/**
 * Class that holds the data relative to an HTTP petition's result.
 * It contains the HTTP status code of the response, the HTTP headers of the response,
 * and a parsed {@link JsonObject JSON} with the body of said response
 */
public class PetitionResult {
    private int status;
    private JsonObject body;
    private HttpHeaders headers;

    public PetitionResult(int status, String body, HttpHeaders headers) {
        this.status = status;
        this.headers = headers;

        this.body = JsonParser.parseString(body).getAsJsonObject();
    }

    public int getStatus() {
        return status;
    }

    public JsonObject getBody() {
        return body;
    }

    public HttpHeaders getHeaders() {
        return  headers;
    }
}
