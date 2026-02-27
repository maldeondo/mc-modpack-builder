public class Utils {
    public static final int CLIENT_MOD = 0;
    public static final int SERVER_MOD = 1;
    public static final int CLIENT_AND_SERVER_MOD = 2;
    
    public static String clientTypeFormat(int type) throws Exception {
        return switch (type) {
            case 0 -> "C";
            case 1 -> "S";
            case 2 -> "C+S";
            default -> throw new Exception("UNKNOWN_TYPE");
        };
    }

    public static final int UNUSED_MOD = 0;
    public static final int ACTIVE_MOD = 1;
    public static final int PRODUCTION_MOD = 2;
    public static final int CANCELLED_MOD = 3;

    public static String clientStatusFormat(int status) throws Exception {
        return switch (status) {
            case 0 -> "-";
            case 1 -> "A";
            case 2 -> "A+P";
            case 3 -> "C";
            default -> throw new Exception("UNKNOWN_STATUS");
        };
    }

    public static final String repeat(int amount, String text) {
        return new String(new char[amount]).replace("\0", text);
    }

    public static final int LONGEST_NAME_INDEX = 0;
    public static final int LONGEST_VERSION_INDEX = 1;

    public static final int MINIMUM_NAME_LENGHT = 3;
    public static final int MINIMUM_VERSION_LENGHT = 7;

    // Index -- Name -- Version -- Type -- Status
    public static final String TABLE_FORMAT = "%-3s| %-15s | %-10s | %-4s | %-6s |\n";

    public static String tableFormat(int[] longest_chars) {
        return "%-3s| %-" + longest_chars[LONGEST_NAME_INDEX] + "s | %-" + longest_chars[LONGEST_VERSION_INDEX] + "s | %-4s | %-6s |\n";
    }

    public static final String TABLE_SEPARATOR = "---|-----------------|------------|------|--------|\n";

    public static final String tableSeparator(int[] longest_chars) {
        return "---|" + repeat(longest_chars[LONGEST_NAME_INDEX] + 2, "-") + "|" + repeat(longest_chars[LONGEST_VERSION_INDEX] + 2, "-") + "|------|--------|\n";
    }

}
