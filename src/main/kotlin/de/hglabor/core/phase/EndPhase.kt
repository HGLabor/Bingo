package de.hglabor.core.phase

import de.hglabor.core.GamePhase
import de.hglabor.core.mechanics.ConnectionHandler
import de.hglabor.settings.Settings
import de.hglabor.utils.broadcast
import de.hglabor.utils.getTeam
import de.hglabor.utils.user
import net.axay.kspigot.event.listen
import net.axay.kspigot.extensions.console
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.taskRunLater
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerLoginEvent

class EndPhase(private val winner: Player?) : GamePhase() {
    init {
        onlinePlayers.forEach {
            it.allowFlight = true
            it.isFlying = true
        }
        listeners += listen<PlayerLoginEvent> { ConnectionHandler.handlePlayerLoginEvent(it) }
        listeners += listen<EntityDamageEvent> { it.isCancelled = true }
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
        broadcast(winner?.user?.checkFields.toString())
    }

    override fun nextPhase(): GamePhase {
        TODO("Not yet implemented")
    }
}
