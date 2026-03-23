/*
*  Copyright 2026 Mario Aldeondo (@maldeondo)
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


package mc.modpack.builder.data;

public class Mod {

    private String name;
    private String version;
    private String fileName;

    private String eLink;
    private String dLink;

    private int type; // Client (0) / Server (1) / Client + Server (2)
    private int status; // Unused (0) / Active (1) / Production (2) / Cancelled (3)

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
    public void setELink(String eLink) { this.eLink = eLink; }
    public void setDLink(String dLink) { this.dLink = dLink; }
    public void setModType(int type) { this.type = type; }
    public void setModStatus(int status) { this.status = status; }
    public void setFileName(String fileName) { this.fileName = fileName; }


    public void updateMod(String version, String dLink) {
        setVersion(version);
        setDLink(dLink);
    }
}
