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

import javax.naming.NamingException;

import mc.modpack.builder.misc.Utils;

public class ModPack {
    private String name;
    private ArrayList<PMod> modList; // mod list

    public ModPack(String name, ArrayList<PMod> modList) throws NamingException {
        if (Utils.validString(name)) {
            this.name = name;
        } else throw new NamingException();

        this.modList = modList;
    }

    public ModPack(String name) throws NamingException { 
        this(name, new ArrayList<PMod>()); 
    }

    // GETTERS

    public PMod getMod(int index) throws IndexOutOfBoundsException {
        if (Utils.validIndex(index, getModNum())) return modList.get(index);
        else throw new IndexOutOfBoundsException();
    }

    public int getModNum() {
        return modList.size();
    }

    // GSON

    public String getName() { return name; }
    public ArrayList<PMod> getModArray() { return modList; }

    public void setName(String name) throws NamingException {
        if (Utils.validString(name)) {
            this.name = name;
        } else throw new NamingException();
    }
    public void setModArray(ArrayList<PMod> modList) { this.modList = modList; }

    // LOGIC BLOCK

    public void addMod(PMod mod, int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, getModNum())) throw new IndexOutOfBoundsException();
        else {
            modList.add(index, mod);

            updateLongestField(mod.getName(), Utils.LONGEST_NAME_INDEX, Utils.MINIMUM_NAME_LENGHT);
            updateLongestField(mod.getVersion(), Utils.LONGEST_VERSION_INDEX, Utils.MINIMUM_VERSION_LENGHT);
        }
    }

    public void addMod(PMod mod) { this.addMod(mod, getModNum()); }

    public void addModList(ArrayList<PMod> modList) {
        for (PMod mod: modList) addMod(mod);
    }

    public void removeMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, getModNum())) throw new IndexOutOfBoundsException();
        else {
            modList.remove(index);

            updateLongestRemoved(this);
        }
    }

    public void removeMod(String name) throws NamingException, IndexOutOfBoundsException {
        if (!Utils.validString(name)) throw new NamingException();
        else for (PMod mod : modList) {
            if (mod.getModFile().getRMod().getModCurseForgeName() == name) {
                removeMod();
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
