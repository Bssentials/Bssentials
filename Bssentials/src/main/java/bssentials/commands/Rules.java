package bssentials.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import bssentials.Bssentials;

public class Rules extends BCommand {

    private File file = null;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (null == file)
            file = new File(Bssentials.DATA_FOLDER, "rules.txt");
        try {
            List<String> content = Files.readAllLines(file.toPath());
            for (String line : content)
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}