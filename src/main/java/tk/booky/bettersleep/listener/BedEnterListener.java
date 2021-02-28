package tk.booky.bettersleep.listener;
// Created by booky10 in BetterSleepPlugin (22:35 27.02.21)

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import tk.booky.bettersleep.manager.BetterSleepManager;
import tk.booky.bettersleep.manager.MessageManager;

public class BedEnterListener implements Listener {

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        if (event.isCancelled() || !event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) return;

        Player player = event.getPlayer();
        World world = player.getWorld();

        if (!world.isDayTime() && BetterSleepManager.addSleeping(world.getUID(), player.getUniqueId())) {
            MessageManager.broadcastWorld(world, MessageManager.SLEEPING_NEEDED, BetterSleepManager.getSleeping(world.getUID()).size(), BetterSleepManager.getSleepingNeeded(world), String.format(MessageManager.SLEEPING_NEEDED_JOIN, player.getName()));

            if (BetterSleepManager.canSkip(world) && !BetterSleepManager.isAlreadySkipping(world.getUID())) {
                MessageManager.broadcastWorld(world, false, MessageManager.SLEEPING_START);
                BetterSleepManager.skipNight(world, (none) -> MessageManager.broadcastWorld(world, false, MessageManager.NIGHT_OVER));
            }
        }
    }
}