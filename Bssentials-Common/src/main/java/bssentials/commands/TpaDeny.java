package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true, aliases = {"tpdeny", "tpno", "tpano"})
public class TpaDeny extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (Tpa.tpaMap.containsKey(user.getName(false))) {
            User from = getUserByName( Tpa.tpaMap.get(user.getName(false)) );
            user.sendMessage("&aTeleport Request denied.");
            from.sendMessage("&aTeleport Request denied.");
            Tpa.tpaMap.remove(user.getName(false));
        }
        user.sendMessage("&4You have no teleport requests to deny");
        return true;
    }

}
