package me.architetto.expholder.service;

import me.architetto.expholder.config.ConfigManager;
import me.architetto.expholder.util.ExpUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ExpHolderService {

    private static ExpHolderService instance;

    private final HashMap<UUID, Integer> userExp;

    private ExpHolderService() {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        this.userExp = new HashMap<>();
    }

    public static ExpHolderService getInstance() {
        if (instance == null)
            instance = new ExpHolderService();
        return instance;
    }

    //take exp from player
    public void depositExp(Player player) {
        if (player.getLevel() == 0 && player.getExp() == 0f)
            return;

        int expToStore = ExpUtil.getPlayerExp(player);
        this.userExp.merge(player.getUniqueId(), expToStore, Integer::sum);
        player.setExp(0f);
        player.setLevel(0);
        player.sendMessage(ChatColor.YELLOW + "You have deposited "
                + ChatColor.AQUA + expToStore + ChatColor.YELLOW + " experience unit");
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_CLOSE,1,1);
    }

    //give back player's exp
    public void withdrawExp(Player player) {
        Integer storedExp = this.userExp.get(player.getUniqueId());
        if (storedExp == null) {
            player.sendMessage(ChatColor.RED + "Error: " + ChatColor.RESET + " you have no experience deposited");
            return;
        }
        player.giveExp(storedExp);
        player.sendMessage(ChatColor.YELLOW + "You have withdrawn " + ChatColor.AQUA + storedExp
                + ChatColor.YELLOW + " experience unit");
        this.userExp.remove(player.getUniqueId());
    }

    public void withdrawExp(Player player, int level) {
        Integer storedExp = this.userExp.get(player.getUniqueId());
        if (storedExp == null) {
            player.sendMessage(ChatColor.RED + "Error: " + ChatColor.RESET + " you have no experience deposited");
            return;
        }

        int expAtLevel = ExpUtil.getExpAtLevel(level);

        if (storedExp < expAtLevel) {
            player.sendMessage(ChatColor.RED + "Error: " + ChatColor.RESET
                    + " not enough experience deposited to reach level " + level);
            return;
        }
        player.giveExp(expAtLevel);
        player.sendMessage(ChatColor.YELLOW + "You have withdrawn " + ChatColor.AQUA + expAtLevel
                + ChatColor.YELLOW + " experience");
        this.userExp.put(player.getUniqueId(),storedExp - expAtLevel);
    }

    public HashMap<UUID,Integer> userExpList() {
        return this.userExp;
    }

    public void loadData() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[ExpHolder]"
                + ChatColor.RESET +  " Load expHolder data...");

        ConfigManager configManager = ConfigManager.getInstance();

        ConfigurationSection configurationSection = configManager.getConfig("data.yml")
                .getConfigurationSection("");

        if (configurationSection == null)
            return;

        for (String uuid : configurationSection.getKeys(false)) {
            this.userExp
                    .put(UUID.fromString(uuid),configManager.getInt(configManager.getConfig("data.yml"),uuid));
        }

    }

    public void saveData() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[ExpHolder]"
                + ChatColor.RESET + " Save expHolder data...");
        ConfigManager cm = ConfigManager.getInstance();
        cm.getConfig("data.yml")
                .getKeys(false).forEach(key -> cm.setData(cm.getConfig("data.yml"),key,null));
        this.userExp.forEach((uuid, integer) ->
                cm.setData(cm.getConfig("data.yml"),uuid.toString(),integer));
    }

}
