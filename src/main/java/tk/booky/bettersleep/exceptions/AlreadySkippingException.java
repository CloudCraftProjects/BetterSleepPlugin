package tk.booky.bettersleep.exceptions;
// Created by booky10 in BetterSleepPlugin (14:23 28.02.21)

import org.bukkit.World;

public class AlreadySkippingException extends WorldException {

    public AlreadySkippingException(World world) {
        super(world, "The night in %s is already getting skipped!");
    }
}