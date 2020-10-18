package bssentials.commands;

import bssentials.api.User;

@CmdInfo(onlyPlayer = true)
public class Hat extends BCommand {

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (user.getItemInMainHand() != 0) {
            user.setHelmentToMainHandItem();
            user.sendMessage("&6Item successfuly put on your head.");
            return true;
        }
        user.sendMessage("&4You must have something to put on your head!");
        return true;
    }

}