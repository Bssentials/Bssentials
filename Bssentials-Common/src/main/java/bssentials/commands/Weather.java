package bssentials.commands;

import bssentials.Bssentials;
import bssentials.api.IWorld;
import bssentials.api.User;

@CmdInfo(aliases = {"sun", "rain"}, onlyPlayer = true)
public class Weather extends BCommand {

    @Override
    public boolean onCommand(User user, String l, String[] args) {
        if (l.equalsIgnoreCase("sun")) {
            user.getWorld().setStorm(false);
            user.sendMessage("Set weather to CLEAR in " + user.getWorld().getName());
            return true;
        }
        if (l.equalsIgnoreCase("rain")) {
            user.getWorld().setStorm(true);
            user.sendMessage("Set weather to STORM in " + user.getWorld().getName());
            return true;
        }

        if (args.length < 1) {
            user.sendMessage("&4Usage: /weather <sun|rain> [world]");
            return true;
        }
        IWorld w = user.getWorld();
        if (args.length > 1) w = Bssentials.getInstance().getWorld(args[1]);

        if (args[0].equalsIgnoreCase("sun")) {
            w.setStorm(false);
            user.sendMessage("Set weather to CLEAR in " + w.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("rain")) {
            w.setStorm(true);
            user.sendMessage("Set weather to STORM in " + w.getName());
            return true;
        }
        return true;
    }

}