public class Mod {

    private String name;
    private String version;

    private String e_link;
    private String d_link;

    private int type; // Client (0) / Server (1) / Client + Server (2)
    private int status;

    public Mod(String name, String version, String e_link, String d_link, int type, int status) {
        this.name = name;
        this.version = version;
        this.e_link = e_link;
        this.d_link = d_link;
        this.type = type;
        this.status = status;
    }

    public Mod(String name) {
        this(name, null, null, null, -1, -1);
    }

    // GETTERS

    public String getName() { return this.name; }
    public String getVersion() { return this.version; }
    public String getELink() { return this.e_link; }
    public String getDLink() { return this.d_link; }
    public int getModType() { return this.type; }
    public int getModStatus() { return this.status; }

    // SETTERS

    public void setName(String name) { this.name = name; }
    public void setVersion(String version) { this.version = version; }
    public void setELink(String e_link) { this.e_link = e_link; }
    public void setDLink(String d_link) { this.d_link = d_link; }
    public void setModType(int type) { this.type = type; }
    public void setModStatus(int status) { this.status = status; }


    public void updateMod(String version, String d_link) {
        setVersion(version);
        setDLink(d_link);
    }
}
