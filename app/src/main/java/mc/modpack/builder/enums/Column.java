package mc.modpack.builder.enums;

public enum Column {
    NAME(),
    VERSION(),
    STATUS(),
    TYPE();
    
    private int value = 0;

    Column() {
        
    }
    
    public String getHeader() {
        return "";
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
}
