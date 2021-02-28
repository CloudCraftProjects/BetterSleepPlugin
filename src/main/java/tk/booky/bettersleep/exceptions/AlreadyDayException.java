package tk.booky.bettersleep.exceptions;
// Created by booky10 in BetterSleepPlugin (13:31 28.02.21)

import org.bukkit.World;

public class AlreadyDayException extends WorldException {

    public AlreadyDayException(World world) {
        super(world, "It is already day in %s!");
    }
}