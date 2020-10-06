package bssentials.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import bssentials.Bssentials;
import bssentials.api.User;

@CmdInfo(permission = "NONE")
public class MOTD extends BCommand {

    private File file = null;

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (null == file)
            file = new File(Bssentials.DATA_FOLDER, "motd.txt");
        try {
            List<String> content = Files.readAllLines(file.toPath());
            for (String line : content) {
                line = line.replace("{PLAYER}", user.getName(true))
                           .replace("{ONLINE}", "" + Bukkit.getOnlinePlayers().size());
                user.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}