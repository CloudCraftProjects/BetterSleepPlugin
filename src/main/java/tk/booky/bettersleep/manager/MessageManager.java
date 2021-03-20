package tk.booky.bettersleep.manager;
// Created by booky10 in BetterSleepPlugin (13:27 28.02.21)

import org.bukkit.World;

public final class MessageManager {

    public static final String PREFIX = "§7[§bSleeping§7]§c ";
    public static final String NIGHT_OVER = PREFIX + "§aDie Nacht ist vorbei!";
    public static final String SLEEPING_NEEDED = "§7%s/%s Spieler im Bett! %s";
    public static final String SLEEPING_NEEDED_JOIN = "§a+%s";
    public static final String SLEEPING_NEEDED_LEAVE = "§c-%s";
    public static final String SLEEPING_START = PREFIX + "§aDie Nacht wird übersprungen...";
    public static final String SLEEPING_CANCEL = PREFIX + "§cDas Überspringen wurde wegen %s abgebrochen!";

    public static void broadcastWorld(World world, String message, Object... args) {
        broadcastWorld(world, true, message, args);
    }

    public static void broadcastWorld(World world, boolean actionBar, String message, Object... args) {
        if (actionBar) world.getPlayers().forEach(player -> player.sendActionBar(String.format(message, args)));
        else world.getPlayers().forEach(player -> player.sendMessage(String.format(message, args)));
    }
}