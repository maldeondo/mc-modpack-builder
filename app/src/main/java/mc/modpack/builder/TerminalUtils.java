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

import java.io.IOException;
import java.io.PrintWriter;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import org.jline.utils.NonBlockingReader;
import org.jline.utils.InfoCmp.Capability;

public class TerminalUtils {
    private Terminal jlineTerminal;
    private Modpack modpack;

    private int height;
    private int startScrollingIndex;
    private int endScrollingIndex;
    private int scrollingSize;

    private int selected;

    public TerminalUtils(Modpack modpack) throws IOException {
        this.jlineTerminal = TerminalBuilder.builder().system(true).build();
        jlineTerminal.enterRawMode();

        this.modpack = modpack;

        height = jlineTerminal.getHeight();
        startScrollingIndex = 0;
        endScrollingIndex = Math.min(height - 4, modpack.getModNum() - 1);
        scrollingSize = Math.min(height - 4, modpack.getModNum() - 1);

        jlineTerminal.puts(Capability.cursor_invisible);
        jlineTerminal.flush();
    }
    // TABLE_FORMAT = %-3s| %-15s | %-10s | %-4s | %-6s |\n
    private static final String tableFormat(int[] longestChars) {
        return "%3s | %-" + longestChars[Utils.LONGEST_NAME_INDEX] + "s | %-" + longestChars[Utils.LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |%s\n";
    }

    // TABLE_SEPARATOR = ---|-----------------|------------|------|--------|\n
    private static final String tableSeparator(int[] longestChars) {
        return "----|" + Utils.repeat(longestChars[Utils.LONGEST_NAME_INDEX] + 2, "-") + "|" + Utils.repeat(longestChars[Utils.LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

    private void resize() {
        if (height != jlineTerminal.getHeight()) {
            height = jlineTerminal.getHeight();

            scrollingSize = Math.min(height - 4, modpack.getModNum() - 1);
            //endScrollingIndex += scrollingSize / 2;
            
        }
        
        if (selected > endScrollingIndex) {
            startScrollingIndex++;
            endScrollingIndex++;

        } else if (selected < startScrollingIndex) {
            startScrollingIndex--;
            endScrollingIndex--;
        }
    }

    public void moveUp() {
        if (selected > 0) selected--;
    }

    public void moveDown() {
        if (selected < modpack.getModNum() - 1) selected++;
    }

    public void writeModpack() throws Exception {
        int[] longestChars = modpack.getTable().getLongestChars();
        PrintWriter writer = jlineTerminal.writer();

        resize();

        String dynamicTableFormat = tableFormat(longestChars);

        writer.print(String.format(
            dynamicTableFormat, 
            "",
            "Mod",
            "Version",
            "Type",
            "Status",
            ""
        ));

        writer.print(tableSeparator(longestChars));

        for (int i = startScrollingIndex; i < selected; i++) writer.print(buildModLine(i, dynamicTableFormat));

        writer.print(buildModLine(selected, "\u001B[2m\u001B[1m" + dynamicTableFormat + "\u001B[0m"));

        for (int i = selected + 1; i <= endScrollingIndex; i++) writer.print(buildModLine(i, dynamicTableFormat));

        writer.flush();
    }

    private String buildModLine(int index, String table) throws Exception {
        StringBuilder block = new StringBuilder();
        Mod temp = modpack.getMod(index);

        block.append(String.format(
            table,
            String.valueOf(index + 1),
            temp.getName(),
            temp.getVersion(),
            Utils.clientTypeFormat(temp.getModType()),
            Utils.clientStatusFormat(temp.getModStatus()),
	        (index == selected) ? " <" : ""
        ));

        return block.toString();
    }

    public String readMovement() throws IOException {
        NonBlockingReader reader = jlineTerminal.reader();
        int num;

        if ((num = reader.read()) != 27) return String.valueOf((char) num);
        else if ((num = reader.read(10)) != 10) return switch (reader.read(10)) {
                case 65 -> "UP";
                case 66 -> "DOWN";
                case 67 -> "RIGHT";
                case 68 -> "LEFT";
                default -> "UNKNOWN";
        };
        else return "ESC";
    }

    public void clean() {
        jlineTerminal.writer().print("\033[H"); 
        jlineTerminal.writer().flush();
        jlineTerminal.puts(Capability.clr_eos);
    }

    public void close() throws IOException {
        // Re-allow cursor to restore terminal usability
        jlineTerminal.puts(Capability.cursor_visible);
        jlineTerminal.flush();

        jlineTerminal.reader().close();
        jlineTerminal.writer().close();

        jlineTerminal.close();
    }
}

