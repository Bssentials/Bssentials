package bssentials.include;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public abstract class FileConfiguration extends MemorySection implements Configuration {

    protected Configuration defaults;
    protected FileConfigurationOptions options;

    public FileConfiguration() {}

    public FileConfiguration(Configuration defaults) {
        this.defaults = defaults;
    }

    public void save(File file) throws IOException {
        Files.createParentDirs(file);
        String data = saveToString();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);

        try {
            writer.write(data);
        } finally {
            writer.close();
        }
    }

    public void save(String file) throws IOException {
        save(new File(file));
    }

    public abstract String saveToString();

    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        final FileInputStream stream = new FileInputStream(file);
        load(new InputStreamReader(stream, Charsets.UTF_8));
    }

    public void load(Reader reader) throws IOException, InvalidConfigurationException {
        BufferedReader input = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } finally {input.close();}
        loadFromString(builder.toString());
    }

    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        load(new File(file));
    }

    public abstract void loadFromString(String contents) throws InvalidConfigurationException;

    protected abstract String buildHeader();

    @Override
    public FileConfigurationOptions options() {
        if (options == null) options = new FileConfigurationOptions(this);
        return (FileConfigurationOptions) options;
    }

    @Override
    public void addDefault(String path, Object value) {
        if (defaults == null) defaults = new YamlConfiguration();
        defaults.set(path, value);
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        for (Map.Entry<String, Object> entry : defaults.entrySet())
            addDefault(entry.getKey(), entry.getValue());
    }

    @Override
    public void addDefaults(Configuration defaults) {
        addDefaults(defaults.getValues(true));
    }

    @Override
    public void setDefaults(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public Configuration getDefaults() {
        return defaults;
    }

    @Override
    public ConfigurationSection getParent() {
        return null;
    }

}
