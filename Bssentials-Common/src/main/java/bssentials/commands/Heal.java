package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Heal extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (label.equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                if (hasPerm(user, label)) {
                    user.setHealthAndFoodLevel(20, 20);
                    user.sendMessage("&aYou have been healed!");
                    return true;
                } else {
                    user.sendMessage("No Permission");
                    return false;
                }
            } else {
                if (user.isAuthorized("bssentials.commands.heal.other")) {
                    User target = getUserByName(args[0]);
                    if (target == null) {
                        user.sendMessage("&4Could not find player!");
                        return false;
                    } else {
                        target.setHealthAndFoodLevel(20, 20);
                        target.sendMessage("&aYou have been healed!");
                        user.sendMessage("&a" + target.getName(true) + " has been healed!");
                        return true;
                    }
                } else {
                    user.sendMessage("No Permission");
                    return false;
                }
            }
        }

        if (label.equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                if (hasPerm(user, label)) {
                    user.setHealthAndFoodLevel(-1, 20);
                    user.sendMessage("&aYou have been fed!");
                } else user.sendMessage("&4No Permission");

                return true;
            } else {
                if (user.isAuthorized("bssentials.commands.feed.other")) {
                    User target = getUserByName(args[0]);
                    if (target == null) {
                        user.sendMessage("&4Could not find player!");
                        return false;
                    } else {
                        target.setHealthAndFoodLevel(-1, 20);
                        target.sendMessage("&aYou have been fed!");
                        user.sendMessage("&a" + target.getName(true) + " has been fed!");
                        return true;
                    }
                } else {
                    user.sendMessage("&4No Permission");
                    return false;
                }
            }
        }
        return false;
    }

}