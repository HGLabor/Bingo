package de.hglabor.commands;

import de.hglabor.Bingo;
import de.hglabor.utils.GamePhase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("bingo.start")) {
            if(Bingo.getManager().getGamePhase().equals(GamePhase.WAITING)) {
                Bingo.getManager().startGame();
                sender.sendMessage(Bingo.getLocalization().getPrefix(Locale.GERMAN) + "§aThe event starts now.");
            }
        } else {
            sender.sendMessage(Bingo.getLocalization().getPrefix(Locale.GERMAN) + "§cYou don't have the required permissions.");
        }
        return false;
    }
}
