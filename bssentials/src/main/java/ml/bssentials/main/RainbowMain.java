package ml.bssentials.main;

import PluginReference.MC_Server;
import PluginReference.PluginBase;
import ml.bssentials.commands.Broadcast;
import ml.bssentials.commands.Nuke;
import ml.bssentials.commands.Ping;
import ml.bssentials.commands.Pm;
import ml.bssentials.commands.RemoveLag;
import ml.bssentials.commands.Staff;
import ml.bssentials.commands.UUIDCommand;
import ml.bssentials.commands.ViewNick;
import ml.bssentials.commands.spawnmob;

/**
 * Bssentials for Project Rainbow
 *
 * @author Isaiah Patton
 */
public class RainbowMain extends PluginBase {
    public MC_Server server;

    @Override
    public void onStartup(MC_Server server) {
        log("Bssentials starting up!");
        log("Your running Rainbow API! This version of Bssentials is unstable!");
        this.server = server;

        server.registerCommand(new RemoveLag());
        server.registerCommand(new Pm());
        server.registerCommand(new UUIDCommand());
        server.registerCommand(new ViewNick());
        server.registerCommand(new Nuke());
        server.registerCommand(new Ping());
        server.registerCommand(new spawnmob());
        server.registerCommand(new Broadcast());
        // TODO: server.registerCommand(new Staff());
    }

    public void log(Object message) {
        System.out.println("[Bssentials] " + message);
    }

    public void onPlayerLogin() {

    }
}
