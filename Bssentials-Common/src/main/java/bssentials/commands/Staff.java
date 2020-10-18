package bssentials.commands;

import bssentials.api.User;

@CmdInfo(aliases = {"mods", "admins"})
public class Staff extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        user.sendMessage("Server Staff Members:");
        for (String staff : getConfig().getStringList("staff"))
            user.sendMessage(" -" + staff);

        return false;
    }

}