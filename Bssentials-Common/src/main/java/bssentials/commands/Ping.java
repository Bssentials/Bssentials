package bssentials.commands;

import bssentials.api.User;

@CmdInfo
public class Ping extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        user.sendMessage(user.isPlayer() ? "&aYour Ping: " + user.getPing() + " ms" : "Your Ping: 0ms");
        return true;
    }

}