package bssentials.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BCommand implements CommandExecutor {

    public boolean onlyPlayer;
    public CmdInfo i;

    public List<String> aliases = new ArrayList<String>();

    public BCommand() {
        CmdInfo i = this.getClass().getAnnotation(CmdInfo.class);
        onlyPlayer = false;
        if (null != i) {
            onlyPlayer = i.onlyPlayer();
            this.i = i;
            for (String s : i.aliases()) {
                aliases.add(s);
            }
        }
    }

    public boolean addAlias(String e) {
        return aliases.add(e);
    }

    public boolean removeAlias(String e) {
        return aliases.remove(e);
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

        return onCommand(sender, cmd, args);
    }

    public boolean hasPerm(CommandSender s, Command cmd) {
        if (null != i.permission()) {
            String ip = i.permission();
            if (ip.equalsIgnoreCase("RQUIRES_OP")) return s.isOp();
            if (ip.equalsIgnoreCase("NONE")) return true;

            return s.hasPermission(ip);
        }
        String c = cmd.getName().toLowerCase(Locale.ENGLISH);
        return (s.isOp() || s.hasPermission("bssentials.command." + c) || s.hasPermission("essentials." + c)
                || s.hasPermission("accentials.command." + c) || s.hasPermission("bssentials.command.*"));
    }

    public void message(CommandSender cs, String message) {
        if (cs instanceof Player) cs.sendMessage(message);
        else cs.sendMessage(ChatColor.stripColor(message));
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String[] args);

}