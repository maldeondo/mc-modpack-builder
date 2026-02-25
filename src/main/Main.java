public class Main {
    public static void main(String[] args) throws Exception {
        Mod m1 = new Mod("JEI", "v1", "http", "http", 2, 2);
        Mod m2 = new Mod("JourneyMap", "v2", "http", "http", 0, 0);

        Modpack modpack = new Modpack();
        modpack.addMod(m1);
        modpack.addMod(m2);

        modpack.printModpack();

        modpack.removeMod(1);
        modpack.printModpack();
    }
}