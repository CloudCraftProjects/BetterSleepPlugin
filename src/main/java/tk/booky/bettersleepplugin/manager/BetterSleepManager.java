package tk.booky.bettersleepplugin.manager;
// Created by booky10 in BetterSleepPlugin (22:37 27.02.21)

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tk.booky.bettersleepplugin.BetterSleepMain;
import tk.booky.bettersleepplugin.exceptions.AlreadyDayException;
import tk.booky.bettersleepplugin.exceptions.AlreadySkippingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public final class BetterSleepManager {

    private static final List<UUID> sleeping = new ArrayList<>();
    private static final List<UUID> skipping = new ArrayList<>();

    public static final double PERCENTAGE = 0.3;

    public static boolean isSleeping(UUID uuid) {
        return sleeping.contains(uuid);
    }

    public static List<UUID> getSleeping() {
        return Collections.unmodifiableList(sleeping);
    }

    public static boolean addSleeping(UUID uuid) {
        if (!isSleeping(uuid)) return sleeping.add(uuid);
        else return false;
    }

    public static boolean removeSleeping(UUID uuid) {
        return sleeping.remove(uuid);
    }

    public static boolean canSkip(World world) {
        return (double) world.getPlayerCount() / sleeping.size() >= PERCENTAGE;
    }

    public static BukkitTask skipNight(World world, Consumer<Boolean> consumer) {
        if (world.isDayTime()) throw new AlreadyDayException(world);
        if (skipping.contains(world.getUID())) throw new AlreadySkippingException(world);

        skipping.add(world.getUID());
        return new BukkitRunnable() {
            @Override
            public void run() {
                if (world.isDayTime() || !canSkip(world)) {
                    consumer.accept(world.isDayTime());
                    cancel();
                } else {
                    world.setTime(world.getTime() + 100);
                }
            }

            @Override
            public synchronized void cancel() throws IllegalStateException {
                skipping.remove(world.getUID());
                super.cancel();
            }
        }.runTaskTimer(BetterSleepMain.main, 5, 5);
    }
}