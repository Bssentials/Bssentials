package ml.bssentials.commands;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import ml.bssentials.main.Bssentials;
import ml.bssentials.main.Perms;

public class Staff extends CommandBase {
    public Bssentials main;

    public Staff(Bssentials m) {
        this.main = m;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase("staff")) {
            if (args.length == 0) {
                if (sender.hasPermission(Perms.STAFFLIST.permission)) {
                    sender.sendMessage(ChatColor.GREEN + "[Bssentials] Staff:");

                    Set<String> keys = main.getConfig().getConfigurationSection("staff").getKeys(false);
                    sender.sendMessage(ChatColor.BLUE + StringUtils.join(keys, ", "));
                } else if (args.length > 1 && args[0].equalsIgnoreCase("add")) {
                    List<String> staffList = main.getConfig().getStringList("staff");
                    String staff = args[1].toLowerCase();

                    if (!staffList.contains(staff)) {
                        staffList.add(staff);
                        main.getConfig().set("staff", staffList);
                        main.saveConfig();
                        sendMessage(sender, "Staff member added to the list");
                    } else {
                        sendMessage(sender,
                                ChatColor.RED + "Error! The name you have typed is already not on the list");
                    }
                } else if (args.length > 1 && args[0].equalsIgnoreCase("remove")) {
                    List<String> staffList = main.getConfig().getStringList("staff");
                    String staff = args[1].toLowerCase();

                    if (staffList.contains(staff)) {
                        staffList.remove(staff);
                        main.getConfig().set("staff", staffList);
                        main.saveConfig();
                        sender.sendMessage("staff member removed from the list");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error! The name you have typed is already not on the list");
                        return false;
                    }
                }
            }
            return false;
        }
        return false;
    }
}
