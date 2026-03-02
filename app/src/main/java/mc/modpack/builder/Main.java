package mc.modpack.builder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        Mod m1 = new Mod("JEI", "v1", "http", "http", 2, 2);
        Mod m2 = new Mod("JourneyMap", "v2", "http", "http", 0, 0);
        Mod m3 = new Mod("ChocoCraft", "v0.59.21", "http", "http", 1, 1);

        Modpack modpack = new Modpack();
        modpack.addMod(m1);
        modpack.addMod(m2);
        modpack.addMod(m3);

        modpack.printModpack();

        modpack.removeMod(1);
        modpack.removeMod("JEI");
        modpack.printModpack();
    }
}