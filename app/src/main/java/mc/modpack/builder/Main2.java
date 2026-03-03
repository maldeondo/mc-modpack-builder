package mc.modpack.builder;

public class Main2 {
    public static void main(String[] args) throws Exception {
        run();
    }

    private static void run() throws Exception {
        TerminalUtils term = new TerminalUtils();
        
        Mod m1 = new Mod("JEI", "v1", "http", "http", 2, 2);
        Mod m2 = new Mod("JourneyMap", "v2", "http", "http", 0, 0);
        Mod m3 = new Mod("ChocoCraft", "v0.59.213", "http", "http", 1, 1);
        
        Modpack modpack = new Modpack();
        modpack.addMod(m1);
        modpack.addMod(m2);
        modpack.addMod(m3);

        int selected = 0; boolean exit = false;

        while (!exit) {
            term.clean();
            term.push(modpack, selected);
            switch (Character.toLowerCase(term.get())) {
                case 'w': if (selected > 0) selected--; break;
                case 's': if (selected < modpack.getModNum() - 1) selected++; break;
                case 'q': exit = true; break;
                default: break;
            }
        }

        //term.principal(modpack, 1);
    }
}
