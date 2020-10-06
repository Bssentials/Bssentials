package bssentials.commands;

import java.util.Locale;

import org.bukkit.GameMode;
import bssentials.api.User;

@CmdInfo(aliases = {"gm", "gmc", "gms", "gmsp", "gma"}, onlyPlayer = true)
public class Gamemode extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (label.length() < 5)
            return onShortCommand(user, label);

        if (args.length <= 0) {
            message(user, "Usage: /gamemode <mode> [player]");
            return false;
        }

        if (args.length > 0) {
            String g = args[0];
            if (g.length() == 1) {
                char c = g.charAt(0);
                switch (c) {
                    case '0':
                    case 's':
                        user.setGameMode(GameMode.SURVIVAL);
                        break;
                    case '1':
                    case 'c':
                        user.setGameMode(GameMode.CREATIVE);
                        break;
                    case '2':
                    case 'a':
                        user.setGameMode(GameMode.ADVENTURE);
                        break;
                    case '3':
                        user.setGameMode(GameMode.SPECTATOR);
                        break;
                }
                return true;
            }
            if (g.length() == 2) {
                user.setGameMode(GameMode.SPECTATOR);
                return true;
            }
            user.setGameMode(GameMode.valueOf(g.toUpperCase(Locale.ENGLISH)));
        }
        return false;
    }

    public boolean onShortCommand(User sender, String g) {
        if (g.equalsIgnoreCase("gmc"))  sender.setGameMode(GameMode.CREATIVE);
        if (g.equalsIgnoreCase("gms"))  sender.setGameMode(GameMode.SURVIVAL);
        if (g.equalsIgnoreCase("gma"))  sender.setGameMode(GameMode.ADVENTURE);
        if (g.equalsIgnoreCase("gmsp")) sender.setGameMode(GameMode.SPECTATOR);

        return true;
    }

}