package ml.bssentials.updater;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.common.base.Joiner;

/**
 * A simple auto-updater.
 * <br>Please follow this link to read more about checking for updates in your plugin : https://www.skyost.eu/Skyupdater.txt.
 * <br><br>Thanks to Gravity for his updater (this file use some parts of his code) !
 * 
 * @author Skyost
 */

public class Skyupdater {
    
    private final Plugin plugin;
    private final int id;
    private final File pluginFile;
    private final boolean download;
    private final boolean announce;
    private final File updateFolder;
    private URL url;
    
    private final YamlConfiguration config = new YamlConfiguration();
    
    private Result result = Result.SUCCESS;
    private String[] updateData;
    private String response;
    private Thread updaterThread;
    
    private static final String SKYUPDATER_VERSION = "0.5.1";
    
    public enum Result {
        
        /**
         * A new version has been found, downloaded and will be loaded at the next server reload / restart.
         */
        
        SUCCESS,
        
        /**
         * A new version has been found but nothing was downloaded.
         */
        
        UPDATE_AVAILABLE,
        
        /**
         * No update found.
         */
        
        NO_UPDATE,
        
        /**
         * The updater is disabled.
         */
        
        DISABLED,
        
        /**
         * An error occured.
         */
        
        ERROR;
    }
    
    public enum InfoType {
        
        /**
         * Gets the download URL.
         */
        
        DOWNLOAD_URL,
        
        /**
         * Gets the file name.
         */
        
        FILE_NAME,
        
        /**
         * Gets the game version.
         */
        
        GAME_VERSION,
        
        /**
         * Gets the file's title.
         */
        
        FILE_TITLE,
        
        /**
         * Gets the release type.
         */
        
        RELEASE_TYPE;
    }
    
    /**
     * Initializes Skyupdater.
     * 
     * @param plugin Your plugin.
     * @param id Your plugin ID on BukkitDev (you can get it here : https://api.curseforge.com/servermods/projects?search=your+plugin).
     * @param pluginFile The plugin file. You can get it from your plugin using <i>getFile()</i>.
     * @param download If you want to download the file.
     * @param announce If you want to announce the progress of the update.
     * 
     * @throws IOException InputOutputException.
     * @throws InvalidConfigurationException If there is a problem with Skyupdater's config.
     */
    
