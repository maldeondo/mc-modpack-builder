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

import java.util.Arrays;
import java.util.List;

public class App {
    public static final String HELP_MSG = String.format(
        "Usage: java -jar mc-modpack-builder-%s.jar [OPTION]\n" +
        "\t-d, --debug -> Show detailed developer-oriented errors\n" +
        "\t-h, --help -> Show this message and exit\n" + 
        "\t-v, --version -> Show running version and exit\n" +
        "\n" +
        "More info can be found at <https://github.com/maldeondo/mc-modpack-builder>\n",
        Utils.VERSION
    );

    public static final String VER_MSG = 
        "mc-modpack-manager %s\n" +
        "Copyright (c) 2026 Mario Aldeondo (@maldeondo)\n" +
        "<https://github.com/maldeondo/mc-modpack-builder>\n";

    public static final String ERR_MSG = 
        "mc-modpack-builder: %s\n" +
        "Try -h or --help for more information.\n";

    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);

        argsList.contains("hola");
        try {
            Main.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void checkFlags(List<String> argsList) {
        
    }
}
