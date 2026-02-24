public class Constants {
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
            case 2 -> "P";
            case 3 -> "C";
            default -> throw new Exception("UNKNOWN_STATUS");
        };
    }

    // Name -- Version -- Type
    public static final String TABLE_FORMAT = "%-3s%-15s%-10s%-5s%-2s\n";
}
