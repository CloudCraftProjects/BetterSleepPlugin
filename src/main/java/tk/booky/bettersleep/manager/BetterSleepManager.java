package tk.booky.bettersleep.manager;
// Created by booky10 in BetterSleepPlugin (22:37 27.02.21)

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tk.booky.bettersleep.BetterSleepMain;
import tk.booky.bettersleep.exceptions.AlreadyDayException;
import tk.booky.bettersleep.exceptions.AlreadySkippingException;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class BetterSleepManager {

    private static final HashMap<UUID, UUID> sleeping = new HashMap<>();
    private static final List<UUID> skippingWorlds = new ArrayList<>();

    public static final double PERCENTAGE = 0.1;

    public static boolean isSleeping(UUID player) {
        return sleeping.containsKey(player);
    }

    public static List<UUID> getSleeping(UUID world) {
        return sleeping.entrySet().stream().filter(entry -> world.equals(entry.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static boolean addSleeping(UUID world, UUID player) {
        if (!isSleeping(player)) {
            sleeping.put(player, world);
            return true;
        } else {
            return false;
        }
    }

    public static void removeSleeping(UUID player) {
        sleeping.remove(player);
    }

    public static boolean canSkip(World world) {
        return getSleeping(world.getUID()).size() >= getSleepingNeeded(world);
    }

    public static BukkitTask skipNight(World world, Consumer<Void> consumer) {
        if (world.isDayTime()) throw new AlreadyDayException(world);
        if (skippingWorlds.contains(world.getUID())) throw new AlreadySkippingException(world);

        skippingWorlds.add(world.getUID());
        return new BukkitRunnable() {

            @Override
            public void run() {
                getSleeping(world.getUID()).forEach(uuid -> {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player == null || !player.isOnline()) {
                        removeSleeping(uuid);
                    } else {
                        if (!player.isSleeping()) {
                            removeSleeping(uuid);
                        }
                    }
                });

                if (world.isDayTime() || !canSkip(world)) {
                    if (world.isDayTime()) consumer.accept(null);
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

    public static int getSleepingNeeded(World world) {
        return Math.max((int) (world.getPlayerCount() * PERCENTAGE), 1);
    }

    public static boolean isAlreadySkipping(UUID world) {
        return skippingWorlds.contains(world);
    }
}