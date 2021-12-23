package me.architetto.expholder.commands.admin;

import me.architetto.expholder.commands.CommandName;
import me.architetto.expholder.commands.SubCommand;
import me.architetto.expholder.service.ExpHolderService;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ListCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.LIST_COMMAND;
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String getSyntax() {
        return "null";
    }

    @Override
    public String getPermission() {
        return "expholder.list";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        HashMap<UUID,Integer> userExp = ExpHolderService.getInstance().userExpList();
        userExp.forEach((uuid, integer) -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            sender.sendMessage("name : " + offlinePlayer.getName() + " || stored exp: " + integer);
        });


    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
