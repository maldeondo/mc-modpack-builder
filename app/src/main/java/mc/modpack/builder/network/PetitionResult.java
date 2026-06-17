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
