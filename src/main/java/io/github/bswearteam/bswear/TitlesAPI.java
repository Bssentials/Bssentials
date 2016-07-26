/**
 * @Author: Aditya / AdityaTD, A member of the BSwear Team
 * All Rights Reserved.
 * Do not copy or redistribute.
 * CODE FROM: CLUSTERAPI BY ADITYATD.
 * Used with permmisson.
 **/

package io.github.bswearteam.bswear;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TitlesAPI implements Listener {
  public static boolean works = true;
  public static String nmsver;
  
  @Deprecated
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, message, null);
  }
  
  @Deprecated
  public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, null, message);
  }
  
  @Deprecated
  public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
  }
  
  public static void sendPacket(Player player, Object packet)
  {
    try
    {
      Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
      playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static Class<?> getNMSClass(String name)
  {
    String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    try
    {
      return Class.forName("net.minecraft.server." + version + "." + name);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    try
    {
      if (title != null)
      {
        title = ChatColor.translateAlternateColorCodes('&', title);
        title = title.replaceAll("%player%", player.getDisplayName());
        Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
        Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
        Object titlePacket = titleConstructor.newInstance(new Object[] { enumTitle, chatTitle, fadeIn, stay, fadeOut });
        sendPacket(player, titlePacket);
      }
      if (subtitle != null)
      {
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
        Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
        Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
        Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
        Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { enumSubtitle, chatSubtitle, fadeIn, stay, fadeOut });
        sendPacket(player, subtitlePacket);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static void sendTabTitle(Player player, String header, String footer)
  {
    if (header == null) {
      header = "";
    }
    header = ChatColor.translateAlternateColorCodes('&', header);
    if (footer == null) {
      footer = "";
    }
    footer = ChatColor.translateAlternateColorCodes('&', footer);
    
    header = header.replaceAll("%player%", player.getDisplayName());
    footer = footer.replaceAll("%player%", player.getDisplayName());
    try
    {
      Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + header + "\"}" });
      Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + footer + "\"}" });
      Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(new Class[] { getNMSClass("IChatBaseComponent") });
      Object packet = titleConstructor.newInstance(new Object[] { tabHeader });
      Field field = packet.getClass().getDeclaredField("b");
      field.setAccessible(true);
      field.set(packet, tabFooter);
      sendPacket(player, packet);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
