package de.hglabor.commands

import de.hglabor.Bingo
import de.hglabor.core.GamePhaseManager
import de.hglabor.settings.Settings
import de.hglabor.utils.noriskutils.scoreboard.KBoard
import de.hglabor.utils.sendMsg
import de.hglabor.utils.user
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.server
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SideBoardCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        if (sender.user.kBoard == null) {
            sender.sendMsg("Scoreboard aktiviert")
            sender.sendMsg("/sideboard um das Scoreboard zu deaktivieren")
            GamePhaseManager.phase.createScoreboard(sender)
        } else {
            sender.user.kBoard = null
            sender.scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
            sender.sendMsg("Scoreboard deaktiviert")
            sender.sendMsg("/sideboard um das Scoreboard zu aktivieren")
        }
        return true
    }

    init {
        Bingo.bingo.getCommand("sideboard")?.setExecutor(this)
    }
}
