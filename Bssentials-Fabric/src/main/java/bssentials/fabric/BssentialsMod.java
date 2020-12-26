package bssentials.fabric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bssentials.Bssentials;
import bssentials.Warps;
import bssentials.api.IBssentials;
import bssentials.api.IWarps;
import bssentials.api.IWorld;
import bssentials.api.Location;
import bssentials.api.User;
import bssentials.commands.BCommand;
import bssentials.commands.BssentialsCmd;
import bssentials.commands.CmdInfo;
import bssentials.commands.Heal;
import bssentials.commands.SpawnMob;
import bssentials.configuration.BssConfiguration;
import bssentials.configuration.BssConfiguration.ConfigException;
import bssentials.configuration.Configs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.level.ServerWorldProperties;

public class BssentialsMod implements ModInitializer, IBssentials {

    public MinecraftServer server;
    public Logger LOGGER = LogManager.getLogger("BssentialsFabric");

    public static File warpdir;
    private int registered = 0;
    private Warps warpManager;

    public static File DATA_FOLDER;
    public static User CONSOLE_USER;

    public HashMap<UUID, User> users;
    public HashMap<String, IWorld> worlds;

    @Override
    public void onInitialize() {
        Bssentials.setInstance(this, new File(FabricLoader.getInstance().getConfigDirectory(), "Bssentials"));

        warpdir = new File(Bssentials.DATA_FOLDER, "warps");
        warpdir.mkdirs();
        warpManager = new Warps(this, warpdir);
        users = new HashMap<>();
        worlds = new HashMap<>();

        saveResource("config.yml", false);
        saveResource("info.txt", false);
        saveResource("motd.txt", false);
        saveResource("rules.txt", false);

        LOGGER.info("Loading configuration...");
        try {
            Configs.initConfigs();
        } catch (ConfigException e1) {
            e1.printStackTrace();
        }

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            this.server = server;
            CONSOLE_USER = new ConsoleUser(server);

            LOGGER.info("Registering commands...");

            Registry.ENTITY_TYPE.forEach((type) -> {
                if (type.isSummonable())
                    SpawnMob.mobs.put(type.toString(), type);
            });

            register("bssentials", new BssentialsCmd());

            for (Class<? extends BCommand> clazz : getCommandClasses()) {
                try {
                    register(clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.warn("Unable to register \"/" + clazz.getName() + "\" command.");
                }
            }
            register("feed", new Heal());
            LOGGER.info("Registered " + registered + " commands");
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            LOGGER.info("Bssentials version " + getVersion());
        });
    }

    public InputStream getResource(String filename) {
        if (filename == null)
            throw new IllegalArgumentException("Filename cannot be null");

        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            if (url == null) return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals(""))
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null)
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in Bssentials");

        File outFile = new File(Bssentials.DATA_FOLDER, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(Bssentials.DATA_FOLDER, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
        if (!outDir.exists()) outDir.mkdirs();

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
                out.close();
                in.close();
            } else {
                LOGGER.warn("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
            }
        } catch (IOException ex) {
            LOGGER.error("Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    public void register(String name, BCommand base) {
        try {
            CommandWrapper wrap = new CommandWrapper(name, base);
            if (wrap.register(server.getCommandManager().getDispatcher(), name) != null);
                registered++;
         } catch(Exception e) { e.printStackTrace(); }
    }

    public void register(BCommand... bases) {
        for (BCommand bcmd : bases) {
            register(bcmd.getClass().getSimpleName().toLowerCase(Locale.ENGLISH), bcmd);
            String permission = bcmd.info.permission();
            if (permission.equalsIgnoreCase("REQUIRES_OP") || permission.equalsIgnoreCase("NONE"))
                continue;
        }
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

    @Override
    public IWarps getWarps() {
        return warpManager;
    }

    @Override
    public BssConfiguration getMainConfig() {
        return Configs.MAIN;
    }

    @Override
    public User getUser(Object base) {
        if (base instanceof ServerPlayerEntity) {
            ServerPlayerEntity p = (ServerPlayerEntity) base;
            if (users.containsKey(p.getUuid()))
                return users.get(p.getUuid());
            User user = new FabricUser(p);
            users.put(p.getUuid(), user);
            return user;
        }
        if (base instanceof String) {
            ServerPlayerEntity p = server.getPlayerManager().getPlayer((String)base);
            if (users.containsKey(p.getUuid()))
                return users.get(p.getUuid());
            User user = new FabricUser(p);
            users.put(p.getUuid(), user);
            return user;
        }
        return CONSOLE_USER;
    }

    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return "FABRIC_BETA_BUILD";
    }

    @Override
    public void broadcastMessage(String string) {
        for (ServerPlayerEntity plr : server.getPlayerManager().getPlayerList())
            plr.sendMessage(new LiteralText(string), false);
    }

    @Override
    public Collection<IWorld> getWorlds() {
        for (ServerWorld w : server.getWorlds()) { 
            String name = ((ServerWorldProperties)w.getLevelProperties()).getLevelName();
            if (!worlds.containsKey(name))
                worlds.put(name, new FabricWorld(w));
        }
        return worlds.values();
    }

    @Override
    public int getOnlinePlayerCount() {
        return server.getCurrentPlayerCount();
    }

    @Override
    public IWorld getWorld(String string) {
        for (ServerWorld w : server.getWorlds()) {
            String name = ((ServerWorldProperties)w.getLevelProperties()).getLevelName();
            if (!worlds.containsKey(name))
                worlds.put(name, new FabricWorld(w));
        }
        return worlds.get(string);
    }

    public static Location copy(ServerPlayerEntity e, BlockPos pos) {
        Location l = new Location();
        l.x = pos.getX();
        l.y = pos.getY();
        l.z = pos.getZ();
        l.world = ((ServerWorldProperties)e.getServerWorld().getLevelProperties()).getLevelName();
        return l;
    }
}
