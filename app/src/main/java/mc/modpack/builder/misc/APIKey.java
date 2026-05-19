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

import mc.modpack.builder.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class APIKey {
    public static String fetchKey() throws IOException {
        String key = null;

        key = fetchFromFile(Utils.WORKING_DIR + "API_KEY");
        if (Utils.validString(key)) return key;

        key = fetchFromFile(".env/API_KEY");
        if (Utils.validString(key)) return key;

        key = fetchFromEnv();
        if (Utils.validString(key)) return key;

        return fetchFromUser();

    }

    private static String fetchFromEnv() throws IOException {
        return System.getenv("API_KEY");
    }

    private static String fetchFromFile(String path) throws IOException {
        Scanner sc = new Scanner(new File(path));

        String key = sc.nextLine();
        sc.close();

        return key;
    }

    private static String fetchFromUser() throws IOException {
        System.out.println("The CurseForge API key could not be found, please enter one below:");

        Scanner sc = new Scanner(System.in);

        String key = sc.nextLine();
        sc.close();

        storeToFile(key);

        return key;
    }

    private static void storeToFile(String key) throws IOException {
        FileWriter writer = new FileWriter(Utils.WORKING_DIR + "API_KEY");

        writer.write(key);

        writer.close();
    }
}
