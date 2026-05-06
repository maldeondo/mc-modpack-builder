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
import java.util.UUID;

public class ModRegistry {
    private HashMap<UUID, Mod> modList;
    private int modNum;

    public ModRegistry() {}

    public int getModNum() {
        return modNum;
    }

    public Mod getModData(UUID uuid) {
        return modList.get(uuid);
    }

    public void setModList(HashMap<UUID, Mod> modList) {
        if (modList != null) this.modList = modList;
    }

    public void addMod(Mod mod) {
        modList.put(mod.getID(), mod);
    }

    public void removeMod(Mod mod) {
        modList.remove(mod.getID());
    }
}
