package mc.modpack.builder;

import java.io.File;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        Mod m1 = new Mod("JEI", "v1", "http", "http", 2, 2);
        Mod m2 = new Mod("JourneyMap", "v2", "http", "http", 0, 0);
        Mod m3 = new Mod("ChocoCraft", "v0.59.21", "http", "http", 1, 1);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //System.out.println(file.getPath());

        Modpack modpack = new Modpack();
        modpack.addMod(m1);
        modpack.addMod(m2);
        modpack.addMod(m3);

        File file = new File(modpack.getFile());

        if (!file.exists()) file.createNewFile();
        FileWriter writer = new FileWriter(file);

        gson.toJson(modpack, writer);
        writer.close();

        modpack.printModpack();

        modpack.removeMod(1);
        modpack.removeMod("JEI");
        modpack.printModpack();
    }
}