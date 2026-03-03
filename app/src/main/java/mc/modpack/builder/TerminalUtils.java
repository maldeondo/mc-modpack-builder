package mc.modpack.builder;

import java.io.IOException;
import java.io.PrintWriter;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import org.jline.utils.NonBlockingReader;
import org.jline.utils.InfoCmp.Capability;

public class TerminalUtils {
    private Terminal jlineTerminal;

    public TerminalUtils() throws IOException {
        this.jlineTerminal = TerminalBuilder.builder().system(true).build();
        jlineTerminal.enterRawMode();

        jlineTerminal.puts(Capability.cursor_invisible);
        jlineTerminal.flush();
    }
    // TABLE_FORMAT = %-3s| %-15s | %-10s | %-4s | %-6s |\n
    private static String tableFormat(int[] longestChars) {
        return "%3s | %-" + longestChars[Utils.LONGEST_NAME_INDEX] + "s | %-" + longestChars[Utils.LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |%s\n";
    }

    // TABLE_SEPARATOR = ---|-----------------|------------|------|--------|\n
    private static final String tableSeparator(int[] longestChars) {
        return "----|" + Utils.repeat(longestChars[Utils.LONGEST_NAME_INDEX] + 2, "-") + "|" + Utils.repeat(longestChars[Utils.LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

    public void push(Modpack modpack, int selected) throws Exception {
        int[] longestChars = modpack.getTable().getLongestChars();
        PrintWriter writer = jlineTerminal.writer();

        String dynamicTableFormat = tableFormat(longestChars);

        Mod temp;

        writer.println();

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

        for (int i = 0; i < selected; i++) {
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

        for (int i = selected + 1; i < modpack.getModNum(); i++) {
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

    public String read() throws IOException {
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
        jlineTerminal.puts(Capability.cursor_visible);
        jlineTerminal.flush();

        jlineTerminal.reader().close();
        jlineTerminal.writer().close();

        jlineTerminal.close();
    }
}

