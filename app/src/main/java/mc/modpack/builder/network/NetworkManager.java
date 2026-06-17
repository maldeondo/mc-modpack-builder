package mc.modpack.builder.network;

import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mc.modpack.builder.enums.ModLoader;


public class NetworkManager {
    private final String key;

    /**
    * Initialises a networkManager object, with an optional key
    * Not introducing the key will result in basically no API functionality,
    * as most endpoints are key-locked
    *
    * @param key the API key of the user
    */
    public NetworkManager(String key) {
        this.key = key;
    }

    /**
    * Returns the raw info Curseforge has from the mod based on its mod ID
    *
    * @param uid unique ID of the mod in Curseforge's servers
    *
    * @return a parsed {@link JsonObject JSON} with all the information
    */
    public JsonObject getRawInfo(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);
        return result.getBody();
    }

    /**
    * Retrieves the mod's name based on its Curseforge ID.
    * It makes API calls
    *
    * @param uid unique ID of the mod in Curseforge's servers
    *
    * @return the mod's name
    */
    public String getModName(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);

        return getModName(result.getBody());
    }

    /**
    * Retrieves the mod's name based on a JSON object previously returned by Curseforge's API
    *
    * @param info the parsed {@link JsonObject JSON} returned by Curseforge
    *
    * @return the mod's name
    */
    public String getModName(JsonObject info) {
        return info.get("data").getAsJsonObject().get("name").getAsString();
    }

    /**
    * Retrieves the mod's URL in Curseforge's frontend based on its Curseforge ID
    * It makes API calls
    *
    * @param uid unique ID of the mod in Curseforge's servers
    *
    * @return the url, contained in a String
    */
    public String getModURL(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);

        return getModURL(result.getBody());
    }

    /**
    * Retrieves the mod's URL in Curseforge's frontend based on a JSON object previously returned by Curseforge's API
    *
    * @param info the parsed {@link JsonObject JSON} returned by Curseforge
    *
    * @return the url, contained in a String
    */
    public String getModURL(JsonObject info) {
        return info.get("data").getAsJsonObject().get("links").getAsJsonObject().get("websiteUrl").getAsString();
    }

    /**
    * Figures out which of all the mod files to download based on the combination of mod, version and modloader,
    * and downloads it into the desired path
    *
    * @param modId Curseforge's unique ID of the mod
    * @param version Minecraft version that will be used
    * @param modLoader Modloader used in the modpack
    * @param filePath path to download the file into
    *
    * @return the downloaded file's name, or an empty String if something went wrong
    */
    public String downloadMod(String modId, String version, ModLoader modLoader, String filePath) throws IOException, InterruptedException {
        //Get the information by ID
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + modId, key);

        //Get the id of the file that needs to be downloaded from the version and modloader
        JsonArray array = result.getBody().get("data").getAsJsonObject().get("latestFilesIndexes").getAsJsonArray();
        int fileId = getVersion(array, version, modLoader.getCurseForgeID());

        if(fileId != Integer.MIN_VALUE) {
            //Get the info for that file to get the download link
            String route = "v1/mods/" + modId + "/files/" + fileId;
            PetitionResult result33 = PetitionMaker.makePetition(route, key);
            JsonObject resultJson =  result33.getBody().get("data").getAsJsonObject();

            //Downloading the mod
            String fileName = resultJson.get("fileName").getAsString();
            String downloadRoute = resultJson.get("downloadUrl").getAsString();
            PetitionMaker.downloadMod(downloadRoute, filePath + "/" + fileName);

            //Showing that everything went fine
            return fileName;
        }
        else {
            //The mod version or launcher doesn't exist
            return "";
        }
    }

    public LinkedList<ModVersions> getAvaiableVersions(String uid) throws IOException, InterruptedException {
        PetitionResult result = PetitionMaker.makePetition("v1/mods/" + uid, key);

        return getAvaiableVersions(result.getBody());
    }

    public LinkedList<ModVersions> getAvaiableVersions(JsonObject info)  {
        LinkedList<ModVersions> result = new LinkedList<>();
        JsonArray versions = info.get("data").getAsJsonObject().get("latestFilesIndexes").getAsJsonArray();

        for(int i=0; i<versions.size(); i++) {
            JsonObject file = versions.get(i).getAsJsonObject();

            int loader = -1;
            String version = file.get("gameVersion").getAsString();

            try {
                loader = file.get("modLoader").getAsInt();
            }
            catch(Exception ex) { }
            finally {
                ModVersions toAdd = new ModVersions(loader, version);
                result.add(toAdd);
            }
        }

        return result;
    }

    /**
    * Get the file ID to be downloaded using the unique combination of version and ModLoader.
    * If there are several possible files, it will take the latest one
    *
    * @param versions an {@link JsonArray array} of all the mod's possible versions that Curseforge can serve
    * @param version the Minecraft version used in the modpack
    * @param modLoader the modloader used in the modpack
    *
    * @return an integer representing the file's ID in Curseforge's servers, or {@link Integer#MIN_VALUE MIN_VALUE} if the desired combination can't be found
    */
    private int getVersion(JsonArray versions, String version, int modLoader) {
        int versionCount = versions.size();
        int id = Integer.MIN_VALUE;

        for(int i=0; i<versionCount; i++) {
            JsonObject check = versions.get(i).getAsJsonObject();

            try {
                String wantedLoader = check.get("modLoader").getAsString();

                if(wantedLoader != null) {
                    if((check.get("gameVersion").getAsString().equals(version)) && (wantedLoader.equals(Integer.toString(modLoader)))) {
                        int possibleId = check.get("fileId").getAsInt();

                        if(id < possibleId) {
                            id = possibleId;
                        }
                    }
                }
            }
            catch(Exception ex) {

            }
        }

        return id;
    }
}
