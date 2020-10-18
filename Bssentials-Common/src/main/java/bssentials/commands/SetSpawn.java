package bssentials.commands;

import java.io.IOException;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class SetSpawn extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length > 1) {
            user.sendMessage("&4Usage: /setspawn");
            return true;
        }

        try {
            getPlugin().getWarps().setWarp("spawn", user.getLocation());
            user.sendMessage("&aSpawn set!");
        } catch (IOException e) {
            e.printStackTrace();
            user.sendMessage("&4Unable to write to spawn.yml");
        }
        return true;
    }

}