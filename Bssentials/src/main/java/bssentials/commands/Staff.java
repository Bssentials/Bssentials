package bssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CmdInfo(aliases = {"mods", "admins", "staffmembers"})
public class Staff extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {

        message(sender, "Server Staff Members:");
        for (String staff : getConfig().getStringList("staff"))
            message(sender, " -" + staff);

        return false;
    }

}