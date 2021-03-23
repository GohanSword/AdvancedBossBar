package Lists;

import BossBar.Bar;
import fr.gohansword.advancedbossbar.AdvancedBossBar;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit {
    static AdvancedBossBar plugin;

    public Bar bar;

    BossBar b;

    public Quit(AdvancedBossBar main) {
        plugin = main;
        this.bar = new Bar(plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (plugin.cfg.getConfig().getBoolean("bossbar.enabled")) {
            try {
                this.b = (BossBar)Bar.bar.get("bossbar");
                this.b.removePlayer(event.getPlayer());
            } catch (Exception e) {
                return;
            }
        }
    }
}
