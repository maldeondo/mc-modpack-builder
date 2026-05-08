package mc.modpack.builder.network;

import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NetworkManager {
    private final String key;

    public NetworkManager(String key) {
        this.key = key;
    }

    //Gets the mod name using the curseforge ID
    public String getModName(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);

        return result.getBody().get("data").getAsJsonObject().get("name").getAsString();
    }

    public void downloadMod(String modId, String version, String modLoader) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + modId, key);

        JsonArray array = result.getBody().get("data").getAsJsonObject().get("latestFilesIndexes").getAsJsonArray();
        int fileId = getVersion(array, version, modLoader);
        System.out.println(fileId);

        String route = "v1/mods/" + modId + "/files/" + fileId;
        PetitionResult result33 = PetitionMaker.makePetition(route, key);
        JsonObject resultJson =  result33.getBody().get("data").getAsJsonObject();

        String downloadRoute = resultJson.get("downloadUrl").getAsString();
        System.out.println("Downloading from route: " + downloadRoute);
        PetitionMaker.downloadMod(downloadRoute, "./testJar.jar");
        System.out.println("Mod downloaded");
    }

    private int getVersion(JsonArray versions, String version, String modLoader) {
        int versionCount = versions.size();
        int id = Integer.MIN_VALUE;

        for(int i=0; i<versionCount; i++) {
            JsonObject check = versions.get(i).getAsJsonObject();

            try {
                String wantedLoader = check.get("modLoader").getAsString();

                if(wantedLoader != null) {
                    if((check.get("gameVersion").getAsString().equals(version)) && (wantedLoader.equals(modLoader))) {
                        int possibleId = check.get("fileId").getAsInt();

                        if(id < possibleId) {
                            id = possibleId;
                        }
                        /*
                            System.out.println("Version: " + check.get("gameVersion"));
                            System.out.println("File ID: " + check.get("fileId"));
                            System.out.println("Modloader: " + check.get("modLoader"));
                            System.out.println("File Name: " + check.get("filename"));
                            System.out.println();
                         */
                    }
                }
            }
            catch(Exception ex) {

            }
        }

        return id;
    }
}
