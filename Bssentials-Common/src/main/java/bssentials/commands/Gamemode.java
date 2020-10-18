package bssentials.commands;

import bssentials.api.User;

@CmdInfo(aliases = {"gm", "gmc", "gms", "gmsp", "gma"}, onlyPlayer = true)
public class Gamemode extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (label.length() < 5)
            return onShortCommand(user, label);

        if (args.length <= 0) {
            user.sendMessage("Usage: /gamemode <mode> [player]");
            return false;
        }

        if (args.length > 0) {
            String g = args[0];
            if (g.length() == 1) {
                char c = g.charAt(0);
                switch (c) {
                    case '0':
                    case 's':
                        user.setGameMode(0);
                        break;
                    case '1':
                    case 'c':
                        user.setGameMode(1);
                        break;
                    case '2':
                    case 'a':
                        user.setGameMode(2);
                        break;
                    case '3':
                        user.setGameMode(3);
                        break;
                }
                return true;
            }
            if (g.length() == 2) {
                user.setGameMode(3);
                return true;
            }
            if (g.equalsIgnoreCase("survival")) user.setGameMode(0);
            if (g.equalsIgnoreCase("creative")) user.setGameMode(1);
            if (g.equalsIgnoreCase("adventure")) user.setGameMode(2);
        }
        return false;
    }

    public boolean onShortCommand(User sender, String g) {
        if (g.equalsIgnoreCase("gmc"))  sender.setGameMode(1);
        if (g.equalsIgnoreCase("gms"))  sender.setGameMode(0);
        if (g.equalsIgnoreCase("gma"))  sender.setGameMode(2);
        if (g.equalsIgnoreCase("gmsp")) sender.setGameMode(3);

        return true;
    }

}