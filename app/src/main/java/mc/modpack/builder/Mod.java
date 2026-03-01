package mc.modpack.builder;

public class Mod {

    private String name;
    private String version;
    private String fileName;

    private String eLink;
    private String dLink;

    private int type; // Client (0) / Server (1) / Client + Server (2)
    private int status;

    public Mod(String name, String version, String eLink, String dLink, int type, int status) {
        this.name = name;
        this.version = version;
        this.eLink = eLink;
        this.dLink = dLink;
        this.type = type;
        this.status = status;
    }
    
    // GETTERS

    public String getName() { return this.name; }
    public String getVersion() { return this.version; }
    public String getELink() { return this.eLink; }
    public String getDLink() { return this.dLink; }
    public int getModType() { return this.type; }
    public int getModStatus() { return this.status; }
    public String getFileName() { return this.fileName; }

    // SETTERS

    public void setName(String name) { this.name = name; }
    public void setVersion(String version) { this.version = version; }
    public void setELink(String e_link) { this.eLink = e_link; }
    public void setDLink(String d_link) { this.dLink = d_link; }
    public void setModType(int type) { this.type = type; }
    public void setModStatus(int status) { this.status = status; }
    public void setFileName(String fileName) { this.fileName = fileName; }


    public void updateMod(String version, String d_link) {
        setVersion(version);
        setDLink(d_link);
    }
}
