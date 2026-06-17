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

package mc.modpack.builder.data;

import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;

import mc.modpack.builder.Utils;
import mc.modpack.builder.enums.ModLoader;
import mc.modpack.builder.enums.ModType;
import mc.modpack.builder.network.NetworkManager;

public class RMod {
    // CurseForge Project ID
    private int id;

    private String name;
    private ModType type;

    private String modURL;
    private String slug;

    private HashMap<String, ModFile> cacheMap;

    public RMod() {
        this.cacheMap = new HashMap<String, ModFile>();
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, ModType type, HashMap<String, ModFile> cacheMap, String url) {
        this.id = modCurseForgeID;
        this.name = modCurseForgeName;
        this.type = (type != null) ? type : ModType.UNDEFINED;
        this.slug = Utils.removeLastSlash(url).substring(url.lastIndexOf("/"));

        if (cacheMap != null) this.cacheMap = cacheMap;
        else this.cacheMap = new HashMap<String, ModFile>();
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, HashMap<String, ModFile> cacheMap, String url) {
        this(modCurseForgeID, modCurseForgeName, ModType.UNDEFINED, cacheMap, url);
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, HashMap<String, ModFile> cacheMap) {
        this(modCurseForgeID, modCurseForgeName, ModType.UNDEFINED, cacheMap, "");
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, ModType type, String url) {
        this(modCurseForgeID, modCurseForgeName, type, null, url);
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, ModType type) {
        this(modCurseForgeID, modCurseForgeName, type, null, "");
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, String url) {
        this(modCurseForgeID, modCurseForgeName, ModType.UNDEFINED, url);
    }
    
    public RMod(int modCurseForgeID, String modCurseForgeName){
        this(modCurseForgeID, modCurseForgeName, "");
    }

    // GETTERS

    public int getModCurseForgeID() {
        return id;
    }

    public String getModCurseForgeName() {
        return name;
    }

    public ModType getModType() {
        return type;
    }

    public String getSlug(){
        return slug;
    }
      
    public String getModURL() {
        return modURL;
    }

    // SETTERS

    public void setModCurseForgeID(int id) {
        this.id = id;
    }

    public boolean setModCurseForgeName(String name) {
        if (Utils.validString(name)) {
            this.name = name;
            return true;
        } else return false;
    }

    public boolean setModType(ModType type) {
        if (type.valid()) {
            this.type = type;
            return true;
        } else return false;
    }

    public boolean setSlug(String slug){
        if(Utils.validString(slug)){
            this.slug = slug;
            return true;
        }else return false;
    }
      
    public void setModURL(String newURL) {
        this.modURL = newURL;
    }

    // HASHMAP METHODS
    public ModFile getModFile(String modVersion) {
        return cacheMap.get(modVersion);
    }

    public boolean addModFile(ModFile modFile) {
        // modFile already present (not replaced) -> false
        // modFile not present (added) -> true
        return (cacheMap.putIfAbsent(modFile.getFileName(), modFile) == null) ? true : false;
    }

    public boolean removeModFile(ModFile modFile) {
        // modFile did not exist (not removed) -> false
        // modFile did exist (removed) -> true
        return (cacheMap.remove(modFile.getFileName()) == null) ? false : true;
    }


    //API integration

    /**
    * Retrieves and sets mod name and url from the mod id
    * Mod id must be set before calling this function, otherwise, it will fail
    *
    * @param manager NetworkManager object used to handle the download
    * @return true if the information could be retrieved, false otherwise
    */
    public boolean refreshModInfo(NetworkManager manager) {
        try {
            String modId = Integer.toString(id);

            //Retrieving the info
            String name = manager.getModName(modId);
            String url = manager.getModURL(modId);

            //Setting the name
            setModCurseForgeName(name);
            setModURL(url);

            //Everything went fine
            return true;
        }
        catch(Exception ex) {
            //Error while retrieving the info
            return false;
        }
    }

    /**
    * Downloads a specific version of the mod into the /files directory and adds it to the cache
    *
    * @param manager NetworkManager object used to handle the download
    * @param version String representing the Minecraft version for which the mod should be downloaded
    * @param loader ModLoader object representing the mod loader that will be used in the modpack
    *
    * @return true if the file existed and could be downloaded, false otherwise
    */
    public boolean downloadVersion(NetworkManager manager, String version, ModLoader loader) {
        try {
            //Trying to download the file
            String fileName = manager.downloadMod(Integer.toString(id), version, loader, "./files");

            if(fileName.isEmpty()) {
                //A handled error ocurred, and the file wasnt downloaded
                return false;
            }
            else {
                //File was downloaded correctly. Creating ModFile class
                ModFile file = new ModFile(fileName, version, loader);
                addModFile(file);

                //Operation finaled successfully
                return true;
            }
        }
        catch(IOException | InterruptedException ex) {
            //Error while downloading
            return false;
        }
    }
}
