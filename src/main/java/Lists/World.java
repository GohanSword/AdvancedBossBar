package Lists;

import BossBar.Bar;
import fr.gohansword.advancedbossbar.AdvancedBossBar;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.List;

public class World {
    static AdvancedBossBar plugin;

    public Bar bar;


    BossBar bb;

    public World(AdvancedBossBar main) {
        plugin = main;
        this.bar = new Bar(plugin);
    }

    public static List<String> worlds = new ArrayList<>();

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (plugin.cfg.getConfig().getBoolean("bossbar.enabled") &&
                plugin.cfg.getConfig().getBoolean("bossbar.only-in-certain-worlds.enabled")) {
            if (worlds.isEmpty())
                for (String w : plugin.cfg.getConfig().getStringList("bossbar.only-in-certain-worlds.worlds"))
                    worlds.add(w);
            if (worlds.contains(event.getPlayer().getWorld().getName()))
                if (this.bar.isCasting()) {
                    this.bb = (BossBar)Bar.bar.get("bossbar");
                    if (!this.bb.getPlayers().contains(event.getPlayer())) {
                        this.bb.addPlayer(event.getPlayer());
                        return;
                    }
                    return;
                }
            if (this.bar.isCasting()) {
                this.bb = (BossBar)Bar.bar.get("bossbar");
                if (this.bb.getPlayers().contains(event.getPlayer())) {
                    this.bb.removePlayer(event.getPlayer());
                    return;
                }
            }
        }
    }
}