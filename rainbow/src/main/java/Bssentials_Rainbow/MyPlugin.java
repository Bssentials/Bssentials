package Bssentials_Rainbow;

import PluginReference.MC_Server;
import PluginReference.PluginBase;

import Bssentials_Rainbow.commands.Broadcast;
import Bssentials_Rainbow.commands.Nuke;
import Bssentials_Rainbow.commands.Ping;
import Bssentials_Rainbow.commands.Pm;
import Bssentials_Rainbow.commands.RemoveLag;
import Bssentials_Rainbow.commands.Staff;
import Bssentials_Rainbow.commands.UUIDCommand;
import Bssentials_Rainbow.commands.ViewNick;
import Bssentials_Rainbow.commands.spawnmob;

public class MyPlugin extends PluginBase {
    public MC_Server server;

    @Override
    public void onStartup(MC_Server server) {
        log("Bssentials starting up!");
        log("Your running Bssentials for the Rainbow API! This version of Bssentials is unstable!");
        this.server = server;

        server.registerCommand(new RemoveLag());
        server.registerCommand(new Pm());
        server.registerCommand(new UUIDCommand());
        server.registerCommand(new ViewNick());
        server.registerCommand(new Nuke());
        server.registerCommand(new Ping());
        server.registerCommand(new spawnmob());
        server.registerCommand(new Broadcast());
        server.registerCommand(new Staff(this));
    }

    public void log(Object message) {
        System.out.println("[Bssentials] " + message);
    }
}
