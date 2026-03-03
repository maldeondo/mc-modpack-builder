package mc.modpack.builder;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

public class TerminalUtils {
    private Terminal jlineTerminal;

    public TerminalUtils() throws IOException {
        this.jlineTerminal = TerminalBuilder.builder().system(true).build();
    }
    // TABLE_FORMAT = %-3s| %-15s | %-10s | %-4s | %-6s |\n
    private static String tableFormat(int[] longestChars) {
        return "%-3s| %-" + longestChars[Utils.LONGEST_NAME_INDEX] + "s | %-" + longestChars[Utils.LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |\n";
    }

    // TABLE_SEPARATOR = ---|-----------------|------------|------|--------|\n
    private static final String tableSeparator(int[] longestChars) {
        return "---|" + Utils.repeat(longestChars[Utils.LONGEST_NAME_INDEX] + 2, "-") + "|" + Utils.repeat(longestChars[Utils.LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

    public void principal(Modpack modpack, int[] longestChars) {
        jlineTerminal.puts(Capability.clear_screen);
        jlineTerminal.flush();

        Reader reader = jlineTerminal.reader();
        PrintWriter writer = jlineTerminal.writer();

        String dynamic_table_format = tableFormat(longestChars);
        
        String dynamic_table_separator = tableSeparator(longestChars);

        Mod temp;
        StringBuilder block = new StringBuilder();

        block.append("\n");

        writer.println();
        writer.flush();


        block.append(String.format(dynamic_table_format, "", "Mod", "Version", "Type", "Status"));
        writer.print(String.format(dynamic_table_format, "", "Mod", "Version", "Type", "Status"));
        writer.flush();

        block.append(tableSeparator(longestChars));
        writer.print(tableSeparator(longestChars));
        writer.flush();

        for (int i = 0; i < modpack.getModNum(); i++) {
            temp = modpack.getMod(i);
            block.append(String.format(dynamic_table_format, String.valueOf(i + 1),  temp.getName(), temp.getVersion(), Utils.clientTypeFormat(temp.getModType()), Utils.clientStatusFormat(temp.getModStatus())));
        }

        for (int i = 0; i < modpack.getModNum(); i++) {
            temp = modpack.getMod(i);
        }

        return block.toString();
    }
}

