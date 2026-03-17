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

public class Utils {
    public static final String WORKING_DIR = System.getProperty("user.home") + "/.config/mc-modpack-builder/";

    public static String fileFromName(String name) {
        return String.format("%s%s.json", WORKING_DIR, name);
    }

    public static final int CLIENT_MOD = 0;
    public static final int SERVER_MOD = 1;
    public static final int CLIENT_AND_SERVER_MOD = 2;
    
    public static String modTypeFormat(int type) {
        return switch (type) {
            case 0 -> "C";
            case 1 -> "S";
            case 2 -> "C+S";
            default -> "";
        };
    }

    public static final int UNUSED_MOD = 0;
    public static final int ACTIVE_MOD = 1;
    public static final int PRODUCTION_MOD = 2;
    public static final int CANCELLED_MOD = 3;

    public static String modStatusFormat(int status) {
        return switch (status) {
            case 0 -> "-";
            case 1 -> "A";
            case 2 -> "A+P";
            case 3 -> "C";
            default -> "";
        };
    }

    public static final String repeat(int amount, String text) {
        return new String(new char[amount]).replace("\0", text);
    }

    public static final boolean validString(String data) {
        return (data != null && data != "");
    }

    public static final boolean validIndex(int number, int max) {
        return (number >= 0 && number <= max);
    }

    // TABLE_FORMAT = %-3s| %-15s | %-10s | %-4s | %-6s |\n
    public static final String tableFormat(int[] longestChars) {
        return "%3s | %-" + longestChars[Utils.LONGEST_NAME_INDEX] + "s | %-" + longestChars[Utils.LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |%s\n";
    }

    // TABLE_SEPARATOR = ---|-----------------|------------|------|--------|\n
    public static final String tableSeparator(int[] longestChars) {
        return "----|" + Utils.repeat(longestChars[Utils.LONGEST_NAME_INDEX] + 2, "-") + "|" + Utils.repeat(longestChars[Utils.LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

    
    public static final int LONGEST_NAME_INDEX = 0;
    public static final int LONGEST_VERSION_INDEX = 1;

    public static final int MINIMUM_NAME_LENGHT = 3;
    public static final int MINIMUM_VERSION_LENGHT = 7;

}
