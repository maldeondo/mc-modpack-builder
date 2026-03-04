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

public class Table {
    private int[] longestChars = {Utils.MINIMUM_NAME_LENGHT, Utils.MINIMUM_VERSION_LENGHT};
    
    // Index -- Name -- Version -- Type -- Status

    public Table() {}

    // GSON

    public int[] getLongestChars() { return longestChars; }
    public void setLongestChars(int[] longestChars) { this.longestChars = longestChars; }

    public void updateLongestRemoved(Modpack modpack) {
        resetLongestChars();

        for (int i = 0; i < modpack.getModNum(); i++) updateLongest(modpack.getMod(i));
    }

    // Name + Version
    public void updateLongest(Mod mod) {
        updateLongestField(mod.getName(), Utils.LONGEST_NAME_INDEX, Utils.MINIMUM_NAME_LENGHT);
        updateLongestField(mod.getVersion(), Utils.LONGEST_VERSION_INDEX, Utils.MINIMUM_VERSION_LENGHT);
    }

    private void updateLongestField(String data, int field, int minimum) {
        int chars = data.length();

        if (chars > minimum && chars > longestChars[field]) longestChars[field] = chars;
    }

    public void resetLongestChars() {
        longestChars[Utils.LONGEST_NAME_INDEX] = Utils.MINIMUM_NAME_LENGHT;
        longestChars[Utils.LONGEST_VERSION_INDEX] = Utils.MINIMUM_VERSION_LENGHT;
    }

    // TABLE_FORMAT = %-3s| %-15s | %-10s | %-4s | %-6s |\n
    private static String tableFormat(int[] longestChars) {
        return "%-3s| %-" + longestChars[Utils.LONGEST_NAME_INDEX] + "s | %-" + longestChars[Utils.LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |\n";
    }

    // TABLE_SEPARATOR = ---|-----------------|------------|------|--------|\n
    private static final String tableSeparator(int[] longestChars) {
        return "---|" + Utils.repeat(longestChars[Utils.LONGEST_NAME_INDEX] + 2, "-") + "|" + Utils.repeat(longestChars[Utils.LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

}
