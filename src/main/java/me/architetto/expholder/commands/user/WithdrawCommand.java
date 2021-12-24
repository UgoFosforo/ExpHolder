package me.architetto.expholder.commands.user;

import me.architetto.expholder.commands.CommandName;
import me.architetto.expholder.commands.SubCommand;
import me.architetto.expholder.service.ExpHolderService;
import org.bukkit.entity.Player;

import java.util.List;

public class WithdrawCommand extends SubCommand {
    @Override
    public String getName() {
        return CommandName.WITHDRAW_COMMAND;
    }

    @Override
    public String getDescription() {
        return "withdraw all deposited experience or selected amount of levels if enough experience is stored";
    }

    @Override
    public String getSyntax() {
        return "/expholder " + CommandName.WITHDRAW_COMMAND + " [level]";
    }

    @Override
    public String getPermission() {
        return "expholder.withdraw";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {

        if (args.length == 2) {
            try {
                int level = Integer.parseInt(args[1]);
                ExpHolderService.getInstance().withdrawExp(sender,level);
            } catch (NumberFormatException e) {
                sender.sendMessage("Error: only integer value allowed");
            }
            return;
        }

        ExpHolderService.getInstance().withdrawExp(sender);

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
