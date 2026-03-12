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

import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        run();
    }

    private static void run() throws Exception {
        Mod m1 = new Mod("JEI", "v1", "http", "http", 2, 2);
        Mod m2 = new Mod("JourneyMap", "v2", "http", "http", 0, 0);
        Mod m3 = new Mod("ChocoCraft", "v0.59.213", "http", "http", 1, 1);
        Mod m4 = new Mod("SecurityCraft", "v0.59.213", "http", "http", 1, 1);
        Mod m5 = new Mod("WorldEdit", "v0.59.213", "http", "http", 1, 1);
        Mod m6 = new Mod("WorldGuard", "v0.59.213", "http", "http", 1, 1);
        Mod m7 = new Mod("Alex Mobs", "v0.59.213", "http", "http", 1, 1);
        Mod m8 = new Mod("Crate", "v0.59.213", "http", "http", 1, 1);
        Mod m9 = new Mod("Create Modules", "v0.59.213", "http", "http", 1, 1);
        Mod m10 = new Mod("GalactiCraft", "v0.59.213", "http", "http", 1, 1);
        Mod m11 = new Mod("DecoCraft", "v0.59.213", "http", "http", 1, 1);
        Mod m12 = new Mod("Twilight Forest", "v0.59.213", "http", "http", 1, 1);

        Modpack modpack = new Modpack();

        modpack.addModList(new ArrayList<Mod>(List.of(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12)));

        TerminalUtils term = new TerminalUtils(modpack);

        boolean running = true;

        while (running) {
            term.clean();
            term.writeModpack();
            switch (term.readMovement()) {
                case "UP", "w": term.moveUp(); break;
                case "DOWN", "s": term.moveDown(); break;
                case "q", "Q": running = false; break;
                default: break;
            }
        }

        term.close();
    }
}