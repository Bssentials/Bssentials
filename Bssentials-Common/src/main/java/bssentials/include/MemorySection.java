package bssentials.include;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A type of {@link ConfigurationSection} that is stored in memory.
 */
public class MemorySection implements ConfigurationSection {

    protected final Map<String, Object> map = new LinkedHashMap<String, Object>();
    private final Configuration root;
    private final ConfigurationSection parent;
    private final String path;
    private final String fullPath;

    protected MemorySection() {
        if (!(this instanceof Configuration))
            throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
        this.path = "";
        this.fullPath = "";
        this.parent = null;
        this.root = (Configuration) this;
    }

    protected MemorySection(ConfigurationSection parent, String path) {
        this.path = path;
        this.parent = parent;
        this.root = parent.getRoot();
        this.fullPath = createPath(parent, path);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        Set<String> result = new LinkedHashSet<String>();

        Configuration root = getRoot();
        if (root != null && root.options().copyDefaults()) {
            ConfigurationSection defaults = getDefaultSection();

            if (defaults != null) {
                result.addAll(defaults.getKeys(deep));
            }
        }

        mapChildrenKeys(result, this, deep);

        return result;
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        Configuration root = getRoot();
        if (root != null && root.options().copyDefaults()) {
            ConfigurationSection defaults = getDefaultSection();

            if (defaults != null)
                result.putAll(defaults.getValues(deep));
        }

        mapChildrenValues(result, this, deep);

        return result;
    }

    @Override
    public boolean contains(String path) {
        return contains(path, false);
    }

    @Override
    public boolean contains(String path, boolean ignoreDefault) {
        return ((ignoreDefault) ? get(path, null) : get(path)) != null;
    }

    @Override
    public boolean isSet(String path) {
        Configuration root = getRoot();
        if (root == null) return false;
        if (root.options().copyDefaults()) return contains(path);
        return get(path, null) != null;
    }

    @Override
    public String getCurrentPath() {
        return fullPath;
    }

    @Override
    public String getName() {
        return path;
    }

    @Override
    public Configuration getRoot() {
        return root;
    }

    @Override
    public ConfigurationSection getParent() {
        return parent;
    }

    @Override
    public void addDefault(String path, Object value) {
        Configuration root = getRoot();
        if (root == null) throw new IllegalStateException("Cannot add default without root");
        if (root == this)throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
        root.addDefault(createPath(this, path), value);
    }

    @Override
    public ConfigurationSection getDefaultSection() {
        Configuration root = getRoot();
        Configuration defaults = root == null ? null : root.getDefaults();
        if (defaults != null)
            if (defaults.isConfigurationSection(getCurrentPath()))
                return defaults.getConfigurationSection(getCurrentPath());
        return null;
    }

