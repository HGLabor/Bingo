package de.hglabor.commands

import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.WaitingPhase
import de.hglabor.localization.Localization
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs

object StartCommand {
    init {
        command("start") {
            requiresPermission("hglabor.bingo.startgame")
            runs {
                if (GamePhaseManager.phase is WaitingPhase) {
                    /*  if (
                          !(GamePhaseManager.phase as WaitingPhase).worldGenerator.hasFinished
                          &&
                          !(GamePhaseManager.phase as WaitingPhase).netherGenerator.hasFinished
                      ) {
                          player.sendMessage("Map wird noch vorgeladen")
                      } */
                    GamePhaseManager.phase.startNextPhase()
                } else {
                    player.sendMessage(Localization.getMessage("bingo.gameNotStarted", player.locale().language))
                }
            }
        }
    }
}
