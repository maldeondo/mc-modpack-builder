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

    public String getModURL(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);

        return result.getBody().get("data").getAsJsonObject().get("links").getAsJsonObject().get("websiteUrl").getAsString();
    }

    public boolean downloadMod(String modId, String version, String modLoader, String filePath) throws IOException, InterruptedException {
        //Get the information by ID
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + modId, key);

        //Get the id of the file that needs to be downloaded from the version and modloader
        JsonArray array = result.getBody().get("data").getAsJsonObject().get("latestFilesIndexes").getAsJsonArray();
        int fileId = getVersion(array, version, modLoader);

        if(fileId == Integer.MIN_VALUE) {
            //Get the info for that file to get the download link
            String route = "v1/mods/" + modId + "/files/" + fileId;
            PetitionResult result33 = PetitionMaker.makePetition(route, key);
            JsonObject resultJson =  result33.getBody().get("data").getAsJsonObject();

            //Downloading the mod
            String downloadRoute = resultJson.get("downloadUrl").getAsString();
            PetitionMaker.downloadMod(downloadRoute, filePath);

            //Showing that everything went fine
            return true;
        }
        else {
            //The mod version or launcher doesn't exist
            return false;
        }
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
