package de.hglabor.rendering

import de.hglabor.config.Config
import de.hglabor.core.GameManager
import de.hglabor.core.GamePhase
import de.hglabor.utils.broadcast
import de.hglabor.utils.command
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import org.bukkit.Bukkit
import org.bukkit.World
import org.popcraft.chunky.ChunkyBukkit
import org.popcraft.chunky.platform.BukkitWorld

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
        if (useWorldBorderSize) {
            world.worldBorder.size = this.radius * 2
            command("chunky worldborder")
        } else {
            command("chunky radius ${this.radius}")
        }
        command("chunky start")
        val generationTask = chunky.chunky.generationTasks[BukkitWorld(world)]
        task(period = 20) {
            broadcast("Lade ${KColors.PURPLE}${world.name}: ${KColors.GOLD}${generationTask!!.progress.percentComplete.format()}% - ${KColors.GRAY}Radius: $radius")
            if (generationTask.progress.isComplete) {
                hasFinished = true;
                it.cancel()

                if (checkToStartGame) {
                    if (onlinePlayers.size >= Config.playerCountToStart && GameManager.currentGamePhase == GamePhase.WAITING) {
                        GameManager.startGame(20)
                    }
                }
            }
        }
    }

    private fun Float.format() = "%.2f".format(this).toDouble()
}
