package bssentials.commands;

import bssentials.api.User;

@CmdInfo
public class Warp extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length > 0) {
            user.teleport( getPlugin().getWarps().getWarp(args[0]) );

            return true;
        }

        user.sendMessage("&4Usage: /warp <warp>");
        return true;
    }

}