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
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mc.modpack.builder.data.Modpack;

public class Retrieve {
    public static void main(String[] args) throws Exception {
        Modpack retrieve;
        Gson gson = new Gson();

        FileReader reader = new FileReader(new File(Utils.WORKING_DIR + "modpack.json"));
    
        retrieve = gson.fromJson(reader, Modpack.class);



        System.out.println(retrieve.getFile());

        reader.close();
    }
}
