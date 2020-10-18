package bssentials.commands;

import java.util.ArrayList;
import java.util.List;

import bssentials.Bssentials;
import bssentials.api.IBssentials;
import bssentials.api.User;
import bssentials.include.FileConfiguration;

public abstract class BCommand {

    public boolean onlyPlayer;
    public CmdInfo info;
    private IBssentials bss;

    public List<String> aliases = new ArrayList<String>();

    public BCommand() {
        this.bss = Bssentials.getInstance();
        CmdInfo i = this.getClass().getAnnotation(CmdInfo.class);
        onlyPlayer = false;
        if (null != i) {
            onlyPlayer = i.onlyPlayer();
            this.info = i;
            for (String s : i.aliases())
                aliases.add(s);
        }
    }

    public boolean hasPerm(User s, String cmd) {
        if (null != info.permission() && info.permission().length() > 3) {
            String ip = info.permission();
            if (ip.equalsIgnoreCase("RQUIRES_OP")) return s.isOp();
            if (ip.equalsIgnoreCase("NONE")) return true;

            return s.isAuthorized(ip);
        }
        String c = cmd.toLowerCase();
        return (s.isOp() || s.isAuthorized("bssentials.command." + c) || s.isAuthorized("essentials." + c) || s.isAuthorized("bssentials.command.*"));
    }

    public FileConfiguration getConfig() {
        return bss.getMainConfig();
    }

    public IBssentials getPlugin() {
        return bss;
    }

    public User getUserByName(String name) {
        return bss.getUser(name);
    }

    public abstract boolean onCommand(User sender, String label, String[] args);

}
