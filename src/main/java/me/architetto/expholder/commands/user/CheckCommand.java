package me.architetto.expholder.commands.user;

import me.architetto.expholder.commands.CommandName;
import me.architetto.expholder.commands.SubCommand;
import me.architetto.expholder.service.ExpHolderService;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CheckCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.CHECK_COMMAND;
    }

    @Override
    public String getDescription() {
        return "check amount of experience deposited";
    }

    @Override
    public String getSyntax() {
        return "/expholder " + CommandName.CHECK_COMMAND;
    }

    @Override
    public String getPermission() {
        return "null";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        HashMap<UUID,Integer> expList = ExpHolderService.getInstance().userExpList();
        Integer expStored = expList.get(sender.getUniqueId());
        if (expStored == null) {
            sender.sendMessage("You have no experience deposited");
            return;
        }
        sender.sendMessage("You have " + expStored + " experience deposited");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
