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
import java.util.UUID;

import mc.modpack.builder.Utils;

public class ModPack {
    private int[] longestChars = {Utils.MINIMUM_NAME_LENGHT, Utils.MINIMUM_VERSION_LENGHT};
    private ArrayList<Mod> modList; // mod list
    private ArrayList<Object> selectedModList;
    private int modNum = 0;

    private String name;
    private String file;

    public ModPack(String name, ArrayList<Mod> modList) {
        if (Utils.validString(name)) {
            this.name = name;
            file = Utils.fileFromName(name);
        }

        this.modList = modList;
        this.selectedModList = new ArrayList<Object>();
        selectedModList.add(true);
        selectedModList.add(false);
        System.out.println(UUID.randomUUID());
    }

    public ModPack(String name) { this(name, new ArrayList<Mod>()); }

    public ModPack() { this("modpack"); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (Utils.validIndex(index, modNum)) return modList.get(index);
        else throw new IndexOutOfBoundsException();
    }

    // GSON

    public String getName() { return name; }
    public String getFile() { return file; }
    public int getModNum() { return modNum; }
    public ArrayList<Mod> getModArray() { return modList; }

    public void setName(String name) {
        this.name = name;
        this.file = Utils.fileFromName(name);
    }
    public void setModNum(int modNum) { this.modNum = modNum; }
    public void setModArray(ArrayList<Mod> modArray) { this.modList = modArray; }

    // LOGIC BLOCK

    public void addMod(Mod mod, int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, modNum)) throw new IndexOutOfBoundsException();
        else {
            modList.add(index, mod);
            modNum++;

            updateLongestField(mod.getName(), Utils.LONGEST_NAME_INDEX, Utils.MINIMUM_NAME_LENGHT);
            updateLongestField(mod.getVersion(), Utils.LONGEST_VERSION_INDEX, Utils.MINIMUM_VERSION_LENGHT);
        }
    }

    public void addMod(Mod mod) { this.addMod(mod, modNum); }

    public void addModList(ArrayList<Mod> modList) {
        for (Mod mod: modList) addMod(mod);
    }

    public void replaceModList(ArrayList<Mod> modList) {
        this.modList = new ArrayList<Mod>();

        modNum = 0;
        addModList(modList);
    }

    public void removeMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, modNum)) throw new IndexOutOfBoundsException();
        else {
            modList.remove(index);
            modNum--;

            updateLongestRemoved(this);
        }
    }

    public void removeMod(String name) throws IndexOutOfBoundsException {
        if (!Utils.validString(name)) throw new IndexOutOfBoundsException();
        else for (int target = 0; target < modNum; target++) {
            if (modList.get(target).getName() == name) {
                removeMod(target);
                break;
            }
        }
    }

    private void updateLongestField(String data, int field, int minimum) {
        int chars = data.length();

        if (chars > minimum && chars > longestChars[field]) longestChars[field] = chars;
    }

    public void updateLongestRemoved(ModPack modpack) {
        resetLongestChars();

        for (int i = 0; i < modpack.getModNum(); i++) updateLongest(modpack.getMod(i));
    }

    public void resetLongestChars() {
        longestChars[Utils.LONGEST_NAME_INDEX] = Utils.MINIMUM_NAME_LENGHT;
        longestChars[Utils.LONGEST_VERSION_INDEX] = Utils.MINIMUM_VERSION_LENGHT;
    }

    public void updateLongest(Mod mod) {
        updateLongestField(mod.getName(), Utils.LONGEST_NAME_INDEX, Utils.MINIMUM_NAME_LENGHT);
        updateLongestField(mod.getVersion(), Utils.LONGEST_VERSION_INDEX, Utils.MINIMUM_VERSION_LENGHT);
    }

    public int[] getLongestChars() {
        return longestChars;
    }
}
