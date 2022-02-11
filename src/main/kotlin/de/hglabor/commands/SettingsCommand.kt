package de.hglabor.commands

import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.settings.SettingsGUI
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.requiresPermission
import net.axay.kspigot.commands.runs
import net.axay.kspigot.gui.openGUI

object SettingsCommand {
    init {
        command("settings") {
            requiresPermission("hglabor.bingo.settings")
            runs {
                if (GamePhaseManager.phase is InGamePhase && !player.isOp) {
                    player.sendMessage("§cDas Spiel hat schon begonnen. Du kannst Einstellungen nur in der Lobby bearbeiten.")
                } else {
                    player.openGUI(SettingsGUI().gui)
                }
            }
        }
    }
}
