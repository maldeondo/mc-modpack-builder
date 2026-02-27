public class Modpack {
    private final int MAX_MODS;
    private Mod[] modlist; // mod array

    private int[] longest_chars = {Utils.MINIMUM_NAME_LENGHT, Utils.MINIMUM_VERSION_LENGHT};
    
    private int current_mods = 0;

    public Modpack(int max_mods) {
        this.MAX_MODS = max_mods;
        modlist = new Mod[MAX_MODS];
    }

    public Modpack() { this(64); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (!validIndex(index)) throw new IndexOutOfBoundsException();
        else return modlist[index];
    }

    public int getModNum() { return current_mods + 1; }

    public boolean full() { return current_mods == MAX_MODS; }

    // LOGIC BLOCK

    private boolean validIndex(int index) { return (index >= 0 && index <= this.current_mods); }

    public void addMod(Mod mod, int index) throws IndexOutOfBoundsException {
        if (!validIndex(index) || full()) throw new IndexOutOfBoundsException();
        else {
            
            for (int i = current_mods - 1; i > index; i--) {
                modlist[i] = modlist[i - 1]; 
            }

            modlist[index] = mod;
            current_mods++;

            updateLongest(mod.getName(), Utils.LONGEST_NAME_INDEX, 3);
            updateLongest(mod.getVersion(), Utils.LONGEST_VERSION_INDEX, 7);
        }
    }

    public void addMod(Mod mod) { this.addMod(mod, current_mods); }

    public void removeMod(int index) throws IndexOutOfBoundsException {
        if (!validIndex(index)) throw new IndexOutOfBoundsException();
        else {

            for (int i = index; i < current_mods - 1; i++) {
                modlist[index] = modlist[index + 1];
            }

            current_mods--;
            updateLongestFull();
        }
    }

    private void updateLongestFull() {
        longest_chars[Utils.LONGEST_NAME_INDEX] = 3;
        longest_chars[Utils.LONGEST_VERSION_INDEX] = 7;

        for (int i = 0; i < current_mods; i++) {
            updateLongest(modlist[i].getName(), Utils.LONGEST_NAME_INDEX, 3);
            updateLongest(modlist[i].getVersion(), Utils.LONGEST_VERSION_INDEX, 7);
        }
    }
    private void updateLongest(String data, int field, int minimum) {
        int chars = data.length();

        if (chars > minimum && chars > longest_chars[field]) longest_chars[field] = chars;
    }

    public void printModpack() throws Exception {
        String dynamic_table_format = Utils.tableFormat(longest_chars);
        
        String dynamic_table_separator = Utils.tableSeparator(longest_chars);

        Mod temp;
        StringBuilder block = new StringBuilder();

        block.append("\n");
        block.append(String.format(dynamic_table_format, "", "Mod", "Version", "Type", "Status"));
        block.append(dynamic_table_separator);

        for (int i = 0; i < current_mods; i++) {
            temp = getMod(i);
            block.append(String.format(dynamic_table_format, String.valueOf(i + 1),  temp.getName(), temp.getVersion(), Utils.clientTypeFormat(temp.getModType()), Utils.clientStatusFormat(temp.getModStatus())));
        }

        System.out.print(block);
    }
}
