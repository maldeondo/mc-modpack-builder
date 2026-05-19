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
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Scanner;
import com.google.gson.Gson;

public class APIKey {
    private String key;

    public APIKey(String key) {
        if (Utils.validString(key)) this.key = key;
        else key = null;
    }

    public APIKey() {
        this(null);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // FETCH BLOCK
    public static APIKey fetchKey(Gson gson) {
        APIKey key;

        key = fetchFromEnv();
        if (key != null) return key;

        key = fetchFromDisk(gson);
        if (key != null) return key;



        return new APIKey();
    }

    private static APIKey fetchFromEnv() {
        return new APIKey(System.getenv("API_KEY"));
    }

    private static APIKey fetchFromDisk(Gson gson) {
        try {
            FileReader reader = new FileReader(new File(Utils.WORKING_DIR + "api.json"));

            return gson.fromJson(reader, APIKey.class);
        } catch (Exception ex) {
            return null;
        }
    }

    private static APIKey fetchFromUser() {
        System.out.println("The CurseForge API key could not be found, please enter one below:");

        Scanner sc = new Scanner(System.in);
        String keyString = sc.nextLine();
        return new APIKey();
    }

    private static void storeToFile() {

    }
}
