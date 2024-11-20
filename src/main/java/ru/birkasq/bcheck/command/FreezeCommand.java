package ru.birkasq.bcheck.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.birkasq.bcheck.BCheck;
import ru.birkasq.bcheck.utils.ColorManager;
import ru.birkasq.bcheck.utils.DataManager;

public class FreezeCommand implements CommandExecutor, Listener {
    public static List<UUID> freezedPlayers = new ArrayList<>();
    private int SchedulerCheck;
    private BCheck plugin;
    FileConfiguration config;
    File playersfile = new File("plugins/BCheck/logs.yml");
    FileConfiguration players = YamlConfiguration.loadConfiguration(this.playersfile);
    List<String> list = this.players.getStringList("checks");

    public FreezeCommand(BCheck plugin) {
        this.plugin = plugin;
    }

    private void executeCommands(List<String> commands, String suspectName) {
        for (String command : commands) {
            command = command.replace("%suspect%", suspectName);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public boolean onCommand(CommandSender s, Command cmd, String l, String[] args) {
        if (cmd.getName().equalsIgnoreCase("check")) {
            if (!(s instanceof Player)) {
                s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("only-player")));
                return true;
            }
            if (args.length != 1) {
                s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("freeze-args")));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            String targetName = args[0];
            String[] title = new String[]{ColorManager.toString(this.plugin.getConfig().getString("title-check-sms-start").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName), ColorManager.toString(this.plugin.getConfig().getString("subtitle-check-sms-start").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName)};
            String[] title_2 = new String[]{ColorManager.toString(this.plugin.getConfig().getString("title-check-sms-stop").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName), ColorManager.toString(this.plugin.getConfig().getString("subtitle-check-sms-stop").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName)};
            Integer[] delay_freeze_message = new Integer[]{this.plugin.getConfig().getInt("delay-freeze-message")};
            if (!s.hasPermission("bcheck.use") && !s.hasPermission("bcheck.admin")) {
                s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("no-permission")));
                return true;
            }
            if (target != null) {
                if (freezedPlayers.contains(target.getUniqueId())) {
                    freezedPlayers.remove(target.getUniqueId());
                    if (this.plugin.getConfig().getBoolean("sound-freeze.stop")) {
                        target.playSound(target.getLocation(), Sound.ITEM_TOTEM_USE, 25.0f, 25.0f);
                    }
                    s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("check-sms-moder-stop").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName));
                    target.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("check-sms-off").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName));
                    target.sendTitle(title_2[0], title_2[1], 25, 45, 25);
                    target.setHealth(20.0);
                    target.setFoodLevel(20);
                    target.setRemainingAir(300);
                    target.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("remaining")).replaceAll("%suspect%", targetName).replaceAll("%executor%", s.getName()));
                    Bukkit.getScheduler().cancelTask(this.SchedulerCheck);
                    List<String> stopCommands = this.plugin.getConfig().getStringList("commands.on-freeze-stop");
                    executeCommands(stopCommands, targetName);
                    return true;
                }

                freezedPlayers.add(target.getUniqueId());
                s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("check-sms-moder-start").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName));
                target.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("check-sms").replaceAll("%executor%", s.getName())).replaceAll("%suspect%", targetName));

                if (this.plugin.getConfig().getBoolean("sound-freeze.start")) {
                    target.playSound(target.getLocation(), Sound.ITEM_TOTEM_USE, 25.0f, 25.0f);
                }
                target.sendTitle(title[0], title[1], 25, 45, 25);

                Boolean add_logs_file = this.plugin.getConfig().getBoolean("logs.add-logs-file");
                if (add_logs_file) {
                    if (this.list.isEmpty()) {
                        this.list = new ArrayList<>();
                    }
                    this.list.add("");
                    this.list.add("Проверяющий: " + s.getName());
                    this.list.add("Подозреваемый: " + targetName);
                    this.list.add("Дата: " + DataManager.date);
                    this.list.add("");
                    this.players.set("checks", this.list);
                    try {
                        this.players.save(this.playersfile);
                    } catch (IOException var14) {
                        var14.printStackTrace();
                    }
                } else {
                    s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("logs.logs-false-sms")));
                }
                List<String> startCommands = this.plugin.getConfig().getStringList("commands.on-freeze-start");
                executeCommands(startCommands, targetName);
                this.SchedulerCheck = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
                    for (String message : this.plugin.getConfig().getStringList("check-sms-move-player")) {
                        target.sendMessage(ColorManager.toString(message.replace("%suspect%", targetName)).replaceAll("%executor%", s.getName()));
                    }
                }, 0L, delay_freeze_message[0]);
            } else {
                s.sendMessage(ColorManager.toString(this.plugin.getConfig().getString("not-online").replace("%executor%", s.getName())).replace("%suspect%", targetName));
                return true;
            }
        }
        return true;
    }
}
