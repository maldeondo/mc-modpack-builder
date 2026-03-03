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

        int selected = 0; boolean running = true;

        while (running) {
            term.clean();
            term.push(modpack, selected);
            switch (term.read()) {
                case "UP", "w": if (selected > 0) selected--; break;
                case "DOWN", "s": if (selected < modpack.getModNum() - 1) selected++; break;
                case "q", "Q": running = false; break;
                default: break;
            }
        }

        term.close();

        //term.principal(modpack, 1);
    }
}
