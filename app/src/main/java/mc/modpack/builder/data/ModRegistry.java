package mc.modpack.builder.data;

import java.util.HashMap;
import java.util.UUID;

public class ModRegistry {
    private HashMap<UUID, Mod> modList;
    private int modNum;

    public ModRegistry() {}

    public int getModNum() {
        return modNum;
    }

    public Mod getModData(UUID uuid) {
        return modList.get(uuid);
    }

    public void setModList(HashMap<UUID, Mod> modList) {
        if (modList != null) this.modList = modList;
    }

    public void addMod(Mod mod) {
        modList.put(mod.getID(), mod);
    }

    public void removeMod(Mod mod) {
        modList.remove(mod.getID());
    }
}
