package me.architetto.expholder.commands.user;

import me.architetto.expholder.commands.CommandName;
import me.architetto.expholder.commands.SubCommand;
import me.architetto.expholder.service.ExpHolderService;
import org.bukkit.entity.Player;

import java.util.List;

public class DepositCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.DEPOSIT_COMMAND;
    }

    @Override
    public String getDescription() {
        return "deposit all the experience possessed";
    }

    @Override
    public String getSyntax() {
        return "/expholder " + CommandName.DEPOSIT_COMMAND;
    }

    @Override
    public String getPermission() {
        return "expholder.deposit";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {

        ExpHolderService.getInstance().depositExp(sender);

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