    @Override
    public void set(String path, Object value) {
        Configuration root = getRoot();
        if (root == null) throw new IllegalStateException("Cannot use section without a root");

        final char separator = root.options().pathSeparator();
        int i1 = -1, i2;
        ConfigurationSection section = this;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            String node = path.substring(i2, i1);
            ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null) {
                if (value == null) return;
                section = section.createSection(node);
            } else section = subSection;
        }

        String key = path.substring(i2);
        if (section == this) {
            if (value == null) {
                map.remove(key);
            } else map.put(key, value);
        } else section.set(key, value);
    }

    @Override
    public Object get(String path) {
        return get(path, getDefault(path));
    }

    @Override
    public Object get(String path, Object def) {
        if (path.length() == 0) return this;

        Configuration root = getRoot();
        if (root == null) throw new IllegalStateException("Cannot access section without a root");
        final char separator = root.options().pathSeparator();
        int i1 = -1, i2;
        ConfigurationSection section = this;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            section = section.getConfigurationSection(path.substring(i2, i1));
            if (section == null)
                return def;
        }

        String key = path.substring(i2);
        if (section == this) {
            Object result = map.get(key);
            return (result == null) ? def : result;
        }
        return section.get(key, def);
    }

    @Override
    public ConfigurationSection createSection(String path) {
        Configuration root = getRoot();
        if (root == null) throw new IllegalStateException("Cannot create section without a root");

        final char separator = root.options().pathSeparator();
        int i1 = -1, i2;
        ConfigurationSection section = this;
        while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1) {
            String node = path.substring(i2, i1);
            ConfigurationSection subSection = section.getConfigurationSection(node);
            section = subSection == null ? section.createSection(node) : subSection;
        }

        String key = path.substring(i2);
        if (section == this) {
            ConfigurationSection result = new MemorySection(this, key);
            map.put(key, result);
            return result;
        }
        return section.createSection(key);
    }

    @Override
    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        ConfigurationSection section = createSection(path);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                section.createSection(entry.getKey().toString(), (Map<?, ?>) entry.getValue());
            } else section.set(entry.getKey().toString(), entry.getValue());
        }
        return section;
    }

    // Primitives
    @Override
    public String getString(String path) {
        Object def = getDefault(path);
        return getString(path, def != null ? def.toString() : null);
    }

    @Override
    public String getString(String path, String def) {
        Object val = get(path, def);
        return (val != null) ? val.toString() : def;
    }

    @Override
    public boolean isString(String path) {
        Object val = get(path);
        return val instanceof String;
    }

    @Override
    public int getInt(String path) {
        Object def = getDefault(path);
        return getInt(path, (def instanceof Number) ? toInt(def) : 0);
    }

    @Override
    public int getInt(String path, int def) {
        Object val = get(path, def);
        return (val instanceof Number) ? toInt(val) : def;
    }

    public static int toInt(Object object) {
        if (object instanceof Number) return ((Number) object).intValue();
        try {
            return Integer.parseInt(object.toString());
        } catch (NumberFormatException | NullPointerException e) {}
        return 0;
    }

    @Override
    public boolean isInt(String path) {
        Object val = get(path);
        return val instanceof Integer;
    }

    @Override
    public boolean getBoolean(String path) {
        Object def = getDefault(path);
        return getBoolean(path, (def instanceof Boolean) ? (Boolean) def : false);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        Object val = get(path, def);
        return (val instanceof Boolean) ? (Boolean) val : def;
    }

    @Override
    public boolean isBoolean(String path) {
        Object val = get(path);
        return val instanceof Boolean;
    }

    @Override
    public double getDouble(String path) {
        Object def = getDefault(path);
        return getDouble(path, (def instanceof Number) ? toDouble(def) : 0);
    }

    @Override
    public double getDouble(String path, double def) {
        Object val = get(path, def);
        return (val instanceof Number) ? toDouble(val) : def;
    }

    @Override
    public boolean isDouble(String path) {
        Object val = get(path);
        return val instanceof Double;
    }

    @Override
    public long getLong(String path) {
        Object def = getDefault(path);
        return getLong(path, (def instanceof Number) ? toLong(def) : 0);
    }

    @Override
    public long getLong(String path, long def) {
        Object val = get(path, def);
        return (val instanceof Number) ? toLong(val) : def;
    }

    public static double toDouble(Object object) {
        if (object instanceof Number) return ((Number) object).doubleValue();

        try {
            return Double.parseDouble(object.toString());
        } catch (NumberFormatException | NullPointerException e) {}
        return 0;
    }

    public static long toLong(Object object) {
        if (object instanceof Number) return ((Number) object).longValue();

        try {
            return Long.parseLong(object.toString());
        } catch (NumberFormatException | NullPointerException e) {}
        return 0;
    }

    @Override
    public boolean isLong(String path) {
        Object val = get(path);
        return val instanceof Long;
    }

    // Java
    @Override
    public List<?> getList(String path) {
        Object def = getDefault(path);
        return getList(path, (def instanceof List) ? (List<?>) def : null);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        Object val = get(path, def);
        return (List<?>) ((val instanceof List) ? val : def);
    }

    @Override
    public boolean isList(String path) {
        Object val = get(path);
        return val instanceof List;
    }

    @Override
    public List<String> getStringList(String path) {
        List<?> list = getList(path);
        if (list == null) return new ArrayList<String>(0);
        List<String> result = new ArrayList<String>();
        for (Object object : list)
            if ((object instanceof String) || (isPrimitiveWrapper(object)))
                result.add(String.valueOf(object));
        return result;
    }

    @Override
    public <T extends Object> T getObject(String path, Class<T> clazz) {
        Object def = getDefault(path);
        return getObject(path, clazz, (def != null && clazz.isInstance(def)) ? clazz.cast(def) : null);
    }

    @Override
    public <T extends Object> T getObject(String path, Class<T> clazz, T def) {
        Object val = get(path, def);
        return (val != null && clazz.isInstance(val)) ? clazz.cast(val) : def;
    }

    @Override
    public ConfigurationSection getConfigurationSection(String path) {
        Object val = get(path, null);
        if (val != null)
            return (val instanceof ConfigurationSection) ? (ConfigurationSection) val : null;
        val = get(path, getDefault(path));
        return (val instanceof ConfigurationSection) ? createSection(path) : null;
    }

    @Override
    public boolean isConfigurationSection(String path) {
        Object val = get(path);
        return val instanceof ConfigurationSection;
    }

    protected boolean isPrimitiveWrapper(Object input) {
        return input instanceof Integer || input instanceof Boolean || input instanceof Character || input instanceof Byte
                || input instanceof Short || input instanceof Double || input instanceof Long || input instanceof Float;
    }

    protected Object getDefault(String path) {
        Configuration root = getRoot();
        Configuration defaults = root == null ? null : root.getDefaults();
        return (defaults == null) ? null : defaults.get(createPath(this, path));
    }

    protected void mapChildrenKeys(Set<String> output, ConfigurationSection section, boolean deep) {
        if (section instanceof MemorySection) {
            MemorySection sec = (MemorySection) section;

            for (Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.add(createPath(section, entry.getKey(), this));
                if ((deep) && (entry.getValue() instanceof ConfigurationSection)) {
                    ConfigurationSection subsection = (ConfigurationSection) entry.getValue();
                    mapChildrenKeys(output, subsection, deep);
                }
            }
        } else {
            Set<String> keys = section.getKeys(deep);
            for (String key : keys)
                output.add(createPath(section, key, this));
        }
    }

    protected void mapChildrenValues(Map<String, Object> output, ConfigurationSection section, boolean deep) {
        if (section instanceof MemorySection) {
            MemorySection sec = (MemorySection) section;
            for (Map.Entry<String, Object> entry : sec.map.entrySet()) {
                String childPath = createPath(section, entry.getKey(), this);
                output.remove(childPath);
                output.put(childPath, entry.getValue());

                if (entry.getValue() instanceof ConfigurationSection)
                    if (deep)
                        mapChildrenValues(output, (ConfigurationSection) entry.getValue(), deep);
            }
        } else {
            Map<String, Object> values = section.getValues(deep);
            for (Map.Entry<String, Object> entry : values.entrySet())
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
        }
    }

    public static String createPath(ConfigurationSection section, String key) {
        return createPath(section, key, (section == null) ? null : section.getRoot());
    }

    public static String createPath(ConfigurationSection section, String key, ConfigurationSection relativeTo) {
        Configuration root = section.getRoot();
        if (root == null) throw new IllegalStateException("Cannot create path without a root");
        char separator = root.options().pathSeparator();

        StringBuilder builder = new StringBuilder();
        if (section != null) {
            for (ConfigurationSection parent = section; (parent != null) && (parent != relativeTo); parent = parent.getParent()) {
                if (builder.length() > 0)
                    builder.insert(0, separator);
                builder.insert(0, parent.getName());
            }
        }

        if ((key != null) && (key.length() > 0)) {
            if (builder.length() > 0)
                builder.append(separator);
            builder.append(key);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        Configuration root = getRoot();
        return new StringBuilder().append(getClass().getSimpleName()).append("[path='").append(getCurrentPath()).append("', root='").append(root == null ? null : root.getClass().getSimpleName()).append("']").toString();
    }

}