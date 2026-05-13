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
    ANY((byte) 0),
    FORGE((byte) 1),
    CAULDRON((byte) 2),
    LITELOADER((byte) 3),
    FABRIC((byte) 4),
    QUILT((byte) 5),
    NEOFORGE((byte) 6),
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
            case 0 -> ANY;
            case 1 -> FORGE;
            case 2 -> CAULDRON;
            case 3 -> LITELOADER;
            case 4 -> FABRIC;
            case 5 -> QUILT;
            case 6 -> NEOFORGE;
            default -> UNKNOWN;
        };
    }

    public static ModLoader fromCurseForgeID(String curseForgeID) {
        return fromCurseForgeID(Integer.parseInt(curseForgeID));
    }
}
