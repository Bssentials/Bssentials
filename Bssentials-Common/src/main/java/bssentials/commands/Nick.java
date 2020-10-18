package bssentials.commands;

import bssentials.Bssentials;
import bssentials.api.User;

@CmdInfo
public class Nick extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 1) {
            user.sendMessage("&4Usage: /nick <nickname>");
            return false;
        }

        String nick = Bssentials.translateAlternateColorCodes('&', args[0].replaceAll("[SPACECHAR]", " "));
        if (nick.equals("reset"))
            nick = user.getName(false);

        try {
            user.setNick(nick);
            user.sendMessage("&aNickname set to: " + nick);
        } catch (Exception e) {
            user.sendMessage("&4Nickname is too long!");
        }
        return true;
    }

}
