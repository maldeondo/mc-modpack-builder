package mc.modpack.builder;

public class Modpack {
    private final int MAX_MODS;
    private Mod[] modlist; // mod array

    private Table table;
    private int current_mods = 0;

    public Modpack(int max_mods) {
        this.MAX_MODS = max_mods;
        modlist = new Mod[MAX_MODS];
        table = new Table();
    }

    public Modpack() { this(128); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (!validIndex(index)) throw new IndexOutOfBoundsException();
        else return modlist[index];
    }

    public int getModNum() { return current_mods; }

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

            table.updateLongest(mod);
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
            table.updateLongestRemoved(this);
        }
    }

    public void printModpack() throws Exception { System.out.print(table.printTable(this)); }
}
