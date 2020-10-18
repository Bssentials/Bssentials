package bssentials.commands;

import bssentials.api.User;

@CmdInfo
public class Fly extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 2 && user.isAuthorized("bssentials.command.fly.other")) {
            User target = getUserByName(args[0]);
            boolean b = target.isAllowedToFly();
            target.setAllowFly(!b);
            String m = "&3Set flight " + !b;
            target.sendMessage(m);
            user.sendMessage(m);
        } else if (args.length == 0) {
            boolean b = user.isAllowedToFly();
            user.setAllowFly(!b);

            user.sendMessage("&3Set flight " + !b);
        } else user.sendMessage("&4Usage: /fly OR /fly [player]");

        return false;
    }

}
