package tk.booky.bettersleepplugin.manager;
// Created by booky10 in BetterSleepPlugin (13:27 28.02.21)

import org.bukkit.World;

public final class MessageManager {

    public static final String PREFIX = "§7[§bSleeping§7]§c ";
    public static final String NIGHT_OVER = PREFIX + "§aDie Nacht ist vorbei und es ist nun Tag!";
    public static final String SLEEPING_NEEDED = PREFIX + "§cEs sind jetzt %s/%s Spieler im Bett! (%s)";
    public static final String SLEEPING_NEEDED_JOIN = "%s ist hinzugekommen.";
    public static final String SLEEPING_NEEDED_LEAVE = "%s ist rausgegangen.";
    public static final String SLEEPING_START = PREFIX + "§aDie Nacht wird übersprungen...";
    public static final String SLEEPING_CANCEL = PREFIX + "§cDas Überspringen der Nacht wurde abgebrochen, da %s aus dem Bett gegangen ist!";

    public static void broadcastWorld(World world, String message, Object... args) {
        world.getPlayers().forEach(player -> player.sendMessage(String.format(message, args)));
    }
}