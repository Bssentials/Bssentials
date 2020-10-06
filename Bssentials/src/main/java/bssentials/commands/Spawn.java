package bssentials.commands;

import bssentials.api.User;

@CmdInfo
public class Spawn extends Warp {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 0) {
            user.teleport(getPlugin().getWarps().getWarp("spawn"));
            return true;
        }

        user.sendMessage("&4Usage: /spawn");
        return true;
    }

}