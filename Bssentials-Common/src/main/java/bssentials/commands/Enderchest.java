package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"echest", "ender"})
public class Enderchest extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 0) {
            user.openEnderchest(user);
            return true;
        } else if (args.length == 1) {
            User target = getUserByName(args[0]);
            if (target == null || !target.isOnline()) {
                user.sendMessage("&4Player not found");
                return true;
            }
            user.sendMessage("Opening " + target.getName(false) + "'s enderchest.");
            user.openEnderchest(target);
            return true;
        }
        return true;
    }

}