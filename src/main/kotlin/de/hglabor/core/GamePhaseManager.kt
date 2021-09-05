package de.hglabor.core

import de.hglabor.core.phase.WaitingPhase
import net.axay.kspigot.runnables.task
import org.bukkit.entity.Player
import java.util.concurrent.atomic.AtomicInteger

object GamePhaseManager {
    var phase: GamePhase = WaitingPhase()
    private val timer: AtomicInteger = AtomicInteger()
    private var player: Player? = null

    fun run() = task(period = 20L) { phase.tick(timer.getAndIncrement()) }
    fun resetTimer() = timer.set(0)
}
