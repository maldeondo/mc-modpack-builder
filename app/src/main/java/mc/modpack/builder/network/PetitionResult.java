package mc.modpack.builder.network;

import java.net.http.HttpHeaders;

public class PetitionResult {
    private int status;
    private String body;
    private HttpHeaders headers;

    public PetitionResult(int status, String body, HttpHeaders headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public HttpHeaders getHeaders() {
        return  headers;
    }
}
