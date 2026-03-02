package mc.modpack.builder;

public class Modpack {
    private final int MAX_MODS;
    private int current_mods = 0;
    private Mod[] modlist; // mod array

    private String name;
    private String modpack_file;
    private Table table;
    
    public Modpack(String name, int max_mods) {
        this.MAX_MODS = max_mods;
        if (Utils.validString(name)) {
            this.name = name;
            modpack_file = String.format("%s%s.json", Utils.WORKING_DIR, name);
        }
        modlist = new Mod[MAX_MODS];
        table = new Table();
    }

    public Modpack(String name) { this(name, 128); }

    public Modpack() { this("modpack"); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, current_mods)) throw new IndexOutOfBoundsException();
        else return modlist[index];
    }

    // GSON

    public String getName() { return name; }
    public String getFile() { return modpack_file; }
    public int getModNum() { return current_mods; }
    public Mod[] getModList() { return modlist; }
    public Table getTable() { return table; }

    public void setName(String name) { this.name = name; }
    public void setFile(String modpack_file) { this.modpack_file = modpack_file; }
    public void setModNum(int current_mods) { this.current_mods = current_mods; }
    public void setModList(Mod[] modlist) { this.modlist = modlist; }
    public void setTable(Table table) { this.table = table; }

    // LOGIC BLOCK

    public boolean full() { return current_mods == MAX_MODS; }

    public void addMod(Mod mod, int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, current_mods) || full()) throw new IndexOutOfBoundsException();
        else {
            
            for (int i = current_mods - 1; i > index; i--) {
                modlist[i] = modlist[i - 1]; 
            }

            modlist[index] = mod;
            current_mods++;

            table.updateLongest(mod);
        }
    }

    public void addMod(Mod mod) { this.addMod(mod, current_mods); }

    public void removeMod(int index) throws IndexOutOfBoundsException {
        if (!Utils.validIndex(index, current_mods)) throw new IndexOutOfBoundsException();
        else {

            for (int i = index; i < current_mods - 1; i++) {
                modlist[index] = modlist[index + 1];
            }

            current_mods--;
            table.updateLongestRemoved(this);
        }
    }

    public void removeMod(String name) throws IndexOutOfBoundsException {
        int target;

        if (!Utils.validString(name)) throw new IndexOutOfBoundsException();
        else for (target = 0; target < current_mods; target++) {
            if (modlist[target].getName() == name) {
                removeMod(target);
                break;
            }
        }
    }

    public void printModpack() throws Exception { System.out.print(table.printTable(this)); }
}
