package de.hglabor.core.phase

import de.hglabor.core.GamePhase
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.settings.Settings
import de.hglabor.utils.broadcast
import de.hglabor.utils.getTeam
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.console
import net.axay.kspigot.runnables.taskRunLater
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerLoginEvent

class EndPhase(private val winner: Player?) : GamePhase() {
    init {
        listeners += listen<PlayerLoginEvent> { ConnectionHandler.handlePlayerLoginEvent(it) }
        broadcast("Spiel ist beendet")
        broadcast("Server Restart in 10 Sekunden")
        taskRunLater(10 * 20) { Bukkit.dispatchCommand(console, "restart") }
    }

    override fun tick(tick: Int) {
        if (Settings.teams) {
            broadcast("Team[${winner?.getTeam()?.id}] hat gewonnen")
        } else {
            broadcast("${winner?.name} hat gewonnen")
        }
    }

    override fun nextPhase(): GamePhase {
        TODO("Not yet implemented")
    }
}
