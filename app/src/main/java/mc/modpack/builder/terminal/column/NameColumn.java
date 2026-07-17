package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.Mod;

public class NameColumn extends Column {
    public NameColumn() {
        super();
    }

    @Override
    public String getHeader() {
        return "Name";
    }

    @Override
    public String getValue(Mod mod) {
        return mod.getName();
    }
}
