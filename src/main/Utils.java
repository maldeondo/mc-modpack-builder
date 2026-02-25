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
            case 0 -> "";
            case 1 -> "A";
            case 2 -> "A+P";
            case 3 -> "C";
            default -> throw new Exception("UNKNOWN_STATUS");
        };
    }

    public static final String repeat(int amount, String text) {
        return new String(new char[amount]).replace("\0", text);
    }

    // Index -- Name -- Version -- Type -- Status
    public static final String TABLE_FORMAT = "%-3s| %-15s | %-10s | %-4s | %-6s |\n";
    public static final String TABLE_FORMAT_1 = "%-3s| %-";
    public static final String TABLE_FORMAT_2 = "s | %-10s | %-4s | %-6s |\n";

    public static final String TABLE_SEPARATOR = "---|-----------------|------------|------|--------|\n";

    public static final String tableSeparator(int max_name) {
        return "---|" + repeat(max_name + 2, "-") + "|------------|------|--------|\n";
    }
}
