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

    private int selected;

    public TerminalUtils(Modpack modpack) throws IOException {
        this.jlineTerminal = TerminalBuilder.builder().system(true).build();
        jlineTerminal.enterRawMode();

        this.modpack = modpack;

        height = jlineTerminal.getHeight();
        startScrollingIndex = 0;
        endScrollingIndex = Math.min(height - 4, modpack.getModNum() - 1);

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

            endScrollingIndex = Math.min(jlineTerminal.getHeight() - 4, modpack.getModNum() - 1);
        }
        
        if (selected > endScrollingIndex && selected < modpack.getModNum()) {
            startScrollingIndex++;
            endScrollingIndex++;

        } else if (selected < startScrollingIndex && selected != (modpack.getModNum() - 1)) {
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

        Mod temp;

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

        for (int i = startScrollingIndex; i < selected; i++) {
            temp = modpack.getMod(i);
            writer.print(String.format(
                dynamicTableFormat,
                String.valueOf(i + 1),
                temp.getName(),
                temp.getVersion(),
                Utils.clientTypeFormat(temp.getModType()),
                Utils.clientStatusFormat(temp.getModStatus()),
                ""
            ));
        }

        writer.print(String.format(
            "\u001B[2m\u001B[1m" + dynamicTableFormat + "\u001B[0m", 
            String.valueOf(selected + 1),
            modpack.getMod(selected).getName(),
            modpack.getMod(selected).getVersion(),
            Utils.clientTypeFormat(modpack.getMod(selected).getModType()),
            Utils.clientStatusFormat(modpack.getMod(selected).getModStatus()),
            " <"
        ));

        for (int i = selected + 1; i <= endScrollingIndex; i++) {
            temp = modpack.getMod(i);
            writer.print(String.format(
                dynamicTableFormat,
                String.valueOf(i + 1),
                temp.getName(),
                temp.getVersion(),
                Utils.clientTypeFormat(temp.getModType()),
                Utils.clientStatusFormat(temp.getModStatus()),
                ""
            ));
        }

        writer.flush();
        //writer.close();
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

