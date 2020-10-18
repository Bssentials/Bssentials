package bssentials.include;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * An implementation of {@link Configuration} which saves all files in Yaml.
 */
public class YamlConfiguration extends FileConfiguration {

    protected static final String COMMENT_PREFIX = "# ";
    protected static final String BLANK_CONFIG = "{}\n";
    private final DumperOptions yamlOptions = new DumperOptions();
    private final LoaderOptions loaderOptions = new LoaderOptions();
    private final Representer yamlRepresenter = new YamlRepresenter();
    private final Yaml yaml = new Yaml(new YamlConstructor(), yamlRepresenter, yamlOptions, loaderOptions);

    public class YamlRepresenter extends Representer {
        public YamlRepresenter() {
            this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        }

        private class RepresentConfigurationSection extends RepresentMap {
            @Override
            public Node representData(Object data) {
                return super.representData(((ConfigurationSection) data).getValues(false));
            }
        }
    }

    private class YamlConstructor extends SafeConstructor {
        public YamlConstructor() {this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());}

        private class ConstructCustomObject extends ConstructYamlMap {
            @Override
            public Object construct(Node node) {
                if (node.isTwoStepsConstruction()) throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
                Map<?, ?> raw = (Map<?, ?>) super.construct(node);

                return raw;
            }

            @Override
            public void construct2ndStep(Node node, Object object) {
                throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
            }
        }
    }

    @Override
    public String saveToString() {
        yamlOptions.setIndent(options().indent());
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        String header = buildHeader();
        String dump = yaml.dump(getValues(false));

        if (dump.equals(BLANK_CONFIG))dump = "";
        return header + dump;
    }

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException {
        Map<?, ?> input;
        try {
            input = (Map<?, ?>) yaml.load(contents);
        } catch (YAMLException e) {throw new InvalidConfigurationException(e);
        } catch (ClassCastException e) {throw new InvalidConfigurationException("Top level is not a Map.");}

        String header = parseHeader(contents);
        if (header.length() > 0) options().header(header);
        if (input != null) convertMapsToSections(input, this);
    }

    protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            else section.set(key, value);
        }
    }

    protected String parseHeader(String input) {
        String[] lines = input.split("\r?\n", -1);
        StringBuilder result = new StringBuilder();
        boolean readingHeader = true;
        boolean foundHeader = false;

        for (int i = 0; (i < lines.length) && (readingHeader); i++) {
            String line = lines[i];

            if (line.startsWith(COMMENT_PREFIX)) {
                if (i > 0) result.append("\n");
                if (line.length() > COMMENT_PREFIX.length()) result.append(line.substring(COMMENT_PREFIX.length()));

                foundHeader = true;
            } else if ((foundHeader) && (line.length() == 0)) result.append("\n");
            else if (foundHeader) readingHeader = false;
        }
        return result.toString();
    }

    @Override
    protected String buildHeader() {
        String header = options().header();
        if (options().copyHeader()) {
            Configuration def = getDefaults();
            if ((def != null) && (def instanceof FileConfiguration)) {
                FileConfiguration filedefaults = (FileConfiguration) def;
                String defaultsHeader = filedefaults.buildHeader();
                if ((defaultsHeader != null) && (defaultsHeader.length() > 0)) return defaultsHeader;
            }
        }
        if (header == null) return "";
        StringBuilder builder = new StringBuilder();
        String[] lines = header.split("\r?\n", -1);
        boolean startedHeader = false;

        for (int i = lines.length - 1; i >= 0; i--) {
            builder.insert(0, "\n");
            if ((startedHeader) || (lines[i].length() != 0)) {
                builder.insert(0, lines[i]);
                builder.insert(0, COMMENT_PREFIX);
                startedHeader = true;
            }
        }
        return builder.toString();
    }

    @Override
    public FileConfigurationOptions options() {
        if (options == null) options = new FileConfigurationOptions(this);
        return (FileConfigurationOptions) options;
    }

    public static YamlConfiguration loadConfiguration(File file) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (FileNotFoundException ex) {
        } catch (IOException | InvalidConfigurationException ex){ System.err.println("Cannot load " + file + ex.getMessage()); }

        return config;
    }

    public static YamlConfiguration loadConfiguration(Reader reader) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(reader);
        } catch (IOException | InvalidConfigurationException ex) { System.err.println("Cannot load configuration from stream " + ex.getMessage()); }
        return config;
    }

}
