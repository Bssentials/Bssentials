package bssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Ping extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        sender.sendMessage("Pong!"); // TODO show ms
        return false;
    }

}
