package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.ModPack;
import mc.modpack.builder.data.PMod;

public class NameColumn extends Column {
    public NameColumn(ModPack modPack) {
        super();
    }

    @Override
    public String getHeader() {
        return "Mod";
    }

    @Override
    public String getValue(PMod mod) {
        return mod.getModFile().getRMod().getModCurseForgeName();
    }
}
