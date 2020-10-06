package bssentials.commands;

import org.bukkit.ChatColor;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Hat extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (user.getItemInMainHand() != 0) {
            user.setHelmentToMainHandItem();
            user.sendMessage(ChatColor.YELLOW + "Item successfuly put on your head.");
            return true;
        }
        user.sendMessage(ChatColor.YELLOW + "You must have something to put on your head!");
        return true;
    }

}