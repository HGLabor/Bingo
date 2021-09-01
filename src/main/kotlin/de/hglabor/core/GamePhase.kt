package de.hglabor.core

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

abstract class GamePhase() {
    val listeners = mutableListOf<Listener>()
    abstract fun tick(tick: Int)
    abstract fun nextPhase(): GamePhase

    protected fun startNextPhase() {
        listeners.forEach { HandlerList.unregisterAll(it) }
        GamePhaseManager.phase = nextPhase()
    }
}
