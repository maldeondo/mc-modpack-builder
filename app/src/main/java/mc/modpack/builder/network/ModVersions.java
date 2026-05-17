package mc.modpack.builder.network;

public class ModVersions {
    private int modLoader;
    private String version;

    public ModVersions(int modLoader, String version) {
        this.modLoader = modLoader;
        this.version = version;
    }

    public int getModLoader() {
        return modLoader;
    }

    public String getVersion() {
        return version;
    }
}
