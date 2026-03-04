/*
*  Copyright 2026 Mario Aldeondo
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

package mc.modpack.builder;

import java.util.ArrayList;

public class Modpack {
    private ArrayList<Mod> modList; // mod list
    private int modNum = 0;

    private String name;
    private String file;
    private Table table;
    
    public Modpack(String name, ArrayList<Mod> modList) {
        if (Utils.validString(name)) {
            this.name = name;
            file = Utils.fileFromName(name);
        }

        this.modList = modList;
        table = new Table();
    }

    public Modpack(String name) { this(name, new ArrayList<Mod>()); }

    public Modpack() { this("modpack"); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, modNum)) throw new IndexOutOfBoundsException();
        else return modList.get(index);
    }

    // GSON

    public String getName() { return name; }
    public String getFile() { return file; }
    public int getModNum() { return modNum; }
    public ArrayList<Mod> getModArray() { return modList; }
    public Table getTable() { return table; }

    public void setName(String name) {
        this.name = name;
        this.file = Utils.fileFromName(name);
    } 
    public void setModNum(int modNum) { this.modNum = modNum; }
    public void setModArray(ArrayList<Mod> modArray) { this.modList = modArray; }
    public void setTable(Table table) { this.table = table; }

    // LOGIC BLOCK

    public void addMod(Mod mod, int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, modNum)) throw new IndexOutOfBoundsException();
        else {
            modList.add(index, mod);
            modNum++;

            table.updateLongest(mod);
        }
    }

    public void addMod(Mod mod) { this.addMod(mod, modNum); }

    public void addModList(ArrayList<Mod> modList) {
        for (Mod mod: modList) 
            addMod(mod);
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

            table.updateLongestRemoved(this);
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
}
