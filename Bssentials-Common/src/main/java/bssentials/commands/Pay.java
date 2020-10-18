package bssentials.commands;

import java.math.BigDecimal;

import bssentials.api.User;

@CmdInfo
public class Pay extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length < 2) {
            user.sendMessage("&4Usage: /pay <player> <amount>");
            return true;
        }

        User target = getUserByName(args[0]);

        BigDecimal d = new BigDecimal(args[1]);
        user.setMoney(user.getMoney().subtract(d));
        target.setMoney(target.getMoney().add(d));

        user.sendMessage("&a $" + args[1] + " sent to " + args[0]);
        target.sendMessage("&a" + user.getName(true) + " has given you $" + args[1]);

        return true;
    }

}
