package bssentials.commands;

import bssentials.api.Econ;
import bssentials.api.User;

@CmdInfo(aliases = {"bal", "money"})
public class Balance extends BCommand {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        try {
            sender.sendMessage("&aBalance: &c" + Econ.getMoney(sender.getName(false)));
        } catch (Exception e) {
            sender.sendMessage("&4Exception while getting balance: " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

}