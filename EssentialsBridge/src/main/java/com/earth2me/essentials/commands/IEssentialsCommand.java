package com.earth2me.essentials.commands;

import org.bukkit.Server;
import org.bukkit.command.Command;

import com.earth2me.essentials.CommandSource;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentialsModule;
import com.earth2me.essentials.User;

public interface IEssentialsCommand {

	public String getName();

	public void setEssentials(Essentials essentials);

	public void setEssentialsModule(IEssentialsModule module);

	public void run(Server server, CommandSource sender, String commandLabel, Command command, String[] args);

	public void run(Server server, User user, String commandLabel, Command command, String[] args);

}
