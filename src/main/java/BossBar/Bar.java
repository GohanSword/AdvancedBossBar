package BossBar;

import fr.gohansword.advancedbossbar.AdvancedBossBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class Bar {
    static AdvancedBossBar plugin;

    int taskID;

    public Bar(AdvancedBossBar main) {
        plugin = main;
    }

    public static Map<String, Integer> barID = new HashMap<>();

    public static Map<String, BossBar> bar = new HashMap<>();

    public void cast() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, new Runnable() {
            int max = Bar.plugin.bar.getConfig().getConfigurationSection("titles").getKeys(false).size();

            double current = 1.0D;

            int line = 1;

            double time = 1.0D / Bar.plugin.getBarProgress();

            BossBar bossBar;

            public void run() {
                if (!Bar.bar.containsKey("bossbar")) {
                    this.bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', Bar.plugin.bar.getConfig().getString("titles.1.title")), Bar.this.getBarColor(1), BarStyle.SOLID, new org.bukkit.boss.BarFlag[0]);
                    Bar.bar.put("bossbar", this.bossBar);
                    Bar.barID.put("bossbar", Integer.valueOf(Bar.this.taskID));
                    this.bossBar.setVisible(true);
                }
                if (this.current <= 0.0D) {
                    if (this.line >= this.max) {
                        this.line = 1;
                    } else {
                        this.line++;
                    }
                    this.current = 1.0D;
                    this.bossBar.setColor(Bar.this.getBarColor(this.line));
                    this.bossBar.setTitle(ChatColor.translateAlternateColorCodes('&', Bar.plugin.bar.getConfig().getString("titles." + this.line + ".title")));
                }
                this.bossBar.setProgress(this.current);
                this.current -= this.time;
            }
        },5L, 20L);
    }

    public boolean stop() {
        if (bar.containsKey("bossbar")) {
            Bukkit.getScheduler().cancelTask(((Integer)barID.get("bossbar")).intValue());
            BossBar bb = bar.get("bossbar");
            bb.removeAll();
            bar.clear();
            barID.clear();
            return true;
        }
        return false;
    }

    public boolean isCasting() {
        if (bar.containsKey("bossbar"))
            return true;
        return false;
    }

    public BarColor getBarColor(int line) {
        if (plugin.bar.getConfig().contains("titles." + line + ".bar-color")) {
            String color = plugin.bar.getConfig().getString("titles." + line + ".bar-color");
            if (color.equalsIgnoreCase("pink"))
                return BarColor.PINK;
            if (color.equalsIgnoreCase("yellow"))
                return BarColor.YELLOW;
            if (color.equalsIgnoreCase("green"))
                return BarColor.GREEN;
            if (color.equalsIgnoreCase("blue"))
                return BarColor.BLUE;
            if (color.equalsIgnoreCase("red"))
                return BarColor.RED;
            if (color.equalsIgnoreCase("white"))
                return BarColor.WHITE;
            return color.equalsIgnoreCase("purple") ? BarColor.PURPLE : BarColor.PINK;
        }
        return BarColor.PINK;
    }
}
