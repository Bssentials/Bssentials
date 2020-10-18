package bssentials.include;

public class FileConfigurationOptions {

    private int indent = 2;
    private String header = null;
    private boolean copyHeader = true;

    private char pathSeparator = '.';
    private boolean copyDefaults = false;
    private final Configuration configuration;

    protected FileConfigurationOptions(FileConfiguration fileConfiguration) {
        this.configuration = fileConfiguration;
    }

    public FileConfiguration configuration() {
        return (FileConfiguration) configuration;
    }

    public FileConfigurationOptions copyDefaults(boolean value) {
        this.copyDefaults = value;
        return this;
    }

    public FileConfigurationOptions pathSeparator(char value) {
        this.pathSeparator = value;
        return this;
    }

    public String header() {
        return header;
    }

    public FileConfigurationOptions header(String value) {
        this.header = value;
        return this;
    }

    public boolean copyHeader() {
        return copyHeader;
    }

    public FileConfigurationOptions copyHeader(boolean value) {
        copyHeader = value;
        return this;
    }

    public int indent() {
        return indent;
    }

    public FileConfigurationOptions indent(int value) {
        this.indent = value;
        return this;
    }

    public char pathSeparator() {
        return pathSeparator;
    }

    public boolean copyDefaults() {
        return copyDefaults;
    }

}