package bssentials.commands;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import bssentials.api.User;

@CmdInfo(aliases = {"bss", "ess", "essentials"})
public class BssentialsCmd extends BCommand {

    @Override
    public boolean onCommand(User sender, String label, String[] args) {
        sender.sendMessage("&aRunning Bssentials " + getPlugin().getVersion());
        sender.sendMessage("&aAuthors: https://bit.ly/bssentialscontributors");
        sender.sendMessage(" - &aBy Isaiah, ramidzkh, krisp, Machine-Maker");

        if (args.length > 0 && args[0].equalsIgnoreCase("list")) {
            sender.sendMessage("&aCommand &0|&a Aliases &0|&a Permission");
            for (Class<?> c : getCommandClasses()) {
                CmdInfo i = c.getAnnotation(CmdInfo.class);
                sender.sendMessage("&a/" + c.getSimpleName().toLowerCase(Locale.ENGLISH)
                        + " &0|&a " + Arrays.toString(i.aliases()) + " &0|&a " + i.permission());
            }
        }

        return true;
    }
    
    public List<Class<? extends BCommand>> getCommandClasses() {
        List<Class<? extends BCommand>> classList = new ArrayList<Class<? extends BCommand>>();
        try {
            Enumeration<URL> en = getClass().getClassLoader().getResources("bssentials");

            if (en.hasMoreElements()) {
                URL url = en.nextElement();
                JarURLConnection urlcon = (JarURLConnection) url.openConnection();
                try (JarFile jar = urlcon.getJarFile();) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        String entry = entries.nextElement().getName().replaceAll("/", ".").replace(".class", "");
                        if (!(entry.startsWith("bssentials.commands") && entry.length() > 20))
                            continue;
                        try {
                            Class<?> clazz = Class.forName(entry);
                            if (!clazz.getSimpleName().equals("BssentialsCmd") && clazz.isAnnotationPresent(CmdInfo.class) && BCommand.class.isAssignableFrom(clazz))
                                classList.add(clazz.asSubclass(BCommand.class));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return classList;
    }

}