package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.InGamePhase
import de.hglabor.settings.SettingsGUI
import net.axay.kspigot.gui.openGUI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SettingsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.hasPermission("hglabor.bingo.settings") && sender is Player) {
            if (GamePhaseManager.phase is InGamePhase && !sender.isOp) {
                sender.sendMessage("§cDas Spiel hat schon begonnen. Du kannst Einstellungen nur in der Lobby bearbeiten.")
            } else {
                sender.openGUI(SettingsGUI().gui)
            }
        }
        return true
    }

    init {
        Bingo.bingo.getCommand("settings")?.setExecutor(this)
    }
}
