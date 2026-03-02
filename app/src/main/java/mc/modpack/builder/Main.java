package mc.modpack.builder;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {
    public static void main(String[] args) throws Exception {
        Mod m1 = new Mod("JEI", "v1", "http", "http", 2, 2);
        Mod m2 = new Mod("JourneyMap", "v2", "http", "http", 0, 0);
        Mod m3 = new Mod("ChocoCraft", "v0.59.21", "http", "http", 1, 1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(Utils.WORKING_DIR + "test.json");
        if (!file.exists()) file.createNewFile();

        System.out.println(file.getPath());

        Modpack modpack = new Modpack();
        modpack.addMod(m1);
        modpack.addMod(m2);
        modpack.addMod(m3);

        mapper.writeValue(file, modpack);

        modpack.printModpack();

        modpack.removeMod(1);
        modpack.removeMod("JEI");
        modpack.printModpack();
    }
}