package Fichiers;

import fr.gohansword.advancedbossbar.AdvancedBossBar;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Bars {
    private AdvancedBossBar plugin;

    private FileConfiguration dataConfig = null;

    private File dataConfigFile = null;

    public Bars(AdvancedBossBar plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.dataConfigFile == null)
            this.dataConfigFile = new File(this.plugin.getDataFolder(),
                    "bossbar-messages.yml");
        this.dataConfig =
                (FileConfiguration) YamlConfiguration.loadConfiguration(this.dataConfigFile);
        InputStream defConfigStream = this.plugin.getResource("bossbar-messages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig =
                    YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            this.dataConfig.setDefaults((Configuration)defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null)
            reloadConfig();
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.dataConfigFile == null)
            return;
        try {
            getConfig().save(this.dataConfigFile);
        } catch (IOException ex) {
            this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " +
                    this.dataConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (this.dataConfigFile == null)
            this.dataConfigFile = new File(this.plugin.getDataFolder(),
                    "bossbar-messages.yml");
        if (!this.dataConfigFile.exists())
            this.plugin.saveResource("bossbar-messages.yml", false);
    }
}

