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

import java.io.IOException;
import java.io.PrintWriter;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import org.jline.utils.NonBlockingReader;
import org.jline.utils.InfoCmp.Capability;

import mc.modpack.builder.data.Modpack;

public class TerminalUtils {
    private Terminal jlineTerminal;
    private Table table;

    private int height;

    public TerminalUtils(Modpack modpack, Table table) throws IOException {
        this.table = table;
        height = 0;

        this.jlineTerminal = TerminalBuilder.builder().system(true).build();
        jlineTerminal.enterRawMode();

        jlineTerminal.puts(Capability.cursor_invisible);
        jlineTerminal.flush();
    }

    public void moveUp() {
        table.moveUp();
    }

    public void moveDown() {
        table.moveDown();
    }

    public void writeModpack() throws Exception {
        PrintWriter writer = jlineTerminal.writer();

        int newHeight = Math.min(table.getModpack().getModNum() - 1, jlineTerminal.getHeight() - 4);

        if (newHeight != height) {
            table.resize(newHeight);
            height = newHeight;
        }

        writer.print(table.getFullTable());

        writer.flush();
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
