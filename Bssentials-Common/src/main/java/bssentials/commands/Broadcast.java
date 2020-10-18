package bssentials.commands;

import bssentials.Bssentials;
import bssentials.api.User;

@CmdInfo
public class Broadcast extends BCommand {

    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("&4Usage: /broadcast <message>");
            return true;
        }

        String message = join(args, " ");
        Bssentials.getInstance().broadcastMessage("&4[Broadcast] &r" + message);
        return true;
    }

    public static String join(String[] strs, String seperator) {
        String str = "";
        for (String s : strs)
            str += s + seperator;
        return str;
    }

}
