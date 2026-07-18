package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.PMod;

public class StatusColumn extends Column {
    public StatusColumn() {
        super();
    }

    @Override
    public String getHeader() {
        return "Status";
    }

    @Override
    public String getValue(PMod mod) {
        if(mod.getStatus()) {
            return "T";
        }
        else {
            return "F";
        }
    }
}
