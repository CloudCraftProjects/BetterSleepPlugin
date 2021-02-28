package tk.booky.bettersleep.listener;
// Created by booky10 in BetterSleepPlugin (14:55 28.02.21)

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import tk.booky.bettersleep.manager.BetterSleepManager;
import tk.booky.bettersleep.manager.MessageManager;

public class BedLeaveListener implements Listener {

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        World world = player.getWorld();

        BetterSleepManager.removeSleeping(player.getUniqueId());

        if (!world.isDayTime()) {
            MessageManager.broadcastWorld(world, MessageManager.SLEEPING_NEEDED, BetterSleepManager.getSleeping(world.getUID()).size(), BetterSleepManager.getSleepingNeeded(world), String.format(MessageManager.SLEEPING_NEEDED_LEAVE, player.getName()));

            if (!BetterSleepManager.canSkip(world) && BetterSleepManager.isAlreadySkipping(world.getUID())) {
                MessageManager.broadcastWorld(world, MessageManager.SLEEPING_CANCEL, player.getName());
            }
        }
    }
}