/*
*  Copyright 2026 Mario Aldeondo
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/

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