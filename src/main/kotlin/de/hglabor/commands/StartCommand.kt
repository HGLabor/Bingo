package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.WaitingPhase
import de.hglabor.localization.Localization
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object StartCommand : CommandExecutor {
    init {
        Bingo.bingo.getCommand("start")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.hasPermission("hglabor.bingo.startgame")) {
                if (GamePhaseManager.phase is WaitingPhase) {
                  /*  if (
                        !(GamePhaseManager.phase as WaitingPhase).worldGenerator.hasFinished
                        &&
                        !(GamePhaseManager.phase as WaitingPhase).netherGenerator.hasFinished
                    ) {
                        sender.sendMessage("Map wird noch vorgeladen")
                        return true
                    } */
                    GamePhaseManager.phase.startNextPhase()
                } else {
                    sender.sendMessage(Localization.getMessage("bingo.gameNotStarted", sender.locale().displayLanguage))
                }
            }
        }
        return false
    }
}