    public Skyupdater(final Plugin plugin, final int id, final File pluginFile, final boolean download, final boolean announce) throws IOException, InvalidConfigurationException {
        this.plugin = plugin;
        this.id = id;
        this.pluginFile = pluginFile;
        this.download = download;
        this.announce = announce;
        updateFolder = Bukkit.getUpdateFolderFile();
        if(!updateFolder.exists()) {
            updateFolder.mkdir();
        }
        final File skyupdaterFolder = new File(plugin.getDataFolder().getParentFile(), "Skyupdater");
        if(!skyupdaterFolder.exists()) {
            skyupdaterFolder.mkdir();
        }
        final String lineSeparator = System.lineSeparator();
        final StringBuilder header = new StringBuilder();
        header.append("Skyupdater configuration - https://www.skyost.eu/Skyupdater.txt" + lineSeparator + lineSeparator);
        header.append("What is Skyupdater ?" + lineSeparator);
        header.append("Skyupdater is a simple updater created by Skyost (https://www.skyost.eu). It is used to auto-update Bukkit Plugins." + lineSeparator + lineSeparator);
        header.append("What happens during the update process ?" + lineSeparator);
        header.append("1. Connection to curseforge.com." + lineSeparator);
        header.append("2. Plugin version compared against version on curseforge.com." + lineSeparator);
        header.append("3. Downloading of the plugin from curseforge.com if a newer version is found." + lineSeparator + lineSeparator);
        header.append("So what is this file ?" + lineSeparator);
        header.append("This file is just a config file for this auto-updater." + lineSeparator + lineSeparator);
        header.append("Configuration :" + lineSeparator);
        header.append("'enable': Choose if you want to enable the auto-updater." + lineSeparator);
        header.append("'api-key': OPTIONAL. Your BukkitDev API Key." + lineSeparator + lineSeparator);
        header.append("Good game, I hope you will enjoy your plugins always up-to-date ;)" + lineSeparator);
        final File configFile = new File(skyupdaterFolder, "skyupdater.yml");
        if(!configFile.exists()) {
            configFile.createNewFile();
            config.options().header(header.toString());
            config.set("enable", true);
            config.set("api-key", "NONE");
            config.save(configFile);
        }
        config.load(configFile);
        if(!config.getBoolean("enable")) {
            result = Result.DISABLED;
            log(Level.INFO, "Skyupdater is disabled.");
            return;
        }
        url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + id);
        updaterThread = new Thread(new UpdaterThread());
        updaterThread.start();
    }
    
    /**
     * Gets the version of Skyupdater.
     * 
     * @return The version of Skyupdater.
     */
    
    public static final String getVersion() {
        return SKYUPDATER_VERSION;
    }
    
    /**
     * Gets the result of Skyupdater.
     * 
     * @return The result of the update process.
     */
    
    public final Result getResult() {
        waitForThread();
        return result;
    }
    
    /**
     * Gets informations about the latest file.
     * 
     * @param type The type of information you want.
     * 
     * @return The information you want.
     */
    
    public final String getLatestFileInfo(final InfoType type) {
        waitForThread();
        switch(type) {
        case DOWNLOAD_URL:
            return updateData[0];
        case FILE_NAME:
            return updateData[1];
        case GAME_VERSION:
            return updateData[2];
        case FILE_TITLE:
            return updateData[3];
        case RELEASE_TYPE:
            return updateData[4];
        }
        return null;
    }
    
    /**
     * Gets raw data about the latest file.
     * 
     * @return An array string which contains every of the update process.
     */
    
    public final String[] getLatestFileData() {
        waitForThread();
        return updateData;
    }
    
    /**
     * Downloads a file.
     * 
     * @param site The URL of the file you want to download.
     * @param pathTo The path where you want the file to be downloaded.
     * 
     * @return <b>true</b>If the download was a success.
     * </b>false</b>If there is an error during the download.
     * 
     * @throws IOException InputOutputException.
     */
    
    private final boolean download(final String site, final File pathTo) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(site).openConnection();
            connection.addRequestProperty("User-Agent", "Skyupdater v" + SKYUPDATER_VERSION);
            response = connection.getResponseCode() + " " + connection.getResponseMessage();
            if(!response.startsWith("2")) {
                log(Level.WARNING, "Bad response : '" + response + "' when trying to download the update.");
                result = Result.ERROR;
                return false;
            }
            final byte[] data = new byte[1024];
            final long size = connection.getContentLengthLong();
            final long koSize = size / 1000;
            
            int lastPercent = 0;
            int percent = 0;
            float totalDataRead = 0;
            final InputStream inputStream = connection.getInputStream();
            final FileOutputStream fileOutputStream = new FileOutputStream(pathTo);
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
            
            int i = 0;
            while((i = inputStream.read(data, 0, 1024)) >= 0) {
                totalDataRead += i;
                bufferedOutputStream.write(data, 0, i);
                if(announce) {
                    percent = (int)((long)(totalDataRead * 100) / size);
                    if(lastPercent != percent) {
                        lastPercent = percent;
                        log(Level.INFO, percent + "% of " + koSize + "ko...");
                    }
                }
            }
            
            bufferedOutputStream.close();
            fileOutputStream.close();
            inputStream.close();
            return true;
        }
        catch(final Exception ex) {
            log(Level.SEVERE, "Exception '" + ex + "' occured when downloading update. Please check your network connection.");
            result = Result.ERROR;
        }
        return false;
    }
    
    /**
     * Compares two versions.
     * 
     * @param version1 The version you want to compare to.
     * @param version2 The version you want to compare with.
     * 
     * @return <b>true</b> If <b>versionTo</b> is inferior than <b>versionWith</b>.
     * <br><b>false</b> If <b>versionTo</b> is superior or equals to <b>versionWith</b>.
     */
    
    public static final boolean compareVersions(final String versionTo, final String versionWith) {
        return normalisedVersion(versionTo, ".", 4).compareTo(normalisedVersion(versionWith, ".", 4)) > 0;
    }
    
    /**
     * Gets the formatted name of a version.
     * <br>Used for the method <b>compareVersions(...)</b> of this class.
     * 
     * @param version The version you want to format.
     * @param separator The separator between the numbers of this version.
     * @param maxWidth The max width of the formatted version.
     * 
     * @return A string which the formatted version of your version.
     * 
     * @author Peter Lawrey.
     */

    private static final String normalisedVersion(final String version, final String separator, final int maxWidth) {
        final StringBuilder stringBuilder = new StringBuilder();
        for(final String normalised : Pattern.compile(separator, Pattern.LITERAL).split(version)) {
            stringBuilder.append(String.format("%" + maxWidth + 's', normalised));
        }
        return stringBuilder.toString();
    }
    
    /**
     * Logs a message if "announce" is set to true.
     * 
     * @param level The level of logging.
     * @param message The message.
     */
    
    private final void log(final Level level, final String message) {
        if(announce) {
            Bukkit.getLogger().log(level, "[Skyupdater] " + message);
        }
    }
    
    /**
     * As the result of Updater output depends on the thread's completion,
     * <br>it is necessary to wait for the thread to finish before allowing anyone to check the result.
     * 
     * @author <b>Gravity</b> from his Updater.
     */
    
    private final void waitForThread() {
        if(updaterThread != null && updaterThread.isAlive()) {
            try {
                updaterThread.join();
            }
            catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class UpdaterThread implements Runnable {
    
        @Override
        public void run() {
            try {
                final String pluginName = plugin.getName().replace("_", " ");
                final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.addRequestProperty("User-Agent", "Skyupdater v" + SKYUPDATER_VERSION);
                
                final String apiKey = config.getString("api-key");
                if(apiKey != null && !apiKey.equals("NONE")) {
                    connection.addRequestProperty("X-API-Key", apiKey);
                }
                response = connection.getResponseCode() + " " + connection.getResponseMessage();
                if(!response.startsWith("2")) {
                    log(Level.INFO, "Bad response : '" + response + (response.startsWith("402") ? "'. Maybe your API Key is invalid ?" : "'."));
                    result = Result.ERROR;
                    return;
                }
                
                final InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                final String response = bufferedReader.readLine();
                
                if(response != null && !response.equals("[]")) {
                    final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(response);
                    final JSONObject jsonObject = (JSONObject)jsonArray.get(jsonArray.size() - 1);
                    updateData = new String[]{String.valueOf(jsonObject.get("downloadUrl")), String.valueOf(jsonObject.get("fileName")), String.valueOf(jsonObject.get("gameVersion")), String.valueOf(jsonObject.get("name")), String.valueOf(jsonObject.get("releaseType"))};
                    
                    if(compareVersions(updateData[3].split("^v|[\\s_-]v")[1].split(" ")[0], plugin.getDescription().getVersion()) && updateData[0].toLowerCase().endsWith(".jar")) {
                        result = Result.UPDATE_AVAILABLE;
                        
                        if(download) {
                            log(Level.INFO, "Downloading a new update : " + updateData[3] + "...");
                            
                            if(download(updateData[0], new File(updateFolder, pluginFile.getName()))) {
                                result = Result.SUCCESS;
                                log(Level.INFO, "The update of '" + pluginName + "' has been downloaded and installed. It will be loaded at the next server load / reload.");
                            }
                            else {
                                result = Result.ERROR;
                            }
                            
                        }
                        else {
                            log(Level.INFO, "An update has been found for '" + pluginName + "' but nothing was downloaded.");
                        }
                        return;
                        
                    }
                    else {
                        result = Result.NO_UPDATE;
                        log(Level.INFO, "No update found for '" + pluginName + "'.");
                    }
                    
                }
                else {
                    log(Level.SEVERE, "The ID '" + id + "' was not found (or no files found for this project) ! Maybe the author(s) (" + Joiner.on(", ").join(plugin.getDescription().getAuthors()) + ") of '" + pluginName + "' has/have misconfigured his/their plugin ?");
                    result = Result.ERROR;
                }
                
                bufferedReader.close();
                inputStreamReader.close();
            }
            catch(final Exception ex) {
                log(Level.SEVERE, "Exception '" + ex + "'. Please check your network connection.");
                result = Result.ERROR;
            }
        }
        
    }

}