package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.PMod;

public class VersionColumn extends Column {
    public VersionColumn() {
        super();
    }

    @Override
    public String getHeader() {
        return "Version";
    }

    @Override
    public String getValue(PMod mod) {
        return mod.getModFile().getMCVersion();
    }
}
