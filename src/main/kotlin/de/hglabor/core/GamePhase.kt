package de.hglabor.core

import de.hglabor.settings.Settings
import de.hglabor.utils.ColorUtils
import de.hglabor.utils.noriskutils.scoreboard.KBoard
import de.hglabor.utils.user
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.onlinePlayers
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

abstract class GamePhase {
    val listeners = mutableListOf<Listener>()
    abstract fun tick(tick: Int)
    protected abstract fun nextPhase(): GamePhase

    fun startNextPhase() {
        listeners.forEach { HandlerList.unregisterAll(it) }
        GamePhaseManager.phase = nextPhase()
    }

    open fun createScoreboard(player: Player) {
        player.user.kBoard = KBoard(player, ColorUtils.colorInRainbowBold("BINGO"))
        player.user.kBoard?.updateLines {
            +""
            +"IP: ${KColors.AQUA}HG${KColors.RESET}Labor.de"
            +"Version: ${KColors.YELLOW}1.13 - 1.17.1"
            +"${KColors.LIGHTGRAY}${KColors.STRIKETHROUGH}------------------"
            +"Items: ${KColors.YELLOW}${Settings.itemCount}x${Settings.itemCount}"
            +"Reihen: ${KColors.YELLOW}${Settings.rowsToComplete}"
            +""
        }
    }

    open fun updateScoreboard(tick: Int) {
        onlinePlayers.forEach {
            it.user.kBoard?.updateLine(5, "Items: ${KColors.YELLOW}${Settings.itemCount}x${Settings.itemCount}")
            it.user.kBoard?.updateLine(6, "Reihen: ${KColors.YELLOW}${Settings.rowsToComplete}")
        }
    }
}
