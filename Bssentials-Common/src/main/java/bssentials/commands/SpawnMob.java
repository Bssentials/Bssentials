package bssentials.commands;

import java.util.HashMap;
import java.util.Set;

import bssentials.api.User;

@CmdInfo(aliases = {"mob"})
public class SpawnMob extends BCommand {

    public static HashMap<String, Object> mobs = new HashMap<>();

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (args.length == 0) {
            user.sendMessage("&4" + join(mobs.keySet(), "&7,&a"));
            return true;
        }

        if (!(user.isPlayer())) {
            User target = getUserByName(args[1]);
            target.getWorld().spawnEntity(user.getLocation(), args[0], mobs.get(args[0]));
            target.sendMessage("&aSpawned " + args[0]);
            user.sendMessage("Spawned mob on " + target.getName(true));
        } else {
            user.getWorld().spawnEntity(user.getLocation(), args[0], mobs.get(args[0]));
            user.sendMessage("&aSpawned " + args[0]);
        }
        return true;
    }

    private String join(Set<String> mobs, String string) {
        String s = "Mobs: ";
        for (String sp : mobs) s += "&a" + sp + string;

        return s;
    }

}
