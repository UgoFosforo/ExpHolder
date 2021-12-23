package me.architetto.expholder;

import me.architetto.expholder.commands.CommandManager;
import me.architetto.expholder.config.ConfigManager;
import me.architetto.expholder.service.ExpHolderService;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ExpHolder extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        ConfigManager.getInstance().setPlugin(plugin);
        Objects.requireNonNull(getCommand("expholder")).setExecutor(new CommandManager());
        ExpHolderService.getInstance().loadData();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ExpHolderService.getInstance().saveData();
    }
}
