package bssentials.commands;

import java.io.IOException;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class SetWarp extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            user.sendMessage("&4Usage: /setwarp <warp>");
            return true;
        }

        try {
            getPlugin().getWarps().setWarp(args[0], user.getLocation());
            user.sendMessage("&aWarp set!");
        } catch (IOException e) {
            e.printStackTrace();
            user.sendMessage("&4Unable to write to " + args[0] + ".yml");
        }
        return true;
    }

}