package tk.booky.bettersleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.booky.bettersleep.listener.BedEnterListener;

public final class BetterSleepMain extends JavaPlugin {

    public static BetterSleepMain main;

    @Override
    public void onEnable() {
        main = this;

        Bukkit.getPluginManager().registerEvents(new BedEnterListener(), this);
    }
}
