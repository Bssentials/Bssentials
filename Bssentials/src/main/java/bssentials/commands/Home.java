package bssentials.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"homes"})
public class Home extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        String home = "home";
        if (args.length > 0) home = args[0];
        Location l = User.getByName(sender.getName()).getHome(home);
        if (null != l)
            ((Player) sender).teleport(l);

        return false;
    }

}