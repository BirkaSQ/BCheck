package ru.birkasq.bcheck;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.birkasq.bcheck.command.FreezeCommand;
import ru.birkasq.bcheck.listener.AllListener;

public class BCheck
        extends JavaPlugin {
    public void onEnable() {
        File config;
        Bukkit.getServer().getPluginManager().registerEvents(new AllListener(this), this);
        this.getCommand("check").setExecutor(new FreezeCommand(this));
        File file = new File("plugins/BCheck");
        file.mkdir();
        File playersfile = new File("plugins/BCheck/logs.yml");
        if (!playersfile.exists()) {
            try {
                playersfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!(config = new File(this.getDataFolder() + File.separator + "config.yml")).exists()) {
            this.getConfig().options().copyDefaults();
            this.saveDefaultConfig();
        }
    }

    public void onDisable() {
    }
}
