package Lists;

import BossBar.Bar;
import fr.gohansword.advancedbossbar.AdvancedBossBar;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join {
    static AdvancedBossBar plugin;

    public Bar bar;

    BossBar bb;

    BossBar b;

    public Join(AdvancedBossBar main) {
        plugin = main;
        this.bar = new Bar(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (plugin.cfg.getConfig().getBoolean("bossbar.enabled")) {
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
            if (this.bar.isCasting()) {
                this.b = (BossBar)Bar.bar.get("bossbar");
                this.b.addPlayer(event.getPlayer());
            }
        }
    }
