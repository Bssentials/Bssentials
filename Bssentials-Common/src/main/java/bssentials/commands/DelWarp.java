package bssentials.commands;

import bssentials.api.User;

@CmdInfo
public class DelWarp extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            user.sendMessage("&4Usage: /delwarp <warp>");
            return true;
        }

        user.sendMessage(getPlugin().getWarps().removeWarp(args[0]) ? ("&aWarp removed!") : ("&4Unable to remove warp!"));
        return true;
    }

}