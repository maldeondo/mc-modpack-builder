public class Modpack {
    private Mod[] list; // mod array
    private final int MAX_MODS;

    public Modpack(int max_mods) {
        this.MAX_MODS = max_mods;
    }

    public Modpack() { this(64); }
}
