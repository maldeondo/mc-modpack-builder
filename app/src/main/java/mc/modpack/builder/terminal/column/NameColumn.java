package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.PMod;

public class NameColumn extends Column {
    public NameColumn() {
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
