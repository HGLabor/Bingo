package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GameManager
import de.hglabor.core.GamePhase
import de.hglabor.localization.Localization
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object StartCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(sender.hasPermission("hglabor.bingo.startgame")) {
                if(GameManager.currentGamePhase != GamePhase.STARTING && !GameManager.isStarted) {
                    GameManager.startGame(10)
                } else {
                    sender.sendMessage(Localization.getMessage("bingo.gameNotStarted", sender.locale))
                }
            }
        }
        return false
    }

    init {
        Bingo.bingo.getCommand("start")?.setExecutor(this)
    }


}