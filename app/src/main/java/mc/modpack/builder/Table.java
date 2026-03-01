package mc.modpack.builder;

public class Table {
    private int[] longest_chars = {Utils.MINIMUM_NAME_LENGHT, Utils.MINIMUM_VERSION_LENGHT};
    
    // Index -- Name -- Version -- Type -- Status

    public Table() {}

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

        if (chars > minimum && chars > longest_chars[field]) longest_chars[field] = chars;
    }

    public void resetLongestChars() {
        longest_chars[Utils.LONGEST_NAME_INDEX] = Utils.MINIMUM_NAME_LENGHT;
        longest_chars[Utils.LONGEST_VERSION_INDEX] = Utils.MINIMUM_VERSION_LENGHT;
    }

    // TABLE_FORMAT = %-3s| %-15s | %-10s | %-4s | %-6s |\n
    private static String tableFormat(int[] longest_chars) {
        return "%-3s| %-" + longest_chars[Utils.LONGEST_NAME_INDEX] + "s | %-" + longest_chars[Utils.LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |\n";
    }

    // TABLE_SEPARATOR = ---|-----------------|------------|------|--------|\n
    private static final String tableSeparator(int[] longest_chars) {
        return "---|" + Utils.repeat(longest_chars[Utils.LONGEST_NAME_INDEX] + 2, "-") + "|" + Utils.repeat(longest_chars[Utils.LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

    public String printTable(Modpack modpack) throws Exception {
        String dynamic_table_format = tableFormat(longest_chars);
        
        String dynamic_table_separator = tableSeparator(longest_chars);

        Mod temp;
        StringBuilder block = new StringBuilder();

        block.append("\n");
        block.append(String.format(dynamic_table_format, "", "Mod", "Version", "Type", "Status"));
        block.append(dynamic_table_separator);

        for (int i = 0; i < modpack.getModNum(); i++) {
            temp = modpack.getMod(i);
            block.append(String.format(dynamic_table_format, String.valueOf(i + 1),  temp.getName(), temp.getVersion(), Utils.clientTypeFormat(temp.getModType()), Utils.clientStatusFormat(temp.getModStatus())));
        }

        return block.toString();
    }
}
