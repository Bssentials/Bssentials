package com.earth2me.essentials;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

import bssentials.Bssentials;

public class Essentials extends PluginBase {

    private Bssentials bss;
    public Essentials(Bssentials bss) {
        this.bss = bss;
    }

    @Override
    public File getDataFolder() {
        return bss.getDataFolder();
    }

    @Override
    public PluginDescriptionFile getDescription() {
        return bss.getDescription();
    }

    @Override
    public FileConfiguration getConfig() {
        return bss.getConfig();
    }

    @Override
    public InputStream getResource(String filename) {
        return bss.getResource(filename);
    }

    @Override
    public void saveConfig() {
        bss.saveConfig();
    }

    @Override
    public void saveDefaultConfig() {
        bss.saveDefaultConfig();
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        bss.saveResource(resourcePath, replace);
    }

    @Override
    public void reloadConfig() {
        bss.reloadConfig();
    }

    @Override
    public PluginLoader getPluginLoader() {
        return bss.getPluginLoader();
    }

    @Override
    public Server getServer() {
        return bss.getServer();
    }

    @Override
    public boolean isEnabled() {
        return bss.isEnabled();
    }

    @Override
    public void onDisable() {
        bss.onDisable();
    }

    @Override
    public void onLoad() {
        bss.onLoad();
    }

    @Override
    public void onEnable() {
        bss.onEnable();
    }

    @Override
    public boolean isNaggable() {
        return bss.isNaggable();
    }

    @Override
    public void setNaggable(boolean canNag) {
        bss.setNaggable(canNag);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return bss.getDefaultWorldGenerator(worldName, id);
    }

    @Override
    public Logger getLogger() {
        return bss.getLogger();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return bss.onTabComplete(sender, command, alias, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return bss.onCommand(sender, command, label, args);
    }

}