package bssentials.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import bssentials.Bssentials;

public class BssConfiguration extends YamlConfiguration {

    public File file;

    public BssConfiguration(File f) throws ConfigException {
        super();
        this.file = f;
        try {
            this.load(f);
        } catch (IOException | InvalidConfigurationException e) {
            if (!f.exists()) {
                Bssentials.get().getLogger().info("Configuration file \"" + f.getName() + "\" does not exist. Creating new configuration...");
                try {
                    saveDefaultConfig();
                } catch (URISyntaxException | IOException e1) {
                    throw new ConfigException(e1);
                }
            } else throw new ConfigException(e);
        }
    }

    public void saveDefaultConfig() throws URISyntaxException, IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(file.getName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String s = "";
        String str = "";
        while ((s = reader.readLine()) != null)
            str += s + System.lineSeparator();

        if (!file.exists()) {
            file.createNewFile();
        } else return;
        Files.write(file.toPath(), str.getBytes());
    }

    public class ConfigException extends Exception {
        private static final long serialVersionUID = 1L;

        public ConfigException(Exception e) {
            super(e);
        }

    }

}