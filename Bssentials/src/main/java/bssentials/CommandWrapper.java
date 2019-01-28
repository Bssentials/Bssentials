package bssentials;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import bssentials.commands.BCommand;

public class CommandWrapper extends Command {

    private BCommand ex;

    protected CommandWrapper(String name, BCommand base) {
        super(name);
        this.setEx(base);
    }

    @Override
    public List<String> getAliases() {
        return ex.aliases;
    }

    public void setEx(BCommand ex) {
        this.ex = ex;
    }

    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        return ex.onCommand(arg0, this, arg1, arg2);
    }

}
