package de.hglabor.rendering

import de.hglabor.config.Config
import de.hglabor.core.GamePhaseManager
import de.hglabor.core.phase.WaitingPhase
import de.hglabor.utils.broadcast
import de.hglabor.utils.command
import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.runnables.task
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.World
import org.popcraft.chunky.ChunkyBukkit
import org.popcraft.chunky.GenerationTask
import java.util.*

class ChunkGenerator(
    private val world: World?,
    private val radius: Double = 1000.0,
    private val useWorldBorderSize: Boolean = false,
    private val checkToStartGame: Boolean = false,
) {
    private val chunky = Bukkit.getPluginManager().getPlugin("Chunky") as ChunkyBukkit
    private val generations = mutableMapOf<UUID, PregeneratedWorld>()
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
        val generationTask = chunky.chunky.generationTasks[world.name] ?: error("No Generation Task")
        generations[world.uid] = PregeneratedWorld(world, generationTask)
        task(period = 20) { runnable ->
            onlinePlayers.forEach { it.showBossBar(generations[world.uid]!!.bossBar()) }
            if (generationTask.progress.isComplete) {
                onlinePlayers.forEach { it.hideBossBar(generations[world.uid]!!.bossBar()) }
                generations.remove(world.uid)
                hasFinished = true
                runnable.cancel()
                if (checkToStartGame) {
                    if (onlinePlayers.size >= Config.playerCountToStart && GamePhaseManager.phase is WaitingPhase) {
                        TODO()
                        //TODOGamePhaseManager.startGame(20)
                    }
                }
            }
        }
    }

    data class PregeneratedWorld(val world: World, val generationTask: GenerationTask) {
        private val bossBar = BossBar.bossBar(Component.text(world.name),
            generationTask.progress.percentComplete / 100,
            BossBar.Color.GREEN,
            BossBar.Overlay.PROGRESS)

        fun bossBar(): BossBar {
            return bossBar.progress(generationTask.progress.percentComplete / 100F)
        }
    }
}
