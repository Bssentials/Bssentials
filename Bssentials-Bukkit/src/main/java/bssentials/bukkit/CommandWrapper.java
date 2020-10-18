package bssentials.bukkit;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.api.User;
import bssentials.commands.BCommand;

public class CommandWrapper extends Command {

    private BCommand ex;

    protected CommandWrapper(String name, BCommand base) {
        super(name);
        this.setEx(base);
    }

    @Override
    public List<String> getAliases() {
        return ex.aliases;
    }

    public void setEx(BCommand ex) {
        this.ex = ex;
    }

    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        if (ex.onlyPlayer && !(arg0 instanceof Player)){
            arg0.sendMessage("Player only command!");
            return false;
        }
        User user = ex.getUserByName(arg0.getName());
        if (!ex.hasPerm(user, this.getName())){
            user.sendMessage(ChatColor.RED + "No permission for command.");
            return false;
        }

        return ex.onCommand(user, arg1, arg2);
    }

}