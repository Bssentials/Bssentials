package bssentials.commands;

import bssentials.api.User;

@CmdInfo(aliases = {"bss", "ess", "essentials"})
public class BssentialsCmd extends BCommand {

    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        sender.sendMessage("&aRunning Bssentials " + getPlugin().getVersion());
        sender.sendMessage("&aAuthors: https://bit.ly/bssentialscontributors");
        sender.sendMessage(" - &aBy Isaiah, ramidzkh, krisp, Machine-Maker");

        return true;
    }

}