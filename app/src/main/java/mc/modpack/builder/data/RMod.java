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

import java.util.HashMap;

import mc.modpack.builder.enums.ModType;

public class RMod {

    // CurseForge Project ID
    private int id;

    private String name;
    private ModType type;

    private HashMap<String, ModFile> cacheMap;

    public RMod() {}

    public RMod(int modCurseForgeID, String modCurseForgeName, ModType type, HashMap<String, ModFile> cacheMap) {
        this.id = modCurseForgeID;
        this.name = modCurseForgeName;
        this.type = (type != null) ? type : ModType.UNDEFINED;

        if (cacheMap != null) this.cacheMap = cacheMap;
        else this.cacheMap = new HashMap<String, ModFile>();
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, HashMap<String, ModFile> cacheMap) {
        this(modCurseForgeID, modCurseForgeName, ModType.UNDEFINED, cacheMap);
    }

    public RMod(int modCurseForgeID, String modCurseForgeName, ModType type) {
        this(modCurseForgeID, modCurseForgeName, type, null);
    }

    public RMod(int modCurseForgeID, String modCurseForgeName) {
        this(modCurseForgeID, modCurseForgeName, ModType.UNDEFINED);
    }

    public int getModCurseForgeID() {
        return id;
    }

    public String getModCurseForgeName() {
        return name;
    }

    public ModType getModType() {
        return type;
    }

    public void setType(ModType type) {
        if (type != null) this.type = type;
    }

    // HashMap methods
    public ModFile getModFile(String modVersion) {
        return cacheMap.get(modVersion);
    }

    public boolean addModFile(ModFile modFile) {
        // modFile already present (not replaced) -> false
        // modFile not present (added) -> true
        return (cacheMap.putIfAbsent(modFile.getModVersion(), modFile) == null) ? true : false;
    }

    public boolean removeModFile(ModFile modFile) {
        // modFile did not exist (not removed) -> false
        // modFile did exist (removed) -> true
        return (cacheMap.remove(modFile.getModVersion()) == null) ? false : true;
    }
}
