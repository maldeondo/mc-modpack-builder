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

package mc.modpack.builder.enums;

public enum ModLoader {
    FORGE((byte) 0),
    NEOFORGE((byte) 1),
    FABRIC((byte) 2),
    QUILT((byte) 3),
    UNKNOWN((byte) -1);

    private final byte curseForgeID;

    ModLoader(byte curseForgeID) {
        this.curseForgeID = curseForgeID;
    }

    public int getCurseForgeID() {
        return (int) curseForgeID;
    }

    public boolean valid() {
        return (this != null && this != UNKNOWN);
    }

    public static ModLoader fromCurseForgeID(int curseForgeID) {
        return switch (curseForgeID) {
            case 0 -> ModLoader.FORGE;
            case 1 -> ModLoader.NEOFORGE;
            case 2 -> ModLoader.FABRIC;
            case 3 -> ModLoader.QUILT;
            default -> ModLoader.UNKNOWN;
        };
    }
}
