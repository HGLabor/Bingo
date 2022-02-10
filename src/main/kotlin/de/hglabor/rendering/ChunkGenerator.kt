package de.hglabor.rendering

import de.hglabor.config.Config
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.WaitingPhase
import de.hglabor.utils.broadcast
import de.hglabor.utils.command
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.World
import org.popcraft.chunky.ChunkyBukkit

class ChunkGenerator(
    private val world: World?,
    private val radius: Double = 1000.0,
    private val useWorldBorderSize: Boolean = false,
    private val checkToStartGame: Boolean = false,
) {
    private val chunky = Bukkit.getPluginManager().getPlugin("Chunky") as ChunkyBukkit
    var hasFinished: Boolean = false

    fun pregenerate() {
        if (world == null) {
            broadcast("World is null, cannot pregenerate map")
            return
        }
        command("chunky world ${world.name}")
        command("chunky center 0 0")
        if (useWorldBorderSize) {
            world.worldBorder.size = this.radius * 2
            command("chunky worldborder")
        } else {
            command("chunky radius ${this.radius}")
        }
        command("chunky start")
        val generationTask = chunky.chunky.generationTasks[world.name]
        task(period = 20) {
            broadcast("Lade ${KColors.GOLD}${world.name}: ${generationTask!!.progress.percentComplete.format()}% - Radius: $radius")
            if (generationTask.progress.isComplete) {
                hasFinished = true
                it.cancel()

                if (checkToStartGame) {
                    if (onlinePlayers.size >= Config.playerCountToStart && GamePhaseManager.phase is WaitingPhase) {
                        TODO()
                        //TODOGamePhaseManager.startGame(20)
                    }
                }
            }
        }
    }

    private fun Float.format() = "%.2f".format(this).toDouble()
}
