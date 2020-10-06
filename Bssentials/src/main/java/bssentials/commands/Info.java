package bssentials.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import bssentials.Bssentials;
import bssentials.api.User;

@CmdInfo(aliases = {"about"}, permission = "NONE")
public class Info extends BCommand {
    
    private File file = null;

    @Override
    public boolean onCommand(User user, String label, String[] args) {
        if (null == file)
            file = new File(Bssentials.DATA_FOLDER, "info.txt");
        try {
            List<String> content = Files.readAllLines(file.toPath());
            for (String line : content) {
                // TODO Chapters
                user.sendMessage(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
