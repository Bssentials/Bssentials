package bssentials.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import bssentials.Bssentials;
import bssentials.api.User;

public abstract class BCommand implements CommandExecutor {

    public boolean onlyPlayer;
    public CmdInfo info;
    private Bssentials bss;

    public List<String> aliases = new ArrayList<String>();

    public BCommand() {
        this.bss = Bssentials.getInstance();
        CmdInfo i = this.getClass().getAnnotation(CmdInfo.class);
        onlyPlayer = false;
        if (null != i) {
            onlyPlayer = i.onlyPlayer();
            this.info = i;
            for (String s : i.aliases())
                aliases.add(s);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (onlyPlayer && !(sender instanceof Player)){
            sender.sendMessage("Player only command!");
            return false;
        }
        if (!(hasPerm(sender, cmd))){
            message(sender, ChatColor.RED + "No permission for command.");
            return false;
        }

        return onCommand(bss.getUser(sender.getName()), string, args);
    }

    @Deprecated
    public boolean hasPerm(CommandSender s, Command cmd) {
        if (null != info.permission() && info.permission().length() > 3) {
            String ip = info.permission();
            if (ip.equalsIgnoreCase("RQUIRES_OP")) return s.isOp();
            if (ip.equalsIgnoreCase("NONE")) return true;

            return s.hasPermission(ip);
        }

        String c = cmd.getName().toLowerCase();
        return (s.isOp() || s.hasPermission("bssentials.command." + c) || s.hasPermission("essentials." + c)
                || s.hasPermission("bssentials.command.*"));
    }

    @Deprecated
    public boolean hasPerm(User s, Command cmd) {
        if (null != info.permission() && info.permission().length() > 3) {
            String ip = info.permission();
            if (ip.equalsIgnoreCase("RQUIRES_OP")) return s.isOp();
            if (ip.equalsIgnoreCase("NONE")) return true;

            return s.isAuthorized(ip);
        }

        String c = cmd.getName().toLowerCase();
        return (s.isOp() || s.isAuthorized("bssentials.command." + c) || s.isAuthorized("essentials." + c) || s.isAuthorized("bssentials.command.*"));
    }

    public boolean hasPerm(User s, String cmd) {
        if (null != info.permission() && info.permission().length() > 3) {
            String ip = info.permission();
            if (ip.equalsIgnoreCase("RQUIRES_OP")) return s.isOp();
            if (ip.equalsIgnoreCase("NONE")) return true;

            return s.isAuthorized(ip);
        }
        String c = cmd.toLowerCase();
        return (s.isOp() || s.isAuthorized("bssentials.command." + c) || s.isAuthorized("essentials." + c) || s.isAuthorized("bssentials.command.*"));
    }

    @Deprecated
    public boolean message(CommandSender cs, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);

        boolean isPlr = cs instanceof Player;
        cs.sendMessage(isPlr ? message : ChatColor.stripColor(message));
        return isPlr;
    }

    public boolean message(User user, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);

        boolean isPlr = user.isPlayer();
        user.sendMessage(message);
        return isPlr;
    }

    public FileConfiguration getConfig() {
        return bss.getConfig();
    }

    public Bssentials getPlugin() {
        return bss;
    }

    public User getUserByName(String name) {
        return bss.getUser(name);
    }

    public abstract boolean onCommand(User sender, String label, String[] args);

}
