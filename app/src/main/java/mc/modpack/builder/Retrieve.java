package mc.modpack.builder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Retrieve {
    public static void main(String[] args) throws Exception {
        Modpack retrieve;
        Gson gson = new Gson();

        FileReader reader = new FileReader(new File(Utils.WORKING_DIR + "modpack.json"));
    
        retrieve = gson.fromJson(reader, Modpack.class);

        retrieve.printModpack();

        reader.close();
    }
}
