package bssentials.commands;

import bssentials.api.User;

@CmdInfo(aliases = {"eposition"})
public class GetPos extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length <= 0)
            user.sendMessage(user.isPlayer() ? "&aPos: " + user.getLocation().toString() : "Usage: /getpos <player>");
        user.sendMessage("&aPos: " + getUserByName(args[0]).getLocation().toString());
        return true;
    }

}
