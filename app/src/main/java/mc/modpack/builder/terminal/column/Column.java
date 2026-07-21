package mc.modpack.builder.terminal.column;

import mc.modpack.builder.data.Mod;
import mc.modpack.builder.data.PMod;
import mc.modpack.builder.data.ModPack;

public class Column {
    private ModPack modPack;
    private int maxChars = 0;

    public String getHeader() {
        return "No headers found";
    }

    public String getValue(PMod mod) {
        return "No value found";
    }

    public int getMaxChars() {
        return maxChars;
    }

    public void calculateMaxChars(ModPack modPack) {
        for (int i = 0; i < modPack.getModNum(); i++) {
            setMaxChars(modPack.getMod(i));
        }
    }

    public void setMaxChars(Mod mod) {
        String modChars = mod.getName();

        if (modChars.length() > maxChars) this.maxChars = modChars.length();
    }
}
