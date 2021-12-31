package bssentials.fabric.commands;

import java.util.ArrayList;
import java.util.Collection;

import bssentials.api.User;
import bssentials.commands.BCommand;
import bssentials.commands.CmdInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

@CmdInfo(aliases = {})
public class ModsCommand extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        user.sendMessage("Mods:");
        
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();

        String con = "";
        for (ModContainer mod : mods) {
            String id = mod.getMetadata().getId();
            if (id.startsWith("fabric-") || id.equals("java")) continue; // Avoid 50 listings for Fabric API
            con += ", &a" + mod.getMetadata().getName() + "&f";
        }
        
        con = con.substring(1).trim();
        user.sendMessage(con);
        return false;
    }

}