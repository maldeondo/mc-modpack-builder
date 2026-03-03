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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

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
    

        // Create a dumb terminal
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        System.out.println("Terminal type: " + terminal.getType());

        terminal.writer().println("Hello from dumb terminal!");
        terminal.writer().flush();

        PrintWriter writert = terminal.writer();

        // Write text
        writert.println("Hello, JLine!");
        writert.flush();

        // Use ANSI escape sequences for formatting (if supported)
        writert.println("\u001B[1;31mThis text is bold and red\u001B[0m");
        writert.flush();
        Reader reader = terminal.reader();

        terminal.writer().println("Press any key (or 'q' to quit):");
        terminal.writer().flush();

        int c;

        while ((c = reader.read()) != 'q') {
            terminal.writer().printf("You pressed: %c (ASCII: %d)%n", (char) c, c);
            terminal.writer().flush();
        }


            terminal.enterRawMode();
            var readernew = terminal.reader();
            var writernew  = terminal.writer();

            boolean ejecutando = true;
            int seleccion = 0;
            String[] opciones = {"[1] Añadir Mod", "[2] Guardar Pack", "[3] Salir"};

            while (ejecutando) {
                // 1. LIMPIAR PANTALLA
                terminal.puts(Capability.clear_screen);
                terminal.flush();

                // 2. DIBUJAR INTERFAZ
                writernew.println("=== GESTOR DE MODPACKS ===");
                for (int i = 0; i < opciones.length; i++) {
                    if (i == seleccion) writernew.print("> "); // Marcador de selección
                    else writernew.print("  ");
                    writernew.println(opciones[i]);
                }
                writernew.println("==========================");
                writernew.println("Usa 'W' (arriba), 'S' (abajo) o 'Q' (salir)");
                writernew.flush();

                // 3. REACCIONAR A TECLAS
                int tecla = readernew.read();
                switch (Character.toLowerCase((char) tecla)) {
                    case 'w': if (seleccion > 0) seleccion--; break;
                    case 's': if (seleccion < opciones.length - 1) seleccion++; break;
                    case 'q': ejecutando = false; break;
                }
            }


        terminal.close();
    
    }
}