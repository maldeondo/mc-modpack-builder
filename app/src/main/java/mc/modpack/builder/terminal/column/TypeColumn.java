package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.PMod;

public class TypeColumn extends Column {
    public TypeColumn() {
        super();
    }

    @Override
    public String getHeader() {
        return "Version";
    }

    @Override
    public String getValue(PMod mod) {
        return mod.getModFile().getRMod().getModType().getShortVersion();
    }
}
