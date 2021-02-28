package tk.booky.bettersleepplugin.manager;
// Created by booky10 in BetterSleepPlugin (22:37 27.02.21)

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tk.booky.bettersleepplugin.BetterSleepMain;
import tk.booky.bettersleepplugin.exceptions.AlreadyDayException;
import tk.booky.bettersleepplugin.exceptions.AlreadySkippingException;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class BetterSleepManager {

    private static final HashMap<UUID, UUID> sleeping = new HashMap<>();
    private static final List<UUID> skippingWorlds = new ArrayList<>();

    public static final double PERCENTAGE = 0.3;

    public static boolean isSleeping(UUID uuid) {
        return sleeping.containsKey(uuid);
    }

    public static List<UUID> getSleeping(World world) {
        return sleeping.entrySet().stream().filter(entry -> world.getUID().equals(entry.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static boolean addSleeping(World world, UUID uuid) {
        if (!isSleeping(uuid)) {
            sleeping.put(uuid, world.getUID());
            return true;
        } else {
            return false;
        }
    }

    public static void removeSleeping(UUID uuid) {
        sleeping.remove(uuid);
    }

    public static boolean canSkip(World world) {
        return (double) world.getPlayerCount() / sleeping.size() >= PERCENTAGE;
    }

    public static BukkitTask skipNight(World world, Consumer<Boolean> consumer) {
        if (world.isDayTime()) throw new AlreadyDayException(world);
        if (skippingWorlds.contains(world.getUID())) throw new AlreadySkippingException(world);

        skippingWorlds.add(world.getUID());
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
                skippingWorlds.remove(world.getUID());
                super.cancel();
            }
        }.runTaskTimer(BetterSleepMain.main, 5, 5);
    }
}