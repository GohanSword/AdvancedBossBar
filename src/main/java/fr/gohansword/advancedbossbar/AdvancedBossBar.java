package fr.gohansword.advancedbossbar;


import BossBar.Bar;
import Fichiers.Bars;
import Fichiers.Settings;
import Lists.Join;
import Lists.World;
import jdk.internal.vm.annotation.Hidden;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import sun.security.util.Debug;

public final class AdvancedBossBar extends JavaPlugin implements @NotNull Listener {
    public Settings cfg;

    public Hidden data;

    public Bars bar;

    public Bar b;

    int taskID;

    public void onEnable() {
        this.cfg = new Settings(this);
        this.bar = new Bars(this);
        this.b = new Bar(this);
        registerCmds();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents((Listener)new Join(this), (Plugin)this);
        pm.registerEvents((Listener)new World(this), (Plugin)this);
        getServer().getPluginManager().registerEvents(this, (Plugin)this);

        if (this.cfg.getConfig().getBoolean("bossbar.enabled")) {
            this.b.cast();
            addBarPlayers();
        }
    }

    public void onDisable() {
        if (this.cfg.getConfig().getBoolean("bossbar.enabled") &&
                this.b.isCasting()) {
            BossBar bb = (BossBar)Bar.bar.get("bossbar");
            bb.removeAll();
        }
    }

    public void registerCmds() {
    }

    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public long getSeconds() {
        long secs = this.cfg.getConfig().getLong("announce.seconds");
        secs *= 20L;
        return secs;
    }

    public double getBarProgress() {
        double secs = 60.0D;
        if (this.cfg.getConfig().contains("bossbar.seconds"))
            secs = this.cfg.getConfig().getDouble("bossbar.seconds");
        return secs;
    }

    public void addBarPlayers() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable() {
            public void run() {
                if (Bukkit.getOnlinePlayers().size() >= 1 &&
                        AdvancedBossBar.this.b.isCasting()) {
                    BossBar bb = (BossBar) Bar.bar.get("bossbar");
                    for (Player on : Bukkit.getOnlinePlayers())
                        bb.addPlayer(on);
                }
                Bukkit.getScheduler().cancelTask(AdvancedBossBar.this.taskID);
            }
        },  20L, 1000L);
    }
}
