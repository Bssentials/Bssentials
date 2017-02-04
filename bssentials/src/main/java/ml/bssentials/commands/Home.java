package ml.bssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.bssentials.main.Bssentials;

public class Home implements CommandExecutor {
    private Bssentials main;
    
    public Home(Bssentials bss) {
        main = bss;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You are not a player");
            return false;
        }
        Player player = (Player) sender;

        /* SETHOME COMMAND */
        if(cmd.getName().equalsIgnoreCase("sethome")) {
            main.createHome(player);
        }
        
        /* DELHOME COMMAND */
        if(cmd.getName().equalsIgnoreCase("delhome")) {
            main.delHome(player);
        }
        
        /* HOME COMMAND */
        if(cmd.getName().equalsIgnoreCase("home")) {
            if (main.getHomeConfig().getConfigurationSection("homes." + player.getName()) == null) {
                sender.sendMessage(ChatColor.RED + "You have to set your home first /sethome");
            } else {
                if (args.length == 0) {
                    World w = Bukkit.getServer().getWorld(main.getHomeConfig().getString("homes." + player.getName() + ".world"));
                    double x = main.getHomeConfig().getDouble("homes." + player.getName() + ".x");
                    double y = main.getHomeConfig().getDouble("homes." + player.getName() + ".y");
                    double z = main.getHomeConfig().getDouble("homes." + player.getName() + ".z");
                    player.teleport(new Location(w, x, y, z));
                    sender.sendMessage(ChatColor.GREEN + "Welcome home " + player.getName() + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid args");
                }
            }
        }
        return true;
    }

}
