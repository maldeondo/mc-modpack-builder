package mc.modpack.builder.network;

import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NetworkManager {
    private final String key;

    public NetworkManager(String key) {
        this.key = key;
    }

    //Gets the mod name from the curseforge ID
    public String getModName(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);

        JsonObject obj = JsonParser.parseString(result.getBody()).getAsJsonObject();
        return obj.get("data").getAsJsonObject().get("name").getAsString();
    }
}
