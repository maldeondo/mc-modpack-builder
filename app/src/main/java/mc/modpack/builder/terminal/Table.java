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

import mc.modpack.builder.Utils;
import mc.modpack.builder.data.Mod;
import mc.modpack.builder.data.Modpack;

public class Table {
    private Modpack modpack;
    private int startingPos;
    private int endingPos;
    private int height;
    
    private int selected;

    public Table(Modpack modpack, int height) {
        this.modpack = modpack;
        this.height = height;

        this.startingPos = 0;
        this.selected = 0;
        this.endingPos = height;
    }

    public Table(Modpack modpack) {
        this(modpack, modpack.getModNum() - 1);
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

    public void setModpack(Modpack modpack) {
        this.modpack = modpack;
    }

    public void moveUp() {
        if (selected > 0) selected--;
        scroll();
    }

    public void moveDown() {
        if (selected < modpack.getModNum() - 1) selected++;
        scroll();
    }

    private void scroll() {
        if (selected > endingPos) {
            startingPos++;
            endingPos++;

        } else if (selected < startingPos) {
            startingPos--;
            endingPos--;
        }
    }

    public void resize(int newHeight) {
        // newHeight > height -> make table larger
        // newHeight < height -> make table smaller

        int difference = Math.abs(newHeight - height);

        if (newHeight > height) scaleUp(difference);
        else scaleDown(difference);

        setHeight(newHeight);
    }

    private void setHeight(int newHeight) {
        this.height = newHeight;
    }

    private void scaleUp(int difference) {
        while ((startingPos > 0) && (difference > 0)) {
            startingPos--;
            difference--;
        }

        for (; difference > 0; difference--) endingPos++;
    }

    private void scaleDown(int difference) {
        while ((endingPos > selected) && (difference > 0)) {
            endingPos--;
            difference--;
        }

        for (; difference > 0; difference--) startingPos++;
    }

    public String getFullTable() throws Exception {
        StringBuilder block = new StringBuilder();
        String dynamicTableFormat = Utils.tableFormat(modpack.getLongestChars());
        String dynamicTableSeparator = Utils.tableSeparator(modpack.getLongestChars());

        block.append(getHeader(dynamicTableFormat));
        block.append(dynamicTableSeparator);

        block.append(getBlock(dynamicTableFormat));
        
        return block.toString();
    }

    private String getHeader(String format) {
        return String.format(
            format,
            "",
            "Mod",
            "Version",
            "Type",
            "Status",
            ""
        );
    }

    private String getBlock(String format) throws Exception {
        StringBuilder block = new StringBuilder();

        for (int i = startingPos; i < selected; i++) {
            block.append(getLine(i, format));    
        }

        block.append(getLine(selected, "\u001B[2m\u001B[1m" + format + "\u001B[0m"));

        for (int i = selected + 1; i <= endingPos; i++) {
            block.append(getLine(i, format));
        }

        return block.toString();
    }

    private String getLine(int index, String format) throws Exception{
        Mod mod = modpack.getMod(index);

        return String.format(
            format,
            String.valueOf(index + 1),
            mod.getName(),
            mod.getVersion(),
            Utils.clientTypeFormat(mod.getModType()),
            Utils.clientStatusFormat(mod.getModStatus()),
            ""
        );
    }
}
