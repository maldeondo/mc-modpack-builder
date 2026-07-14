/*
*  Copyright 2026 Mario Aldeondo (@maldeondo)
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

package mc.modpack.builder.misc;

import java.io.Console;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class APIKey {
    
    // ~/.config/mc-modpack-builder/CURSEFORGE_API_KEY
    private static final Path path = Path.of(Utils.WORKING_DIR + "api_key");

    /**
     * Fetches the user's {@code CurseForge API Key} from one of three sources:
     * <ul>
     * <li>1 - The base/api_key file</li>
     * <li>2 - The CURSEFORGE_API_KEY environment variable</li>
     * <li>3 - A user prompt when the program is launched</li>
     * </ul>
     * 
     * @return decoded API key
     * @throws IOException if the file can't be modified
     */
    public static String fetchKey() throws IOException {
        String key = null;

        // 1 File (user)
        // 2 Env (dev)
        // 3 Prompt (user)

        key = fetchFromFile();
        if (Utils.validString(key)) return key;

        key = fetchFromEnv();
        if (Utils.validString(key)) return key;
        
        return fetchFromUser();
    }

    /**
     * Fetches the value mapped to the {@code CURSEFORGE_API_KEY} environment variable.
     * 
     * @return {@code API Key} value if variable exists or {@code null} if it doesn't
     */
    private static String fetchFromEnv() {
        return Utils.decodeB64(System.getenv("CURSEFORGE_API_KEY")); // null if absent
    }

    /**
     * Fetches the value present in the {@code base/api_key} file.
     * 
     * @return {@code API Key} value if file exists or {@code null} if it doesn't
     * @throws IOException if the file can't be modified
     */
    private static String fetchFromFile() throws IOException {
        try {
            return Utils.decodeB64(Files.readString(path));
        } 
        catch (NoSuchFileException ex) { // file doesn't exist -> try with env variable
            return null;
        }
    }

    /**
     * Fetches the value from a shadow user prompt
     * 
     * @return new {@code API Key} value from the user
     * @throws IOException if an I/O error occurs when saving the key to disk
     */
    private static String fetchFromUser() throws IOException {
        try {
            Console con = System.console(); String key;

            System.out.println("The CurseForge API key could not be found or is not valid.");

            do {
                key = new String(con.readPassword("Please enter a valid key (shadow) -> "));
            } while (!Utils.validString(key));

            storeToFile(key);

            return key;
        }
        catch (NullPointerException ex) {
            return null; // A System.console() object is not available
        }
    }

    /**
     * Stores the new key to the {@code base/CURSEFORGE_API_KEY} file
     * 
     * @param key value
     * @throws IOException if an I/O error occurs
     */
    private static void storeToFile(String key) throws IOException {
        Files.writeString(path, Utils.encodeB64(key), StandardOpenOption.CREATE); // overwrite any previous file
    }
}
