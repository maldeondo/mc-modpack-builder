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

import java.util.ArrayList;

public class RMod {

    // CurseForge Project ID
    private Integer id;

    private String name;
    private String type;
    private ArrayList<ModFile> cacheList;

    public RMod(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type != null) this.type = type;
    }

    public void addCacheEntry(ModFile mod) {
        if (!cacheList.contains(mod)) cacheList.add(mod);
    }
}
