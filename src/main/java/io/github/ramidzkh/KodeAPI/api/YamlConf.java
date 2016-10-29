package io.github.ramidzkh.KodeAPI.api;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlConf {

	public YamlConf() {}
	
	public YamlConfiguration loadConfig(File path, String file) {
		File f = new File(path, file + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		return config;
	}
	
	public YamlConfiguration readFile(File path, String filename) {
		File f = new File(path, filename);
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public void loadFromJar(File file) {/*To do*/}
	public void writeTo(File file, String text) {/*To do*/}
	public void writeTo(String file, String text) {
		File file1 = new File(file);
		writeTo(file1, text);
	}
	
	public static void saveConf(FileConfiguration config, File file) {
		try {
			config.save(file); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
