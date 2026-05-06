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

package mc.modpack.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class App {
    public static final String HELP_MSG = String.format(
        "Usage: java -jar mc-modpack-builder-%s.jar [OPTION]\n" +
        "   -d, --debug -> Show detailed developer-oriented errors\n" +
        "   -h, --help -> Show this message and exit\n" + 
        "   -v, --version -> Show running version and exit\n" +
        "\n" +
        "More info can be found at <https://github.com/maldeondo/mc-modpack-builder>\n",
        Utils.VERSION
    );

    public static final String VER_MSG = String.format(
        "mc-modpack-manager %s\n" +
        "Copyright (c) 2026 Mario Aldeondo (@maldeondo)\n" +
        "<https://github.com/maldeondo/mc-modpack-builder>\n",
        Utils.VERSION
    );

    public static final String ERR_MSG = 
        "mc-modpack-builder: %s\n" +
        "Try -h or --help for more information.\n";

    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);

        String apiKey = getAPIKey();

        if (!checkFlags(argsList)) {
            try {
                Main.run(apiKey);
            } catch (Exception ex) {
                if (checkDebug(argsList)) ex.printStackTrace();
                else System.out.printf(ERR_MSG, ex.getMessage());
            }
        }
    }

    private static boolean checkFlags(List<String> argsList) {
        boolean flagIsPresent = false;

        if (argsList.contains("-h") || argsList.contains("--help")) System.out.print(HELP_MSG);
        else if (argsList.contains("-v") || argsList.contains("--version")) System.out.print(VER_MSG);
        else flagIsPresent = true;

        return flagIsPresent;
    }

    private static boolean checkDebug(List<String>argsList) {
        return (argsList.contains("-d") || argsList.contains("--debug")); 
    }

    private static String getAPIKey() {
        BufferedReader reader = new BufferedReader(new FileReader(".env"));
        String line = reader.readLine();
        reader.close();

        return line.split("=")[1];
    }
}
