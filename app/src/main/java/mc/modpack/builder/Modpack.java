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

public class Modpack {
    private final int MAX_MODS;
    private int currentMods = 0;
    private Mod[] modArray; // mod array

    private String name;
    private String file;
    private Table table;
    
    public Modpack(String name, int maxMods) {
        this.MAX_MODS = maxMods;
        if (Utils.validString(name)) {
            this.name = name;
            file = Utils.fileFromName(name);
        }
        modArray = new Mod[MAX_MODS];
        table = new Table();
    }

    public Modpack(String name) { this(name, 128); }

    public Modpack() { this("modpack"); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, currentMods)) throw new IndexOutOfBoundsException();
        else return modArray[index];
    }

    // GSON

    public String getName() { return name; }
    public String getFile() { return file; }
    public int getModNum() { return currentMods; }
    public Mod[] getModArray() { return modArray; }
    public Table getTable() { return table; }

    public void setName(String name) {
        this.name = name;
        this.file = Utils.fileFromName(name);
    } 
    public void setModNum(int currentMods) { this.currentMods = currentMods; }
    public void setModArray(Mod[] modArray) { this.modArray = modArray; }
    public void setTable(Table table) { this.table = table; }

    // LOGIC BLOCK

    public boolean full() { return currentMods == MAX_MODS; }

    public void addMod(Mod mod, int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, currentMods) || full()) throw new IndexOutOfBoundsException();
        else {
            
            for (int i = currentMods - 1; i > index; i--) {
                modArray[i] = modArray[i - 1]; 
            }

            modArray[index] = mod;
            currentMods++;

            table.updateLongest(mod);
        }
    }

    public void addMod(Mod mod) { this.addMod(mod, currentMods); }

    public void removeMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, currentMods)) throw new IndexOutOfBoundsException();
        else {

            for (int i = index; i < currentMods - 1; i++) {
                modArray[index] = modArray[index + 1];
            }

            currentMods--;
            table.updateLongestRemoved(this);
        }
    }

    public void removeMod(String name) throws IndexOutOfBoundsException {
        int target;

        if (!Utils.validString(name)) throw new IndexOutOfBoundsException();
        else for (target = 0; target < currentMods; target++) {
            if (modArray[target].getName() == name) {
                removeMod(target);
                break;
            }
        }
    }

    public void printModpack() throws Exception { System.out.print(table.printTable(this)); }
}
