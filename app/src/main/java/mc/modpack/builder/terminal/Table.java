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

package mc.modpack.builder.terminal;

import mc.modpack.builder.Mod;
import mc.modpack.builder.Modpack;
import mc.modpack.builder.Utils;

public class Table {
    private Modpack modpack;
    private int startingPos;
    private int selected;
    private int height;

    public Table(Modpack modpack, int startingPos, int height) {
        this.modpack = modpack;
        this.startingPos = startingPos;
        this.height = height;
        this.selected = 0;
    }

    public Table(Modpack modpack, int height) {
        this(modpack, 0, height);
    }

    public Table(Modpack modpack) {
        this(modpack, 0, 0);
    }

    public Modpack getModpack() {
        return modpack;
    }

    public int getStartingPos() {
        return startingPos;
    }

    public int getTableHeight() {
        return height;
    }

    public int getPackLength() {
        return modpack.getModNum() - 1;
    }

    public void setModpack(Modpack modpack) {
        this.modpack = modpack;
    }

    public void setTableHeight(int height) {
        int offset = height - this.height;
        int spaceLeftBottom = height - (this.height + getStartingPos());
        this.height = height;

        int spaceTop = offset - spaceLeftBottom;
        if(spaceTop > 0) {
            setStartingPos(getStartingPos() - spaceTop);
        }
    }

    public void setStartingPos(int startingPos) {
        this.startingPos = startingPos;
    }

    /**
     * Gets the mod that corresponds to a row in the table
     * @param position The relative row in the table the mod lives in
     * @return null if position isn't valid, the mod otherwise
     */
    public Mod getRelativePosition(int position) {
        Mod mod = null;

        if((position >= 0) && (position < getTableHeight())) {
            mod = modpack.getMod(startingPos + position);
        }

        return mod;
    }

    public void moveUp() {
        if(selected > 0) {
            selected--;
        }

        if(selected < getStartingPos()) {
            setStartingPos(getStartingPos() - 1);
        }
    }

    public void moveDown() {
        if(selected < (getPackLength())) {
            selected++;
        }

        if(selected > (getStartingPos() + height - 1)) {
            setStartingPos(getStartingPos() + 1);
        }
    }

    public String getLine(int position, String format) throws Exception{
        if(position == (selected - getStartingPos())) {
            format = "\u001B[2m\u001B[1m" + format + "\u001B[0m";
        }

        StringBuilder block = new StringBuilder();
        Mod mod = getRelativePosition(position);

        block.append(String.format(
                format,
                String.valueOf(position + 1 + getStartingPos()),
                mod.getName(),
                mod.getVersion(),
                Utils.clientTypeFormat(mod.getModType()),
                Utils.clientStatusFormat(mod.getModStatus()),
                ""
        ));

        return block.toString();
    }
}
