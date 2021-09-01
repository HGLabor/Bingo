package de.hglabor.core

import de.hglabor.core.phase.WaitingPhase
import de.hglabor.localization.Localization
import de.hglabor.rendering.ChunkGenerator
import de.hglabor.settings.Settings
import de.hglabor.utils.broadcast
import de.hglabor.utils.checkedItems
import de.hglabor.utils.getTeam
import net.axay.kspigot.extensions.console
import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

object GamePhaseManager {
    var phase: GamePhase = WaitingPhase()
    private val timer: AtomicInteger = AtomicInteger()
    var isStarted = false
    private var isFinished = false;

    fun run() = task(period = 20L) { phase.tick(timer.getAndIncrement()) }
    fun resetTimer() = timer.set(0)

    fun endGame(player: Player) {
        isStarted = false
        if (isFinished) return
        isFinished = true
        var i = 0
        task(
            period = 20
        ) {
            i++
            if (i > 19) {
                Localization.broadcastMessage("bingo.serverIsRestarting")
                Bukkit.dispatchCommand(console, "restart")
            } else {
                if (Settings.teams) {
                    broadcast("Team[${player.getTeam()?.id}] hat gewonnen")
                } else {
                    broadcast("${player.name} hat gewonnen")
                }
            }
        }
    }

    fun posInRankingString(player: Player): String {
        val position = posInRanking(player)
        return if (position < 0) "?" else (position + 1).toString()
    }

    fun posInRanking(player: Player): Int {
        data class TempBingo(val uuid: UUID, val found: Int)

        val allPlayers = arrayListOf<TempBingo>()
        for (i in checkedItems) allPlayers.add(TempBingo(i.key.uniqueId, i.value.size))
        val sorted = allPlayers.sortedBy { it.found }
        val me = sorted.find { it.uuid == player.uniqueId }
        return sorted.indexOf(me)
    }
}
