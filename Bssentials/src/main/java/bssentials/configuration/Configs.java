package bssentials.configuration;

import java.io.File;

import bssentials.Bssentials;
import bssentials.configuration.BssConfiguration.ConfigException;

public class Configs {

    public static BssConfiguration MAIN;

    public static void initConfigs() throws ConfigException {
        MAIN = new BssConfiguration(new File(Bssentials.getInstance().getDataFolder(), "config.yml"));
    }

}