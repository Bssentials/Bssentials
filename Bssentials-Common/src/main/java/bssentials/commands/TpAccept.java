package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"tpyes", "tpayes"})
public class TpAccept extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (Tpa.tpaMap.containsKey(user.getName(false))) {
            User from = getUserByName( Tpa.tpaMap.get(user.getName(false)) );
            user.sendMessage("&aTeleport Request accepted.");
            from.sendMessage("&aTeleport Request accepted.");
            from.teleport(user.getLocation());
            Tpa.tpaMap.remove(user.getName(false));
        }
        user.sendMessage("&4You have no teleport requests to accept");
        return true;
    }

}
