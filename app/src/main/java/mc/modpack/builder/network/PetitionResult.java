package mc.modpack.builder.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpHeaders;

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
