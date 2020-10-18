package bssentials.commands;

import bssentials.Bssentials;
import bssentials.api.User;

@CmdInfo
public class Pm extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 2) {
            user.sendMessage("&4Usage /pm <player> <message>");
            return false;
        }
        User target = getUserByName(args[0]);
        if(target != null) {
            String message = Bssentials.translateAlternateColorCodes('&', join(args));
            String format = "&6[%s&6" + " -> %s&6]";

            target.sendMessage(String.format(format, user.getName(true), "me") + message);
            user.sendMessage(String.format(format, "me", target.getName(true)) + message);
        } else user.sendMessage("That player is not currently online!");

        return false;
    }

    public String join(String[] args) {
        String result = " ";

        int i = 0;
        for (String s : args) {
            if (i > 0) result = result + " " + s;
            i++;
        }

        return result.trim();
    }

}