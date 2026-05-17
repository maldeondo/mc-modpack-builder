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

public class ModRegistry {
    private HashMap<Integer, RMod> modMap;

    public ModRegistry() {}

    public int getModNum() {
        return modMap.size();
    }

    public RMod getModData(int id) {
        return modMap.get(id);
    }

    public void setModMap(HashMap<Integer, RMod> modList) {
        if (modList != null) this.modMap = modList;
    }

    public boolean addMod(RMod rMod) {
        // rMod already present (not replaced) -> false
        // rMod not present (added) -> true
        return (modMap.putIfAbsent(rMod.getModCurseForgeID(), rMod) == null) ? true : false;
    }

    public boolean removeMod(RMod rMod) {
        // rMod did not exist (not removed) -> false
        // rMod did exist (removed) -> true
        return (modMap.remove(rMod.getModCurseForgeID()) == null) ? false : true;
    }
}
