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

import mc.modpack.builder.Utils;
import mc.modpack.builder.enums.ModLoader;

public class ModFile {

    // Autocontained Key
    private String fileName;

    private String mcVersion;

    private ModLoader modLoader;

    public ModFile(String fileName, String mcVersion, ModLoader modLoader) {
        this.fileName = Utils.addJarExtension(fileName);
        this.mcVersion = mcVersion;
        this.modLoader = (modLoader.valid()) ? modLoader : ModLoader.UNKNOWN;
    }

    public ModFile(String fileName, String mcVersion, int modLoaderCurseForgeID) {
        this(fileName, mcVersion, ModLoader.fromCurseForgeID(modLoaderCurseForgeID));
    }

    public ModFile(String fileName, String mcVersion, String modLoaderCurseForgeID) {
        this(fileName, mcVersion, ModLoader.fromCurseForgeID(modLoaderCurseForgeID));
    }

    public ModFile(String fileName, String mcVersion) {
        this(fileName, mcVersion, ModLoader.UNKNOWN);
    }

    // GETTERS

    public String getFileName() {
        return fileName;
    }

    public String getMCVersion() {
        return mcVersion;
    }

    public String getPath() {
        return Utils.MOD_DIR + fileName;
    }

    public ModLoader getModLoader() {
        return modLoader;
    }

    // SETTERS

    public boolean setModLoader(ModLoader modLoader) {
        if (modLoader.valid()) {
            this.modLoader = modLoader;
            return true;
        } else return false;
    }

    public boolean setFileName(String fileName) {
        String newFileName = Utils.addJarExtension(fileName);

        if (newFileName != null) {
            this.fileName = newFileName;
            return true;
        } else return false;
    }

    public boolean setMCVersion(String mcVersion) {
        if (Utils.validString(mcVersion)) {
            this.mcVersion = mcVersion;
            return true;
        } else return false;
    }

}
