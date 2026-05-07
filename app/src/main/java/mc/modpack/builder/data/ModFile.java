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
    private String modVersion;
    private String mcVersion;

    private String fileName;
    private ModLoader modLoader;

    public ModFile(String mcVersion, String modVersion, String fileName, ModLoader modLoader) {
        this.mcVersion = mcVersion;
        this.modVersion = modVersion;
        this.fileName = fileName;
        this.modLoader = (modLoader != null) ? modLoader : ModLoader.UNKNOWN;
    }

    public ModFile(String mcVersion, String modVersion, String fileName, int modLoaderCurseForgeID) {
        this(mcVersion, modVersion, fileName, ModLoader.fromCurseForgeID(modLoaderCurseForgeID));
    }

    public ModFile(String mcVersion, String modVersion, String fileName) {
        this(mcVersion, modVersion, fileName, ModLoader.UNKNOWN);
    }

    public String getMCVersion() {
        return mcVersion;
    }

    public String getModVersion() {
        return modVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return Utils.MOD_DIR + fileName;
    }

    public ModLoader getModLoader() {
        return modLoader;
    }

    public void setModLoader(ModLoader modLoader) {
        if (modLoader.valid()) this.modLoader = modLoader;
    }

    public void setFileName(String fileName) {
        this.fileName = Utils.addJarExtension(fileName);
    }

}
