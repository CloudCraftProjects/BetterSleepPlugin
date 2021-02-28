package tk.booky.bettersleepplugin.exceptions;
// Created by booky10 in BetterSleepPlugin (14:24 28.02.21)

import org.bukkit.World;

public class WorldException extends IllegalStateException {

    private final World world;

    public WorldException(World world, String message) {
        super(String.format(message, String.format("%s (%s)", world.getName(), world.getUID())));

        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}