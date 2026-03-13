package mc.modpack.builder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private File file;
    private PrintWriter out;

    public Logger(String route) throws IOException {
        if(route.isEmpty()) {
            file = new File("logs.txt");
        }
        else {
            file = new File(route);
        }

        if(!file.exists()) {
            file.createNewFile();
        }

        out = new PrintWriter(file);
    }

    public boolean log(Object obj) {
        try {
            out.print(obj.toString());
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean logLine(Object obj) {
        try {
            out.println(obj.toString());
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void terminate() {
        try {
            out.close();
        }
        catch (Exception e) {}
    }
}
