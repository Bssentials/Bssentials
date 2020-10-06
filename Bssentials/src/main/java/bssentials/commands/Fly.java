package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo
public class Fly extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 2 && user.isAuthorized("bssentials.command.fly.other")) {
            User target = getUserByName(args[0]);
            boolean b = target.isAllowedToFly();
            target.setAllowFly(!b);
            String m = ChatColor.DARK_AQUA + "Set flight " + !b;
            target.sendMessage(m);
            message(user, m);
        } else if (args.length == 0) {
            boolean b = user.isAllowedToFly();
            user.setAllowFly(!b);

            message(user, ChatColor.DARK_AQUA + "Set flight " + !b);
        } else message(user, ChatColor.RED + "Usage: /fly OR /fly [player]");

        return false;
    }

}
