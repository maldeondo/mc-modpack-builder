public class Modpack {
    private final int MAX_MODS;
    private Mod[] list; // mod array
    private int max_format_name; // max chars among all names
    
    private int current_mods;

    public Modpack(int max_mods) {
        this.MAX_MODS = max_mods;
        list = new Mod[MAX_MODS];

        max_format_name = 0;
        current_mods = 0;
    }

    public Modpack() { this(64); }

    // GETTERS

    public Mod getMod(int index) throws IndexOutOfBoundsException {
        if (!validIndex(index)) throw new IndexOutOfBoundsException();
        else return list[index];
    }

    public int getModNum() { return current_mods + 1; }

    public boolean full() { return current_mods == MAX_MODS; }

    // LOGIC BLOCK

    private boolean validIndex(int index) { return (index >= 0 && index <= this.current_mods); }

    public void addMod(Mod mod, int index) throws IndexOutOfBoundsException {
        if (!validIndex(index) || full()) throw new IndexOutOfBoundsException();
        else {
            
            for (int i = current_mods - 1; i > index; i--) {
                list[i] = list[i - 1]; 
            }

            list[index] = mod;
            current_mods++;
        }
    }

    public void removeMod(int index) throws IndexOutOfBoundsException {
        if (!validIndex(index)) throw new IndexOutOfBoundsException();
        else {

            for (int i = index; i < current_mods - 1; i++) {
                list[index] = list[index + 1];
            }

            current_mods--;
        }
    }

    public void addMod(Mod mod) { this.addMod(mod, current_mods); }

    public void printModpack() throws Exception {
        Mod temp;
        StringBuilder block = new StringBuilder();

        block.append("\n");
        block.append(String.format(Constants.TABLE_FORMAT, "", "Mod", "Version", "Type", "Status"));

        for (int i = 0; i < current_mods; i++) {
            temp = getMod(i);
            block.append(String.format(Constants.TABLE_FORMAT, String.valueOf(i + 1),  temp.getName(), temp.getVersion(), Constants.clientTypeFormat(temp.getModType()), Constants.clientStatusFormat(temp.getModStatus())));
        }

        System.out.print(block);
    }
}
